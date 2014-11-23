package solverSimuleParametrable;

public abstract class ConstanteK {    
 double k ;
 
 public ConstanteK() {} ; 
  
 void calculerK(double deltaE) {}
 
 
}



// le but de la constante K est de garder en moyenne le rapport deltaE/K unitaire 
// ainsi la proba d'accepation est de l'ordre de exp(-1/T) 
// la probablite d'accepter une solution plus mauvaise est moins conditionnee par le deltaE

// NB : il faut toujours eviter que k soit proche de 0
// dans ce cas, la proba explose independament de T, on ne sort plus des minimums locaux
// essayer de garder toujours k>1