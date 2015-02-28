package solver.parametres;

/**
 * {@inheritDoc}
 * <p>
 * A chaque appel de modification de la température, la température descend d'un pas exponentiellement décroissant.
 * Le nombre réel d'itérations est une approximation puisque le pas exponentiel est un double à précision limitée.
 * Le coefficient normalisé(variable) permet de choisir la pente indépendamment de 
 * la température de début, de celle de fin et du nombre d'itérations.
 * @see FonctionExpoExplicite
 */
public class FonctionExpoRecursive extends Fonction {

	/**
	 * Facteur normalisé.Il permet de choisir la pente indépendamment de 
	 * la température de début, de celle de fin et du nombre d'itérations.
	 */
	double facteur; 
	                                            
	/**
	 * Création d'une variable facteur(normalisé).
	 */
	public FonctionExpoRecursive(double tdebut, double tfinal, int nbIteration, double coef ) {
		super(tdebut,tfinal,nbIteration);
		this.facteur = Math.exp(-coef/this.nbIteration) ; 
		System.out.println(this.facteur);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Ici, diminue la distance de la température actuelle à la température finale d'un certain facteur.
	 * Le calcul est moins lourd que pour FonctionExpoExplicite mais aussi moins précis(assez précis pour nos besoins par contre)
	 */
	public boolean modifierT() {
		if (this.t < this.Tfinal) return false;
		else { 
			this.t=(this.t-this.Tfinal)*this.facteur+this.Tfinal;
			//System.out.println("valeur de G "+this.t);
			return true;		  
		}
	}
}