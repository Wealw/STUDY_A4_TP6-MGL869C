/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */

// @feature BFS and @feature DFS
public class WorkSpace { // supply template actions
 public void init_vertex( Vertex v ) {}
 public void preVisitAction( Vertex v ) {}
 public void postVisitAction( Vertex v ) {}
 public void nextRegionAction( Vertex v ) {}
 public void checkNeighborAction( Vertex vsource,Vertex vtarget ) {}
}
// ---