package solver.commun;

/**
 * Template pour le recuit. Tous les recuits doivent impl�menter cette interface ou h�riter d'une classe le faisant.
 */
public interface IRecuit {
	
	/**
	 * 
	 * @param problem
	 * Le probl�me sur lequel on veut effectuer le recuit.
	 */
	public void lancer(Probleme problem);
	
}
