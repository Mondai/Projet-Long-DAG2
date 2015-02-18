package probleme2D;
import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.MutationElementaire;

public class Hauteur extends EnergiePotentielle {

	

	@Override
	public double calculer(Etat etat) {
		int x=((Position2D) etat).getX();
		int y=((Position2D) etat).getY();
		
		int largeur =((Position2D) etat).relief.largeur;
		int hauteur =((Position2D) etat).relief.hauteur;
		
		double Energie = (((Position2D) etat).getRelief().getMatrice2D())[(((x % largeur) + largeur) % largeur)][(((y % hauteur) + hauteur) % hauteur)];
		
		if (Energie<((Position2D)etat).getMeilleureEnergie()) {
			((Position2D)etat).setMeilleureEnergie(Energie);
		}
		
		return Energie;
	}

	@Override
	public double calculerDeltaE(Etat etat, MutationElementaire mutation) {
		
		MutationElementairePixel mutationElementaire = (MutationElementairePixel) mutation;
		int largeur =((Position2D) etat).relief.largeur;
		int hauteur =((Position2D) etat).relief.hauteur;
		
		
		
		
		// Energie précédente
		int x=((Position2D) etat).getX();
		int y=((Position2D) etat).getY();
		int Eprecedent=(((Position2D) etat).getRelief().getMatrice2D())[(((x % largeur) + largeur) % largeur)][(((y % hauteur) + hauteur) % hauteur)];
		
		// Nouvelle Energie
		int xnew=((Position2D) etat).getX()+mutationElementaire.deltaX;
		int ynew=((Position2D) etat).getY()+mutationElementaire.deltaY;
		/*
		System.out.println("hauteur "+hauteur);
		System.out.println("largeur "+largeur);
		System.out.println("xnew "+xnew);
		System.out.println("ynew "+ynew); */
		
		int Enew=(((Position2D) etat).getRelief().getMatrice2D())[(((xnew % largeur) + largeur) % largeur)][(((ynew % hauteur) + hauteur) % hauteur)];
		
		return Enew-Eprecedent;
	}

}
