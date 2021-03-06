package dag3;

import parametrage.EnergiePotentielle;



/**
 * D�crit l'�nergie potentielle pour le probl�me de coloriage de graphes.
 * Aucun calcul ici, seulement une r�cup�ration d'attribut d'instances GrapheColorie.
 */
public class Conflits extends EnergiePotentielle {
	
	/**
	 * Ici, la fonction qui calcule l'�nergie potentielle de l'�tat en question va chercher le nombre d'ar�tes
	 * d'un coloriage(�tat) et le renvoie.
	 */
	public static double calculer(GrapheColorie coloriage) {
		return coloriage.getNombreConflitsAretes();
	}

}
