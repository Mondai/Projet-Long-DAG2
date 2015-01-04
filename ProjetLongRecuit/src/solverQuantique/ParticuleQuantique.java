package solverQuantique;



public class ParticuleQuantique {

	DataProblem dataProblem ;
	int nbReplique ;
	RepresentationEtat[] representationsEtat ;
	EnergieTotale energieTotale ;
	double actualEc ;

	public ParticuleQuantique ( DataProblem dataProblem, 
			int nbReplique, RepresentationEtat[] representationsEtat, EnergieTotale energieTotale ) {
		this.dataProblem=dataProblem ;
		this.nbReplique = nbReplique ;
		this.representationsEtat=representationsEtat ;
		this.energieTotale=energieTotale ;
		// et actualEc ?
	}
}
