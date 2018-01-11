

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel{
	
	
	private Map<Integer,Edge> mapEdge = new HashMap<Integer, Edge>(); 
	private ArrayList<Edge> algEdge = new ArrayList<Edge>();
	double latDist, lonDist, lonMax, latMin;
	public Graph g;
	
	public Shape mapLine;
	public Shape algLine;
	
	
	
	
	
	
	
	public void implementEdges(Map<Integer,Edge> map, double maxLat, double minLat, double maxLon, double minLon) {
		
		latDist = Math.abs(maxLat - minLat);
		lonDist = Math.abs(maxLon - minLon);
		latMin = Math.abs(minLat);
		lonMax = Math.abs(maxLon);
		mapEdge.putAll(map);
		
	    repaint();
	 }
	
	public void drawAlgEdge(Map<Integer,Edge> map, ArrayList<Edge> e, double maxLat, double minLat, double maxLon, double minLon) {
		
		latDist = Math.abs(maxLat - minLat);
		lonDist = Math.abs(maxLon - minLon);
		latMin = Math.abs(minLat);
		lonMax = Math.abs(maxLon);
		mapEdge.putAll(map);
		algEdge.addAll(e);
	    repaint();
	 }
	
	
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g);
		int width = getHeight();
		int height = getWidth();
		
		
		this.setBackground(Color.WHITE);
		//draws the map's edge 
		for(int i = 0; i < mapEdge.size(); i++){
        	g.setColor(Color.BLUE);
            mapLine = new Line2D.Double(height -(1-(((mapEdge.get(i).v1.longitude + lonMax) * (height / lonDist)))),
            		(width-((mapEdge.get(i).v1.latitude - latMin) * (width / latDist))), 
            		(height-(1-((mapEdge.get(i).v2.longitude + lonMax) * (height / lonDist)))),
            		(width-((mapEdge.get(i).v2.latitude - latMin) * (width / latDist))));
            g2.draw(mapLine);
        }
		
		
		
		
		//draws the algorithm's edge
		if(algEdge.size()!=0){
			g2.setColor(Color.RED);
			for(int i = 0; i < algEdge.size(); i++){
	        	g.setColor(Color.BLUE);
	            algLine = new Line2D.Double(height -(1-(((algEdge.get(i).v1.longitude + lonMax) * (height / lonDist)))),
	            		(width-((algEdge.get(i).v1.latitude - latMin) * (width / latDist))), 
	            		(height-(1-((algEdge.get(i).v2.longitude + lonMax) * (height / lonDist)))),
	            		(width-((algEdge.get(i).v2.latitude - latMin) * (width / latDist))));
	            g2.draw(algLine);
	        }
			
			
		}
		
	}
	
	
	
	
	
	
	
}
