package solverCommun;
// Interface repr�sentant le concept abstrait de mutation dans l'algorithme de recuit

public interface IMutation {

	/**
	 * 
	 * @param probleme
	 * Le probl�me dont on cherche une mutation �lementaire possible.
	 * @param etat
	 * L'�tat dont on cherche une mutation �lementaire possible.
	 * @return un objet MutationElementaire
	 */
	abstract public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat);
	
	/**
	 * 
	 * @param probleme
	 * Le probl�me sur lequel on cherche � r�aliser la mutation.
	 * @param etat
	 * L'�tat sur lequel on cherche � r�aliser la mutation.
	 * @param mutation
	 * La mutation � r�aliser.
	 */
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation); 	// le probl�me fait une mutation sur demande
	
}
