package solver.quantique;

/**
 * Classe représentant un double.
 * <p>
 * Utilisé dans RecuitQuantiqueAccelere_Iter, afin de passer des nombres double "par référence". 
 * <p>
 * En effet, les types primitifs sont immutables et passés par valeurs dans des fonctions tandis que les objets sont mutables et passés par référence.
 * 
 * @see RecuitQuantiqueAccelere_Iter
 */
public class MutableDouble {

	/**
	 * valeur de MutableDouble en double
	 */
	private double value;

	public MutableDouble(double value) {
		this.value = value;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
}
