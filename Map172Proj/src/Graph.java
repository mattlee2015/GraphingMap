import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;

public class Graph{
	public static int vertCounter = 0;
	public static int edgeCounter = 0;
	public static String[] file;
	public static String item;
	public static String a,b,c,d;
	public static double latitude, longitude;
	public static double minLon = Integer.MAX_VALUE;
	public static double minLat = Integer.MAX_VALUE;
	public static double maxLon = Integer.MIN_VALUE;
	public static double maxLat = Integer.MIN_VALUE;
	
	public  HashMap<String, Vertex> graph = new HashMap<String,Vertex>();
	
	public static ArrayList<Edge> directions = new ArrayList<Edge>();
	
	public static Map<String, Edge> edgeMap = new HashMap<String, Edge>();
	public static  Map<Integer, Vertex> countV = new HashMap<Integer, Vertex>();
	
	public static  Map<Integer, Edge> countE = new HashMap<Integer,Edge>();
	
	public static  ArrayList<Edge> printKruskal = new ArrayList<Edge>();
	public static  ArrayList<Edge> kruskalEdge = new ArrayList<Edge>();
	public static  ArrayList<String> visited=new ArrayList<String>();
	public static  Map<String, Vertex> vertexMap = new HashMap<String, Vertex>();
	public static  ArrayList<String> pathToPrint =new ArrayList<String>();
	public static ArrayList<Edge> disjointPath = new ArrayList<Edge>();
	
	
	public static String findSmallestVertex(ArrayList<String> visited){
		
		ArrayList<String> removedVerts = new ArrayList<String>();
		String sVert="";
		double path = Double.MAX_VALUE;
		for(int i=0; i<visited.size();i++){
			if(!vertexMap.get(visited.get(i)).known && vertexMap.get(visited.get(i)).distance<path){
				
				
				path=vertexMap.get(visited.get(i)).distance; //set value of path to distance
				sVert=vertexMap.get(visited.get(i)).intersectionID; //set string to name
			}else if(vertexMap.get(visited.get(i)).known){
				
				removedVerts.add(vertexMap.get(visited.get(i)).intersectionID); //add to removedVerts ArrayList
			}
		}
		
		
		if(removedVerts.size()>0){ //method to reduce our arraylist size till 0
			for(int z=0; z<removedVerts.size(); z++){
				visited.remove(removedVerts.get(z));
			}
		}
		
		return sVert;
	}
	
	public static void dijkstrasAlgorithm(Vertex v1, Vertex v2){

		

		double weight;
		Vertex temp, smallest;
		v1.distance=0;
		v1.known = true;
		
		//add the vertex to the visited list
		visited.add(v1.intersectionID);
		smallest = v1;
		smallest.known = true;
		while(v2.known!=true){
			
			ArrayList<Neighbors> adjacent = new ArrayList<Neighbors>();
			
			for(int i=0; i<adjacent.size();i++){
				temp = vertexMap.get(smallest).neighbors.get(i).v;
				weight = smallest.neighbors.get(i).w;
				
				visited.add(smallest.intersectionID);
				
				//for the purposes of our adjacent list not known
				if(!vertexMap.get(adjacent.get(i).v.intersectionID).known){
					vertexMap.get(temp.intersectionID).distance=smallest.distance+weight; //the temp's vertex distance is equal to smallest's distance +weight
					vertexMap.get(temp.intersectionID).path=smallest; //helps to set our path to smallest vertex
				}
				
			}
			
			smallest = vertexMap.get(findSmallestVertex(visited));

		}
		
	}
	
	
	public static ArrayList<Edge> kruskalAlgorithm(){
		
		int edges=0;
		int index = 0;
		ArrayList<Edge> list = new ArrayList<Edge>();
		ArrayList<Vertex> knownV = new ArrayList<Vertex>();
		Collections.sort(kruskalEdge); //our method from the edge class
		
		//add first edge and vertices associated with it
		
		
		list.add(kruskalEdge.get(index));
		knownV.add(kruskalEdge.get(index).v1);
		knownV.add(kruskalEdge.get(index).v2);
		
		while(edges < vertCounter - 1){
			//in the situation of when one of the vertices is known or both
			if(knownV.contains(kruskalEdge.get(index).v1)&&!knownV.contains(kruskalEdge.get(index).v2)
					||!knownV.contains(kruskalEdge.get(index).v1)&&knownV.contains(kruskalEdge.get(index).v2)
					||!knownV.contains(kruskalEdge.get(index).v1)&&!knownV.contains(kruskalEdge.get(index).v2)){
				//add values to kruskal list of edges
				list.add(kruskalEdge.get(index));
				//add vertices to known vertices array list
				knownV.add(kruskalEdge.get(index).v1);
				knownV.add(kruskalEdge.get(index).v2);
			
			}
			//increment index and edges
			index++;
			edges++;
		}
		//return final kruskal list of shortest edges
		return list;
	}
	
	
	
	public static ArrayList<Edge> finalPath(Vertex v1, Vertex v2){
		dijkstrasAlgorithm(v1, v2);
		Vertex temp = v2;
		pathToPrint.add(v2.intersectionID);
		while(temp!=v1){
			Edge e = new Edge(temp, temp.path);
			disjointPath.add(e);
			pathToPrint.add(temp.path.intersectionID);
			temp = temp.path;
			
		}
		
		int locations = pathToPrint.size()-1;
		for(int i=0; i<pathToPrint.size(); i++){
			System.out.print(pathToPrint.get(i));
			if(locations>0){
				System.out.print("->");
			}
			locations--;
		}
		
		System.out.println("The total distance between " + v1.intersectionID + " and " + v2.intersectionID + " is: " + v2.distance + " miles");
		
		return disjointPath;
	}
	
	
	
