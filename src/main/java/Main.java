/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */

import java.io.*;

/**
 * Main class for GPL example
 * @author Roberto E. Lopez-Herrejon
 * @feature Prog 
 */
public class Main {
	
	// ***********************************************************************
	// Definition of variables to indicate that a feature is selected or not
	
	// Core features, initialized to false because they must be set nonetheless in the configuration input/file
	public static boolean GPL=false;
	public static boolean PROG=false;
	public static boolean BENCH=false;
	
	// Type of graph
	public static boolean UNDIRECTED=false;
	public static boolean DIRECTED=false;
	
	// Weights
	public static boolean WEIGHTED=false;
	
	// Search features
	public static boolean DFS=false;
	public static boolean BFS=false;
	
	// Algorithms
	public static boolean NUMBER=false;
	public static boolean CYCLE=false;
	public static boolean CONNECTED=false;
	public static boolean STRONGLYCONNECTED=false;
	public static boolean MSTPRIM=false;
	public static boolean MSTKRUSKAL=false;
	public static boolean SHORTESTPATH=false;
	
	
	
	/** Definition of arguments
	 * [0] Name of Benchmark file
	 * [1] Name of starting vertex, ex.  v0 
	 * [2] ... names of features that are selected for execution
	 */
    public static void main( String[] args ) {
    	
    	// Step 0: Verify consistency of product configuration
    	if (!validConfiguration(args)) {
    		System.out.println ("Invalid configuration");
    		System.exit(0);
    	};
    	
    	// @DEBUG 
    	System.out.println("Well formed configuration");
    	// System.exit(0);
    	
//    	// @DEBUG
//    	// Finding the path
//    	File f = new File("./src/main/graphs");
//    	for (File file: f.listFiles()) {
//    		System.out.println("File in directory " + file.getName());
//    	}
    	
    	// Step 1: create graph object
        Graph g = new  Graph();
//      Graph gaux = new  Graph();
        
        // Step 2: sets up the benchmark file to read
        g.openBenchmark( args[0] );
         
        // Step 3: reads number of vertices, number of edges and weights
        int num_vertices = 0;
        int num_edges = 0;
        try {
            num_vertices = g.readNumber();
            num_edges = g.readNumber();
            // ignores the additional description files
            g.readNumber();   // undirected, directed
            g.readNumber();   // self loops
            g.readNumber();   // unique
        }catch( IOException e ) {
        	System.out.println("Error while reading benchmark file configuration values");
        	System.exit(0);
        }
        
        // @DEBUG
        System.out.println("Benchmark file read");
        
        // Step 4: reserves space for vertices, edges and weights
        Vertex V[] = new  Vertex[num_vertices];
        int startVertices[] = new int[num_edges];
        int endVertices[] = new int[num_edges];
        
        // @feature WEIGHTED selected
        int weights[] = new int[num_edges];
        
        // Step 5: creates the vertices objects 
        int i=0;
        for ( i=0; i<num_vertices; i++ ) {
            V[i] = new Vertex().assignName( "v"+i );
            g.addVertex( V[i] );
        }
                  
        // Step 6: reads the edges
        try {
            for( i=0; i<num_edges; i++ ) {
                startVertices[i] = g.readNumber();
                endVertices[i] = g.readNumber();
            }
        }catch( IOException e ) {
        	System.out.println("Error while reading the edges");
        	System.exit(0);
        }
        
        // @feature WEIGHTED selected
        // Step 7: reads the weights
        if (WEIGHTED) {
        	try {
        		for( i=0; i<num_edges; i++ ) weights[i] = g.readNumber();
        	}catch ( IOException e ) {
        	System.out.println("Error while reading the weigths");
        	System.exit(0);
        	}
        }
        // ----
        
        // Stops the benchmark reading
        g.stopBenchmark();
         
        // Step 8: Adds the edges
        for ( i=0; i<num_edges; i++ ) {
        	
        	//@feature WEIGHTED selected or not
        	if (WEIGHTED)  { 
        		g.addAnEdge( V[startVertices[i]], V[endVertices[i]],weights[i] );
        	} else{
        		g.addAnEdge( V[startVertices[i]], V[endVertices[i]]);
        	}
        }
     
        // Executes the selected features
        Graph.startProfile();
        g.run( g.findsVertex( args[1] ) );
           
        Graph.stopProfile();
        g.display();
        Graph.resumeProfile();
            
        // End profiling
            
        Graph.endProfile();

    } // main


