import java.util.LinkedList; // we require the linked list class /** class to represent a vertex in a graph */
public class Vertex implements Comparable<Vertex> {
private int index; // the index of this vertex
private LinkedList<AdjListNode> adjList; // the adjacency list of vertex
  // possibly other fields, e.g. representing data stored at the node
  /** create a new instance of vertex with index n */
public Vertex(int n) {
	index = n; // set index
	adjList = new LinkedList<AdjListNode>();// create empty adjacency list
}
   /** return the index of the vertex */
  public int getIndex(){
    return index;
  }
  
  /** set the index of the vertex */
  public void setIndex(int n){ 
	  index = n;
  }
  
 public void setAdjList(LinkedList<AdjListNode> adjList) {
	this.adjList = adjList;
}
/** return the  adjacency list of the vertex*/
  public LinkedList<AdjListNode> getAdjList(){
   return adjList;
  }
     /** add vertex with index m to the adjacency list */
     public void addToAdjList(int m, int n){
       adjList.addLast(new AdjListNode(m,n));
  }
     /** return the degree of the vertex */
  public int vertexDegree(){ 
	  return adjList.size();
  }
	
	@Override
	public int compareTo(Vertex o) {
		// TODO Auto-generated method stub
		return 0;
	}
  
  
}
