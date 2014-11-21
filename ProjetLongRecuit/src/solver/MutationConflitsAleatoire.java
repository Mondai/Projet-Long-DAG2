package solver;

import solverCommun.Etat;
import solverCommun.IMutation;
import solverCommun.Probleme;


// Implementation de l'interface IMutation sous la forme d'une mutation aleatoire d'un noeud en conflit de coloriage
// La couleur est choisie aleatoirement.
public class MutationConflitsAleatoire implements IMutation {

	// Execution de la modification
	public void faire(Probleme probleme) {
		
		for (Etat etat : probleme.etats){
			GrapheColorie coloriage = (GrapheColorie) etat; 
			
			// Determination aleatoire d'une mutation a effectuer parmi les noeuds en conflit.
				
				int compteur = (int) (coloriage.gen.nextDouble()*coloriage.listeNoeudsConflit.size());
				int noeudConflitAleatoire = 0;
				
				for (int noeud: coloriage.listeNoeudsConflit){
					// en comptant noeud = 0 existant
					if (compteur < 0){
						noeudConflitAleatoire = noeud;
					}
					compteur--;
				}
				
				int couleurNoeud = coloriage.couleurs[noeudConflitAleatoire];
				int couleurAleatoire = couleurNoeud;
				
				while (couleurAleatoire == couleurNoeud){
					couleurAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.k);
				}
				
				Modification modif = new Modification(noeudConflitAleatoire, couleurNoeud);
			
			// Execution de la mutation elementaire			
				coloriage.derniereModif = modif;
				coloriage.couleurs[noeudConflitAleatoire] = couleurAleatoire;	
				coloriage.updateLocal(noeudConflitAleatoire, couleurNoeud);
		}
		

			
	}
		

	// Annulation de la derniere mutation elementaire effectuee
	public void defaire(Probleme probleme) {
		
		for (Etat etat : probleme.etats){
			GrapheColorie coloriage = (GrapheColorie) etat; 
			
			Modification modif = coloriage.derniereModif;
			int couleurAStocker = modif.couleurPrecedente;
			modif = new Modification(modif.noeudModifie, coloriage.couleurs[modif.noeudModifie]);
			int prevColor = coloriage.couleurs[modif.noeudModifie];
			coloriage.couleurs[modif.noeudModifie] = couleurAStocker;	
			coloriage.updateLocal(modif.noeudModifie, prevColor);
		}
		
	}


	@Override
	public void faire(Probleme particule, Etat etat) {
		GrapheColorie coloriage = (GrapheColorie) etat; 
		
		// Determination aleatoire d'une mutation a effectuer parmi les noeuds en conflit.
			
		int compteur = (int) (coloriage.gen.nextDouble()*coloriage.listeNoeudsConflit.size());
		int noeudConflitAleatoire = 0;
		
		for (int noeud: coloriage.listeNoeudsConflit){
			// en comptant noeud = 0 existant
			if (compteur < 0){
				noeudConflitAleatoire = noeud;
			}
			compteur--;
		}
		
		int couleurNoeud = coloriage.couleurs[noeudConflitAleatoire];
		int couleurAleatoire = couleurNoeud;
		
		while (couleurAleatoire == couleurNoeud){
			couleurAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.k);
		}
		
		Modification modif = new Modification(noeudConflitAleatoire, couleurNoeud);
	
		// Execution de la mutation elementaire			
		coloriage.derniereModif = modif;
		coloriage.couleurs[noeudConflitAleatoire] = couleurAleatoire;	
		coloriage.updateLocal(noeudConflitAleatoire, couleurNoeud);
		
	}


	@Override
	public void defaire(Probleme particule, Etat etat) {
		GrapheColorie coloriage = (GrapheColorie) etat; 
		
		Modification modif = coloriage.derniereModif;
		int couleurAStocker = modif.couleurPrecedente;
		modif = new Modification(modif.noeudModifie, coloriage.couleurs[modif.noeudModifie]);
		int prevColor = coloriage.couleurs[modif.noeudModifie];
		coloriage.couleurs[modif.noeudModifie] = couleurAStocker;	
		coloriage.updateLocal(modif.noeudModifie, prevColor);
	}
	
	
}
