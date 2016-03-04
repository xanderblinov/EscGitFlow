import net.inference.Config;
import net.inference.sqlite.DatabaseApi;
import net.inference.sqlite.dto.TermToTerm;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.AICScore;
import net.sf.javaml.clustering.evaluation.BICScore;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.weka.WekaClusterer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import weka.clusterers.XMeans;
/**
 * Created by M.Pankova on 26.02.16.
 */
public class TermCluster
{
	//public static Connection connection;
	//public static Statement statement;
	//public static ResultSet resSet;

	public static void main(String[] args)throws ClassNotFoundException, SQLException, IOException
	{
		/*Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:Database\\src\\main\\resources\\test.db");
		System.out.println("Database connected");
		statement = connection.createStatement();*/

		DatabaseApi api = new DatabaseApi(Config.Database.TEST, false);

		int countTerm = (int) api.term().count();

		int [][] termsArray = new int[countTerm][countTerm];

		int countTermToTerm = (int) api.termToTerm().count();

		//made matrix between terms
		final List<TermToTerm> termToTermList = api.termToTerm().findAll();


		for(TermToTerm termToTerm: termToTermList)
		{
			//all connection were counted twice!
			termsArray[termToTerm.getFrom().getId()-1][termToTerm.getTo().getId()-1] += termToTerm.getCount();
		}


		Dataset data = new DefaultDataset();
		double[] rel = new double[countTerm];

		for(int i = 0; i < countTerm; i++){
			for(int j = 0; j < countTerm; j++){
				rel[j] = termsArray[i][j];
			}
			Instance tmpInstance = new DenseInstance(rel);// TODO SparseInstance
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
