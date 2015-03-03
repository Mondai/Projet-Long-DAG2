package solver.graphique;
import java.util.ArrayList;
import java.util.List;


/**
 * Une instance de la classe ListEnergie stocke des
 * valeurs avec un certain echantillonage
 * Elle est utilis�e pour observer l'�volution de l'�nergie en fonction du nombre d'it�rations
 * Elle comprend deux listes, la premiere liste qui echantillone, et la deuxi�me moyene.
 * 
 * @author DAG2
 */

public class ListEnergie extends IListEnergie {

	ArrayList<Double> list;
	ArrayList<Double> listTotale;
	int z;
	int echantillonage;
	int taille; // nombre d'energies calcul�es jusqu'a maintenant
	int fenetreK;
	
	
	public ListEnergie(int echantillonage, int fenetreK) {
		this.list = new ArrayList<Double>();
		this.listTotale = new ArrayList<Double>();
		this.z=1;
		this.echantillonage = echantillonage ;
		this.fenetreK = fenetreK;
	}	
	
	
	/**
	 * Ajoute dans la liste si z=echantillonage, et z r�initialis�
	 * sinon on incr�mente z
	 */
	public void add(double energie) {
		if (this.z == this.echantillonage) {
			this.list.add(energie);
			this.z=1;
			
		}
		else {
			this.z++;
		}
	}
	
	/**
	 * Stockage des n derni�res valeurs, afin de moyenner
	 */
	
	public void addTotal(double energieCourante) {
		this.listTotale.add(energieCourante);
		if (this.listTotale.size() > (this.fenetreK + 1) ) this.listTotale.remove(0);
	}
	
	/**
	 * getter de la liste
	 */
	
	public ArrayList<Double> getlistEnergie() {
		return this.list;
	}
	
	/**
	 * getter de la liste des n derniers
	 */
	
	
	public ArrayList<Double> getlistEnergieTotale() {
		return this.listTotale;
	}
	
	public void init() {
		this.z=1;
		this.list= new ArrayList<Double>();
		this.listTotale= new ArrayList<Double>();
	}
	
	public int getTaille() {
		return this.taille;
	}
	
	public void initTaille() {
		this.taille = 0;
	}
	
	public void augmenteTaille() {
		this.taille ++;
	}

	@Override
	public int getFenetreK() {
		return this.fenetreK;		
	}
	
	
}
