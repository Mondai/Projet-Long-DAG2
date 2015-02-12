package solverCommun;

/**
 * Template pour le recuit. Tous les recuits doivent implémenter cette interface ou hériter d'une classe le faisant.
 * @author DAG2
 *
 */
public interface IRecuit {
	
	/**
	 * 
	 * @param problem
	 * Le problème sur lequel on veut effectuer le recuit
	 * @return 
	 */
	public Probleme lancer(Probleme problem);
	
}
