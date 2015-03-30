package dag3;

import modele.Etat;
import modele.Probleme;
import mutation.IMutation;


/**
 * Implémentation de l'interface IMutation sous la forme d'une mutation aléatoire de coloriage.
 * Le noeud et la couleur sont pris aléatoirement.
 */
public class MutationAleatoireColoriage implements IMutation {

	/**
	 * Fonction qui retourne une MutationElementaireNoeud possible, avec un noeud puis une couleur aléatoire
	 * (différente de celle précédente du noeud).
	 */
	public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat) {
		

		
		return new MutationElementaireNoeud(noeudAleatoire, couleurAleatoire);
	}


	@Override
	public double calculer(Probleme arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double calculer(Probleme arg0, Etat arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void faire(Probleme arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Etat faire(Probleme probleme, Etat etat) {
		GrapheColorie coloriage = (GrapheColorie) etat;

		// Determination aleatoire d'une mutation elementaire a effectuer.
		int noeudAleatoire = (int) (coloriage.gen.nextDouble() * coloriage.graphe.getNombreNoeuds());
		int couleurNoeud = coloriage.couleurs[noeudAleatoire];
		int couleurAleatoire = couleurNoeud;

		while (couleurAleatoire == couleurNoeud) {
			couleurAleatoire = (int) (coloriage.gen.nextDouble() * coloriage.k);
		}
		
		GrapheColorie coloriage = (GrapheColorie) etat;
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		
		int couleurPrec = coloriage.couleurs[m.noeud];
		coloriage.couleurs[m.noeud] = m.couleur;
		coloriage.updateLocal(m.noeud, couleurPrec);
	}

	@Override
	public void maj() {
		// TODO Auto-generated method stub
		
	}
	
}