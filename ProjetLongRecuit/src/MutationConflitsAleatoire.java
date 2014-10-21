

// Impl�mentation de l'interface IMutation sous la forme d'une mutation al�atoire d'un noeud en conflit de coloriage
// La couleur est choisie aleatoirement.
public class MutationConflitsAleatoire implements IMutation {

	// Ex�cution de la modification sur le routage
	public void faire(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage) probleme; 
		
		// D�termination al�atoire d'une mutation � effectuer parmi les noeuds en conflit.
			int compteur = (int) (coloriage.gen.nextDouble()*coloriage.nombreNoeudsConflit);//nombre aleatoire entre 0 et le nombre de noeuds en conflit
			int noeudConflitAleatoire = 0;
			for (int j = 0; j < coloriage.graphe.nombreNoeuds; j++) { // a chaque noeud en conflit, on decremente le compteur pour obtenir 0 ce qui done une loi uniforme sur les noeuds en conflit
				if (coloriage.conflits[j] == 1) {
					if (compteur == 0) noeudConflitAleatoire=j; else compteur --;
				}
			}
			
			int couleurNoeud = coloriage.couleurs[noeudConflitAleatoire];
			int couleurAleatoire = couleurNoeud;
			
			while (couleurAleatoire == couleurNoeud){
				couleurAleatoire = (int) (coloriage.gen.nextDouble()*coloriage.k);
			}
			
			
			Modification modif = new Modification(noeudConflitAleatoire, couleurNoeud);
		
		// Ex�cution de la mutation �l�mentaire			
			coloriage.derniereModif = modif;
			coloriage.couleurs[noeudConflitAleatoire] = couleurAleatoire;
			
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