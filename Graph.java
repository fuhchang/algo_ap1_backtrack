// again require the linked list class /** class to represent a graph */
public class Graph {
private Vertex[] vertices; // the vertices
private int numVertices = 0; // number of vertices
  // possibly other fields representing properties of the graph
  /** Create a new instance of Graph with n vertices */
  public Graph(int n) {
    numVertices = n;
    vertices = new Vertex[n];
    for (int i = 0; i < n; i++) 
    	vertices[i] = new Vertex(i);
  }
  
  /** returns number of vertices in the graph */
  public int size(){
    return numVertices;
  }
  
  
  
}