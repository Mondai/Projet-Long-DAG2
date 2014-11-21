package solverCommun;
// Interface repr�sentant le concept abstrait de mutation dans l'algorithme de recuit

public interface IMutation {

	public void faire(Probleme probleme); // le probl�me fait une mutation sur demande
	public void defaire(Probleme probleme); // la derni�re mutation est annul�e
	public void faire(Probleme particule, Etat etat);
	public void defaire(Probleme particule, Etat etat);
}
