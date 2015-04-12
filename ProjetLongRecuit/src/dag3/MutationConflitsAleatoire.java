package dag3;

import java.util.HashSet;

import modele.Etat;
import modele.Probleme;
import mutation.IMutation;
import dag3.GrapheColorie;



/**
 * Impl�mentation de l'interface IMutation sous la forme d'une mutation al�atoire d'un noeud en conflit de coloriage.
 * Le noeud est al�atoirement choisi parmis les noeuds en conflits, la couleur est prise al�atoirement.
 */
public class MutationConflitsAleatoire implements IMutation {
	
	int noeud;
	int couleur;

	@Override
	public double calculer(Probleme probleme) {
		// sert � rien
		return 0;
	}

	@Override
	public double calculer(Probleme probleme, Etat etat) {
		
		
		GrapheColorie coloriage = (GrapheColorie) etat;

		int couleurNoeud = coloriage.couleurs[this.noeud];

		// Propriete: DelatE = F[v][couleurSuiv] - F[v][couleurPrec]
		int Epot = coloriage.F[this.noeud][this.couleur] - coloriage.F[this.noeud][couleurNoeud];
		// diff�rence d'�nergie potentielle
		
		return Epot;
	}

	@Override
	public void faire(Probleme probleme, Etat etat) {
		GrapheColorie coloriage = (GrapheColorie) etat;
		
		int couleurNoeud = coloriage.couleurs[this.noeud];
		coloriage.couleurs[this.noeud] = this.couleur;
		coloriage.updateLocal(this.noeud, couleurNoeud);
	}

	@Override
	public void maj(Probleme probleme, Etat etat) {
		
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
		
		this.noeud = noeud;
		this.couleur = couleurSuiv;
		
	}

	@Override
	public void faire(Probleme arg0) {
		//inutile : non impl�ment�
	}

}
