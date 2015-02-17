package solver.parametres;

/**
 * {@inheritDoc}
 * <p>
 * Calcul explicite de la température avec une exponentielle.
 * Le coefficient normalisé(variable) permet de choisir la pente indépendamment de 
 * la température de début, de celle de fin et du nombre d'itérations.
 * <p>
 * En effet, T(0) = Tdébut <br>
 * Et T(nbIter) = Tfinal+(Tdébut-Tfinal)*exp(coefPente*nbIter) 
 *				= Tfinal+(Tdébut-Tfinal)*exp(-coef*nbIter/nbIter) 
 *              = Tfinal+(Tdébut-Tfinal)*exp(-coef)  <br>
 * Or exp(-coef) est proche de zero. <br>
 *    T(nbIter) ~= Tfinal
 *<p>
 * Le coefficient est à prendre plutot entre 4 et 6, puisque exp(-4) = 0.02 et exp(-6) = 0.0025.
 * Ainsi, on a une bonne exponentielle, qui ne reste pas trop lontemps vers Tfin mais quand même un peu.
 * @see FonctionExpoRecursive
 */
public class FonctionExpoExplicite extends Fonction {

	/**
	 * Coefficient de la pente exponentiel normalisée. Il permet de choisir la pente indépendamment de 
	 * la température de début, de celle de fin et du nombre d'itérations.
	 */
	double coefPente; 
	/**
	 * Nombre d'itérations effectuées.
	 */
	int k;
	
	public void init(){
		super.init();
		this.k=0;
	}
	
	/**
	 * Création d'une variable coefPente(normalisée) et de k.
	 */
	public FonctionExpoExplicite(double tdebut, double tfinal, int nbIteration, double coef ) {
		super(tdebut,tfinal,nbIteration);
		this.k=0;
		this.coefPente = -coef/this.nbIteration ;
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Ici, diminue la distance de la température actuelle à la température finale du facteur(en variable).
	 * Le calcul est un peu lourd, mais précis.
	 */
	public boolean modifierT() {
		if (this.t < this.Tfinal) return false;
		else 
		{ 
			this.k++;
			this.t=Tfinal+(Tdebut-Tfinal)*Math.exp(coefPente*this.k);
			return true;		  
		}
	}
}