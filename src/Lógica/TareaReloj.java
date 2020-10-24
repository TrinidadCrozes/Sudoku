package Lógica;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class TareaReloj extends TimerTask {

	private Timer timer;
	private LocalTime start;
	private GraficaReloj reloj;
	
	/**
	 * Constructor que inicializa la tarea del reloj.
	 * @param t Timer a asignar al de la TareaReloj.
	 */
	public TareaReloj(Timer t) {
		
		this.timer = t;
		//El tiempo de inicio siempre será el mismo.
		this.start = LocalTime.now();
		this.reloj = new GraficaReloj();
		
	}
	
	@Override
	public void run() {
		
		//Calcula el tiempo transcurrido desde el inicio y actualiza el reloj con este valor.
		Duration d = tiempoTranscurrido();
		String hms = String.format("%02d:%02d:%02d", d.toHours(), d.toMinutesPart(), d.toSecondsPart());
		reloj.actualizar(hms);
		 
	}
	
	/**
	 * Calcula el tiempo desde el inicio.
	 * @return d Duration entre el inicio y el tiempo actual.
	 */
	public Duration tiempoTranscurrido() {
		
		//Obtiene el tiempo actual y calcula la duración desde el tiempo de inicio.
		LocalTime stop = LocalTime.now();
		Duration d = Duration.between(start, stop);
		return d;		
		
	}	
	
	/**
	 * Detiene el timer de la tareaReloj.
	 */
	public void stopTimer() {
		
		timer.cancel();
		
	}
	
	/*---------------------------SETTERS Y GETTERS DE LOS ATRIBUTOS---------------------------*/

	/**
	 * Retorna el timer de la TareaReloj.
	 * @return timer Timer.
	 */
	public Timer getTimer() {
		
		return this.timer;
		
	}

	/**
	 * Asigna t al timer de la TareaReloj.
	 * @param t Timer a asignar.
	 */
	public void setTimer(Timer t) {
		
		this.timer = t;
		
	}
	
	/**
	 * Retorna el reloj de la TareaReloj.
	 * @return reloj GraficaReloj.
	 */
	public GraficaReloj getReloj() {
		
		return this.reloj;
	
	}

	/**
	 * Asigna r al reloj de la TareaReloj.
	 * @param r GraficaReloj a asignar.
	 */
	public void setReloj(GraficaReloj r) {
		
		this.reloj = r;
		
	}


}
