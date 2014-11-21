package solver;

import solverCommun.Etat;
import solverCommun.IMutation;
import solverCommun.Probleme;

// Implementation de l'interface IMutation sous la forme d'une mutation aleatoire de coloriage
// Premiere ebauche avec le noeud pris aleatoirement et la couleur aussi.
public class MutationAleatoireColoriage implements IMutation {

	// Execution de la modification aleatoire elementaire
	public void faire(Probleme probleme) {
		//long startTime = System.nanoTime();
		
		for (Etat etat : probleme.etats){
			GrapheColorie coloriage = (GrapheColorie) etat; 
		
			// Determination aleatoire d'une mutation elementaire a effectuer.
				int noeudAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.graphe.getNombreNoeuds());
				int couleurNoeud = coloriage.couleurs[noeudAleatoire];
				int couleurAleatoire = couleurNoeud;
				
				while (couleurAleatoire == couleurNoeud){
					couleurAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.k);
				}
				
				
				Modification modif = new Modification(noeudAleatoire, couleurNoeud);
			
			// Execution de la mutation elementaire			
				coloriage.derniereModif = modif;
				coloriage.couleurs[noeudAleatoire] = couleurAleatoire;
				coloriage.updateLocal(noeudAleatoire, couleurNoeud);
		}
		
		//long endTime = System.nanoTime();
		
		//System.out.println("duree = "+(endTime-startTime)/1000+" micros");

			
	}
	
	//polymorphisme pour affecter un seul etat
	public void faire(Probleme probleme, Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie) etat; 
	
		// Determination aleatoire d'une mutation elementaire a effectuer.
			int noeudAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.graphe.getNombreNoeuds());
			int couleurNoeud = coloriage.couleurs[noeudAleatoire];
			int couleurAleatoire = couleurNoeud;
			
			while (couleurAleatoire == couleurNoeud){
				couleurAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.k);
			}
			
			
			Modification modif = new Modification(noeudAleatoire, couleurNoeud);
		
		// Execution de la mutation elementaire			
			coloriage.derniereModif = modif;
			coloriage.couleurs[noeudAleatoire] = couleurAleatoire;
			coloriage.updateLocal(noeudAleatoire, couleurNoeud);

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
	
	//polymorphisme pour defaire un seul etat
	public void defaire(Probleme probleme, Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie) etat; 
		
		Modification modif = coloriage.derniereModif;
		int couleurAStocker = modif.couleurPrecedente;
		modif = new Modification(modif.noeudModifie, coloriage.couleurs[modif.noeudModifie]);
		int prevColor = coloriage.couleurs[modif.noeudModifie];
		coloriage.couleurs[modif.noeudModifie] = couleurAStocker;		
		coloriage.updateLocal(modif.noeudModifie, prevColor);
		
	}
	
}