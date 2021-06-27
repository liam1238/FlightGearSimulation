package Algorithms;

public class AnomalyReport
{
	public final String description;
	public final  long timeStep;
	public float x, y;
	public AnomalyReport(String description, long timeStep, float x, float current)
	{
		this.description=description;
		this.timeStep= timeStep;
		this.x = x;
		this.y = current;
	}
}
