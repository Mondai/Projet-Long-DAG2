package solver;
import java.util.List;


public abstract class IListEnergie {

	public abstract List<Double> getlistEnergie();
	
	public abstract List<Double> getlistEnergieTotale();
	
	public abstract void add(double energie);
	
	public abstract void init();
	
	public abstract int getTaille();
	
	public abstract void augmenteTaille();
	
	public abstract void initTaille();
	
}


