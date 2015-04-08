package dag3;

import parametrage.EnergiePotentielle;



/**
 * Décrit l'énergie potentielle pour le problème de coloriage de graphes.
 * Aucun calcul ici, seulement une récupération d'attribut d'instances GrapheColorie.
 */
public class Conflits extends EnergiePotentielle {
	
	/**
	 * Ici, la fonction qui calcule l'énergie potentielle de l'état en question va chercher le nombre d'arêtes
	 * d'un coloriage(état) et le renvoie.
	 */
	public static double calculer(GrapheColorie coloriage) {
		return coloriage.getNombreConflitsAretes();
	}

}
