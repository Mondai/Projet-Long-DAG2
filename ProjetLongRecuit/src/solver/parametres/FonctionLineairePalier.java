package solver.parametres;

/**
 * {@inheritDoc}
 * <p>
 * La température ne descend qu'après un certain nombre d'appels de modification de température, dépendant du palier.
 * La descente est constante(linéaire).
 * Le nombre réel d'itérations est une approximation puisque le pas linéaire est un double à précision limitée.
 */
public class FonctionLineairePalier extends Fonction {
	
	/**
	 * Valeur approximée du pas(en négatif) pour descendre de la 
	 * température de début à celle de fin en le nombre d'itérations voulu.
	 */
	double pasLineaire;
	
	/**
	 * Nombre d'itérations que l'on va faire avant de descendre de température.
	 */
	int palier;
	/**
	 * Nombre d'itérations faites sur le palier courant
	 */
	int compteurPalier;
	
	public void init(){
		super.init();
		this.compteurPalier=1;
	}
	
	/**
	 * Création des variables pasLineaire et compteurPalier.
	 */
	public FonctionLineairePalier(double tdebut, double tfinal,int nbIteration, int palier) {
		super(tdebut, tfinal, nbIteration);
		this.pasLineaire = (this.Tfinal-this.Tdebut)/this.nbIteration ;
		this.palier = palier;
		this.compteurPalier = 1;
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * Ici, on vérifie si on doit incrémenter le nombre d'itérations faites sur le présent palier ou aller au prochain palier de température.
	 * Dans le deuxième cas, on ajoute le pas à la température.
	 */
	public boolean modifierT() {
		if (this.t < this.Tfinal) {
			return false;
		} else if(this.compteurPalier == palier) {
			this.t += this.pasLineaire;
			this.compteurPalier = 1;
			return true;
		}else{
			this.compteurPalier++;
			return true;
		}
	}

	// a completer !
}
