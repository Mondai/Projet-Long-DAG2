package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import solver.Coloriage;
import solver.RecuitSimule;


public class SortieGraphique {

	RecuitSimule recuit; // !!!!!!!! --> RecuitSimule pour l'instant car pas de mï¿½thode getlistEnergie dans IRecuit
	int tailleEchantillon;
	Coloriage coloriage;
	double Tdebut;
	double Tfin;
	double kb;
	int echantillonnage;
	




	public SortieGraphique( RecuitSimule recuit,
			int tailleEchantillon, Coloriage coloriage,
			double tdebut, double tfin, double kb,int echantillonnage) {
		super();
		this.recuit = recuit;
		this.tailleEchantillon = tailleEchantillon;
		this.coloriage = coloriage;
		this.Tdebut = tdebut;
		this.Tfin = tfin;
		this.kb = kb;
		this.echantillonnage=echantillonnage;
	}



	public void SortieTexte( String nomTexte) {

		try {
			File f = new File (""+nomTexte);
			PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
			
			pw.println("Sortie Graphique : ");
			pw.println("Taille Echantillon : " + this.tailleEchantillon);
			pw.println("Echantillonnage : " + this.echantillonnage);
			pw.println("Température de départ : " + this.Tdebut);
			pw.println("Température de fin : " + this.Tfin);
			pw.println("Constante k : " + this.kb);
			pw.println("Nombre d'itération : " + this.recuit.getNbPoints());
			pw.println("Seed du coloriage : " + this.coloriage.getSeed());
			pw.println(this.recuit.toString());

			for (int i=0; i<tailleEchantillon ; i++) {
				
				coloriage.initialiser();
				//RecuitSimuleExponentiel recuit = new RecuitSimuleExponentiel(this.kb, this.Tdebut, this.Tfin, this.N, this.nombrePalliers,this.echantillonage);
				
				this.recuit.lancer(coloriage);
				/*
				double[] U = recuit.gethistoriqueEnergie();

				
				for (int j=0; j<nombrePalliers;j++) {
					pw.print(" "+U[j]);  // Ecriture dans le fichier texte du vecteur
					
				}
				pw.print("];");
				
				*/
				pw.print("u"+i+"=");
				List<Double> list = recuit.getListEnergie().getlistEnergie();
				pw.print(list.toString());
				System.out.println(list.get(list.size()-1));
				pw.print(";");
				pw.println("");
			}
				
				pw.print("u=[");
				for (int k=0; k< this.tailleEchantillon;k++) {
					pw.print("u"+k+"; ");
				}
				pw.println("];");
				pw.println("vect="+this.echantillonnage+"*(1:length(u0));");
				
				//pw.print("boxplot(u,vect);");
				
				pw.close();
			
		} catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}



	}
}


