package solver.commun;

/**
 * Classe abstraite servant de template pour les particules d'un probl�me. Un particule dispose de plusieurs �tats,
 * qui sont des possibilit�s pour la particule. Elle dispose d'un lien 
 * vers une �nergie cin�tique et vers une mutation(template).
 * @author DAG2
 * @see EnergieCinetique,IMutation,Etat
 */
public abstract class Particule {
	
	public EnergieCinetique Ec;
	public IMutation mutation;
	public Etat[] etats;

}
