package solverQuantique;

public class EnergieTotale {
	EnergiePotentielle energiePotentielle ;
	EnergieCinetique energieCinetique ;

	public double calculer(ParticuleQuantique particuleQuantique, int numeroReplique) {
		double Et =this.energiePotentielle.calculer(particuleQuantique,numeroReplique)+
				this.energieCinetique.calculer(particuleQuantique) ;
		return Et ; }

	public double calculerDeltaE(ParticuleQuantique etatQuantique, int numeroReplique,MutationElementaire mutation) {
		double deltaE = this.energiePotentielle.calculerDeltaE(etatQuantique,numeroReplique,mutation)+
				this.energieCinetique.calculerDeltaE(etatQuantique,mutation) ;
		return deltaE ;
	}


}

/*
* cette classe permet de simplifier le code des classes filles de ProblemeQuantique, en particulier RecuitQuantiqueParametrable

*/