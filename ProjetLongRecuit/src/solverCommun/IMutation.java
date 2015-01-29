package solverCommun;
// Interface représentant le concept abstrait de mutation dans l'algorithme de recuit

public interface IMutation {

	/**
	 * 
	 * @param probleme
	 * Le problème dont on cherche une mutation élementaire possible.
	 * @param etat
	 * L'état dont on cherche une mutation élementaire possible.
	 * @return un objet MutationElementaire
	 */
	abstract public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat);
	
	/**
	 * 
	 * @param probleme
	 * Le problème sur lequel on cherche à réaliser la mutation.
	 * @param etat
	 * L'état sur lequel on cherche à réaliser la mutation.
	 * @param mutation
	 * La mutation à réaliser.
	 */
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation); 	// le problème fait une mutation sur demande
	
}
