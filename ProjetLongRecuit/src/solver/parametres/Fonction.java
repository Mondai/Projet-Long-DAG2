package solver.parametres;

/**
 * Classe qui décrit un comportement (par exemple linéaire ou exponentiel), et qui est utilisé entre autre pour la température.
 * Cette classe permet de modéliser le comportement des paramètres comme la température dans le recuit simulé ou le
 * paramètre Gamma dans le recuit quantique.
 *
 */
public abstract  class Fonction {       

	/**
	 * Température de départ de la fonction
	 */
	double Tdebut;
	/**
	 * Température de fin de la fonction
	 */
	double Tfinal;
	/**
	 * Nombre d'itérations théoriques effectués(en vrai, peut être un peu différent)
	 */
	public int nbIteration ; 
	
	/**
	 * Température actuelle de la fonction
	 */
	public double t;
	
	public double getTdebut() {
		return Tdebut;
	}

	public double getTfinal() {
		return Tfinal;
	}

	public double getT() {
		return t;
	}

	/**
	 * Fonction qui change la temperature et indique si on est à la temperature finale.
	 * @return True s'il faut continuer, false sinon.
	 */
	 public boolean  modifierT() {
		return false;
	} 
		
	/**
	 * Fonction d'initialisation(ou plus précisemment réinitialisation) de l'instance. 
	 * La température gardée dans l'instance est réinitialisée à la température de début donnée
	 * à l'instanciation de l'objet.
	 */
	public void init(){
		this.t = this.Tdebut;
	}
	 
	/**
	 * Constructeur.
	 * @param tdebut Température de début, gardée em mémoire pour une réinitialisation possible.
	 * @param tfinal Température de fin, gardée en mémoire.
	 * @param nbIteration Le nombre d'itérations, gardé en mémoire.
	 */
	 public Fonction(double tdebut, double tfinal, int nbIteration) {
		this.Tdebut = tdebut;
		this.Tfinal = tfinal;
		this.nbIteration = nbIteration;
		this.t = tdebut;
	}
	
	
}
