package ru.gzheyts.jnetworkviewer.gui;

import net.inference.Config;
import net.inference.database.dto.IAuthor;
import net.inference.database.dto.ICluster;
import net.inference.sqlite.DatabaseApi;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import ru.gzheyts.jnetworkviewer.model.convertеrs.ToStringConverter;

/**
 * @author gzheyts
 */
public class ClusterAuthorTableView extends JPanel
{

	private JTable table;
	private JScrollPane scrollPane;

	public ClusterAuthorTableView(ICluster[] clusters)
	{
		super(new BorderLayout());
		ClusterUsersTableModel tableModel = new ClusterUsersTableModel();

		tableModel.init(clusters);

		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}

	class ClusterUsersTableModel extends AbstractTableModel
	{

		DatabaseApi api = new DatabaseApi(Config.Database.TEST, false);

		private ArrayList<String> columnNames = new ArrayList<String>();
		private ArrayList<Class> columnTypes = new ArrayList<Class>();
		private int rowCount = 0;
		private ArrayList<List<String>> data;


		public void init(ICluster[] clusters)
		{

			data = new ArrayList<>(clusters.length);

			for (int i = 0; i < clusters.length; i++)
			{
				columnNames.add("cluster" + clusters[i].getId());
				columnTypes.add(String.class);
				data.add(i, (List<String>) CollectionUtils.collect(api.author().findAuthorsForCluster(clusters[i]), new Transformer<IAuthor, String>()
				{
					public String transform(IAuthor author)
					{
						return ToStringConverter.convert(author);
					}
				}));
				rowCount = Math.max(rowCount, data.get(i).size());
			}
		}

		@Override
		public String getColumnName(int column)
		{
			return columnNames.get(column);
		}

		@Override
		public Class<?> getColumnClass(int columnIndex)
		{
			return columnTypes.get(columnIndex);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex)
		{
			return false;
		}

		public int getRowCount()
		{
			return rowCount;
		}

		public int getColumnCount()
		{
			return columnNames.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex)
		{
			String name = data.get(columnIndex).get(rowIndex);
			return name == null ? "" : name;
		}
	}
}



