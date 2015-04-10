package dag3;

import parametrage.ParametreGamma;

public class ParametreGammaLineaire extends ParametreGamma{

	/**
	 * Gamma début
	 */
	private double gamma;
	private double facteur;
	private double gammafin;
	
	/**
	 * Construit un paramètre Gamma
	 * @param temp
	 * C'est la réelle valeur numérique de Gamma.
	 * @param froid
	 * C'est le facteur de décroissance exponentiel de Gamma.
	 * @param temperaturefin
	 * Lorsque le recuit se termine, on fait en sorte de s'arrêter à cette valeur.
	 */
	public ParametreGammaLineaire(double temp,double froid,double temperaturefin){
		super(temp,froid,temperaturefin);
	}
	
// Get et Set de la temperature initiale
	/**
	 * @return
	 * Retourne la valeur numérique de Gamma
	 */
	public double getGamma() {
		return this.gamma;
	}

	/**
	 * Permet d'établir la nouvelle valeur numérique de Gamma
	 * @param temperature
	 * Nouvelle valeur numérique de Gamma
	 */
	public void setGamma(double temperature) {
		this.gamma = temperature;
	}
//Get et Set de la temperature de fin	
	/**
	 * @return
	 * Retourne la valeur finale de Gamma
	 */
	public double getGammaFin(){
		return this.gammafin;
	}
	
	/**
	 * Permet d'établir la nouvelle valeur finale de Gamma
	 * @param gammafin
	 * Nouvelle valeur finale de Gamma
	 */
	public void setGammaFin(double gammafin){
		this.gammafin = gammafin;
	}
//Get et Set du refroidissement, refroidissement de la temperature Recuit en exponentiel et lineaire
	/**
	 * Diminue la valeur de Gamma de manière exponentielle grâce au facteur associé.
	 */
	public void refroidissementExp(){
		//this.gamma *= 1-this.facteur;
		this.gamma -= this.facteur; // comportement lineaire
	}
	
	/**
	 * Diminue la valeur de Gamma de manière linéaire grâce au facteur associé. Inutilisé pour le recuit.
	 */
	public void refroidissementLin(){
		this.gamma -= this.facteur;
	}

	/**
	 * @return
	 * Retourne le facteur de décroissance de Gamma
	 */
	public double getFacteur() {
		return this.facteur;
	}

	/**
	 * Permet d'établir le nouveau facteur de décroissance de Gamma
	 * @param facteurDeRefroidissement
	 * Nouveau facteur de décroissance de Gamma
	 */
	public void setFacteur(double facteurDeRefroidissement) {
		this.facteur = facteurDeRefroidissement;
	}
	
	
}