// Interface représentant le concept abstrait de l'Energie dans l'algorithme de recuit

public interface IEnergie {

	// Le problème doit être capable de calculer son energie dans une situation donnée
	public double calculer(Probleme probleme);
		
}
