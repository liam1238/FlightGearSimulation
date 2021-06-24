package Algorithms;

public class AnomalyReport {
	public final String description;
	public final  long timeStamp;
	public float x, y;	//coords in the graph
	public AnomalyReport(String description, long timeStamp, float x, float current){
		this.description=description;
		this.timeStamp=timeStamp;
		this.x = x;
		this.y = current;
	}

}
