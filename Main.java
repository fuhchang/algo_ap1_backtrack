import java.io.*;
import java.util.*;

/**
 program to find shortest path using the backtrack search algorithm
 */
public class Main {
	private static List<Integer> VisitedVertex = new LinkedList<Integer>();
	private static int firstVertex;
	private static int lastVertex;
	private static int numOfVertex;
	private static int numVisitedEdge =0;
	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();
		List<String> vertexLines = new LinkedList<String>();
		String inputFileName = args[0]; // input file name
  
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		// read in the data here
		while (in.hasNextLine()) {
			 String line = in.nextLine();
			vertexLines.add(line);
        }
		in.close();
		reader.close();
		//get number of vertex
		numOfVertex = Integer.parseInt(vertexLines.get(0));
		
		/*
		 * create vertex in vertexArray and add the number of edge for each vertex and its distance
		 */
		 Vertex[] vertexArray = new Vertex[numOfVertex];
		 String[] vertexEdge;
		 int totalEdge=0;
		 int count1=0;
		 for(int i = 0; i < numOfVertex;i++){
			 vertexEdge = vertexLines.get(i+1).split("\\s+");
			 count1 =0;
			 vertexArray[i] = new Vertex(i);
			 while(count1 < vertexEdge.length){
				 if(Integer.parseInt(vertexEdge[count1]) != 0){
				 vertexArray[i].addToAdjList(count1, Integer.parseInt(vertexEdge[count1]));
				 totalEdge++;
				 }
				
				 count1++;
			 }
		 }
		 /*
		  * get the starting vertex and ending vertex no
		  */
		String lastline = vertexLines.get(vertexLines.size()-1);
		vertexEdge = lastline.split("\\s+");
		firstVertex = Integer.parseInt(vertexEdge[0]);
		lastVertex = Integer.parseInt(vertexEdge[1]);
		
		/*
		 * find all the avaliable path avaliable from the start vertex to end vertex and store to VisitedVertex
		 */
		int parent=0;
		int index = firstVertex;
		boolean visitedSetted = false;
		while(numVisitedEdge <= totalEdge){
			
			parent = index;
			index = getsmallest(vertexArray, index, parent);
			visitedSetted = setVisited(parent, index, vertexArray);
			if(!VisitedVertex.isEmpty()){
				if(VisitedVertex.get(VisitedVertex.size()-1) != index && index != 2){
					VisitedVertex.add(index);
				}
			}
			else{
				VisitedVertex.add(index);
			}
			if(visitedSetted == true){
				if(index == lastVertex){
					numVisitedEdge = numVisitedEdge-2;
					index = parent;
				}
			}
		}
		/*
		 * regroup the vertex path by setting to complete path with start and end vertex for each path
		 */
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(firstVertex);
		for(int i=0;i<VisitedVertex.size();i++){
			if(VisitedVertex.get(i) != lastVertex){
				list.add(VisitedVertex.get(i));
			}else if(i != VisitedVertex.size()){
				list.add(VisitedVertex.get(i));
				if(i+1 != VisitedVertex.size()){
					list.add(firstVertex);
				}
			}
		}
		/*
		 * calculate and compare to get the shortest distance for the path.
		 */
		String finalPath = null;
		String path = null;
		int tempPath = 0;
		int currentIndex =0;
		int finalDist =0;
		for(int a=0;a<list.size();a++){
			if(list.get(a) == firstVertex){
				path = Integer.toString(list.get(a));
				tempPath =0;
				currentIndex = list.get(a);
				continue;
			}
			if(list.get(a) == lastVertex){
				path = path + " " + list.get(a);
				for(int i=0;i<vertexArray[currentIndex].getAdjList().size();i++){
					if(vertexArray[currentIndex].getAdjList().get(i).getVertexNumber() == lastVertex){
						tempPath = tempPath + vertexArray[currentIndex].getAdjList().get(i).getVertexValue();
						if(finalDist ==0){
							finalDist = tempPath;
							finalPath = path;
						}else if(finalDist > tempPath){
							finalDist = tempPath;
							finalPath = path;
						}
						continue;
					}
				}
			}
			if(list.get(a) != lastVertex && list.get(a) != firstVertex){
				for(int i=0;i<vertexArray[currentIndex].getAdjList().size();i++){
					if(vertexArray[currentIndex].getAdjList().get(i).getVertexNumber() == list.get(a)){
						path = path + " " + vertexArray[currentIndex].getAdjList().get(i).getVertexNumber();
						tempPath = tempPath + vertexArray[currentIndex].getAdjList().get(i).getVertexValue();
						currentIndex = vertexArray[currentIndex].getAdjList().get(i).getVertexNumber();
						continue;
					}
				}
			}
		}
		System.out.println("shortest path " + finalPath);
		System.out.println("Shortest Dist " + finalDist);
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}
	/*
	 * set the edge to be visited after travel though to make sure its does no use the path again.
	 */
private static boolean setVisited(int parent, int index, Vertex[] vertexArray) {
	boolean firstcon=false;
	boolean secondcon = false;
		for(int i =0;i< vertexArray[index].getAdjList().size();i++){
			if(vertexArray[index].getAdjList().get(i).getVertexNumber() == parent){
				vertexArray[index].getAdjList().get(i).setVisited(true);
				firstcon = true;
			
			}
		}
		for(int i =0;i<vertexArray[parent].getAdjList().size();i++){
			if(vertexArray[parent].getAdjList().get(i).getVertexNumber() == index){
				vertexArray[parent].getAdjList().get(i).setVisited(true);
				secondcon = true;
				
			}
		}
		if(firstcon ==true && secondcon==true){
			numVisitedEdge= numVisitedEdge+2;
			return true;
		}
		return false;
	}
/*
 * find the smallest distance that has no been travel and return the index of the vertex
 */
public static int getsmallest(Vertex[] vertexArray, int index, int parent){
	int result =0;
	int min = 0;
	int count=0;
	for(int j =0;j< vertexArray[index].getAdjList().size();j++){
		if(!vertexArray[index].getAdjList().get(j).isVisited()){
			if(vertexArray[index].getAdjList().get(j).getVertexNumber() != parent){
				if(min == 0){
					min = vertexArray[index].getAdjList().get(j).getVertexValue();
					result = vertexArray[index].getAdjList().get(j).getVertexNumber();
				}else if(min>vertexArray[index].getAdjList().get(j).getVertexValue() && vertexArray[index].getAdjList().get(j).getVertexNumber() != parent && vertexArray[index].getAdjList().get(j).getVertexNumber() != firstVertex){
					min = vertexArray[index].getAdjList().get(j).getVertexValue();
					result = vertexArray[index].getAdjList().get(j).getVertexNumber();
				}
			}
		}
		else{
			count++;
		}		
	}
	if(count == vertexArray[index].getAdjList().size()){
		result = Integer.parseInt(vertexArray[parent].getParent());
	}
	else if(result != parent){
	vertexArray[result].setParent(Integer.toString(parent));
	}
	return result;
}
}

















