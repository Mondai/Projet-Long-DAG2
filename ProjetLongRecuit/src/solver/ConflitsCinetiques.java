package solver;

import java.util.HashSet;

import solverCommun.EnergieCinetique;
import solverCommun.Etat;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;


public class ConflitsCinetiques extends EnergieCinetique {

	public double calculer(Probleme probleme) {
		// pas implémenté encore
		
		GrapheColorieParticule coloriageParticule = (GrapheColorieParticule)	probleme;
		
		return coloriageParticule.calculerEnergie();

	}

	@Override
	public double calculerDeltaE(Etat etat, Etat prev, Etat next, MutationElementaire mutation) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;
		GrapheColorie coloriageNext = (GrapheColorie)	next;
		GrapheColorie coloriagePrev = (GrapheColorie)	prev;
		int deltaE = 0;
		
		int v = m.noeud;
		HashSet<Integer> Valpha = coloriage.getClassesCouleurs()[coloriage.getCouleurs()[m.noeud]];
		HashSet<Integer> Vbeta = coloriage.getClassesCouleurs()[m.couleur];
		
		for (int u : Valpha){
			deltaE += 2*(coloriageNext.spinConflit(u, v) + coloriagePrev.spinConflit(u, v));
		}
		
		for (int u : Vbeta){
			deltaE -= 2*(coloriageNext.spinConflit(u, v) + coloriagePrev.spinConflit(u, v));
		}
		
		return deltaE;
	}

}
