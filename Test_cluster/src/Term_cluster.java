import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.clustering.MultiKMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

import net.sf.javaml.clustering.evaluation.AICScore;
import net.sf.javaml.clustering.evaluation.BICScore;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.tools.weka.WekaClusterer;
import weka.clusterers.XMeans;
/**
 * Created by Мария on 26.02.16.
 */
public class Term_cluster
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

		int [][] term_array = new int[countTerm][countTerm];

		int countTermToTerm = 0;
		resSet = statement.executeQuery("SELECT * FROM term_to_term");
		while (resSet.next())
			countTermToTerm++;

		//сделали матрицу расстояний между терминами
		resSet = statement.executeQuery("SELECT * FROM term_to_term");
		for(int i = 0; i < countTermToTerm; i++){
			resSet.next();
			term_array[resSet.getInt("from") - 1][resSet.getInt("to") - 1] += resSet.getInt("count");
		}

		Dataset data = new DefaultDataset();
		double[] rel = new double[countTerm];

		for(int i = 0; i < countTerm; i++){
			for(int j = 0; j < countTerm; j++){
				rel[j] = term_array[i][j];
			}
			Instance tmpInstance = new DenseInstance(rel);// TODO SparseInstanse
			data.add(tmpInstance);
		}

		//cluster from JavaML
		Clusterer km = new KMeans();
		Dataset[] clusters = km.cluster(data);
		System.out.println("Cluster count: " + clusters.length);

		ClusterEvaluation sse= new SumOfSquaredErrors();
		ClusterEvaluation aic = new AICScore();
		ClusterEvaluation bic = new BICScore();

		double score=sse.score(clusters);
		double aicScore = aic.score(clusters);
		double bicScore = bic.score(clusters);

		System.out.println("AIC score: " + aicScore);
		System.out.println("BIC score: " + bicScore);
		System.out.println("Sum of squared errors: " + score);

		//cluster from weka
		XMeans xm = new XMeans();
		Clusterer jmlxm = new WekaClusterer(xm);
		Dataset[] clusters_weka = jmlxm.cluster(data);
		System.out.println("Weka: " + clusters_weka.length + "score is: " + sse.score(clusters_weka));

	}

}
