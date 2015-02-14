package solver.commun;

/**
 * 
 * Classe abstraite qui sert de template pour les énergies cinétiques du recuit quantique.
 * Concrètement, cette classe correspond à une méthode de calcul de l'énergie cinétique spécifique à un problème.
 */
public abstract class EnergieCinetique{
	
	/**
	 * 
	 * @param probleme
	 * 	Le problème dont on calcule l'énergie cinétique.
	 * @return Un double correspondant à l'énergie cinétique totale de la particule.
	 */
	abstract public double calculer(Probleme probleme);
	
	/**
	 * 
	 * @param etat
	 * 	L'état que l'on modifie.
	 * @param prev
	 * 	L'état situé avant l'état modifié.
	 * @param next
	 * 	L'état situé après l'état modifié.
	 * @param mutation
	 * 	La mutation que subit l'état centre et dont l'on veut calculer le changement d'énergie cinétique provoqué.
	 * @return Le changement d'énergie cinétique provoqué par la mutation sur l'état centre.
	 * 		
	 */
	abstract public double calculerDeltaE(Etat etat, Etat prev, Etat next, MutationElementaire mutation);
	
	/**
	 * 
	 * @param etat
	 * 	L'état que l'on modifie.
	 * @param prev
	 * 	L'état situé avant l'état modifié.
	 * @param next
	 * 	L'état situé après l'état modifié.
	 * @param mutation
	 * 	La mutation que subit l'état centre et dont l'on veut calculer la borne supérieure du changement d'énergie cinétique provoqué.
	 * @return La borne supérieure du changement d'énergie cinétique provoqué par la mutation sur l'état centre.
	 * 		
	 */
	abstract public double calculerDeltaEUB(Etat etat, Etat prev, Etat next, MutationElementaire mutation);
}
