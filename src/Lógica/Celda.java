package Lógica;

public class Celda {

	private Integer valor;
	private EntidadGrafica entidadGrafica;
	
	/**
	 * Constructor que inicializa la celda como vacía.
	 */
	public Celda() {
		valor = 0;
		entidadGrafica = new EntidadGrafica();
	}
	
	/**
	 * Actualiza el valor de la celda.
	 */
	public void actualizar() {
		
		//Sólo actualiza la entidad gráfica si es una de las celdas editables.
		if ( entidadGrafica.getEditable() ) {
			if ( (this.valor+1) <= getCantElementos()) {
				this.valor++;
				entidadGrafica.actualizar(this.valor);
			}
			else {
				this.valor = 1;
			}
		}
		entidadGrafica.actualizar(this.valor);
		
	}
	
	/**
	 * Retorna la cantidad de elementos de la entidad gráfica de la celda. 
	 * @return int longitud de las imagenes de la entidad gráfica.
	 */
	public int getCantElementos() {
		
		return this.entidadGrafica.getImagenes().length;
		
	}
	
	/*---------------------------SETTERS Y GETTERS DE LOS ATRIBUTOS---------------------------*/
	
	/**
	 * Retorna el valor de la celda.
	 * @return valor Integer de la Celda.
	 */
	public Integer getValor() {
		
		return this.valor;
		
	}
	
	/**
	 * Asigna valor al valor de la celda y actualiza la entidad gráfica con ese valor.
	 * @param valor Integer a asignar.
	 */
	public void setValor(Integer valor) {
		if (valor != null && valor <= this.getCantElementos()) {
			this.valor = valor;
			this.entidadGrafica.actualizar(this.valor);
		}
		else {
			this.valor = 0;
			this.entidadGrafica.actualizar(this.valor);
		}
	}
	
	/**
	 * Retorna la entidad gráfica de la celda.
	 * @return entidadGrafica EntidadGrafica de la Celda.
	 */
	public EntidadGrafica getEntidadGrafica() {
		
		return this.entidadGrafica;
	
	}
	
	/**
	 * Asigna e a la entidad gráfica de la celda.
	 * @param e EntidadGrafica a asignar.
	 */
	public void setEntidadGrafica(EntidadGrafica e) {
		
		this.entidadGrafica = e;
	
	}
	
}
 