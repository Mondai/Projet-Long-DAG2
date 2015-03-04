package solver.quantique;

/**
 * Classe repr�sentant un double.
 * <p>
 * Utilis� dans RecuitQuantiqueAccelere_Iter, afin de passer des nombres double "par r�f�rence". 
 * <p>
 * En effet, les types primitifs sont immutables et pass�s par valeurs dans des fonctions tandis que les objets sont mutables et pass�s par r�f�rence.
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
