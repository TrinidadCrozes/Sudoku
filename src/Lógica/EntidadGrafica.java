package Lógica;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class EntidadGrafica {

	private ImageIcon grafico;
	private boolean editable;
	private JLabel label;
	private String[] imagenes;
	
	/**
	 * Constructor que inicializa la entidad gráfica vacía.
	 */
	public EntidadGrafica() {
		grafico = new ImageIcon();
		editable = false;
		label = new JLabel();
		//Los labels con fondo no van a poder ser modificados, inicializa todos con fondo y cuando son eliminados, se cambian.
		label.setOpaque(true);
		label.setBackground(Color.PINK);
		imagenes = new String[]{"/IMG/uno.png", "/IMG/dos.png", "/IMG/tres.png", "/IMG/cuatro.png", "/IMG/cinco.png", "/IMG/seis.png", "/IMG/siete.png", "/IMG/ocho.png", "/IMG/nueve.png"};
	}
	
	/**
	 * Actualiza los valores de la entidad gráfica con el valor pasado como parámetro.
	 * @param indice Integer que inidica cómo se debe actualizar la entidad gráfica.
	 */
	public void actualizar( Integer indice ) {
		//Si indice es 0, entonces se inicializa como una celda que puede ser modificada.
		if ( indice == 0 ) {
			grafico = new ImageIcon();
			editable = true;
			//Al poder ser modificada no va a tener fondo.
			label.setOpaque(false);
		}
		else {
			if ( indice <= imagenes.length ) {
				ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(this.imagenes[indice-1]));
				grafico.setImage(imageIcon.getImage());
			}
		}
	}

	/**
	 * Indica gráficamente que lo que contiene el JLabel es erróneo.
	 */
	public void error() {
		label.setBorder(new LineBorder(Color.red, 2));
	}
	
	/**
	 * Indica gráficamente que lo que contiene el JLabel es correcto.
	 */
	public void correcto() {
		label.setBorder(new LineBorder(new Color(255, 192, 203)));
	}
	
	/*---------------------------SETTERS Y GETTERS DE LOS ATRIBUTOS---------------------------*/
	
	/**
	 * Retorna el gráfico de la entidad gráfica.
	 * @return gráfico ImageIcon de la EntidadGrafica.
	 */
	public ImageIcon getGrafico() {
		return this.grafico;
	}
	
	/**
	 * Asigna grafico al gráfico de la entidad gráfica.
	 * @param grafico ImageIcon a asignar.
	 */
	public void setGrafico(ImageIcon grafico) {
		this.grafico = grafico;
	}
	
	/**
	 * Retorna el label de la entidad gráfica.
	 * @return label JLabel de la EntidadGrafica.
	 */
	public JLabel getLabel() {
		return this.label;
	}
	
	/**
	 * Asigna l al label de la entidad gráfica.
	 * @param l JLabel a asignar.
	 */
	public void setLabel(JLabel l) {
		this.label = l;
	}
	
	/**
	 * Retorna el arreglo de imagenes de la entidad gráfica.
	 * @return imagenes String[] de la EntidadGrafica.
	 */
	public String[] getImagenes() {
		return this.imagenes;
	}
	
	/**
	 * Asigna imagenes a las imagenes de la entidad gráfica.
	 * @param imagenes String[] a asignar.
	 */
	public void setImagenes(String[] imagenes) {
		this.imagenes = imagenes;
	}
	
	/**
	 * Retorna el valor de editable de la entidad gráfica.
	 * @return editable boolean de la EntidadGrafica.
	 */
	public boolean getEditable() {
		return this.editable;
	}
	
	/**
	 * Asigna b al editable de la entidad gráfica.
	 * @param b boolean a asignar.
	 */
	public void setEditable(boolean b) {
		this.editable = b;
	}

} 
