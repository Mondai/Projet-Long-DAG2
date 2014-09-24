// Interface représentant le concept abstrait de mutation dans l'algorithme de recuit

public interface IMutation {

	public void faire(Probleme probleme); // le problème fait une mutation sur demande
	public void defaire(Probleme probleme); // la dernière mutation est annulée
	
}
