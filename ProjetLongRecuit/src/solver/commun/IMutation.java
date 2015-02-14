package solver.commun;

/**
 * Interface représentant le concept abstrait de mutation dans l'algorithme de recuit.
 */
public interface IMutation {

	/**
	 * Renvoie une MutationElementaire possible selon le problème et l'état envoyés.
	 * @param probleme
	 * Le problème dont on cherche une mutation élementaire possible.
	 * @param etat
	 * L'état dont on cherche une mutation élementaire possible.
	 * @return Un objet MutationElementaire
	 */
	abstract public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat);
	
	/**
	 * Le problème fait une mutation sur demande.
	 * @param probleme
	 * Le problème sur lequel on cherche à réaliser la mutation.
	 * @param etat
	 * L'état sur lequel on cherche à réaliser la mutation.
	 * @param mutation
	 * La mutation à réaliser.
	 */
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation);
	
}
