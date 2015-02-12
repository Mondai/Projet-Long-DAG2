package vertexColoring;

import java.util.HashSet;

import solver.commun.EnergieCinetique;
import solver.commun.Etat;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;


public class ConflitsCinetiques extends EnergieCinetique {

	public double calculer(Probleme probleme) {
		// impl�mentation � bien tester!!
		
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

	@Override
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
	
	public double calculerDeltaEUB(Etat etat, Etat prev, Etat next, MutationElementaire mutation) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;
		
		HashSet<Integer> Valpha = coloriage.getClassesCouleurs()[coloriage.getCouleurs()[m.noeud]];
		HashSet<Integer> Vbeta = coloriage.getClassesCouleurs()[m.couleur];

		return 4*(Valpha.size() + Vbeta.size() -1);
	}

}
