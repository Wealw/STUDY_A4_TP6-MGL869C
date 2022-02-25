/**
 * GPL Example
 * Runtime variability and monolithic implementation
 * @author Roberto E. Lopez-Herrejon
 * ETS-LOGTI
 */

// @feature CONNECTED
public class ConnectedWorkSpace extends WorkSpace {
    int counter;

    public ConnectedWorkSpace() {
        counter=0;
    }

    public void init_vertex(Vertex v) {
        v.componentNumber=-1;
    }
      
    public void postVisitAction(Vertex v) {
        v.componentNumber=counter;
    }

    public void nextRegionAction(Vertex v) {
        counter++;
    }
    
} // of RegionWorkSpace
// ---