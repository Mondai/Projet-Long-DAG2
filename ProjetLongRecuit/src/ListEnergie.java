import java.util.ArrayList;
import java.util.List;


public class ListEnergie extends IListEnergie {

	ArrayList<Double> list;
	int z;
	
	
	public ListEnergie() {
		this.list=new ArrayList<Double>();
		this.z=1;
	}
	
	
	
	
	
	public void add(double energie, int echantillonage) {
		if (this.z == echantillonage) {
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
	
	public void init() {
		this.z=1;
		this.list= new ArrayList<Double>();
	}
	
	
}
