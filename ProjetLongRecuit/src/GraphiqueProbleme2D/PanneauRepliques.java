package GraphiqueProbleme2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JPanel;


public class PanneauRepliques extends JPanel {
	private Image img;
	int largeur;
	int hauteur;
	int[] setDesX;
	int[] setDesY;
	
	
	
	
	 public PanneauRepliques(Image img, int[] setDesX, int[] setDesY) {
		super();
		this.img = img;
		this.largeur=img.getWidth(null);
		this.hauteur=img.getHeight(null);
		this.setDesX = setDesX;
		this.setDesY = setDesY;
		
	}
	 
	 public PanneauRepliques(Image img) {
			super();
			this.img = img;
			this.largeur=img.getWidth(null);
			this.hauteur=img.getHeight(null);
		}

	 
	 public PanneauRepliques() {
			super();
		}


	public Image getImg() {
		return img;
	}



	public int[] getSetDesX() {
		return setDesX;
	}



	public int[] getSetDesY() {
		return setDesY;
	}



	public void setImg(Image img) {
		this.img = img;
	}



	public void setSetDesX(int[] setDesX) {
		this.setDesX = setDesX;
	}

	public void deltaSetDesX(int X,int i) {
		this.setDesX[i] = (X);
		//System.out.println("x "+X);
		//System.out.println("largeur "+largeur);
		//System.out.println("x modé"+(((X % largeur) + largeur) % largeur));
		
	}
	
	public void deltaSetDesY(int Y,int i) {
		
		this.setDesY[i] = (Y);
		//System.out.println("y "+Y);
		//System.out.println("hauteur "+hauteur);
		//System.out.println("y modé"+(((Y % hauteur) + hauteur) % hauteur));
	}
	
	

	public void setSetDesY(int[] setDesY) {
		this.setDesY = setDesY;
	}



	public void paintComponent(Graphics g, int numero) {
		    //g.drawImage(this.img, 0, 0, this.getWidth(),this.getHeight(),null);
			
			g.drawImage(this.img, 0, 0,null);
	        g.setColor( new Color(255,0,0));
	        
	        for (int i=0;i<setDesX.length;i++) {
	        	g.fillOval(this.setDesX[i], this.setDesY[i], 8, 8);
	        }
	        g.setColor( new Color(0,255,0));
	        g.fillOval(this.setDesX[numero], this.setDesY[numero], 8, 8);
	        
	    }
	

}
