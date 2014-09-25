import java.util.Iterator;
import java.util.Set;

// Impl�mentation de l'interface IMutation sous la forme d'une mutation al�atoire de coloriage
// Premi�re �bauche avec le noeud pris al�atoirement et la couleur aussi.
public class MutationAleatoireColoriage implements IMutation {

	// Ex�cution de la modification al�atoire �l�mentaire sur le routage
	public void faire(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage) probleme; 
		
		// D�termination al�atoire d'une mutation �l�mentaire � effectuer.
			int noeudAleatoire = (int) (Math.random()*coloriage.graphe.nombreNoeuds);
			int couleurNoeud = coloriage.couleurs[noeudAleatoire];
			int couleurAleatoire = couleurNoeud;
			
			while (couleurAleatoire == couleurNoeud){
				couleurAleatoire = (int) (Math.random()*coloriage.k);
			}
			
			
			Modification modif = new Modification(couleurNoeud, noeudAleatoire);
		
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