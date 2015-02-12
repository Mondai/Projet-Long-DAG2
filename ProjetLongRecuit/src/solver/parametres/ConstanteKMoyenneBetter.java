package solver.parametres;

public class ConstanteKMoyenneBetter extends ConstanteK {   
	
double[] tabDeltaE ;									// on ajoute 3 choses : un tableau circulaire pour sauvegarder les deltaE
int tailleTab ;											// la taille du tableau, qui est la duree de la moyenne
int indice ;											// un indice de lieu d'ecriture dans le tableau	
int nbIterationEffectuee;
	
 public ConstanteKMoyenneBetter(int nbValeur) {				// nbValeur est le nombre de valeur utilise pour la moyenne
	this.tailleTab= nbValeur;
	this.tabDeltaE= new double[nbValeur] ;				// alors normalement, le tableau est automatique initialise a zero partout
	for (int i=0;i<nbValeur;i++){							// c'est la bible "programmer en java" qui le dit ! ... si ca ne le fait cette classe ne fonctionnera pas
		tabDeltaE[i]=0. ; }								// mais on initialise ici dans la version "mieux"
	this.indice=0;										
	this.k=0 ; 											// on prend 0 car debut de somme
	this.nbIterationEffectuee=0;
 }
 
 public void calculerK(double deltaE) {						// on recalcule la moyenne en prenant compte 
	 if (this.nbIterationEffectuee < this.tailleTab) {
		 this.k= this.k*(this.nbIterationEffectuee+deltaE)/(this.nbIterationEffectuee+1) ;   // moyenne sur les nbIter premiers termes
		 this.nbIterationEffectuee ++ ;
		 this.tabDeltaE[this.indice]=deltaE;
		 this.indice=(this.indice+1) % this.tailleTab ;
	 }else{
		 this.k=this.k+((deltaE-this.tabDeltaE[this.indice])/this.tailleTab);
		 this.tabDeltaE[this.indice]=deltaE;
		 this.indice=(this.indice+1) % this.tailleTab ; 
	 }
 }
 
}



// c'est un meilleure version : on fait des cas particulier ici
// mais c'est a tester !!!'