package net.inference.database;

import com.j256.ormlite.support.ConnectionSource;
import net.inference.database.dto.Article;
import net.inference.database.dto.Cluster;
import net.inference.database.dto.Evolution;
import net.inference.database.dto.EvolutionSlice;

import java.util.List;

/**
 * Date: 12/21/2014
 * Time: 9:33 PM
 *
 * @author xanderblinov
 */
public interface DatabaseApi
{

	 ArticleApi article();
     AuthorApi  author();
	 ClusterApi cluster();
	 EvolutionApi evolution();

	/**
	 * Init database and table
	 */
	public void onStart();

	/**
	 * release connection etc.
	 */
	public void onStop();

	public Article addArticle(final Article article);

	public Cluster addCluster(final Cluster cluster);

	public Evolution addEvolution(final Evolution evolution);

	public EvolutionSlice addEvolutionSlice(final EvolutionSlice evolutionSlice);

	public List<? extends Article> getAllArticles();

	ConnectionSource getConnection();
}
