package Algorithms;

import java.util.Collection;

final class Circle
{
	private static final double MULTIPLICATIVE_EPSILON = 1 + 1e-14;
	public final Point centerPoint;
	public final double Radius;
	
	public Circle(Point c, double r)
	{
		this.centerPoint = c;
		this.Radius = r;
	}

	public boolean contains(Point p) { return centerPoint.distance(p) <= Radius * MULTIPLICATIVE_EPSILON; }

	public boolean contains(Collection<Point> ps)
	{
		for (Point p : ps)
		{
			if (!contains(p))
				return false;
		}
		return true;
	}
	
	public String toString() {
		return String.format("Circle(x=%g, y=%g, r=%g)", centerPoint.x, centerPoint.y, Radius);
	}
}
