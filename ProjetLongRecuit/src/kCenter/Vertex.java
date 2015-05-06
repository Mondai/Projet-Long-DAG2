
package kCenter;

import java.util.ArrayList;

class Vertex implements Comparable<Vertex>
{
	public int numero ; 
    public ArrayList<Edge> adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;

    Vertex (int n) {
    	this.numero = n ; 
    	this.adjacencies = new ArrayList<Edge>() ;
    }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}