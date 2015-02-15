package vertexColoring;

import java.util.HashSet;

import solver.commun.EnergieCinetique;
import solver.commun.Etat;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;

/**
 * D�crit l'�nergie cin�tique pour le probl�me de coloriage de graphes.
 *
 */
public class ConflitsCinetiques extends EnergieCinetique {

	/**
	 * Calcule l'�nergie cin�tique totale de la particule quantique.
	 */
	public double calculer(Probleme probleme) {
		
		GrapheColorieParticule coloriageParticule = (GrapheColorieParticule)	probleme;
		int E = 0;
		int spin1 = 1;
		int spin2 = 1;
		
		//calcul des diff�rences spins entre un �tat et son prochain
		for (int p = 0; p < coloriageParticule.replique-1 ; p++){
			GrapheColorie etat = (GrapheColorie) probleme.etats[p];
			GrapheColorie etatNext = (GrapheColorie) probleme.etats[p+1];
			for (int i = 0; i < etat.getCouleurs().length; i++){
				for (int j = 0; j < i; j++){							// les noeuds j < i
					spin1 = 1;
					spin2 = 1;
					if (etat.getCouleurs()[i] == etat.getCouleurs()[j]){
						spin1 = -1;
					}
					if (etatNext.getCouleurs()[i] == etatNext.getCouleurs()[j]){
						spin2 = -1;
					}
					E += spin1*spin2;
				}
				for (int j = i+1; j < etat.getCouleurs().length; j++){	// les noeuds j > i
					spin1 = 1;
					spin2 = 1;
					if (etat.getCouleurs()[i] == etat.getCouleurs()[j]){
						spin1 = -1;
					}
					if (etatNext.getCouleurs()[i] == etatNext.getCouleurs()[j]){
						spin2 = -1;
					}
					E += spin1*spin2;
				}
			}
		}
		
		
		//circularit� avec le d�but de la liste
		GrapheColorie etat = (GrapheColorie) probleme.etats[coloriageParticule.replique-1];
		GrapheColorie etatNext = (GrapheColorie) probleme.etats[0];
		for (int i = 0; i < etat.getCouleurs().length; i++){
			for (int j = 0; j < i; j++){							// les noeuds j < i
				spin1 = 1;
				spin2 = 1;
				if (etat.getCouleurs()[i] == etat.getCouleurs()[j]){
					spin1 = -1;
				}
				if (etatNext.getCouleurs()[i] == etatNext.getCouleurs()[j]){
					spin2 = -1;
				}
				E += spin1*spin2;
			}
			for (int j = i+1; j < etat.getCouleurs().length; j++){	// les noeuds j > i
				spin1 = 1;
				spin2 = 1;
				if (etat.getCouleurs()[i] == etat.getCouleurs()[j]){
					spin1 = -1;
				}
				if (etatNext.getCouleurs()[i] == etatNext.getCouleurs()[j]){
					spin2 = -1;
				}
				E += spin1*spin2;
			}
		}
		
		E /= 2; // Tous les spins sont compt�s deux fois dans les calculs
		
		return E;

	}

	/**
	 * Calcule la diff�rence d'�nergie cin�tique d'un �tat avec son �tat suivant et son �tat pr�c�dent, 
	 * lorsqu'il subit la mutation donn�e.
	 * <p>
	 * Utilise les classes de couleur du noeud chang�, celle de la couleur pr�c�dente et celle de la nouvelle.
	 */
	public double calculerDeltaE(Etat etat, Etat prev, Etat next, MutationElementaire mutation) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;
		GrapheColorie coloriageNext = (GrapheColorie)	next;
		GrapheColorie coloriagePrev = (GrapheColorie)	prev;
		int deltaE = 0;

		HashSet<Integer> Valpha = coloriage.getClassesCouleurs()[coloriage.getCouleurs()[m.noeud]];
		HashSet<Integer> Vbeta = coloriage.getClassesCouleurs()[m.couleur];

		Valpha.remove(m.noeud);	// le calcul suivant requiert d'exclure v de Valpha 

		for (int u : Valpha){
			deltaE += 2*(coloriageNext.spinConflit(u, m.noeud) + coloriagePrev.spinConflit(u, m.noeud));
		}
		
		for (int u : Vbeta){
			deltaE -= 2*(coloriageNext.spinConflit(u, m.noeud) + coloriagePrev.spinConflit(u, m.noeud));
		}
		
		Valpha.add(m.noeud);	// rajouter v dans le classe de couleur (vu qu'on l'a enleve avant)
		
		return deltaE;
	}
	
	/**
	 * Calcule une borne sup�rieure pour la diff�rence d'�nergie cin�tique li�e � la mutation donn�e en temps constant.
	 * <p>
	 * Cette m�thode est utilis�e pour acc�l�rer le recuit quantique, puisque si la borne sup�rieure de 
	 * la diff�rence d'�nergie cin�tique n'est pas assez pour que la diff�rence d'�nergie totale soit n�gative,
	 * alors il y a aucune chance que la diff�rence d'�nergie cin�tique rendre celle ci n�gative.
	 * Le calcul est en temps constant car la valeur retourn�e est 4*(|Va|+|Vb|-1).
	 */
	public double calculerDeltaEUB(Etat etat, Etat prev, Etat next, MutationElementaire mutation) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;
		
		HashSet<Integer> Valpha = coloriage.getClassesCouleurs()[coloriage.getCouleurs()[m.noeud]];
		HashSet<Integer> Vbeta = coloriage.getClassesCouleurs()[m.couleur];

		return 4*(Valpha.size() + Vbeta.size() -1);
	}

}
