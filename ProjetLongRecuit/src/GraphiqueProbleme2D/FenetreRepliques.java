package GraphiqueProbleme2D;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
 
public class FenetreRepliques extends JFrame {
     
	PanneauRepliques panneau;
	int echantillonage;
	int compteur=0;
	
    private static final long serialVersionUID = 1L;
     
    public FenetreRepliques(int echantillonage){
    	this.panneau=new PanneauRepliques();
    	this.echantillonage=echantillonage;
    };
    
    
    public FenetreRepliques(String path,PanneauRepliques panneau, int echantillonage){
    	this.echantillonage=echantillonage;
    	this.panneau =panneau;
        this.setContentPane((panneau));
        
        Image image = (new ImageIcon(path).getImage());
    	BufferedImage bimage = new BufferedImage(image.getWidth(null), image
    	        .getHeight(null), BufferedImage.TYPE_INT_ARGB);
    	
    	//this.setResizable(false);
    	
    	int largeur =bimage.getWidth();
    	int hauteur = bimage.getHeight();
    	this.setSize(600,600);
    	
       
        this.setVisible(true);
        
        
        
        
    }
    
    public void updateGraphics(int numero) {
    	if (this.compteur==0) {
        this.panneau.paintComponent(getGraphics(),numero);
    	} 
    	this.compteur=(this.compteur+1)%this.echantillonage;
    }
    
 
}