
public class Edge implements Comparable<Edge>{

	public String roadID;
	public Vertex v1;
	public Vertex v2;
	
	
	public double weight, miles;
	
	public Edge(String id, Vertex v1, Vertex v2){
		this.roadID = id;
		this.v1 = v1;
		this.v2 = v2;
		
		
	}
	
	public Edge(Vertex v1, Vertex v2){
		this.v1 = v1;
		this.v2 = v2;
		
	}
	
	
	public double getWeight(Vertex v1, Vertex v2){
		
		
		double deltaLat = v2.latitude - v1.latitude;
		double deltaLon = v2.longitude - v1.longitude;
		
		double pythagorean = Math.sqrt(Math.pow(deltaLat, 2)+Math.pow(deltaLon, 2));
		weight = pythagorean;
		return weight;
		
		
	}
	
	public double getMiles(){
		miles = weight*65;
		return miles;
		
	}
	
	public String getRoadID(){
		return roadID;
	}
	
	
	public int compareTo(Edge e) {
		
		if(this.weight == e.weight)
			return 0;
		else if(this.weight < e.weight)
			return -1;
		else if(this.weight > e.weight)
			return 1;
		return 0;
	}

	
	
	
	
	

	
	
	
}
