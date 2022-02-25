/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */
// undirected and Directed have the same code


import java.util.LinkedList;

// *********************************************************************   

/**
 * Class the represents the vertices of the graph
 * @author rlopez
 *
 */
public class Vertex {
    public LinkedList<Neighbor> neighbors;
    public String name;
      
    public Vertex() {
        name      = null;
        neighbors = new LinkedList<Neighbor>();
        
        //@feature BFS or DFS selected
        visited = false;
        // ---
    }

//    public void VertexConstructor() {
//  
//    }
    
    public  Vertex assignName( String name ) {
        this.name = name;
        return this;
    }
   
    public void addNeighbor( Neighbor n ) {
        neighbors.add( n );
    }

    public boolean equals(Object o) {
    	boolean result = false;
    	    	if (!(o instanceof Vertex)) return result;
    	Vertex v = (Vertex) o;
    	if (v.name==this.name) result = true;
    	return result;
    }
    
    public void display() {
    	
    	// @feature SHORTESTPATH
    	System.out.print( "Pred " + predecessor + " DWeight " + dweight + " " );
    	// ---
    	
    	// @feature STRONGLYCONNECTED
        System.out.print( " FinishTime -> " + finishTime + " SCCNo -> " + strongComponentNumber );
        // ---
        
    	// @feature CONNECTED
    	System.out.print( " comp# "+ componentNumber + " " );
    	// ---
    	
    	// @feature MSTKRUSKAL
        if ( representative == null )
            System.out.print( "Rep null " );
        else
            System.out.print( " Rep " + representative.name + " " );
    	// ---
    	
    	// @feature MSTPRIM
    	System.out.print( " Pred " + pred + " Key " + key + " " );
    	// ---
    	
    	// @feature CYCLE
    	System.out.print( " VertexCycle# " + VertexCycle + " " );
    	// ---
    	
    	// @feature NUMBER
    	System.out.print( " # "+ VertexNumber + " " );
    	//----
    	
    	// @feature BFS
    	 if ( visited )
             System.out.print( "  visited " );
         else
             System.out.println( " !visited " );
    	// ----

        System.out.print( " Node " + name + " connected to: " );

        for (Neighbor theNeighbor : neighbors) {
            // @feature UNDIRECTED 
        	System.out.print( theNeighbor.end.name + ", " );
            // ---
        	
            // @feature DIRECTED
            Vertex v = theNeighbor.end;
            System.out.print( v.name + ", " );
            // ---
            
        } // for all the vertices
        
        System.out.println();
    } // of display

    
    // -----------------------------
    // @feature BFS  or feature DFS selected
    public boolean visited;
   
   
    public void init_vertex( WorkSpace w ) {
        visited = false;
        w.init_vertex(this);
    }    
    // ---
    
    // @feature BFS
    static LinkedList<Vertex> Queue =  new LinkedList<Vertex>();
    
    public void bftNodeSearch( WorkSpace w ) {
        Vertex  v;
        Vertex  header;
      
        // Step 1: if preVisitAction is true or if we've already
        //         visited this node
        w.preVisitAction(this);               
        if (visited) return;

        // Step 2: Mark as visited, put the unvisited neighbors in the queue 
        //     and make the recursive call on the first element of the queue
        //     if there is such if not you are done
        visited = true;
         
        // Step 3: do postVisitAction now, you are no longer going through the
        // node again, mark it as black
        w.postVisitAction(this);

        // enqueues the vertices not visited
        for (Neighbor n : neighbors) {
            v = n.end;

            // if the neighbor has not been visited then enqueue 
            if (!v.visited) Queue.add(v);                        
        }

        
        // while there is something in the queue
        while(Queue.size()!=0) {
        	header = Queue.get(0);
            Queue.remove(0);
            header.bftNodeSearch( w );
        } // while there is a vertex pending to visit
                  
    } // of bfsNodeSearch    
    // ---
    
    
    // @feature DFS
    public void dftNodeSearch( WorkSpace w ) {
        Vertex v;

        // Step 1: Do preVisitAction. 
        // If we've already visited this node return
        w.preVisitAction(this);
        if (visited) return;

        // Step 2: else remember that we've visited and 
        //         visit all neighbors
        visited = true;
         
        for (Neighbor n : neighbors) {
            v = n.end;
            w.checkNeighborAction(this,v);
            v.dftNodeSearch(w);
        }
             
        // Step 3: do postVisitAction now
        w.postVisitAction(this);
    } // of dftNodeSearch    
    // ---
    
    // @feature NUMBER
    public int VertexNumber;
    // ----
    
    // @feature CYCLE
    public int VertexCycle;
    public int VertexColor; // white ->0, gray ->1, black->2
    // ---
    
    // @feature MSTPRIM
    public String pred; // the predecessor vertex if any
    public int key; // weight so far from s to it
    // ---
    
    // @feature MSTKRUSKAL
    public  Vertex representative;
    public LinkedList<Vertex> members;
    // ---
    
    // @feature CONNECTED
    public int componentNumber;
    // ---
    
    // @feature STRONGLYCONNECTED
    public int finishTime;
    public int strongComponentNumber;
    // ---
    
    // @feature SHORTESTPATH
    public String predecessor; // the name of the predecessor if any
    public int dweight; // weight so far from s to it
    // ----
    
} // class Vertex
