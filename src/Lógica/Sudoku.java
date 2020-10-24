package Lógica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Timer;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sudoku {

	private Celda[][] tablero;
	private int cantFilas;
	private int cantFilasCuadrante;
	private TareaReloj task;
	private boolean inicioCorrecto;
	private static Logger logger;
	
	/**
	 * Constructor del Sudoku que lo inicializa a partir de un archivo.
	 * @param path Ruta del archivo.
	 */
	public Sudoku(String path) {
		//Inicializa cada uno de los atributos del Sudoku.
		cantFilas = 9;
		cantFilasCuadrante = (int)Math.sqrt(cantFilas);
		tablero = new Celda[cantFilas][cantFilas];
		inicioCorrecto = true;
		//La TareaReloj será inicializada como nula debido a que se inicializa a partir de inicializarTareaReloj con cierta gráfica.
		this.task = null;
		
		int cantEliminados = 0;
		//La cantidad de celdas que se quiere eliminar debe ser un número entre 0 y cantFilas*cantFilas.
		int quieroEliminar = (cantFilas*cantFilas)-30;	
		
		//Inicializa el logger a utilizar.
		if (logger == null){
			
			logger = Logger.getLogger(Sudoku.class.getName());
			
			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.ALL);
			logger.addHandler(hnd);
			
			logger.setLevel(Level.ALL);
			
			Logger rootLogger = logger.getParent();
			for (Handler h : rootLogger.getHandlers()){
				h.setLevel(Level.OFF);
			}
		}
		
		//Primero se lee el archivo y si es posible se inicializa el tablero con sus valores.
		inicioCorrecto = leerArchivo(path);
		
		if ( inicioCorrecto ) {
			
			//Se verifica que la solución que contiene el tablero sea válida.
			inicioCorrecto = solucionValida();
		
			if ( inicioCorrecto ) {
				
				//Elimina aleatoriamente la cantidad de elementos del tablero determinada por quieroEliminar.
				while ( cantEliminados < quieroEliminar ) {
					Random rand = new Random();
					int valueF = rand.nextInt(cantFilas); 
					int valueC = rand.nextInt(cantFilas); 
					//Elimina el valor de la celda en la fila y columna calculados aleatoriamente.
					if ( tablero[valueF][valueC].getValor() != 0 ) {
						tablero[valueF][valueC].setValor(0);
						cantEliminados++;
					}
				}	
			} 
			//En caso de que no se haya podido inicializar correctamente el Sudoku se detendrá la ejecución, por lo que se indica con un logger severe.
			else { 
				logger.severe("La solución que contiene el archivo no es válida.");
			}
	
		}
		
		else {
			logger.severe("El archivo no tiene el formato esperado.");
		}

	}
	
	/**
	 * Inicializa el tablero de Sudoku con los valores leídos del archivo.
	 * @param path Ruta del archivo.
	 * @return boolean true si el archivo pudo ser leído correctamente, false caso contrario.
	 */
	private boolean leerArchivo(String path) {
		
		inicioCorrecto = true;
		String linea;
		String[] numeros;
		int fila = 0;
		Integer agregar = null;
		
		try {
			
			InputStream in = Sudoku.class.getClassLoader().getResourceAsStream(path);
            InputStreamReader inr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(inr);
			
			//Cada línea del archivo debe cumplir con cierto formato:
			while( (linea = br.readLine()) != null && inicioCorrecto ) {
				//Debe ser un dígito separado por un espacio, por lo que su longitud será el doble de la cantidad de filas.
				if ( linea.length() != 2*cantFilas )
					inicioCorrecto = false;
				else {
					numeros = linea.split( " " ); 
					if ( fila < cantFilas ) {
						//Debe tener igual cantidad números que la cantidad de filas del tablero;
						if ( numeros.length == cantFilas ) {
							//Cada una de los números debe cumplir:
							for ( int i = 0; i < numeros.length; i++ ) {
								//Debe ser un sólo caracter;
								if ( numeros[i].length() == 1 ) {
									//Debe ser un dígito;
									if ( !Character.isDigit(numeros[i].charAt(0)) ) {
										inicioCorrecto = false;
									}
									else {
										agregar = Integer.parseInt(numeros[i]);
										//Debe estar entre 1 y 9.
										if ( agregar >= 1 && agregar <= 9 ) {
											tablero[fila][i] = new Celda();
											tablero[fila][i].setValor(agregar);
										}
										//En caso de que no se cumplan las consideraciones anteriores es un archivo inválido.
										else {
											inicioCorrecto = false;
										}
									}
								}
								else {
									inicioCorrecto = false;
								}
							}
						}
						else {
							inicioCorrecto = false;
						}
					}
					else {
						inicioCorrecto = false;
					}
					fila++;
				}
			}
			//La cantidad de filas leídas debe ser igual a la cantidad de filas del tablero.
			if ( fila != cantFilas )
				inicioCorrecto = false;
			
			br.close();
			
		}
		catch( IOException err ) {
			logger.severe("Hubo un error al leer el archivo");
		}
		catch( NumberFormatException err ) {
			logger.severe("Hubo un error al leer el archivo");
		}
		
		return inicioCorrecto;
		
	}
	
	
	/*---------------------------VERIFICACIÓN DEL SUDOKU---------------------------*/
	
	
	/**
	 * Decide si la solución del tablero es una solución sudoku válida.
	 * @return boolean true si es solución válida, false caso contrario.
	 */
	public boolean solucionValida() {
		 
		boolean es = true;
		
		//Por cada celda recorro su fila, columna y cuadrante y lo verifico.
		for ( int i = 0; i < cantFilas; i++ ) {
			for ( int j = 0; j < cantFilas; j++ ) {
				if ( es )
					es = validarFila(i, j) && validarColumna(i, j) && validarCuadrante(i, j);
				else {
					validarFila(i, j);
					validarColumna(i, j);
					validarCuadrante(i, j);
				}
			}
		}
		
		return es;
		
	}
	
	/**
	 * Valida los valores de la fila f del tablero.
	 * @param f Fila del tablero.
	 * @param c Columna del tablero.
	 * @return boolean true si es fila válida, false caso contrario.
	 */
	public boolean validarFila(int f, int c) {
		
		boolean es = true;
		
		//Se valida sólo si la celda tiene una imagen.
		if ( tablero[f][c].getValor() != 0 ) {
			for ( int i = 0; i < cantFilas; i++ ) {
				//Recorre la fila f y marca las celdas como erróneas en caso de que su valor sea el mismo que el de la celda en f y c.
				if ( c != i && tablero[f][i].getValor() == tablero[f][c].getValor() ) {
					es = false;
					tablero[f][c].getEntidadGrafica().error();
					tablero[f][i].getEntidadGrafica().error();
				}
			}
		}
	
		return es;
	}
	
	/**
	 * Valida los valores de la columna c del tablero.
	 * @param f Fila del tablero.
	 * @param c Columna del tablero.
	 * @return boolean true si es columna válida, false caso contrario.
	 */
	public boolean validarColumna(int f, int c) {
		
		boolean es = true;
		
		//Se valida sólo si la celda tiene una imagen.
		if ( tablero[f][c].getValor() != 0 ) {
			for ( int i = 0; i < cantFilas; i++ ) {
				//Recorre la columna c y marca las celdas como erróneas en caso de que su valor sea el mismo que el de la celda en f y c.
				if ( f != i && tablero[i][c].getValor() == tablero[f][c].getValor() ) {
					es = false;
					tablero[f][c].getEntidadGrafica().error();
					tablero[i][c].getEntidadGrafica().error();
				}
			}
		}
		
		return es;
	}
	
	/**
	 * Valida los valores del cuadrante en el que se encuentran f y c en el tablero.
	 * @param f Fila del tablero.
	 * @param c Columna del tablero.
	 * @return boolean true si es cuadrante válida, false caso contrario.
	 */
	public boolean validarCuadrante(int f, int c) {
		
		boolean es = true;
		int x = ((int) f / cantFilasCuadrante) * cantFilasCuadrante;
		int y = ((int) c / cantFilasCuadrante) * cantFilasCuadrante;		
		
		//Se valida sólo si la celda tiene una imagen.
		if ( tablero[f][c].getValor() != 0 ) {
			for ( int i = 0; i < cantFilasCuadrante; i++ ) {
				for ( int j = 0; j < cantFilasCuadrante; j++ ) {
					//Recorre el cuadrante y marca las celdas como erróneas en caso de que su valor sea el mismo que el de la celda en f y c.
					if ( f != (i+x) && c != (j+y) && tablero[i+x][j+y].getValor() == tablero[f][c].getValor() ) {
						es = false;
						tablero[f][c].getEntidadGrafica().error();
						tablero[i+x][j+y].getEntidadGrafica().error();
					}
				}
			}
		}
				
		return es;

	}	
	
	/*---------------------------SETTERS Y GETTERS DE LOS ATRIBUTOS---------------------------*/
	
	/**
	 * Devuelve task del Sudoku.
	 * @return task TareaReloj del Sudoku.
	 */
	public TareaReloj getTask() {
		
		return this.task;
		
	}
	
	/**
	 * Asigna t a task del Sudoku.	
	 * @param t TareaReloj a asignar.
	 */
	public void setTask(TareaReloj t) {
		
		this.task = t;
		
	}
	
	/**
	 * Retorna si el Sudoku ha sido iniciado correctamente o no.
	 * @return inicioCorrecto boolean del Sudoku.
	 */
	public boolean getInicioCorrecto() {
		
		return this.inicioCorrecto;
		
	}
	
	/**
	 * Asigna b al inicioCorrecto del Sudoku.
	 * @param b boolean a asignar.
	 */
	public void setInicioCorrecto(boolean b) {
		
		this.inicioCorrecto = b;
		
	}
	
	/**
	 * Devuelve la celda del tablero que se encuentra en la fila i y columna j.
	 * @param i fila en la que se encuentra la celda.
	 * @param j columna en la que se encuentra la celda.
	 * @return celda en la posición i, j.
	 */
	public Celda getCelda(int i, int j) {
		
		return this.tablero[i][j];
		
	}
	
	/**
	 * Devuelve la cantidad de filas del Sudoku.
	 * @return cantFilas int del Sudoku.
	 */
	public int getCantFilas() {
		
		return this.cantFilas;
		
	}
	
	/**
	 * Asigna f a la cantidad de filas del Sudoku.
	 * @param f int a asignar.
	 */
	public void setCantFilas(int f) {
		
		this.cantFilas = f;
		
	}
	
	/**
	 * Devuelve la cantidad de filas por cuadrante del Sudoku.
	 * @return cantFilasCuadrante int del Sudoku.
	 */
	public int getCantFilasCuadrante() {
		
		return this.cantFilasCuadrante;
		
	}
	
	/**
	 * Asigna fC a la cantidad de filas por cuadrante del Sudoku.
	 * @param fC int a asignar.
	 */
	public void setCantFilasCuadrante(int fC) {
		
		this.cantFilasCuadrante = fC;
		
	}
	
	/*---------------------------OTROS MÉTODOS---------------------------*/
	
	/**
	 * Actualiza el valor de la Celda c.
	 * @param c Celda a accionar.
	 */
	public void accionar(Celda c) {
		
		c.actualizar();
		
	}
	
	/**
	 * Asigna a cada una de las celdas del tablero como correctas.
	 */
	public void todosValidos() {
		
		//Cada una de las celdas del tablero queda marcada como correcta.
		for ( int i = 0; i < cantFilas; i++ ) {
			for ( int j = 0; j < cantFilas; j++ ) {
				tablero[i][j].getEntidadGrafica().correcto();
			}
		}
		
	}
	
	/**
	 * Indica si se completó el tablero o no.
	 * @return boolean true si el tablero está completo, false si no lo está.
	 */
	public boolean estaLleno() {
		
		boolean lleno = true;
		
		//Por cada celda verifica que contenga una imagen.
		for ( int i = 0; i < cantFilas && lleno; i++ ) {
			for ( int j = 0; j < cantFilas && lleno; j++ ) {
				if ( tablero[i][j].getValor() == 0 )
					 lleno = false;
			}
		}		
		
		return lleno;
	
	}
	
	/**
	 * Indica si se ganó la partida o no.
	 * @return boolean true si ganó, false si todavía no lo hizo.
	 */
	public boolean ganoPartida() {
		
		boolean gano = false;
		
		if (  solucionValida() ) {
			//El juego se gana si se completaron todas las celdas y es una solución válida.
			if ( estaLleno() ) {
				//Cuando gana:
				gano = true;
				task.stopTimer(); //se detiene el reloj;
				//todas las celdas quedan inhabilitadas.
				for ( int i = 0; i < cantFilas; i++ ) {
					for ( int j = 0; j < cantFilas; j++ )
						tablero[i][j].getEntidadGrafica().getLabel().setEnabled(false);
				}
			}
		}
		
		return gano;
		
	}
	
	/**
	 * Inicia task del Sudoku a partir de reloj.
	 * @param reloj GraficaReloj a asignar.
	 */
	public void iniciarTareaReloj(GraficaReloj reloj) {
		
		Timer timer = new Timer();
		this.task = new TareaReloj(timer);
		this.task.setReloj(reloj);
		timer.schedule(task, 0, 1000);
		this.task.run();
		
	}
	
	
}