    /**
     * Manual verification of configuration consistency. Does the selected features are a
     * valid configuration? 
     * @param args
     */
	private static boolean validConfiguration(String[] args) {
		boolean consistent = true;
		
		// ******* 
		// Collects what features are selected and marks them as true
		// arg[0] benchmark name, arg[1] start index 
		for (int i=2;i<args.length; i++) {
		
			// @DEBUG
			System.out.println("Argument " + i + " <" + args[i] + ">");
			
			
			if (args[i].equals("PROG")) {
				PROG=true; continue;
			}
			
			if (args[i].equals("GPL")) {
				GPL=true; continue;
			}
			
			if (args[i].equals("BENCH")) {
				BENCH=true; continue;
			}
			
			if (args[i].equals("UNDIRECTED")) {
				UNDIRECTED=true; continue;
			}
			
			if (args[i].equals("DIRECTED")) {
				DIRECTED=true; continue;
			}
			
			if (args[i].equals("WEIGHTED")) {
				WEIGHTED=true; continue;
			}
			
			if (args[i].equals("DFS")) {
				DFS=true; continue;
			}
			
			if (args[i].equals("BFS")) {
				BFS=true; continue;
			}
			
			if (args[i].equals("NUMBER")) {
				NUMBER=true; continue;
			}
			
			if (args[i].equals("CYCLE")) {
				CYCLE=true; continue;
			}

			if (args[i].equals("CONNECTED")) {
				CONNECTED=true; continue;
			}
			
			if (args[i].equals("STRONGLYCONNECTED")) {
				STRONGLYCONNECTED=true; continue;
			}
			
			if (args[i].equals("MSTPRIM")) {
				MSTPRIM=true; continue;
			}
			
			if (args[i].equals("MSTKRUSKAL")) {
				MSTKRUSKAL=true; continue;
			}
			
			if (args[i].equals("SHORTESTPATH")) {
				SHORTESTPATH=true; continue;
			}
			
			
					
			// If it reaches here is because it did not find a feature with the given name
			System.out.println("Error: Feature " + args[i] + " not found");
			System.exit(0);
		} // of for all the features selected
		
		// @DEBUG
		System.out.println("Number of arguments " + args.length);
		displayConfigurationValues();
		
		// *********
		// Start the verification of the conditions
		
		// Only one of DIRECTED and UNDIRECTED can be selected
		if (!(DIRECTED^UNDIRECTED)) consistent=false;
		
		// Only one search method of DFS and BFS can be selected at most
		// true, when both unselected (search is optional), and one of two selected
		// false, if both are selected
		if (DFS && BFS) consistent=false;
		
		// Specific constraints for algorithms
		
		//If NUMBER is selected then one search method must be selected
		if (NUMBER && !(DFS||BFS)) consistent=false;
		
		//If CONNECTED is selected then a search is required on undirected graphs
		if (CONNECTED && (!UNDIRECTED || !(DFS||BFS))) consistent=false;
		
		//If STRONGLYCONNECTED is selected then DFS must be selected on directed graphs
		if (STRONGLYCONNECTED && (!DIRECTED || !DFS)) consistent=false;
		
		// If CYCLE is selected it requires DFS search
		if (CYCLE && !DFS) consistent=false;
		
		// MSTKRUSKAL and MSTPRIM cannot be selected at the same time
		// true, when both unselected (MST are not mandatory), and one of two selected
		// false, if both are selected
		if (MSTPRIM && MSTKRUSKAL) consistent=false;
		
		// If MSTRPRIM selected must have Undirected and Weighted graphs
		if (MSTPRIM && (!UNDIRECTED || !WEIGHTED)) consistent=false;
		
		// If MSTKRUSKAL selected must have Undirected and Weighted graphs
		if (MSTKRUSKAL && (!UNDIRECTED || !WEIGHTED)) consistent=false;
		
		// If SHORTESTPATH selected must have DIRECTED and Weighted graphs
		if (SHORTESTPATH && (!DIRECTED || !WEIGHTED)) consistent=false;

		return consistent;		
		
	} // of validConfiguration


	/**
	 * Shows the names of the selected features
	 */
	private static void displayConfigurationValues() {
		StringBuffer configuration = new StringBuffer();
		
		if (GPL) configuration.append("GPL ");
		if (PROG) configuration.append("PROG ");
		if (BENCH) configuration.append("BENCH ");
		if (UNDIRECTED) configuration.append("UNDIRECTED ");
		if (DIRECTED) configuration.append("DIRECTED ");
		if (WEIGHTED) configuration.append("WEIGHTED ");
		if (DFS) configuration.append("DFS ");
		if (BFS) configuration.append("BFS ");
		if (NUMBER) configuration.append("NUMBER ");
		if (CYCLE) configuration.append("CYCLE ");
		if (CONNECTED) configuration.append("CONNECTED ");
		if (STRONGLYCONNECTED) configuration.append("STRONGLYCONNECTED ");
		if (MSTPRIM) configuration.append("MSTPRIM ");
		if (MSTKRUSKAL) configuration.append("MSTKRUSKAL ");
		if (SHORTESTPATH) configuration.append("SHORTESTPATH ");

		System.out.println("Input configuration: " + configuration);

	} // of displayConfigurationValues
   
}
