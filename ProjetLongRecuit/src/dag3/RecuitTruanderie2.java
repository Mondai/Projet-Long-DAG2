package dag3;
import modele.*;
import parametrage.*;
import mutation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
		
		ArrayList<Etat> e = p.getEtat();
		
		Ponderation J = new Ponderation(p.getGamma());
		double deltapot  = 0;
		double energie = (e.get(0)).getEnergie();
		double energieBest = energie;
		
		int i = 0;
		int mutationsAcceptees = 0;
		int mutationsTentees = 0;
		
		double delta = 0;
		double pr = 0;
		
		while(i<nombreIterations && energieBest!=0){
			 
			double jG = J.calcul(p.getT(),nombreEtat);
			 
			for (int j = 0 ; j < nombreEtat ; j++){
				// on effectue M  fois la mutation sur chaque r�plique avant de descendre gamma
				
				Etat r = e.get(j);
				
				for(int k=0; k<M; k++){
					mutationsTentees++;
					
					m.maj(p, r);
					
					deltapot =  m.calculer(p,r);
					
					delta = deltapot/nombreEtat  - jG*p.differenceSpins(r,m);
						
					//VA REGARDER SI L'ON APPLIQUE LA MUTATION OU NON
					pr=probaAcceptation(delta,deltapot,p.getT());
					
					if(pr>Math.random()){
						mutationsAcceptees++;

						m.faire(p,r);

						energie = r.getEnergie();
						
					}
					if (energie < energieBest){
						energieBest = energie;
						if(energieBest==0){	// condition de fin
							System.out.println("Mutations tent�es : " + mutationsTentees);
							System.out.println("Mutations accept�es : " + mutationsAcceptees);
							System.out.println("Gfin : "+p.getGamma().getGamma());
							return energieBest;
						}

					}
				}
			}
			//UNE FOIS EFFECTUEE SUR tout les etat de la particule on descend gamma
			p.majgamma();
			J.setGamma(p.getGamma());
			Collections.shuffle(p.getEtat());

			i++;
			
		}
		System.out.println("Mutations tent�es : " + mutationsTentees);
		System.out.println("Mutations accept�es : " + mutationsAcceptees);
		
		return energieBest;

	}
}
