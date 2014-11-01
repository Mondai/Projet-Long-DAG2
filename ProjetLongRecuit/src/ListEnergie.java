import java.util.ArrayList;
import java.util.List;


public class ListEnergie extends IListEnergie {

	ArrayList<Double> list;
	ArrayList<Double> listTotale;
	int z;
	int echantillonage;
	int taille; // nombre d'energies calculées jusqu'a maintenant
	
	
	public ListEnergie(int echantillonage) {
		this.list = new ArrayList<Double>();
		this.listTotale = new ArrayList<Double>();
		this.z=1;
		this.echantillonage = echantillonage ;
	}	
	
	public void add(double energie) {
		this.listTotale.add(energie);
		if (this.listTotale.size() > 10) this.listTotale.remove(0);
		if (this.z == this.echantillonage) {
			this.list.add(energie);
			this.z=1;
			
		}
		else {
			this.z++;
		}
	}
	
	public List<Double> getlistEnergie() {
		return this.list;
	}
	
	public List<Double> getlistEnergieTotale() {
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
	
	
}
