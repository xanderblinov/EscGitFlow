package com.esc.common.util.Matrices;

import java.util.Arrays;

/**
 * Created by afirsov on 1/29/2016.
 */
public class LabeledMatrice2Float implements IMatrice2<String,Float>{
    public static void main(String[] args)
    {
        LabeledMatrice2Float mat = new LabeledMatrice2Float(2,2);

        mat.Add(0,0,1.0f);
        mat.Add(0,1,1.0f);
        mat.Add(1,0,1.0f);
        mat.Add(1,1,1.0f);

        mat.NormalizeColumns();
    }

    public LabeledMatrice2Float(int column, int row){
        Initialize(column,row);
    }

    private void Initialize(int column, int row){
        data = new String[row + 1][column + 1];
        initialData = new String[row + 1][column + 1];

        for (int i = 0; i< data.length;i++){
            if(i == 0){
                for (int j = 1; j< data[i].length;j++){
                    data[i][j] = Integer.toString(j);
                    initialData[i][j] = Integer.toString(j);
                }
            }
            else{
                data[i][0] = Integer.toString(i);
                initialData[i][0] = Integer.toString(i);
            }
        }
    }
    public LabeledMatrice2Float(Matrice2Float matrice){
        Float[][] data = matrice.GetMatrice();
        Initialize(data.length, data[0].length);

        for(int i = 0; i<data[0].length;i++){
            for(int j = 0; j<data.length;j++){
                Add(i,j,data[j][i]);
            }
        }
    }

    private String[][] data;
    private String[][] initialData;

    @Override
    public void Add(int column, int row, Float value) {
        if(column + 1 < 0 || column + 1 == 0 || column + 1 > data.length||
                row + 1 < 0 | row + 1 == 0 || row + 1 > data[0].length)
        {
            throw new IllegalArgumentException("Column or row number is invalid for this type if matrix.");
        }

        data[row + 1][column + 1] = "Value:" + value;
    }

    @Override
    public void NormalizeColumns() {
        for (int i = 1; i<data[0].length;i++) {
            Double sumSquare = 0.0;
            for (int j = 1; j<data.length;j++) {
                 sumSquare += Math.pow(Double.parseDouble(data[j][i].split(":")[1]),2);
            }

            sumSquare = Math.pow(sumSquare,(double)1/2);
            for (int j = 1; j<data.length;j++) {
                if(sumSquare == 0 || sumSquare == null || Double.isNaN(sumSquare)){
                    data[j][i] = "Value:0.0f";
                    continue;
                }
                Float result = Float.parseFloat(data[j][i].split(":")[1])/ sumSquare.floatValue();
                data[j][i] = "Value:" + result;
            }
        }
    }

    @Override
    public void Reset() {
        String[][] buffer = initialData;
        initialData = new String[data.length][data[0].length];
        data = buffer;
    }

    @Override
    public int startColumnIndex() {
        return 1;
    }

    @Override
    public int startRowIndex() {
        return 1;
    }

    @Override
    public String[][] GetMatrice() {
        return Arrays.copyOfRange(data, 0, data.length);
    }
}
