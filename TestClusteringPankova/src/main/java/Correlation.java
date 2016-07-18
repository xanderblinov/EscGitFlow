/**
 * Created by Мария on 02.04.2016.
 */
public class Correlation
{
	private double[] X;
	private double[] Y;
	private double Correlation;

	Correlation(double [] x, double [] y)
	{
		if(x.length != y.length)
		{
			System.out.println("Different sample sizes");
			return;
		}

		if(x.length == 0)
		{
			System.out.println("Zero sample size");
			return;
		}

		X = x;
		Y = y;
		int i,  n = x.length;

		double X_ = 0, Y_ = 0, cov = 0, sigmaX = 0, sigmaY = 0;

		for(i = 0; i < n; i++)
		{
			X_ = X_ + x[i];
			Y_ = Y_ + y[i];
		}
		X_ = X_ / n;
		Y_ = Y_ / n;

		for(i = 0; i < n; i++)
		{
			cov = cov + (x[i] - X_)*(y[i] - Y_);
			sigmaX = sigmaX + Math.pow((x[i] - X_), 2);
			sigmaY = sigmaY + Math.pow((y[i] - Y_), 2);
		}

		Correlation = cov / Math.pow(sigmaX*sigmaY, 0.5);
	}

	public double getCorrelation(){ return Correlation; }
}
