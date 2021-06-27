package Algorithms;

public class StatLib {

	public static float max(float[] x)
	{
		float max = x[0];
		for (int i = 1; i < x.length; i++) {
			if (x[i]>max)
				max = x[i];
		}
		return max;
	}

	public static float avg(float[] x)
	{
		float sum = 0;
		for (float v : x)
			sum = sum + v;
		return (sum / x.length);
	}

	public static float var(float[] x)
	{
		float variance = 0;
		float average = avg(x);
		for (float v : x)
			variance = variance + (v * v);
		average = average * average;
		return ((variance / x.length) - average);
	}

	public static float cov(float[] x, float[] y)
	{
		float average_x = avg(x);
		float average_y = avg(y);
		float sum = 0;
		for (int i = 0; i < x.length; i++)
			sum = sum + ((x[i] - average_x) * (y[i] - average_y));
		return sum / x.length;
	}

	public static float pearson(float[] x, float[] y)
	{
		float cov = cov(x,y);
		float var_x = (float) Math.sqrt(var(x));
		float var_y = (float) Math.sqrt(var(y));
		float pearson = 0;
		pearson = cov / (var_x * var_y);
		return pearson;
	}

	public static Line linear_reg(Point[] points)
	{
		float a, b, average_x, average_y;
		float[] arr_x = new float[points.length];
		float[] arr_y = new float[points.length];
		for (int i = 0; i < points.length; i++)
		{
			arr_x[i] = points[i].x;
			arr_y[i] = points[i].y;
		}
		average_x = avg(arr_x);
		average_y = avg(arr_y);
		a = cov(arr_x, arr_y) / var(arr_x);
		b = average_y - (a * average_x);
		Line line_eq = new Line(a, b);
		return line_eq;
	}

	public static float dev(Point p, Point[] points)
	{
		Line temp_linear = linear_reg(points);
		float deviation;
		deviation = dev(p, temp_linear);
		return deviation;
	}

	public static float dev(Point p, Line l)
	{
		float deviation;
		deviation = Math.abs(l.f(p.x) - p.y);
		return deviation;
	}
}
