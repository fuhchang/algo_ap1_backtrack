import java.awt.EventQueue;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
/**
 program to find shortest path using Dijkstra's algorithm
 */
public class Main {
	static int firstLineIndex = 0;
	static int lastLineIndex = 0;
	static ArrayList<Integer> tempVisited = new ArrayList<Integer>();
	static ArrayList<Integer> tempVisitedValue = new ArrayList<Integer>();
	public static void main(String[] args) throws IOException {
		ArrayList<Integer> unvisited = new ArrayList<Integer>();
		int size =0;

		ArrayList <Vertex> vertexArray = new ArrayList<Vertex>();
		ArrayList <String> strArray = new ArrayList();
		long start = System.currentTimeMillis();
		String inputFileName = args[0]; // input file name
		
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		// read in the data here
		while(in.hasNext()){
			if(size ==0){
				size = Integer.parseInt(in.nextLine());
			}
			strArray.add(in.nextLine());
		}
		String[] startend = strArray.get(strArray.size()-1).split(" ");
		firstLineIndex = Integer.parseInt(startend[0]);
		lastLineIndex = Integer.parseInt(startend[1]);
		strArray.remove(strArray.size()-1);
		for(int i=0;i<strArray.size();i++){
			String[] strValue = strArray.get(i).split(" ");
			Vertex vertex = null;
			unvisited.add(i);
			vertex = new Vertex(i);
			for(int a=0;a<strValue.length;a++){
				if(Integer.parseInt(strValue[a]) != 0){
					vertex.addToAdjList(a,Integer.parseInt(strValue[a]));
				}
			}
			vertexArray.add(vertex);
		}
		reader.close();
	
		// create graph here
		for(int i=0; i<unvisited.size();i++){
			if(unvisited.get(i) == firstLineIndex || unvisited.get(i)== lastLineIndex){
				unvisited.remove(i);
			}
		}
		for(int i=0;i<vertexArray.size();i++){
			vertexArray.get(i).setAdjList(sortvalueNsortindex(vertexArray.get(i).getAdjList()));
		}
		rm(firstLineIndex, vertexArray, lastLineIndex, firstLineIndex);
		/*
		int index = firstLineIndex;
		for(int i=0;i<vertexArray.size();i++){
			for(int a=0;a<vertexArray.get(i).getAdjList().size();a++){
				if(vertexArray.get(i).getAdjList().get(a).getVertexNumber() == index ){
					
				}
			}
		}
		*/
		System.out.println(tempVisited);
		// do the work here
		String finalPath = null;
		String path = null;
		int tempPath = 0;
		int currentIndex =0;
		int finalDist =0;
		for(int a=0;a<tempVisited.size();a++){
			//System.out.println(tempVisited.get(a));
			//System.out.println("visited " + tempVisited.get(a));
			//System.out.println("size " + vertexArray.get(tempVisited.get(a)).getAdjList().size());
			if(tempVisited.get(a) == firstLineIndex){
				path = Integer.toString(tempVisited.get(a));
				currentIndex = tempVisited.get(a);
				continue;
			}
			if(tempVisited.get(a) == lastLineIndex){
				//System.out.println("ending 1 path");
				path = path + " " + tempVisited.get(a);
				for(int i=0;i<vertexArray.get(currentIndex).getAdjList().size();i++){
					if(vertexArray.get(currentIndex).getAdjList().get(i).getVertexNumber() == lastLineIndex){
						tempPath = tempPath + vertexArray.get(currentIndex).getAdjList().get(i).getVertexValue();
						//System.out.println("Path " + path);
						//System.out.println("Dist "  + tempPath + " " + finalDist);
						if(finalDist ==0){
							finalDist = tempPath;
							finalPath = path;
						}else if(finalDist > tempPath){
							finalDist = tempPath;
							finalPath = path;
						}
						
						path = null;
						tempPath =0;
						currentIndex =0;
						continue;
					}
				}
			}
			if(tempVisited.get(a) != lastLineIndex && tempVisited.get(a) != firstLineIndex){
				for(int i=0;i<vertexArray.get(currentIndex).getAdjList().size();i++){
					if(vertexArray.get(currentIndex).getAdjList().get(i).getVertexNumber() == tempVisited.get(a)){
						path = path + " " + vertexArray.get(currentIndex).getAdjList().get(i).getVertexNumber();
						//System.out.println("path " +path);
						//System.out.println("before dist " +tempPath);
						tempPath = tempPath + vertexArray.get(currentIndex).getAdjList().get(i).getVertexValue();
						//System.out.println("dist " +tempPath);
						currentIndex = vertexArray.get(currentIndex).getAdjList().get(i).getVertexNumber();
						continue;
					}
				}
			}
			//System.out.println("out come " + finalPath+" " + finalDist);
		}
		System.out.println("shortest path " + finalPath+" " + finalDist);
		// end timer and print total time
		
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}
	private static boolean rm(int index, ArrayList<Vertex> array, int end, int parent){
		int tempIndex = index;
		if(index == firstLineIndex){
			tempVisited.add(firstLineIndex);
		}
		
		LinkedList<AdjListNode> list = array.get(index).getAdjList();
		for(int i=0;i<list.size();i++){
			int count=0;

			for(int a=0;a<list.size();a++){
				if(list.get(a).isVisited()){
					count = i;
				}
				if(count == list.size()){
					return false;
				}
			}	
				tempIndex = list.get(i).getVertexNumber();
				if(tempIndex != parent && tempIndex != firstLineIndex){
					if(tempIndex!= end && list.get(i).isVisited() == false){
						if(tempIndex != firstLineIndex){
							tempVisited.add(tempIndex);
						}
						list.get(i).setVisited(rm(tempIndex, array,end, index));
						array.get(index).setAdjList(list);
					}else if(tempIndex == end){
						tempVisited.add(end);
						tempVisited.add(firstLineIndex);
						if(parent != firstLineIndex){
							tempVisited.add(parent);
						}
						list.get(i).setVisited(true);
					}
				}
		}
		return true;
	}
	/*
	public static void travelNode(ArrayList<Vertex> vertexList, int end, int index, ArrayList<Integer> tempVisited, ArrayList<Integer> visited, int currentVertex){
		
		if(index == end){
			index = currentVertex;
		}
		LinkedList<AdjListNode> listed = vertexList.get(index).getAdjList();
		int min=0;
		int endNode =0;
		
		for(int i=0;i<listed.size();i++){
			
			if(listed.get(i).getVertexNumber() == end){
				//tempVisited.add(listed.get(i).getVertexNumber());
				endNode = listed.get(i).getVertexNumber();
			}else if(min == 0){
				min = listed.get(i).getVertexValue();
				endNode = listed.get(i).getVertexNumber();
			}else if(min > listed.get(i).getVertexValue()){
				min = listed.get(i).getVertexValue();
				endNode = listed.get(i).getVertexNumber();
			}
		}
		index = endNode;
		if(index != end){
			currentVertex = endNode;
		}
		visited.add(endNode);
		tempVisited.add(endNode);
		travelNode(vertexList, end, index , tempVisited, visited,currentVertex);
	}
	*/
	
private static LinkedList<AdjListNode> sortvalueNsortindex(LinkedList<AdjListNode> list){
		
		LinkedList<Integer> indexofstartnumbersaftersort = new LinkedList<Integer>();
		LinkedList<AdjListNode> adjList = list;
		Integer[] listofstartnumbers = new Integer[adjList.size()];
		for(int i=0;i < adjList.size(); i++){
			listofstartnumbers[i] = adjList.get(i).getVertexValue();
		}
		Arrays.sort(listofstartnumbers);
		for(int i = 0; i < adjList.size(); i++){
			for(int j =0 ; j < adjList.size(); j++){
			if(listofstartnumbers[i] == adjList.get(j).getVertexValue()){
				indexofstartnumbersaftersort.add(adjList.get(j).getVertexNumber());
			}
		}
	}
		adjList.clear();
		AdjListNode node;
		for(int i = 0 ; i < listofstartnumbers.length; i ++){
			
			node = new AdjListNode(indexofstartnumbersaftersort.get(i),listofstartnumbers[i]);
			adjList.add(node);
		}
		return adjList;

	}	
}

