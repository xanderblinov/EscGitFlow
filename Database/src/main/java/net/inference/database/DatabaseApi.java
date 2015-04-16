package net.inference.database;

import java.util.List;

import net.inference.database.dto.Article;
import net.inference.database.dto.Cluster;
import net.inference.database.dto.Evolution;
import net.inference.database.dto.EvolutionSlice;

/**
 * Date: 12/21/2014
 * Time: 9:33 PM
 *
 * @author xanderblinov
 */
public interface DatabaseApi
{

	public ArticleApi article();
    public AuthorApi  author();
	public ClusterApi cluster();

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
}
