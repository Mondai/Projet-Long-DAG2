package dag3;

import java.util.HashSet;

import modele.Etat;
import modele.Probleme;
import mutation.IMutation;
import dag3.GrapheColorie;



/**
 * Implémentation de l'interface IMutation sous la forme d'une mutation aléatoire d'un noeud en conflit de coloriage.
 * Le noeud est aléatoirement choisi parmis les noeuds en conflits, la couleur est prise aléatoirement.
 */
public class MutationConflitsAleatoire implements IMutation {
	
	int noeud;
	int couleur;

	@Override
	public double calculer(Probleme probleme) {
		// sert à rien
		return 0;
	}

	@Override
	public double calculer(Probleme probleme, Etat etat) {
		
		
		GrapheColorie coloriage = (GrapheColorie) etat;

		int couleurNoeud = coloriage.couleurs[this.noeud];

		// Propriete: DelatE = F[v][couleurSuiv] - F[v][couleurPrec]
		int Epot = coloriage.F[this.noeud][this.couleur] - coloriage.F[this.noeud][couleurNoeud];	
		//System.out.println("Calcul Mutation de couleur " + couleurNoeud + " , " + this.couleur + " est " + Epot);
		// différence d'énergie potentielle
		
		return Epot;
		//renvoie energie potentielle
	}

	@Override
	public void faire(Probleme probleme, Etat etat) {
		GrapheColorie coloriage = (GrapheColorie) etat;
		
		int couleurNoeud = coloriage.couleurs[this.noeud];
		coloriage.couleurs[this.noeud] = this.couleur;
		coloriage.updateLocal(this.noeud, couleurNoeud);
		//System.out.println("Mutation du noeud " + this.noeud + " de couleur " + couleurNoeud + " , " + coloriage.couleurs[this.noeud] );
	}

	@Override
	public void maj(Probleme probleme, Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;
		
		// Determination aleatoire d'une mutation a effectuer parmi les noeuds en conflit.
		int noeudsEnConflits = coloriage.nombreNoeudsEnConflit();
		//System.out.println("nombre noeuds en conflits "+noeudsEnConflits);
		
		int compteur = (int) (coloriage.gen.nextInt(noeudsEnConflits));

		int noeud = 0;

		try {
			//System.out.println(coloriage.getNoeudsConflitList().size());
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
		//System.out.println("Noeud : " + this.noeud + " et couleur : " + this.couleur);
		
	}

	@Override
	public void faire(Probleme arg0) {
		// TODO Auto-generated method stub
		
	}

}
