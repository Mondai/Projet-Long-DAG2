package solver.quantique;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import GraphiqueProbleme2D.PanneauRepliques;
 
public class FenetreAffichageQuantique extends JFrame {
     
	PanneauAffichageQuantique panneau;
	int compteur=0;
	
    private static final long serialVersionUID = 1L;
     
    public FenetreAffichageQuantique(){
    	this.panneau=new PanneauAffichageQuantique();
    };
    
    
    public FenetreAffichageQuantique(PanneauAffichageQuantique panneau){
    	this.panneau =panneau;
        this.setContentPane((panneau));
    	this.setSize(600,300);
        this.setVisible(true);
        
        
        
        
    }
    
    public void updateGraphics(double rapport, double Jr, double meilleureEnergie) {
        this.panneau.paintComponent(getGraphics(),rapport, Jr, meilleureEnergie);
    }
    
 
}