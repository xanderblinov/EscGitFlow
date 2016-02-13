package net.inference.sqlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import net.inference.Config;
import net.inference.database.IArticleApi;
import net.inference.database.IAuthorApi;
import net.inference.database.IAuthorArticleApi;
import net.inference.database.IAuthorCompanyApi;
import net.inference.database.IClusterApi;
import net.inference.database.ICommonWordApi;
import net.inference.database.ICompanyApi;
import net.inference.database.IDatabaseApi;
import net.inference.database.IEvolutionApi;
import net.inference.database.IPrimitiveAuthorApi;
import net.inference.database.IPrimitiveTermApi;
import net.inference.database.IPrimitiveTermToPrimitiveTermApi;
import net.inference.database.ITermApi;
import net.inference.database.dto.ICommonWord;
import net.inference.sqlite.dto.Article;
import net.inference.sqlite.dto.Author;
import net.inference.sqlite.dto.AuthorToArticle;
import net.inference.sqlite.dto.AuthorToCluster;
import net.inference.sqlite.dto.AuthorToCompany;
import net.inference.sqlite.dto.Cluster;
import net.inference.sqlite.dto.AuthorToAuthor;
import net.inference.sqlite.dto.Company;
import net.inference.sqlite.dto.Evolution;
import net.inference.sqlite.dto.EvolutionSlice;
import net.inference.sqlite.dto.PrimitiveAuthor;
import net.inference.sqlite.dto.PrimitiveAuthorToAuthor;
import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.PrimitiveTermToPrimitiveTerm;
import net.inference.sqlite.dto.Term;


import java.sql.SQLException;

/**
 * Date: 12/21/2014
 * Time: 9:33 PM
 *
 * @author xanderblinov
 */
public class DatabaseApi implements IDatabaseApi
{
	private final DbHelper mDbHelper;
	private IArticleApi mArticleApi = new ArticleApi(this);
	private IAuthorApi authorApi = new AuthorApi(this);
	private IPrimitiveAuthorApi mPrimitiveAuthorApi = new PrimitiveAuthorApi(this);
	private IClusterApi clusterApi = new ClusterApi(this);
	private IEvolutionApi evolutionApi = new EvolutionApi(this);
	private IAuthorCompanyApi authorCompanyApi = new AuthorToCompanyApi(this);
	private IAuthorArticleApi authorArticleApi = new AuthorToArticleApi(this);
	private ICompanyApi companyApi = new CompanyApi(this);
	private IPrimitiveTermApi primtermApi = new PrimitiveTermApi(this);
	private IPrimitiveTermToPrimitiveTermApi primtermToTermApi = new PrimitiveTermToPrimitiveTermApi(this);
	private ITermApi termApi = new TermApi(this);
	private ICommonWordApi commonWordApi = new CommonWordApi(this);


	public DatabaseApi(Config.Database database, boolean recreateDatabase)
	{
		mDbHelper = new DbHelper(database, recreateDatabase);

	}

	@Override
	public void onStart()
	{
		mDbHelper.onStart();
	}

	@Override
	public void onStop()
	{
		mDbHelper.onStop();
	}


	@Override
	public IArticleApi article()
	{
		return mArticleApi;
	}

    @Override
    public IAuthorApi author() {
        return authorApi;
    }

	@Override
	public IClusterApi cluster() {
		return clusterApi;
	}

	@Override
	public IEvolutionApi evolution() {
		return evolutionApi;
	}

	@Override
	public IAuthorArticleApi authorArticle()
	{
		return authorArticleApi;
	}

	@Override
	public IAuthorCompanyApi authorCompany()
	{
		return authorCompanyApi;
	}

	@Override
	public ICompanyApi company()
	{
		return companyApi;
	}

	@Override
	public IPrimitiveAuthorApi primitiveAuthor()
	{
		return mPrimitiveAuthorApi;
	}

	@Override
	public IPrimitiveTermApi primterm()
	{
		return  primtermApi;
	}

	@Override
	public IPrimitiveTermToPrimitiveTermApi primTermToTerm()
	{
		return primtermToTermApi;
	}

	@Override
	public ITermApi term()
	{
		return  termApi;
	}

	@Override
	public ICommonWordApi commonWord()
	{
		return commonWordApi;
	}


	public <T> Dao<Article, T> getArticleDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), Article.class);
	}

	<T> Dao<Author, T> getInferenceAuthorDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), Author.class);
	}

	<T> Dao<AuthorToAuthor, T> getAuthorToAuthorDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), AuthorToAuthor.class);
	}

    <T> Dao<AuthorToCluster, T> getAuthorToClusterDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), AuthorToCluster.class);
	}

    <T> Dao<Cluster, T> getClusterDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), Cluster.class);
	}

	<T> Dao<Evolution, T> getEvolutionDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), Evolution.class);
	}

    <T> Dao<EvolutionSlice, T> getEvolutionSliceDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), EvolutionSlice.class);
	}

	<T> Dao<AuthorToArticle, T> getAuthorToArticleDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), AuthorToArticle.class);
	}

	<T> Dao<AuthorToCompany, T> getAuthorToCompanyDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), AuthorToCompany.class);
	}

	<T> Dao<Company, T> getCompanyDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), Company.class);
	}

	<T> Dao<PrimitiveAuthorToAuthor, T> getPrimitiveAuthorToAuthorDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), PrimitiveAuthorToAuthor.class);
	}
	<T> Dao<PrimitiveAuthor, T> getPrimitiveAuthorDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), PrimitiveAuthor.class);
	}

	<T> Dao<PrimitiveTerm, T> getPrimitiveTermDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), PrimitiveTerm.class);
	}

	<T> Dao<PrimitiveTermToPrimitiveTerm, T> getPrimitiveTermToPrimitiveTermDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), PrimitiveTermToPrimitiveTerm.class);
	}

	<T> Dao<Term, T> getTermDao() throws SQLException
	{
		return DaoManager.createDao(mDbHelper.getConnection(), Term.class);
	}

	public ConnectionSource getConnection() {
		try {
			return mDbHelper.getConnection();
		} catch (SQLException e) {
			SqliteLog.log(e);
		}

		return null;
	}

}
