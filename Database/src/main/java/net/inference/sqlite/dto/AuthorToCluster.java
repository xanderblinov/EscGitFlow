package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.IAuthorToCluster;
import net.inference.database.dto.ICluster;

/**
 * Date: 2/15/2015
 * Time: 6:49 PM
 *
 * @author xanderblinov
 */
@DatabaseTable(tableName = IAuthorToCluster.TABLE_NAME)
public class AuthorToCluster implements IAuthorToCluster
{
    @DatabaseField(generatedId = true)
    private long id;

	@DatabaseField(foreign = true, columnName = Column.author)
	private Author author;
	@DatabaseField(foreign = true, columnName = Column.cluster)
	private Cluster cluster;


	public AuthorToCluster()
	{
	}

	public AuthorToCluster(final IAuthor author, final ICluster cluster)
	{
		setAuthor(author);
		setCluster(cluster);
	}


    public IAuthor getAuthor() {
        return author;
    }

    public void setAuthor(IAuthor author) {
        this.author = (Author) author;
    }

    public ICluster getCluster() {
        return cluster;
    }

    public void setCluster(ICluster cluster) {
        this.cluster = (Cluster) cluster;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorToCluster that = (AuthorToCluster) o;

        if (id != that.id) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        return !(cluster != null ? !cluster.equals(that.cluster) : that.cluster != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (cluster != null ? cluster.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthorToCluster{" +
                "id=" + id +
                ", author=" + author +
                ", cluster=" + cluster +
                '}';
    }
}
