import java.io.IOException;


public class TESTTEST {

	public static void main(String[] args) throws IOException {
		
		double Tdebut=7000;
		double Tfin = 0; // ne marche pas avec 0
		//double facteur = 0.99;
		int N = 10; // nombre it�ration par pallier de T
		int kb=7000;
		int nombrePalliers=100;
		int echantillonage=300;
		
		Conflits energie = new Conflits();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		Graphe graphe = Traducteur.traduire("data/le450_25a.col");
		Coloriage coloriage = new Coloriage(energie, mutation, 25 ,graphe);
		
		ListEnergie listEnergie = new ListEnergie(echantillonage);
		RecuitSimuleLineaire recuit = new RecuitSimuleLineaire(kb,Tdebut,Tfin,1,N,listEnergie);
			
		// SortieGraphique(nombrePalliers,recuit,tailleEchantillon,coloriage,mutation,N,tdebut,tfin, kb)
		SortieGraphique albert = new SortieGraphique(1000,recuit,10,coloriage,10,1000,1,0.3, echantillonage);
		albert.SortieTexte("SortiesGraphiques/test1");
		
		System.out.println(Math.pow(Tfin/Tdebut, 1./nombrePalliers));
		
	}
	
	}