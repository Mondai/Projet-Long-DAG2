package solver;

import solverCommun.Etat;
import solverCommun.IMutation;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;


// Implementation de l'interface IMutation sous la forme d'une mutation aleatoire d'un noeud en conflit de coloriage
// La couleur est choisie aleatoirement.
public class MutationConflitsAleatoire implements IMutation {

	
	public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;
	
		// Determination aleatoire d'une mutation a effectuer parmi les noeuds en conflit.
		int noeudsEnConflits = coloriage.nombreNoeudsEnConflit();
		
		int compteur = (int) (coloriage.gen.nextDouble()*noeudsEnConflits);
		//System.out.println("compteur "+compteur);
		//System.out.println("noeuds en conflit "+noeudsEnConflits);
		int noeud = 0;
		//boolean condition = true;
		//int j = 0;
		
		/*while (condition && j < coloriage.graphe.getNombreNoeuds()){
			if (coloriage.getNoeudsConflit()[j]){
				if (compteur == 0){
					noeud = j;
					condition = false;
				}
				else compteur--;
			}
			j++;
		}*/
		noeud=coloriage.getNoeudsConflitList().get(compteur);
		int couleurPrec = coloriage.couleurs[noeud];
		int couleurSuiv = couleurPrec;
		
		while (couleurSuiv == couleurPrec){
			couleurSuiv = (int) (coloriage.gen.nextDouble()*coloriage.k);
		}
		
		return new MutationElementaireNoeud(noeud, couleurSuiv);
	}


	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		
		int couleurPrec = coloriage.couleurs[m.noeud];
		coloriage.couleurs[m.noeud] = m.couleur;
		coloriage.updateLocal(m.noeud, couleurPrec);
		
	}

}
