import java.util.Iterator;
import java.util.Set;

// Implémentation de l'interface IMutation sous la forme d'une mutation aléatoire de coloriage
// Première ébauche avec le noeud pris aléatoirement et la couleur aussi.
public class MutationAleatoireColoriage implements IMutation {

	// Exécution de la modification aléatoire élémentaire sur le routage
	public void faire(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage) probleme; 
		
		// Détermination aléatoire d'une mutation élémentaire à effectuer.
			int noeudAleatoire = (int) (Math.random()*coloriage.graphe.nombreNoeuds);
			int couleurNoeud = coloriage.couleurs[noeudAleatoire];
			int couleurAleatoire = couleurNoeud;
			
			while (couleurAleatoire == couleurNoeud){
				couleurAleatoire = (int) (Math.random()*coloriage.k);
			}
			
			
			Modification modif = new Modification(couleurNoeud, noeudAleatoire);
		
		// Exécution de la mutation élémentaire			
			coloriage.derniereModif = modif;
			coloriage.couleurs[noeudAleatoire] = couleurAleatoire;
			
		}
		

	// Annulation de la dernière mutation élémentaire effectuée
	public void defaire(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage) probleme; 
		
		Modification modif = coloriage.derniereModif;
		int couleurAStocker = modif.couleurPrecedente;
		modif = new Modification(modif.noeudModifie, coloriage.couleurs[modif.noeudModifie]);
		coloriage.couleurs[modif.noeudModifie] = couleurAStocker;
		
	}
	
}