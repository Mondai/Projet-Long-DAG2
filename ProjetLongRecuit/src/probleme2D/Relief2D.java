package probleme2D;

public class Relief2D {
	
	
	
	int hauteur;
	int largeur;
	int[][] Matrice2D;
	
	
	
	

	public Relief2D(int hauteur, int largeur, int[][] matrice2d) {
		super();
		this.hauteur = hauteur;
		this.largeur = largeur;
		Matrice2D = matrice2d;
	}
	public int getHauteur() {
		return hauteur;
	}
	public int getLargeur() {
		return largeur;
	}
	public int[][] getMatrice2D() {
		return Matrice2D;
	}
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
	public void setMatrice2D(int[][] matrice2d) {
		Matrice2D = matrice2d;
	}
		


}
