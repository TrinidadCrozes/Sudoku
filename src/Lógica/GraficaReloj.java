package Lógica;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GraficaReloj {
	
	private String[] imagenes;
	private JLabel[] grafico;
	
	/**
	 * Constructor de la gráfica del reloj que lo inicializa como vacío.
	 */
	public GraficaReloj() {
		
		imagenes = new String[]{"/IMG/num0.png", "/IMG/num1.png", "/IMG/num2.png", "/IMG/num3.png", "/IMG/num4.png", "/IMG/num5.png", "/IMG/num6.png", "/IMG/num7.png", "/IMG/num8.png", "/IMG/num9.png"};
		grafico = new JLabel[8];
		for ( int i = 0; i < grafico.length; i++ ) {
			grafico[i] = new JLabel();
			grafico[i].setPreferredSize(new Dimension(60, 60));
		}
		
	}
	
	/**
	 * Actualiza los labels del gráfico a partir de time.
	 * @param time String que contiene los caracteres con los que hay que actualizar el tiempo.
	 */
	public void actualizar(String time) {		
		
		for ( int i = 0 ; i < time.length(); i++ ) {	
			//Si el caracter no es :, entonces es un número y con este se actualizan los valores del reloj.
			if ( time.charAt(i) != ':' ) {
				ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(imagenes[Character.getNumericValue(time.charAt(i))]));
				Image img = imageIcon.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH );
				imageIcon.setImage(img);
				grafico[i].setIcon(imageIcon);
				grafico[i].repaint();
			}
			else {
				//Si es :, entonces dejo esa imagen.
				ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/IMG/dosPuntos.png"));
				Image img = imageIcon.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH );
				imageIcon.setImage(img);
				grafico[i].setIcon(imageIcon);
				grafico[i].repaint();				
			}
			
		}
		
	}
	
	/*---------------------------SETTERS Y GETTERS DE LOS ATRIBUTOS---------------------------*/
	
	/**
	 * Retorna las imagenes de la GraficaReloj.
	 * @return imagenes String[] de la GraficaReloj.
	 */
	public String[] getImagenes() {
		
		return this.imagenes;
		
	}
	
	/**
	 * Asigna imgs a las imagenes de la GraficaReloj.
	 * @param imgs String[] a asignar.
	 */
	public void setImagenes(String[] imgs) {
		
		this.imagenes = imgs;
		
	}

	/**
	 * Retorna el arreglo de JLabel que conforman gráfico de la GraficaReloj.
	 * @return grafico JLabel[] de la GraficaReloj.
	 */
	public JLabel[] getGrafico() {
		
		return this.grafico;
		
	}
	
	/**
	 * Asigna labels al gráfico de la GraficaReloj.
	 * @param labels JLabel[] a asignar.
	 */
	public void setGrafico(JLabel[] labels) {
		
		this.grafico = labels;
		
	}

}
