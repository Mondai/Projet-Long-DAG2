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
				int noeudsEnConflits = coloriage.nombreNoeudsEnConflit();
				
				int compteur = (int) (coloriage.gen.nextDouble()*noeudsEnConflits);
				int noeudConflitAleatoire = 0;
				boolean condition = true;
				int j = 0;
				
				
				while (condition && j < coloriage.graphe.getNombreNoeuds()){
					if (coloriage.getNoeudsConflit()[j] == 1){
						if (compteur == 0){
							noeudConflitAleatoire = j;
							condition = false;
						}
						else compteur--;
					}
					j++;
				}
				/*
				for (int noeud: coloriage.listeNoeudsConflit){
					// en comptant noeud = 0 existant
					if (compteur < 0){
						noeudConflitAleatoire = noeud;
						break;
					}
					compteur--;
					//System.out.println("compteur : " + compteur);
					//System.out.println("noeud : " + noeud);
					// Fonctions debug
				}*/
				
				int couleurNoeud = coloriage.couleurs[noeudConflitAleatoire];
				int couleurAleatoire = couleurNoeud;
				
				while (couleurAleatoire == couleurNoeud){
					couleurAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.k);
				}
				
				Modification modif = new Modification(noeudConflitAleatoire, couleurNoeud);
			
			// Execution de la mutation elementaire			
				coloriage.derniereModif = modif;
				coloriage.couleurs[noeudConflitAleatoire] = couleurAleatoire;
				//System.out.println("noeud : " + noeudConflitAleatoire + " et " + noeudsEnConflits + " restants.");
				coloriage.updateLocal(noeudConflitAleatoire, couleurNoeud);
				//System.out.println("noeud : " + noeudConflitAleatoire + " et " + noeudsEnConflits + " restants.");
		}
		
	}
		

	// Annulation de la derniere mutation elementaire effectuee
	public void defaire(Probleme probleme) {
		
		for (Etat etat : probleme.etats){
			GrapheColorie coloriage = (GrapheColorie) etat; 
			int noeudsEnConflits = coloriage.nombreNoeudsEnConflit();
			
			Modification modif = coloriage.derniereModif;
			int couleurAStocker = modif.couleurPrecedente;
			modif = new Modification(modif.noeudModifie, coloriage.couleurs[modif.noeudModifie]);
			int prevColor = coloriage.couleurs[modif.noeudModifie];
			coloriage.couleurs[modif.noeudModifie] = couleurAStocker;	
			//System.out.println("defaire");
			//System.out.println("noeud : " + modif.noeudModifie + " et " + noeudsEnConflits + " restants.");
			coloriage.updateLocal(modif.noeudModifie, prevColor);
			//System.out.println("noeud : " + modif.noeudModifie + " et " + noeudsEnConflits + " restants.");
		}
		
	}


	@Override
	public void faire(Probleme particule, Etat etat) {
		GrapheColorie coloriage = (GrapheColorie) etat; 
		
		// Determination aleatoire d'une mutation a effectuer parmi les noeuds en conflit.
		int noeudsEnConflits = coloriage.nombreNoeudsEnConflit();
		
		int compteur = (int) (coloriage.gen.nextDouble()*noeudsEnConflits);
		int noeudConflitAleatoire = 0;
		boolean condition = true;
		int j = 0;
		
		
		while (condition && j < coloriage.graphe.getNombreNoeuds()){
			if (coloriage.getNoeudsConflit()[j] == 1){
				if (compteur == 0){
					noeudConflitAleatoire = j;
					condition = false;
				}
				else compteur--;
			}
			j++;
		}
		/*
		for (int noeud: coloriage.listeNoeudsConflit){
			// en comptant noeud = 0 existant
			if (compteur < 0){
				noeudConflitAleatoire = noeud;
				break;
			}
			compteur--;
			//System.out.println("compteur : " + compteur);
			//System.out.println("noeud : " + noeud);
			// Fonctions debug
		}*/
		
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
