import java.io.*;
import java.util.*;

/**
 program to find shortest path using the backtrack search algorithm
 */
public class Main {
	private static List<Integer> VisitedVertex = new LinkedList<Integer>();
	private static int startV;
	private static int endV;
	private static int countofline;
	private static int x =0;
	public static void main(String[] args) throws IOException {

		long start = System.currentTimeMillis();
		List<String> alllines = new LinkedList<String>();
		String inputFileName = args[0]; // input file name
  
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		// read in the data here
		while (in.hasNextLine()) {
			 String line = in.nextLine();
			alllines.add(line);
        }
		in.close();
		reader.close();
		
		countofline = Integer.parseInt(alllines.get(0));
		 Vertex[] arrayofvertice = new Vertex[countofline];
		 String[] numbersinaline;
		 int y=0;
		 int count1=0;
		 for(int i = 0; i < countofline;i++){
			 numbersinaline = alllines.get(i+1).split("\\s+");
			 count1 =0;
			 arrayofvertice[i] = new Vertex(i);
			 while(count1 < numbersinaline.length){
				 if(Integer.parseInt(numbersinaline[count1]) != 0){
				 arrayofvertice[i].addToAdjList(count1, Integer.parseInt(numbersinaline[count1]));
				 //arrayofvertice[i].addTodistanceList(Integer.parseInt(numbersinaline[count1]));
				 y++;
				 }
				
				 count1++;
			 }
		 }
		String lastline = alllines.get(alllines.size()-1);
		numbersinaline = lastline.split("\\s+");
		startV = Integer.parseInt(numbersinaline[0]);
		endV = Integer.parseInt(numbersinaline[1]);
		

		int parent=0;
		int index = startV;
		boolean visitedSetted = false;
		while(x <= y){
			
			parent = index;
			index = getsmallest(arrayofvertice, index, parent);
			visitedSetted = setVisited(parent, index, arrayofvertice);
			if(!VisitedVertex.isEmpty()){
			if(VisitedVertex.get(VisitedVertex.size()-1) != index && index != 2){
				VisitedVertex.add(index);
			}
			}
			else{
				VisitedVertex.add(index);
			}
			if(visitedSetted == true){
				if(index == endV){
					x = x-2;
					index = parent;

				}
			}
		}		
		
		// create graph here
		
		
		// do the work here
		

		// end timer and print total time
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(startV);
		for(int i=0;i<VisitedVertex.size();i++){
			if(VisitedVertex.get(i) != endV){
				list.add(VisitedVertex.get(i));
			}else if(i != VisitedVertex.size()){
				list.add(VisitedVertex.get(i));
				if(i+1 != VisitedVertex.size()){
					list.add(startV);
				}
			}
		}
		String finalPath = null;
		String path = null;
		int tempPath = 0;
		int currentIndex =0;
		int finalDist =0;
		for(int a=0;a<list.size();a++){
			//System.out.println(list.get(a));
			if(list.get(a) == startV){
				path = Integer.toString(list.get(a));
				tempPath =0;
				currentIndex = list.get(a);
				continue;
			}
			if(list.get(a) == endV){
				//System.out.println("ending 1 path");
				path = path + " " + list.get(a);
				for(int i=0;i<arrayofvertice[currentIndex].getAdjList().size();i++){
					if(arrayofvertice[currentIndex].getAdjList().get(i).getVertexNumber() == endV){
						tempPath = tempPath + arrayofvertice[currentIndex].getAdjList().get(i).getVertexValue();
						//System.out.println("Path " + path);
						//System.out.println("Dist "  + tempPath + " " + finalDist);
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
			if(list.get(a) != endV && list.get(a) != startV){
				for(int i=0;i<arrayofvertice[currentIndex].getAdjList().size();i++){
					if(arrayofvertice[currentIndex].getAdjList().get(i).getVertexNumber() == list.get(a)){
						path = path + " " + arrayofvertice[currentIndex].getAdjList().get(i).getVertexNumber();
						//System.out.println("path 2 " +path);
						//System.out.println("before dist " +tempPath);
						tempPath = tempPath + arrayofvertice[currentIndex].getAdjList().get(i).getVertexValue();
						//System.out.println("dist 2 " +tempPath);
						currentIndex = arrayofvertice[currentIndex].getAdjList().get(i).getVertexNumber();
						continue;
					}
				}
			}
			//System.out.println("out come " + finalPath+" " + finalDist);
		}
		System.out.println("shortest path " + finalPath);
		System.out.println("Shortest Dist " + finalDist);
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
	}
	
private static boolean setVisited(int parent, int index, Vertex[] arrayofvertice) {
	boolean firstcon=false;
	boolean secondcon = false;
		for(int i =0;i< arrayofvertice[index].getAdjList().size();i++){
			if(arrayofvertice[index].getAdjList().get(i).getVertexNumber() == parent){
				arrayofvertice[index].getAdjList().get(i).setVisited(true);
				firstcon = true;
			
			}
		}
		for(int i =0;i<arrayofvertice[parent].getAdjList().size();i++){
			if(arrayofvertice[parent].getAdjList().get(i).getVertexNumber() == index){
				arrayofvertice[parent].getAdjList().get(i).setVisited(true);
				secondcon = true;
				
			}
		}
		if(firstcon ==true && secondcon==true){
			x= x+2;
			return true;
		}
		return false;
	}

public static int getsmallest(Vertex[] arrayofvertice, int index, int parent){
	int result =0;
	int min = 0;
	int count=0;
	for(int j =0;j< arrayofvertice[index].getAdjList().size();j++){
		if(!arrayofvertice[index].getAdjList().get(j).isVisited()){
			if(arrayofvertice[index].getAdjList().get(j).getVertexNumber() != parent){
			if(min == 0){
				min = arrayofvertice[index].getAdjList().get(j).getVertexValue();
				result = arrayofvertice[index].getAdjList().get(j).getVertexNumber();
			}
			else if(min>arrayofvertice[index].getAdjList().get(j).getVertexValue() && arrayofvertice[index].getAdjList().get(j).getVertexNumber() != parent && arrayofvertice[index].getAdjList().get(j).getVertexNumber() != startV){
				min = arrayofvertice[index].getAdjList().get(j).getVertexValue();
				result = arrayofvertice[index].getAdjList().get(j).getVertexNumber();
			}
			}
		}
		else{
			count++;
		}		
	}
	if(count == arrayofvertice[index].getAdjList().size()){
		result = Integer.parseInt(arrayofvertice[parent].getParent());
	}
	else if(result != parent){
	arrayofvertice[result].setParent(Integer.toString(parent));
	}
	return result;
}
}

















