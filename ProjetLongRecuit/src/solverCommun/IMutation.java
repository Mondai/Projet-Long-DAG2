package solverCommun;
// Interface repr�sentant le concept abstrait de mutation dans l'algorithme de recuit

public interface IMutation {

	abstract public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat); 	// renvoie une MutationElementaire possible
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation); 	// le probl�me fait une mutation sur demande
	
}
