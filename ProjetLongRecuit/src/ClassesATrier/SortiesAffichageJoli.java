package ClassesATrier;

public class SortiesAffichageJoli extends Sorties {
	int compteur ;
	
	
public SortiesAffichageJoli () {}


public void initialisation(double tdebut, double tfinal, int nbMaxIteration,
		String nameT, String nameK) {
	String texte="Pour info, on vient d'utiliser en param�tre la classe temp�rature "+nameT+
			" et la classe constante "+nameK+". /n La temp�rature initiale vaut "+tdebut+" et celle finale "+tfinal+
			". /n"  ;
	this.resultats = new ResultatsTexte(texte);
	this.compteur=0;
}

@Override
public void sauvegarderResultat() {
	this.compteur ++;
	//(ResultatsTexte)this.resultats ;
	
}

@Override
public Resultats rendreResultats() {
	
	//modifier le texte et ajouter 
	return this.resultats;
}


}

