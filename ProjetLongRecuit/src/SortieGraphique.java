
import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class SortieGraphique {

	int nombrePalliers;   // Nombre de points à placer sur le plot
	RecuitSimule recuit; // !!!!!!!! --> RecuitSimule pour l'instant car pas de méthode getlistEnergie dans IRecuit
	int tailleEchantillon;
	Coloriage coloriage;
	int N; // Nombre de changemens par Pallier
	int Tdebut;
	int Tfin;
	double kb;
	int echantillonage;
	




	public SortieGraphique(int nombrePalliers, RecuitSimule recuit,
			int tailleEchantillon, Coloriage coloriage,
			int N, int tdebut, int tfin, double kb,int echantillonage) {
		super();
		this.nombrePalliers = nombrePalliers;
		this.recuit = recuit;
		this.tailleEchantillon = tailleEchantillon;
		this.coloriage = coloriage;
		this.N=N;
		Tdebut = tdebut;
		Tfin = tfin;
		this.kb = kb;
		this.echantillonage=echantillonage;
	}



	public void SortieTexte( String nomTexte) {

		try {
			File f = new File (""+nomTexte);
			PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));


			for (int i=0; i<tailleEchantillon ; i++) {
				
				coloriage.initialiser();
				//RecuitSimuleExponentiel recuit = new RecuitSimuleExponentiel(this.kb, this.Tdebut, this.Tfin, this.N, this.nombrePalliers,this.echantillonage);
				
				this.recuit = recuit;
				this.recuit.lancer(coloriage);
				/*
				double[] U = recuit.gethistoriqueEnergie();

				
				for (int j=0; j<nombrePalliers;j++) {
					pw.print(" "+U[j]);  // Ecriture dans le fichier texte du vecteur
					
				}
				pw.print("];");
				
				*/
				pw.print("u"+i+"=");
				pw.print(recuit.listEnergie.getlistEnergie().toString());
				System.out.println(recuit.listEnergie.getlistEnergie().size());
				pw.print(";");
				pw.println("");
			}
				
				pw.print("u=[");
				for (int k=0; k< this.tailleEchantillon;k++) {
					pw.print("u"+k+"; ");
				}
				pw.println("];");
				pw.println("vect="+this.echantillonage+"*(1:length(u0));");
				pw.print("boxplot(u,vect);");
				
				pw.close();
			
		} catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}



	}
}


