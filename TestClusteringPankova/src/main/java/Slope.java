/**
 * Created by Мария on 24.04.2016.
 */

//считаем наклон кривой между двумя точками, так нигде и не использовался, к сожалению
class Slope
{
	private double Slope;

	public Slope(double x1, double y1, double x2, double y2)
	{
		if(x1 > x2 || y1 > y2)
		{
			System.out.println("Incorrect coordinates.");
			return;
		}
		Slope = (y2 - y1) / (x2 = x1);
	}

	public double getSlope(){ return Slope; }

}
