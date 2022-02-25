/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
// Note: Code for UNDIRECTED and DIRECTED is the same.

// *************************************************************************

/**
 * Class the represents the edges of the graph
 * @author rlopez
 *
 */
public class Edge extends Neighbor {
    public  Vertex start;
        
    public Edge( Vertex the_start, Vertex the_end) {
        start = the_start;
        end = the_end;
    }
        
   public void adjustAdorns(Edge the_edge) {
     // @feature WEIGHTED
       if (Main.WEIGHTED) weight = the_edge.weight;
     // ---
   }
        
    public void display() {
        System.out.print( " start=" + start.name + " end=" + end.name );
        
      // @feature WEIGHTED
        if (Main.WEIGHTED) System.out.print( " Weight=" + weight );
      // ---
      
        System.out.println();
        
    } // of display
     
    
    //@feature WEIGHTED 
    public int weight;
    
    public Edge( Vertex the_start,  Vertex the_end, int the_weight ) {
    	this(the_start,the_end );
        weight = the_weight;
    }
    // ---

    
    public String toString() {
    	
        String result = " start=" + start.name + " end=" + end.name + " weight=" + weight;
        // @feature WEIGHTED
        if (Main.WEIGHTED) result = result + " weight=" + weight;
        // ---
        
        return result;
    }
        
} // of Edge
