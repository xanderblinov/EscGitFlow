package com.esc.common.Modules.ExcelUtil;

import com.esc.common.Clusterers.AffiliationClusterer;
import com.esc.common.util.Pair;
import com.sun.javaws.exceptions.InvalidArgumentException;
import input.Dataset;
import input.FeatureVector;
import jxl.write.WriteException;
import output.Cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by afirsov on 2/4/2016.
 */
public class ClustersToExcel {

    private ArrayList<AffiliationClusterer> arrClust;

    public ClustersToExcel() {
        arrClust = new ArrayList<AffiliationClusterer>();
    }

    public void Add(AffiliationClusterer clusterer) {
        arrClust.add(clusterer);
    }

    public void Transform(String folderWhereToCreate) throws IOException, WriteException, InvalidArgumentException {
        AffiliationClusterer[] massiveClust = arrClust.toArray(new AffiliationClusterer[arrClust.toArray().length]);
        Excel exc = new Excel(folderWhereToCreate + "/"
                + massiveClust[0].getSimilarityType()
                + "_"
                + massiveClust[0].getClasterType()
                + ".xls");

        String resultSheetName = "Result";
        exc.CreateSheet(resultSheetName);

        exc.PutString("Result", 0, 0, "Duration in ms");
        exc.PutString("Result", 1, 0, "Number of clusters");
        exc.PutString("Result", 2, 0, "Max volume");
        exc.PutString("Result", 3, 0, "Noise volume");

        int sheetNmuber = 0;
        int currRowInResult = 1;
        int currColumnInResult = 0;

        for (AffiliationClusterer clst : arrClust) {
            String sheetName = Integer.toString(sheetNmuber);
            exc.CreateSheet(sheetName);
            Dataset set = clst.getSet();
            String[] initialArray = clst.getInitialArray();

            int clusterNumber = set.getClustermap().entrySet().toArray().length;
            int maxVolume = 0;
            int noiseVolume = 0;

            int startDataColumn = 0;
            int startDataRow = 1;
            int offsetCountColumn = clusterNumber + 5;
            int offsetCountRow = 0;

            for (int i = 0; i < clusterNumber; i++) {
                Map.Entry<Integer, Cluster> elem = (Map.Entry<Integer, Cluster>) set.getClustermap().entrySet().toArray()[i];
                exc.PutString(sheetName, startDataColumn + i, startDataRow, "Cluster " + elem.getKey() + " contains: ");
                int clusterVolume = elem.getValue().getClusterelements().toArray().length;
                int j = startDataRow + 1;
                if (elem.getKey() < 0) {
                    noiseVolume = clusterVolume;
                }
                if (clusterVolume > maxVolume) {
                    maxVolume = clusterVolume;
                }
                for (FeatureVector fv : elem.getValue().getClusterelements()) {
                    exc.PutString(sheetName, i, j, initialArray[fv.getLabel() - 1]);
                    j++;
                }
                exc.PutFormula(sheetName, startDataColumn + i, startDataRow - 1, "COUNTA("
                        + (ExcelColumn.toName(startDataColumn + i+1)) + "" + (startDataRow + 1)
                        + ":" + (ExcelColumn.toName(startDataColumn + i+1)) + "" + (clusterVolume + 1) + ")");
            }

            int currColumn = startDataColumn + offsetCountColumn;
            int currRow = startDataRow + offsetCountRow;

            exc.PutString(sheetName, currColumn, currRow, "Duration in ms");
            exc.PutString(sheetName, currColumn, currRow + 1, Long.toString(clst.getDuration()));
            exc.PutFormula(resultSheetName, currColumnInResult, currRowInResult, "'" + sheetName + "'!" +
                    ExcelColumn.toName(currColumn+1) + "" + (currRow + 2));

            exc.PutString(sheetName, currColumn + 1, currRow, "Number of clusters");
            exc.PutFormula(sheetName, currColumn + 1, currRow + 1, "COUNTA(" +
                    ExcelColumn.toName(startDataColumn + 1) + startDataRow
                    + ":" + ExcelColumn.toName(startDataColumn + clusterNumber + 1) + startDataRow + ")");
            exc.PutFormula(resultSheetName, currColumnInResult + 1, currRowInResult, "'" + sheetName + "'!"
                    + ExcelColumn.toName(currColumn + 1+1) + "" + (currRow + 2));

            exc.PutString(sheetName, currColumn + 2, currRow, "Max volume");
            exc.PutFormula(sheetName, currColumn + 2, currRow + 1, "MAX("
                    +  ExcelColumn.toName(startDataColumn + 1) + (startDataRow)
                    +  ":" + ExcelColumn.toName(startDataColumn + clusterNumber + 1) + (startDataRow) + ")");
            exc.PutFormula(resultSheetName, currColumnInResult + 2, currRowInResult, "'" + sheetName + "'!"
                    +  ExcelColumn.toName(currColumn + 2+1) + "" + (currRow + 2));

            exc.PutString(sheetName, currColumn + 3, currRow, "Noise volume");
            exc.PutString(sheetName, currColumn + 3, currRow + 1, Integer.toString(noiseVolume));
            exc.PutFormula(resultSheetName, currColumnInResult + 3, currRowInResult, "'" + sheetName + "'!"
                    +  ExcelColumn.toName(currColumn + 3+1) + "" + (currRow + 2));


            int pairNumber = 0;
            ArrayList<Pair<String, String>> args = clst.getArguments();
            for (Pair pair : args) {
                exc.PutString(resultSheetName, currColumnInResult + 4 + pairNumber, 0, (String) pair.getKey());
                exc.PutString(resultSheetName, currColumnInResult + 4 + pairNumber, currRowInResult, (String) pair.getValue());
                pairNumber++;
            }

            sheetNmuber++;
            currRowInResult++;
        }
        exc.SaveAndClose();
    }

    private final String MaxExcelColumn = "IV";
}
