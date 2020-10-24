package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import Lógica.Celda;
import Lógica.Sudoku;
import Lógica.GraficaReloj;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GUITest extends JFrame {

	private JPanel contentPane;
	private Sudoku sudoku;
	private GraficaReloj reloj;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUITest frame = new GUITest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUITest() {
		
		//Inicializa el panel principal.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(255, 228, 225)));
		setContentPane(contentPane);
		
		contentPane.setLayout(new BorderLayout());	
		
		//Inicializa los paneles que conforman el panel principal.
		JPanel panel_superior = new JPanel();
		panel_superior.setBackground(new Color(255, 228, 225));
		panel_superior.setLayout(new FlowLayout());
		contentPane.add(panel_superior, BorderLayout.NORTH);
		
		JPanel panel_inferior = new JPanel();
		panel_inferior.setBackground(new Color(255, 228, 225));
		contentPane.add(panel_inferior, BorderLayout.SOUTH);
		
		//Inicializa un label con el nombre del juego
		JLabel nombreJuego = new JLabel("SUDOKU");
		nombreJuego.setHorizontalAlignment(SwingConstants.LEFT);
		nombreJuego.setForeground(new Color(255, 20, 147));
		nombreJuego.setFont(new Font("Goudy Old Style", Font.PLAIN, 50));
		panel_superior.add(nombreJuego);
		
		//Inicializa un botón que será utilizado para verificar que las celdas con imagen no estén rompiendo las reglas del Sudoku.
		JButton verificador = new JButton("VERIFICAR SUDOKU");
		verificador.setForeground(new Color(255, 20, 147));
		verificador.setBackground(new Color(255, 228, 225));
		verificador.setFont(new Font("Goudy Old Style", Font.PLAIN, 25));
		verificador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Primero pone todas las celdas como válidas así se elimina lo que estaba mal antes.
				sudoku.todosValidos();
				//En ganoPartida, antes de verificar que el tablero esté completo se verifica que lo que contiene sea válido,
				//por lo que marca las celdas que no están ubicadas correctamente.
				if ( sudoku.ganoPartida() ) {
					//En caso de que se gane la partida se muestra un mensaje indicándolo.
					verificador.setEnabled(false);
					JOptionPane.showMessageDialog(null, "¡FELICITACIONES!\nGanaste!!");
				}
			}
		});
		
		//Inicializa un botón que se utiliza para comenzar la partida.
		JButton comenzar = new JButton("COMENZAR");
		comenzar.setForeground(new Color(255, 20, 147));
		comenzar.setBackground(new Color(255, 228, 225));
		comenzar.setFont(new Font("Goudy Old Style", Font.PLAIN, 25));
		comenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Se inicializa el panel del Sudoku, puede ocurrir un error e interrumpir su ejecución.
				inicializarSudoku();
				//Se inicializa el panel del reloj.
				inicializarReloj(panel_superior);
				comenzar.setEnabled(false);
				panel_inferior.add(verificador);
			}
		});
		panel_inferior.add(comenzar);
		
	}
	
	/**
	 * Inicializa el panel del Sudoku.
	 * @return boolean Indica si el sudoku pudo ser inicializado correctamente.
	 */
	private void inicializarSudoku() {
		
		sudoku = new Sudoku("ARCHIVO/Resolucion.txt");
		
		//En caso de que no se haya podido inicializar el sudoku se mostrará un mensaje de ERROR y se cerrará el juego.
		if ( !sudoku.getInicioCorrecto() ) {
			JOptionPane.showMessageDialog(null, "El juego no pudo ser iniciado correctamente.", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		//Se muestran las instrucciones del Sudoku.
		JOptionPane.showMessageDialog(null, "El juego comienza inicializando un tablero de " + sudoku.getCantFilas() + "x" + sudoku.getCantFilas() + 
				".\nExisten 9 personajes y para completarlo se debe hacer click sobre cada una de las celdas hasta obtener el deseado."
				+ "\nLas celdas que no pueden ser modificadas aparecen con fondo de color."
				+ "\nNo puede haber personajes repetidos en una misma fila, columna o panel.\r\n"
				+ "Gana el juego cuando se completan todas las celdas correctamente"
				, "INSTRUCCIONES", JOptionPane.INFORMATION_MESSAGE);
		
		int cantFilasCuadrante = sudoku.getCantFilasCuadrante();
		
		//Inicializa el panel donde se muestra el Sudoku.
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(cantFilasCuadrante, 0, 0, 0));
		contentPane.add(panel, BorderLayout.CENTER); 
	
		//Se crea la matriz de paneles para los cuadrantes.
		JPanel[][] paneles = new JPanel[cantFilasCuadrante][cantFilasCuadrante];

		//Inicializa la matriz de paneles y cada uno contendrá labels para cada celda.
		for (int i = 0; i < cantFilasCuadrante; i++){
			for (int j = 0; j < cantFilasCuadrante; j++){
				paneles[i][j] = new JPanel();
				paneles[i][j].setLayout(new GridLayout(sudoku.getCantFilasCuadrante(), 0, 0, 0));
				//Acomoda los bordes de los paneles para que se pueda reconocer cada cuadrante.
				if ( ( i == 0 || i == cantFilasCuadrante-1 ) && j != 0 && j != cantFilasCuadrante-1 )
					paneles[i][j].setBorder(new MatteBorder(5, 0, 5, 0, new Color(255, 192, 203)));
				else {
					if ( ( i == 0 || i == cantFilasCuadrante-1 ) && ( j == 0 || j == cantFilasCuadrante-1) )
						paneles[i][j].setBorder(new MatteBorder(5, 5, 5, 5, new Color(255, 192, 203)));
					else {
						if ( j == 0 || j == cantFilasCuadrante-1 )
							paneles[i][j].setBorder(new MatteBorder(0, 5, 0, 5, new Color(255, 192, 203)));
					}
				}
			}
		}

		for (int i = 0; i < sudoku.getCantFilas(); i++){
			int m = (int) i / cantFilasCuadrante;
			for (int j = 0; j < sudoku.getCantFilas(); j++){
				int n = (int) j / cantFilasCuadrante;
				//Se inicializa cada celda del sudoku y es ubicada en el panel correpondiente.
				Celda c = sudoku.getCelda(i, j);
				ImageIcon grafico = c.getEntidadGrafica().getGrafico();
				JLabel label = c.getEntidadGrafica().getLabel();
				paneles[m][n].add(label);
				label.setBorder(new LineBorder(new Color(255, 192, 203)));
				panel.add(paneles[m][n]);
				
				//Acomoda el grafico de cada celda al tamaño del JLabel.
				label.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						reDimensionar(label, grafico);
						label.setIcon(grafico);
					}
				});		
				
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//Cada celda del sudoku se modifica haciendo click sobre ella, en caso de ser posible.
						sudoku.accionar(c);
						reDimensionar(label, grafico);	
					}
				});
			}
		}
	
	}
	
	/**
	 * Inicializa el panel del reloj en panel.
	 * @param panel JPanel donde ubicar el reloj.
	 */
	private void inicializarReloj(JPanel panel) {
		
		reloj = new GraficaReloj();
		
		//Comienza a ejecutarse el reloj.
		sudoku.iniciarTareaReloj(reloj);
		
		JPanel panel_reloj = new JPanel();
		panel_reloj.setBackground(new Color(255, 228, 225));
		panel_reloj.setLayout(new GridLayout());
		panel_reloj.setPreferredSize(new Dimension(500, 70));

		//Agrega cada uno de los labels del gráfico del reloj al panel.
		JLabel[] labels = reloj.getGrafico();
		for ( int i = 0; i < labels.length; i++ ) {
			panel_reloj.add(labels[i]);
		}

		panel.add(panel_reloj);
		
	}
	
	/**
	 * Redimensiona la imagen del gráfico y la ubica en el label.
	 * @param label JLabel donde poner la imagen.
	 * @param grafico ImageIcon del que hay que redimensionar la imagen.
	 */
	private void reDimensionar(JLabel label, ImageIcon grafico) {
	
		Image image = grafico.getImage();
		if (image != null) {  
			Image newimg = image.getScaledInstance(label.getWidth(), label.getHeight(), java.awt.Image.SCALE_SMOOTH);
			grafico.setImage(newimg);
			label.repaint();
			
		}
	}	

}
