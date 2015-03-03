package solver.quantique;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
 
public class PanneauAffichageQuantique extends JPanel {
  public void paintComponent(Graphics g, double rapport, double Jr, double MeilleureEnergie){
	
	Color noir = Color.black;
	((Graphics2D)g).setBackground(noir);
	Font font = new Font("Courier", Font.BOLD, 20);
	//g.drawRect(0, 0, 600, 200);
	g.fillRect(0, 0, 600, 300);
	g.setFont(font);
	g.setColor(Color.red);  
	  
	g.drawString("Rapport des énergies : "+rapport, 20, 60);
    g.drawString("Valeur de Jr : "+Jr, 20, 120);
    g.drawString("Meilleure Energie : "+MeilleureEnergie, 20, 180);
    
    
  }               
}