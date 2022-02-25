import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class orders the vertices of the queue used in MSTPrim based on the weights of its neighbors
 * @author rlopez
 *
 * @param <Vertex>
 */
public class MSTPrimQueueComparator implements Comparator<Vertex>{

	// Graph graph;
	
//	public MSTPrimQueueComparator(Graph graph) {
//		this.graph = graph; 
//	}
	
	/**
	 * Sorts from the minimum weight value of the neighbors of each vertex.
	 * Algorithm:
	 * If both vertices do not have neighbors then return 0
	 * If one vertex has neighbor and the other does not then 
	 * 1) Finds the minimum weight for each of the neighbors for the first vertex
	 * 2) Finds the minimum weight for each of the neighbors for the second vertex
	 * 3) Returns
	 *    -1 if the neighbor in the first vertex has the minimum value
	 *    0 if the minimum values are the same
	 *    1 if the first 
	 */
	@Override
	public int compare(Vertex v1, Vertex v2) {
		int minV1=0, minV2=0;
		
		if (v1.neighbors.size()!=0) 
			minV1 = obtainMinimumWeight(v1.neighbors);
		
		if (v2.neighbors.size()!=0) 
			minV2 = obtainMinimumWeight(v2.neighbors);
		
		return minV1-minV2;
		
	}

	/**
	 * Finds the minimum weight from a list of neighbors
	 * @param neighbors
	 * @return
	 */
	private int obtainMinimumWeight(LinkedList<Neighbor> neighbors) {
		Neighbor firstNeighbor = neighbors.getFirst();
		Edge minEdge = firstNeighbor.edge;
		int minWeight = minEdge.weight;
		
		// Traverses the list to find the minimum
		for (Neighbor next: neighbors) {
			if (next.edge.weight < minWeight) 
				minWeight = next.edge.weight;
		}
		
		return minWeight;
	} // end of obtainMinimumWeight

	
	/** 
	 * Main method for testing the use of the comparator
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Creates the graph for testing
		// The vertices
		Vertex v0 = new Vertex(); v0.assignName("v0");
		Vertex v1 = new Vertex(); v1.assignName("v1");
		Vertex v2 = new Vertex(); v2.assignName("v2");
		Vertex v3 = new Vertex(); v3.assignName("v3");
		Vertex v4 = new Vertex(); v4.assignName("v4");
		Vertex v5 = new Vertex(); v5.assignName("v5");
		Vertex v6 = new Vertex(); v6.assignName("v6");
		Vertex v7 = new Vertex(); v7.assignName("v7");
		Vertex v8 = new Vertex(); v8.assignName("v8");

		
		// Creates the graph
		Graph testGraph = new Graph();
		
		Main.UNDIRECTED = true; // forcing it to be undirected as needed for MST graphs
		System.out.println("Is undirected? " + Main.UNDIRECTED) ;
		
		// Adding vertices
		testGraph.addVertex(v0); testGraph.addVertex(v1); testGraph.addVertex(v2); testGraph.addVertex(v3);
		testGraph.addVertex(v4); testGraph.addVertex(v5); testGraph.addVertex(v6); testGraph.addVertex(v7);
		testGraph.addVertex(v8);
		
		// Adding edges
		testGraph.addAnEdge(v0, v1, 4); testGraph.addAnEdge(v0, v2, 8); testGraph.addAnEdge(v1, v2, 11);
		testGraph.addAnEdge(v2, v3, 7); testGraph.addAnEdge(v1, v4, 8);
		
		// Edge v0_1 = new Edge(v0, v1, 4);
		// Edge v0_2 = new Edge(v0, v2, 8);
		// Edge v1_2 = new Edge(v1, v2, 11);
		// Edge v2_3 = new Edge(v2, v3, 7);
		// Edge v1_4 = new Edge(v1, v4, 8);

		testGraph.addAnEdge(v3, v4, 2); testGraph.addAnEdge(v3, v5, 6); testGraph.addAnEdge(v2, v5, 1);
		testGraph.addAnEdge(v4, v6, 7); testGraph.addAnEdge(v4, v7, 4);
		
		// Edge v3_4 = new Edge(v3, v4, 2);
		// Edge v3_5 = new Edge(v3, v5, 6);
		// Edge v2_5 = new Edge(v2, v5, 1);
		// Edge v4_6 = new Edge(v4, v6, 7);
		// Edge v4_7 = new Edge(v4, v7, 4);

		testGraph.addAnEdge(v5, v7, 2); testGraph.addAnEdge(v6, v7, 14); testGraph.addAnEdge(v6, v8, 9);
		testGraph.addAnEdge(v7, v8, 10); 
		
		// Edge v5_7 = new Edge(v5, v7, 2);
		// Edge v6_7 = new Edge(v6, v7, 14);
		// Edge v6_8 = new Edge(v6, v8, 9);
		// Edge v7_8 = new Edge(v7, v8, 10);
		
		System.out.println("Graph creation done!");
		
		
		// Neighbors of vertex v7 should be v4 (4), v5 (2), v6 (14) and v8 (10)
		for (Neighbor n : v7.neighbors ) {
			System.out.println("Neighbor " + n.toString());
		}
		
		// Test the creation of a linked list of vertices ordered with the comparator
		Queue<Vertex> q = new PriorityQueue<Vertex>(new MSTPrimQueueComparator()); // n
		q.add(v0);  
		System.out.println("Ordered list size " + q.size());
		q.add(v2); 
		System.out.println("Ordered list size " + q.size());
		q.add(v7);
		System.out.println("Ordered list size " + q.size());
		for (Vertex v: q ) {
			System.out.println("Vertex name = " + v.name);
		}
		
	} // of main
	
} // of MSTPrimQueueComparator
