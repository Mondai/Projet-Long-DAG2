package solver.parametres;

public class ConstanteKMoyenneEasy extends ConstanteK {   
	
double[] tabDeltaE ;									// on ajoute 3 choses : un tableau circulaire pour sauvegarder les deltaE
int tailleTab ;											// la taille du tableau, qui est la duree de la moyenne
int indice ;											// un indice de lieu d'ecriture dans le tableau	
	
 public ConstanteKMoyenneEasy(int nbValeur) {				// nbValeur est le nombre de valeur utilise pour la moyenne
	this.tailleTab= nbValeur;
	this.tabDeltaE= new double[nbValeur] ;				// alors normalement, le tableau est automatique initialise a zero partout
	this.indice=0;										// c'est la bible "programmer en java" qui le dit ! ... si ca ne le fait cette classe ne fonctionnera pas
	this.k=1 ; 											// initialisation bidon ! mais pas tant que ça car k sera toujours > 1
 }
 
 public void calculerK(double deltaE) {						// on recalcule la moyenne en prenant compte de la n ieme valeur precendente a ecraser
	 this.k=this.k+((deltaE-this.tabDeltaE[this.indice])/this.tailleTab);
	 this.tabDeltaE[this.indice]=deltaE;
	 this.indice=(this.indice+1) % this.tailleTab ; 
 }
 
 
}



// j'ai pas du tout test, normallement c'est ultra rapide :) 
// la flemme de decrire le principe
// mais en tout cas il faut que tailleTab soit petit devant nbMaxIteration