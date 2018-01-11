import java.util.ArrayList;

public class Vertex {
	
	
	public String intersectionID;
	public double longitude, latitude;
	public double distance = Double.MAX_VALUE;
	public Vertex path = null;
	public boolean known = false;
	public boolean visited;
	public int vNum;
	public ArrayList<Neighbors> neighbors = new ArrayList<Neighbors>();
	
	public Vertex(int vNum, String id, double latitude, double longitude){
		this.vNum = vNum;
		this.intersectionID = id;
		this.latitude = latitude;
		this.longitude = longitude;
		
		
	}
	
	public void addNeighbor(Neighbors n){
		neighbors.add(n);
	}
		
	public double getDistance(){
		return distance;
	}
	
	public boolean isVisisted(){
		return visited;
	}
	
	public void setVisited(boolean v){
		this.visited = v;
	}
	
	public void insert(Neighbors a){
		neighbors.add(a);
	}
	
}
