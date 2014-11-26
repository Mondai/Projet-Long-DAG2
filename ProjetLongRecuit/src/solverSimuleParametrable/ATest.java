package solverSimuleParametrable;

public class ATest {

	public static void main(String[] args) {

	Temperature T = new TemperatureLineaire( 15000, 5000,1000);
	ConstanteK K = new ConstanteKConstant (1.) ; 
	RecuitSimuleParametrable leTest = new RecuitSimuleParametrable(T,K);
	
	
	} 

}
