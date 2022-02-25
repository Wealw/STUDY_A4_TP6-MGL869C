/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
// Note: Code for Undirected is the same as for Directed

/**
 * Class the represents a neighbor of a vertex in the graph
 * @author rlopez 
 *
 */
public class Neighbor {
    public  Vertex end;
    public  Edge edge;
        
    public Neighbor() {
        end = null;
        edge = null;
    }
        
    public Neighbor(Vertex v, Edge e) {
        end = v;
        edge = e;
    }

    public String toString() {
    	return end.name + " " + edge.toString(); 
    }
}
