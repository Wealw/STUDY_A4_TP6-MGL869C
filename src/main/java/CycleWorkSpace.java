/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */

// @feature CYCLE
public class CycleWorkSpace extends WorkSpace {

	   public boolean anyCycles;
	    public int     counter;
	      
	    public static final int WHITE = 0;
	    public static final int GRAY  = 1;
	    public static final int BLACK = 2;
	            
	    public CycleWorkSpace() {
	        anyCycles = false;
	        counter   = 0;
	    }

	    public void init_vertex( Vertex v ) {
	        v.VertexCycle = Integer.MAX_VALUE;
	        v.VertexColor = WHITE; // initialize to white color
	    }

	    public void preVisitAction( Vertex v ) {
	        // This assigns the values on the way in
	        if ( v.visited!=true ) { 
	        	// if it has not been visited then set the
	            // VertexCycle accordingly
	            v.VertexCycle = counter++;
	            v.VertexColor = GRAY; // we make the vertex gray
	        }
	    }

	    public void postVisitAction( Vertex v ) {
	        v.VertexColor = BLACK; // we are done visiting so make it black
	        counter--;
	    } // of postVisitAction

	    public void checkNeighborAction(Vertex vsource, Vertex vtarget ) {
	        // if the graph is directed is enough to check that the source node
	        // is gray and the adjacent is gray also to find a cycle
	        // if the graph is undirected we need to check that the adjacent is not
	        // the father, if it is the father the difference in the VertexCount is
	        // only one.                                   
	       
	    	// @feature DIRECTED
	    	if (Main.DIRECTED) 
	            if (vsource.VertexColor==GRAY && vtarget.VertexColor==GRAY) anyCycles = true;
	        // ---
	    	
	    	// @feature UNDIRECTED
	    	if (Main.UNDIRECTED)
	            if (vsource.VertexColor==GRAY && vtarget.VertexColor==GRAY 
	            		&& vsource.VertexCycle!=(vtarget.VertexCycle+1)) 
	                anyCycles = true;
	    	// ---
	    		        
	    } // of checkNeighborAction
	    
} // of CycleWorkSpace
// --