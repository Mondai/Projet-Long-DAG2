package solver.parametres;

/**
 * {@inheritDoc}
 * <p>
 * A chaque appel de modification de la température, la température descend d'un pas constant.
 * Le nombre réel d'itérations est une approximation puisque le pas linéaire est un double à précision limitée.
 */
public class FonctionLineaire extends Fonction {

	/**
	 * Valeur approximée du pas(en négatif) pour descendre de la 
	 * température de début à celle de fin en le nombre d'itérations voulu.
	 */
	double pasLineaire;
	
	/**
	 * Création d'une variable pasLineaire.
	 */
	public FonctionLineaire(double tdebut, double tfinal, int nbIteration) {
		super(tdebut,tfinal,nbIteration); 
		this.pasLineaire = (this.Tfinal-this.Tdebut)/this.nbIteration ; 
}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Ici, on ajoute le pas à la température et on vérifie si la température résultante est inférieure à la température de fin.
	 */
	public boolean modifierT() {
		if (this.t+this.pasLineaire < this.Tfinal) {
			return false;
		} else {
			this.t += this.pasLineaire; //pas lineaire negatif
			return true;
		}
	}
}