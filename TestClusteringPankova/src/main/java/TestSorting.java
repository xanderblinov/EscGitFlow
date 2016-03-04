import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by M.Pankova on 11.12.15.
 */
public class TestSorting
{
	public static Connection connection;
	public static Statement statement;
	public static ResultSet resSet;

	public static void main(String[] args)throws ClassNotFoundException, SQLException, IOException
	{
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:Database\\src\\main\\resources\\test.db");
		System.out.println("Database connected");
		statement = connection.createStatement();

		int countTerm = 0;
		resSet = statement.executeQuery("SELECT * FROM term");
		while (resSet.next())
			countTerm++;

		int countArticle = 0;
		resSet = statement.executeQuery("SELECT * FROM article");
		while (resSet.next())
			countArticle++;

		int countPrimTerm = 0;
		resSet = statement.executeQuery("SELECT * FROM primitive_term");
		while (resSet.next())
			countPrimTerm++;

		int [][] term_array = new int[countTerm][countArticle];

		String[]  arr_term = new String[countTerm];
		resSet = statement.executeQuery("SELECT * FROM term");
		for(int i = 0; i < countTerm; i++){
			resSet.next();
			arr_term[i] = resSet.getString("value");
			//System.out.println(arr_term[i]);
		}

		resSet = statement.executeQuery("SELECT * FROM primitive_term");
		int num_term, num_pub;
		for(int i = 0; i < countPrimTerm; i++){
			resSet.next();
			num_term = resSet.getInt("term");
			num_pub = resSet.getInt("publication");
			term_array[num_term-1][num_pub-1]++;
		}
		/*for(int i = 0; i < countTerm; i++){
			System.out.print(i + ": (");
			for(int j = 0; j < countArticle; j++)
				System.out.print(term_array[i][j] + ", ");
			System.out.println(")");
		}*/

		double[][] term_distance = new double[countTerm][countTerm];
		for(int i = 0; i < countTerm; i++){
			for(int j = i; j < countTerm; j++){
				term_distance[i][j] = HammingDistance(term_array[i],term_array[j]);
				term_distance[j][i] = term_distance[i][j];
				System.out.println(i+" "+j+"Dist is " +  term_distance[i][j]);
			}
		}

		int k = 10, rand_number; //number of clusters
		List<Integer> cluster_centers = new ArrayList<>();
		for(int i = 0; i < k; i++){
			rand_number = (int)(Math.random()*countTerm);
			if( !cluster_centers.contains(rand_number))
				cluster_centers.add(rand_number);
			else
				i = i-1;
			System.out.print(cluster_centers.get(i) + " ");
		}

		HashSet<Integer>[] clusters = new HashSet[k];
		for(int i = 0; i < k; i++)
			clusters[i] = new HashSet<Integer>();

		int c = 0;
		double dist, min_dist = 100.0;
		for(int i = 0; i < countTerm; i++){
			for(int j = 0; j < k; j++){
				dist = term_distance[cluster_centers.get(j)][i];
				//System.out.println("Dist is " + dist);
				if(dist < min_dist){
					min_dist = dist;
					c = j;
				}
			}
			//System.out.println("Choosed cluster" + c);
			clusters[c].add(i);
		}

		for(int i = 0; i < k; i++){
			Iterator<Integer> itr = clusters[i].iterator();
			System.out.println("\nCluster " + i + "is:");
			while(itr.hasNext()){
				int index = itr.next();
				System.out.print(arr_term[index] + " ");
			}
		}

		connection.close();
		statement.close();
	}

	public static double cosDistance(int[] a, int[] b){
		return productOf(a,b) / (Math.sqrt(productOf(a,a)) * Math.sqrt(productOf(b,b)));
	}

	private static double productOf(int[] a, int[] b){
		double product = 0;
		if(a.length == b.length){
			for(int i = 0; i < a.length; i++)
				product = product + a[i]*b[i];
			return product;
		}
		return 0.0;
	}

	private static double EuclidDistance(int[] a, int[] b){
		double product = 0;
		if(a.length == b.length){
			for(int i = 0; i < a.length; i++)
				product = product + Math.pow((a[i]-b[i]),2);
			return Math.sqrt(product);
		}
		return 0.0;
	}

	private static double HammingDistance(int[] a, int[] b){
		double dist = 0;
		for(int i = 0; i < a.length; i++){
			if(a[i] != 0 && b[i] != 0)
				dist++;
		}
		return  dist;
	}



}
