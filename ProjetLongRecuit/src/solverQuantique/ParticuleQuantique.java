package solverQuantique;



public class ParticuleQuantique {

	RepresentationGraphique representationGraphique ;
	int nbReplique ;
	RepresentationEtat[] representationsEtat ;
	EnergieTotale energieTotale ;
	double actualEc ;

	public ParticuleQuantique ( RepresentationGraphique representationGraphique, 
			int nbReplique, RepresentationEtat[] representationsEtat, EnergieTotale energieTotale ) {
		this.nbReplique = nbReplique ;
		this.representationsEtat=representationsEtat ;
		this.energieTotale=energieTotale ;
	}
}
