package probleme2D;
import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.MutationElementaire;

public class Hauteur extends EnergiePotentielle {

	

	@Override
	public double calculer(Etat etat) {
		int x=((Position2D) etat).getX();
		int y=((Position2D) etat).getY();

		
		double Energie = (((Position2D) etat).getRelief().getMatrice2D())[y][x];
		
		
		if (Energie<((Position2D)etat).getMeilleureEnergie()) {
			((Position2D)etat).setMeilleureEnergie(Energie);
		}
		
		return Energie;
	}

	@Override
	public double calculerDeltaE(Etat etat, MutationElementaire mutation) {
		
		MutationElementairePixel mutationElementaire = (MutationElementairePixel) mutation;
		
		int hauteur=((Position2D) etat).getRelief().getHauteur();
		int largeur=((Position2D) etat).getRelief().getLargeur();


		// Energie précédente
		int x=((Position2D) etat).getX();
		int y=((Position2D) etat).getY();
		int Eprecedent=(((Position2D) etat).getRelief().getMatrice2D())[y][x];
		
		// Nouvelle Energie
		int xnew=((Position2D) etat).getX()+mutationElementaire.deltaX;
		int ynew=((Position2D) etat).getY()+mutationElementaire.deltaY;
		
		xnew=(((xnew % largeur) + largeur) % largeur);
		ynew=(((ynew % hauteur) + hauteur) % hauteur);
		/*
		System.out.println("hauteur "+hauteur);
		System.out.println("largeur "+largeur);
		System.out.println("xnew "+xnew);
		System.out.println("ynew "+ynew); */
		
		int Enew=(((Position2D) etat).getRelief().getMatrice2D())[ynew][xnew];
		double DeltaEp=Enew-Eprecedent;
		//if (DeltaEp!=0) {
		//System.out.println("====================================================");
		//System.out.println("delta Ep "+DeltaEp);
		//System.out.println("====================================================");
		//}
		return Enew-Eprecedent;
	}

}
