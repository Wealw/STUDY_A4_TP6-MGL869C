/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */

// @feature STRONGLYCONNECTED
public class TransposeWorkSpace extends WorkSpace {

	 // Strongly Connected Component Counter
    int SCCCounter;
        
    public TransposeWorkSpace() {
        SCCCounter = 0;
    }
        
    public void preVisitAction( Vertex v ) {
        if (v.visited!=true) 
        	v.strongComponentNumber = SCCCounter;
    }

    public void nextRegionAction( Vertex v ) {
        SCCCounter++;
    }
}
// --