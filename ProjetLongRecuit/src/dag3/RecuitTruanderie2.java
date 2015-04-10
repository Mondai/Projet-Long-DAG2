package dag3;
import modele.*;
import parametrage.*;
import mutation.*;
import io.Writer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

// Cette classe definit le probleme du recuit. Il se charge d'effectuer les mutations elementaires, de calculer l'energie et de diminuer T...

/**
 * 
 * @author Pierre
 *
 */
public class RecuitTruanderie2 extends JFrame
{
	public double solutionNumerique;
	private static final long serialVersionUID = 2L;

	static int nbTours = 1;
	static ParametreK K = new ParametreK(1);



	/**
	 * Methode qui calcule la probabilit� d'acceptation d'un �tat mut�. 
	 * Elle est utilis�e dans la methode solution qui effectue le recuit.
	 * @param deltaE
	 * Variation de H
	 * @param deltaEpot
	 * Variation de H potentiel
	 * @param temperature
	 * Temp�rature du recuit
	 * @return
	 * Probabilit� (entre 0 et 1) d'effectuer la mutation
	 * @throws IOException
	 */
	public static double probaAcceptation(double deltaE, double deltaEpot, Temperature temperature) throws IOException 
	{	if(deltaE<=0 || deltaEpot<0){
		return 1;
	}
	return Math.exp( (-deltaE) / (K.getK()*temperature.getValue()));

	}	
	
	
	/**
	 * C'est la m�thode qui effectue le recuit quantique. 
	 * @param p
	 * Probl�me construit par l'utilisateur
	 * @param m
	 * Une mutation al�atoire correspondant au probl�me trait�.
	 * Les g�n�rations de mutation al�atoires au fil du recuit s'effectueront avec la m�thode maj que l'utilisateur aura impl�ment� dans sa classe (voir IMutation)
	 * @param nombreIterations
	 * Nombre d'iterations pour chaque r�plique. Le nombre de r�pliques, on le rappelle, est d�fini dans Probl�me.
	 * @param seed
	 * Rentrer 1 en argument
	 * @param M
	 * Nombre de fois cons�cutives que l'on traite une r�plique. Rentrer 1 pour une utilisation normale
	 * @param sortie
	 * Fichier .txt dans lequel on stocke le r�sultat final du recuit.
	 * @return
	 * Solution du recuit : la meilleure �nergie rencontr�e par la particule
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public  double solution(Probleme p,IMutation m,int nombreIterations, int seed, int M) throws IOException, InterruptedException
	{	
		int nombreEtat=p.nombreEtat();
		Probleme pBest = p.clone();
		
		ArrayList<Etat> e = p.getEtat();
		System.out.println(e);
		Ponderation J = new Ponderation(p.getGamma());
		double Epot = p.calculerEnergiePotentielle();
		System.out.println("Epot " + Epot);
		double compteurSpinique = p.calculerEnergieCinetique();
		System.out.println("CompteurSpin " + compteurSpinique);
		double E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
		System.out.println("E " + E);
		double deltapot  = 0;
		double energie = (e.get(0)).getEnergie();
		System.out.println("En : " + energie);
		double energieBest = energie;
		
		int i = 0;
		int mutationsAcceptees = 0;
		int mutationsTentees = 0;
		
		while(i<nombreIterations && energieBest!=0){
			
			
			 E = Epot-J.calcul(p.getT(), nombreEtat)*compteurSpinique;
			 
			//System.out.println("Iter");
			 
			for(int j=0;j<nombreEtat;j++){// on effectue M  fois la mutation sur chaque r�plique avant de descendre gamma
				
				Etat r = e.get(j);
				
				for(int k=0; k<M; k++){
					//System.out.println(energieBest);
					mutationsTentees++;
					
					//System.out.println("E");
					
					m.maj(p, r); //TODO pas sur de ca...
					
					deltapot =  m.calculer(p,r);
					System.out.println("deltapot : " + deltapot);
					
					double delta = deltapot/nombreEtat  -J.calcul(p.getT(),nombreEtat)*p.differenceSpins(r,m);
					
					//test deltaE < 0
					/*
					if (deltapot < 0){
						mutationsAcceptees++;
						System.out.println("DE < 0 : " + r2);
						m.faire(p2,r2);
						compteurSpinique += p.differenceSpins(r2,m);
						
						e.set(j, r2);
						p.setEtat(e);
						
						Epot += deltapot/nombreEtat;
						//System.out.println("Epot "+Epot);
						E += delta;// L'energie courante est modifi�e
						//System.out.println("delta "+delta);
						energie += deltapot;
						//System.out.println("deltapot "+deltapot);
						//System.out.println("energie "+energie);
					}*/
			//		else{
						
						//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON
						double pr=probaAcceptation(delta,deltapot,p.getT());
						if(pr>Math.random()){
							
							mutationsAcceptees++;
							//System.out.println("PA " + r2);
							//System.out.println("avant "+r.getEnergie());
							m.faire(p,r); //redonne comportement du recuit DAG3 --> tr�s bizarre
							
							e.set(j, r);
							p.setEtat(e);
							
							Epot += deltapot/nombreEtat;
							//System.out.println("Epot "+Epot);
							E += delta;// L'energie courante est modifi�e
							//System.out.println("delta "+delta);
							energie = r.getEnergie();
							//System.out.println("Dpot "+deltapot);
							//System.out.println("apres "+r.getEnergie());
							//energie += deltapot; // TODO supprimer!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
							//System.out.println("deltapot "+deltapot);
							//System.out.println("energie "+energie);
							
						}
						if (energie < energieBest){
							pBest.setEtat(e);
							//energie = r.getEnergie();
							energieBest = energie;
							System.out.println(energieBest);
						}
				//	}
					/*if(compteurpourlasortie%1000==0){
					Writer.ecriture(compteurpourlasortie,distanceBest, sortie);
					}
					compteurpourlasortie++;
					*/	
				}
				
				
			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());
			
			i++;
			
		}
		// nb mutations 
		System.out.println("Mutations tent�es : " + mutationsTentees);
		System.out.println("Mutations accept�es : " + mutationsAcceptees);
		
		return energieBest;

	}
	
	



}