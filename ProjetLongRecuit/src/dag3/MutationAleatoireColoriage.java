package dag3;

import java.util.HashSet;


import modele.Etat;
import modele.Probleme;
import mutation.IMutation;


/**
 * Implémentation de l'interface IMutation sous la forme d'une mutation aléatoire de coloriage.
 * Le noeud et la couleur sont pris aléatoirement.
 */
public class MutationAleatoireColoriage implements IMutation {
	
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
		// différence d'énergie potentielle
		
		GrapheColorie coloriageNext = (GrapheColorie)	coloriage.getNext();
		GrapheColorie coloriagePrev = (GrapheColorie)	coloriage.getPrevious();
		int deltaE = 0;

		HashSet<Integer> Valpha = coloriage.getClassesCouleurs()[this.couleur];
		HashSet<Integer> Vbeta = coloriage.getClassesCouleurs()[couleurNoeud];

		Valpha.remove(this.noeud);	// le calcul suivant requiert d'exclure v de Valpha 

		for (int u : Valpha){
			deltaE += 2*(coloriageNext.spinConflit(u, this.noeud) + coloriagePrev.spinConflit(u, this.noeud));
		}
		
		for (int u : Vbeta){
			deltaE -= 2*(coloriageNext.spinConflit(u, this.noeud) + coloriagePrev.spinConflit(u, this.noeud));
		}
		
		Valpha.add(this.noeud);	// rajouter v dans le classe de couleur (vu qu'on l'a enleve avant)
		
		return deltaE + Epot;
		//renvoie "energieCin" + energie potentielle
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
		
		// Determination aleatoire d'une mutation elementaire a effectuer.
		int noeudAleatoire = (int) (coloriage.gen.nextDouble() * coloriage.graphe.getNombreNoeuds());
		int couleurNoeud = coloriage.couleurs[noeudAleatoire];
		int couleurAleatoire = couleurNoeud;

		while (couleurAleatoire == couleurNoeud) {
			couleurAleatoire = (int) (coloriage.gen.nextDouble() * coloriage.k);
		}
		
		this.noeud = noeudAleatoire;
		this.couleur = couleurAleatoire;
		
	}

	@Override
	public void faire(Probleme arg0) {
		// TODO Auto-generated method stub
		
	}
	
}