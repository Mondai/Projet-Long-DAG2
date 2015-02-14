package solver.commun;

/**
 * Classe abstraite servant de template pour les �tats d'un probl�me. Un �tat repr�sente une configuration 
 * possible d'une particule. Un �tat dispose n�cessairement d'un lien vers une �nergie potentielle. 
 * @see EnergiePotentielle, Particule
 *
 */
public abstract class Etat {
	
	public EnergiePotentielle Ep;
	
}