	public static ArrayList<Edge> printKruskalMethod(){
		
		kruskalAlgorithm();
		//iterate through kruskals
		for(int i=0;i<kruskalAlgorithm().size();i++){
			//as long as kruskals is not empty
			if(!kruskalAlgorithm().isEmpty()){
			//create an edge from the vertices
			Edge e= new Edge(kruskalAlgorithm().get(i).v1,kruskalAlgorithm().get(i).v2);
			//add these edges to an array list
			printKruskal.add(e);
			}
		}
		//iterate through arraylist
		for(int j=0;j<printKruskal.size();j++){
			//print out names of vertices to the console
			System.out.println(printKruskal.get(j).v1.intersectionID+" to "+printKruskal.get(j).v2.intersectionID);
		}
		//return array list
		return printKruskal;
	}

	
	
	
	
	public static void main(String[] args) {
		//length of argument
		int inputsize=args.length;
		try{
			//buffered reader for arguments
			BufferedReader read = new BufferedReader(new FileReader(args[0]));
			//container equals each line
			item = read.readLine();
			while(item != null){
				//split container by tabs
				file = item.split("\t");
				//a= intersection or road b=ID c=latitude d=longitude
				a = file[0];
				b = file[1];
				c = file[2];
				d = file[3];
				
				if(!a.equals(null) && !b.equals(null) && !c.equals(null) && !d.equals(null)){
			
					if(a.equals("i")){
						if(!a.equals(null) && !b.equals(null) && !c.equals(null) && !d.equals(null)){
					
						latitude = Double.parseDouble(c);
						longitude = Double.parseDouble(d);
					
						Vertex v = vertexMap.get(b);
						if(v == null){
							
							v = new Vertex(vertCounter,b, latitude, longitude);
							
							countV.put(vertCounter, v);
							
							vertexMap.put(b,v);
						
							vertCounter++;
						}
						//alter maximum, minimum longitude and latitude
						if(latitude >maxLat)
							maxLat = latitude;
						if(latitude < minLat)
							minLat = latitude;
						if(longitude > maxLon)
							maxLon = longitude;
						if(longitude < minLon)
							minLon = longitude;
					}
				}//if road
					else if(a.equals("r")){
						if(!vertexMap.get(c).equals(null)&&!vertexMap.get(d).equals(null)){
							
							Vertex v1 = vertexMap.get(c);
							Vertex v2 = vertexMap.get(d);
							
							Edge e = new Edge(v1,v2);
							
							edgeMap.put(b, e);
							
							kruskalEdge.add(e);
						
							countE.put(edgeCounter, e);
							
							vertexMap.get(e.v1.intersectionID).insert(new Neighbors(e.v2,e.weight));
							vertexMap.get(e.v2.intersectionID).insert(new Neighbors(e.v1,e.weight));
							
							edgeCounter++;
						}
					}
				}else
					//print that inputs are not valid
						System.out.println(a + " " + b + " " + c + " " + d+" are invalid inputs. Try again.");
				//read next line
						item = read.readLine();
			}
			read.close();
			//exceptions
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
	
	
		
		if(inputsize==2){ //when we have two conditions 
			//condition1
			if(args[1].equals("-show")){
				//Step1: draw the map
				JFrame frame=new JFrame();
				frame.setPreferredSize(new Dimension(700,700));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Canvas draw=new Canvas();
				frame.add(draw);
				frame.pack();
				frame.setResizable(true);
				frame.setVisible(true);
				draw.implementEdges(countE, maxLat, minLat, maxLon, minLon);
			}
			//condition2
			else if(args[1].equals("-meridianmap")){
				//Step1: print the list of edges, if no "-show"
				System.out.println(printKruskalMethod());				
			}else{//error input command line
				System.out.println("input is invalid.");
			}
			
		}else if(inputsize==3){
			if(args[1].equals("-show") && args[2].equals("-meridianmap")){
				JFrame frame=new JFrame();
				frame.setPreferredSize(new Dimension(700,700));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Canvas draw=new Canvas();
				frame.add(draw);
				frame.pack();
				frame.setResizable(true);
				frame.setVisible(true);
				directions = printKruskalMethod();
				draw.drawAlgEdge(countE, directions, maxLat, minLat, maxLon, minLon);
				System.out.println(printKruskalMethod());		
				
			}else{
				System.out.println("input is invalid.");
			}
			
			
		}else if(inputsize==4){
			if(args[1].equals("-directions")){
				String first = args[2];
				String second = args[3];
				
				System.out.println("Here is how to get from " + vertexMap.get(first).intersectionID + " to " + vertexMap.get(second).intersectionID + ": ");
				directions = finalPath(vertexMap.get(first),vertexMap.get(second));
			}else{
				System.out.println("input is invalid.");
			}
			
		}else if(inputsize==5){
			if(args[1].equals("-show") && args[2].equals("-directions")){ 
				String first=args[3];
				String second=args[4];
				directions = finalPath(vertexMap.get(first),vertexMap.get(second));
				JFrame frame=new JFrame();
				frame.setPreferredSize(new Dimension(700,700));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Canvas draw=new Canvas();
				frame.add(draw);
				frame.pack();
				frame.setResizable(true);
				frame.setVisible(true);
				draw.drawAlgEdge(countE, directions, maxLat, minLat, maxLon, minLon);
				
				
			}else{
				System.out.println("input is invalid.");
			}
		}else{
			System.out.println("invalid inputs were given");
		}
		
		
		
		
		
		
	}
		
		
		
	
}
