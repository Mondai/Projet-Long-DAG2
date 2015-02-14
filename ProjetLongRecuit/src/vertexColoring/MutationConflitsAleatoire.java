package vertexColoring;

import solver.commun.Etat;
import solver.commun.IMutation;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;


/**
 * Impl�mentation de l'interface IMutation sous la forme d'une mutation al�atoire d'un noeud en conflit de coloriage.
 * Le noeud est al�atoirement choisi parmis les noeuds en conflits, la couleur est prise al�atoirement.
 */
public class MutationConflitsAleatoire implements IMutation {

	/**
	 * Fonction qui retourne une MutationElementaireNoeud possible, avec un noeud pris parmis les noeuds en conflit.
	 * Ensuite, on choisit une couleur al�atoire diff�rente de celle pr�c�dente du noeud.
	 */
	public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;
	
		// Determination aleatoire d'une mutation a effectuer parmi les noeuds en conflit.
		int noeudsEnConflits = coloriage.nombreNoeudsEnConflit();
		
		int compteur = (int) (coloriage.gen.nextInt(noeudsEnConflits));

		int noeud = 0;

		try {
			noeud=coloriage.getNoeudsConflitList().get(compteur);
		}
		catch (IndexOutOfBoundsException e){
			System.out.println(e);
		}
		int couleurPrec = coloriage.couleurs[noeud];
		int couleurSuiv = couleurPrec;
		
		while (couleurSuiv == couleurPrec){
			couleurSuiv = (int) (coloriage.gen.nextInt(coloriage.k));
		}
		
		return new MutationElementaireNoeud(noeud, couleurSuiv);
	}

	/**
	 * Fonction qui modifie l'�tat, ici un GrapheColori�, pour prendre en compte la mutation effectu�e.
	 */
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		
		int couleurPrec = coloriage.couleurs[m.noeud];
		coloriage.couleurs[m.noeud] = m.couleur;
		coloriage.updateLocal(m.noeud, couleurPrec);
		
	}

}
