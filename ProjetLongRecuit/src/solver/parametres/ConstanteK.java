package solver.parametres;

public abstract class ConstanteK {    
 public double k ;
 
 /**
  * Classe qui décrit le comportement de la "constante" k dans le calcul de la probabilité.
  * <p>
  * Dans la majorité des cas, elle est constante.
  * Cependant, elle dispose de méthodes qui permettrait de changer le comportement en fonction des 
  * dernières valeurs d'énergie (moyenne sur une fenêtre par exemple).
  * <p>
  * Le but de la constante K est de garder en moyenne le rapport deltaE/K unitaire.
  * Ainsi la proba d'accepation est de l'ordre de exp(-1/T).
  * La probablite d'accepter une solution plus mauvaise est moins conditionnee par le deltaE. <br>
  *
  * NB : il faut toujours eviter que k soit proche de 0.
  * Dans ce cas, la proba explose independament de T, on ne sort plus des minimums locaux.
  * Il faut essayer de garder toujours k strictement supérieur à 1.
  */
 public ConstanteK() {} ; 
 
 public void init(){};
 public void calculerK(double deltaE) {};
 
 
}



// le but de la constante K est de garder en moyenne le rapport deltaE/K unitaire 
// ainsi la proba d'accepation est de l'ordre de exp(-1/T) 
// la probablite d'accepter une solution plus mauvaise est moins conditionnee par le deltaE

// NB : il faut toujours eviter que k soit proche de 0
// dans ce cas, la proba explose independament de T, on ne sort plus des minimums locaux
// essayer de garder toujours k>1