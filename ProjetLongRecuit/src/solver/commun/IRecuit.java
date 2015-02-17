package solver.commun;

/**
 * Template pour le recuit. Tous les recuits doivent implémenter cette interface ou hériter d'une classe le faisant.
 */
public interface IRecuit {
	
	/**
	 * Effectue le recuit sur le problème.
	 * @param problem
	 * Le problème sur lequel on veut effectuer le recuit.
	 */
	public void lancer(Probleme problem);
	
}
