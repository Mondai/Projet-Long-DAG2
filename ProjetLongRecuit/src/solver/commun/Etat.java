package solver.commun;

/**
 * Classe abstraite servant de template pour les états d'un problème. Un état représente une configuration 
 * possible d'une particule. Un état dispose nécessairement d'un lien vers une énergie potentielle. 
 * @see EnergiePotentielle, Particule
 *
 */
public abstract class Etat {
	
	public EnergiePotentielle Ep;
	
}
