package solverCommun;

/**
 * Template pour le recuit. Tous les recuits doivent impl�menter cette interface ou h�riter d'une classe le faisant.
 * @author DAG2
 *
 */
public interface IRecuit {
	
	/**
	 * 
	 * @param problem
	 * Le probl�me sur lequel on veut effectuer le recuit
	 * @return 
	 */
	public Probleme lancer(Probleme problem);
	
}
