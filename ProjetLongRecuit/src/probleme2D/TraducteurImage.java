package probleme2D;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;


public class TraducteurImage {
	

	public static int[][] traduire(String lien) throws IOException {

		
		Image image = (new ImageIcon(lien).getImage());
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image
		        .getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		int hauteur = bimage.getHeight();
		int largeur = bimage.getWidth();
		
		// Création de la matrice de couleurs
		
		int[][] u= new int[hauteur][largeur];
		
		for (int i=0;i<largeur;i++) {
			for(int j=0;j<hauteur;j++) {
				u[j][i]=bimage.getRGB(i,j);
			}
		}
		System.out.println(bimage.getData().toString());
		return u;

	}
	
}
