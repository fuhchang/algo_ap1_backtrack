public class AdjListNode {
private int vertexNumber; // the vertex number of the entry
private int vertexDist;
private boolean visited;
// possibly other fields, for example representing properties // of the edge such as weight, capacity, ...
   /** creates a new entry for vertex numbered n */
   public AdjListNode(int n, int d){
     vertexNumber = n;
     vertexDist = d;
   }
   public int getVertexNumber(){ // gets the vertex number of the entry
     return vertexNumber;
   }
   public int getVertexValue(){
	   return vertexDist;
   }
   public void setVertexNumber(int n){ // sets vertex number to n vertexNumber = n;
	   vertexNumber = n;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	} 
	public void setVertexValue(int n){
		this.vertexDist = n;
	}
}
