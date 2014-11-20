package solver;

import solverCommun.IMutation;
import solverCommun.Probleme;

// Impl�mentation de l'interface IMutation sous la forme d'une mutation al�atoire de coloriage
// Premi�re �bauche avec le noeud pris al�atoirement et la couleur aussi.
public class MutationAleatoireColoriage implements IMutation {

	// Ex�cution de la modification al�atoire �l�mentaire sur le routage
	public void faire(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage) probleme; 
		
		// D�termination al�atoire d'une mutation �l�mentaire � effectuer.
			int noeudAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.graphe.getNombreNoeuds());
			int couleurNoeud = coloriage.couleurs[noeudAleatoire];
			int couleurAleatoire = couleurNoeud;
			
			while (couleurAleatoire == couleurNoeud){
				couleurAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.k);
			}
			
			
			Modification modif = new Modification(noeudAleatoire, couleurNoeud);
		
		// Ex�cution de la mutation �l�mentaire			
			coloriage.derniereModif = modif;
			coloriage.couleurs[noeudAleatoire] = couleurAleatoire;
			
		}
		

	// Annulation de la derni�re mutation �l�mentaire effectu�e
	public void defaire(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage) probleme; 
		
		Modification modif = coloriage.derniereModif;
		int couleurAStocker = modif.couleurPrecedente;
		modif = new Modification(modif.noeudModifie, coloriage.couleurs[modif.noeudModifie]);
		coloriage.couleurs[modif.noeudModifie] = couleurAStocker;
		
	}
	
}