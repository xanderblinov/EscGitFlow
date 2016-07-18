/**
 * Created by Мария on 11.05.2016.
 */
public class MapElem
{
	private String eName;
	private int eCluster;
	private double eWeight;

	public MapElem(String name, int cluster, double weight)
	{
		eName = name;
		eCluster = cluster;
		eWeight = weight;
	}

	public int getClusterNumber(){ return eCluster; }

	public String getName(){ return eName; }

	public double getWeight(){ return eWeight; }
}
