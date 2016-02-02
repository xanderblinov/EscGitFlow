package com.esc.common.util.Matrices;

/**
 * Created by afirsov on 1/29/2016.
 */
public class Matrice2Float implements IMatrice2<Float,Float>{
    public static void main(String[] args)
    {
        Matrice2Float mat = new Matrice2Float(2,2);

        mat.Add(0,0,1.0f);
        mat.Add(0,1,1.0f);
        mat.Add(1,0,1.0f);
        mat.Add(1,1,1.0f);

        mat.NormalizeColumns();
    }

    public Matrice2Float(int column, int row){
        data = new Float[row][column];
        initialData = new Float[row][column];
    }
    private Float[][] data;
    private Float[][] initialData;

    @Override
    public void Add(int column, int row, Float value) {
        if(column<0 || row<0 || row>=data.length || column>=data[0].length)
        {
            throw new IllegalArgumentException("Column or row number is invalid for this type of matrix");
        }
        data[row][column] = value;
    }

    @Override
    public void NormalizeColumns() {
        for (int i = 0; i<data[0].length;i++) {
            Double sumSquare = 0.0;
            for (int j = 0; j<data.length;j++) {
                sumSquare += Math.pow(data[j][i],2);
            }
            sumSquare = Math.pow(sumSquare,(double)1/2);
            for (int j = 0; j<data.length;j++) {
                data[j][i] = sumSquare == 0 || sumSquare == null ? 0.0f :(data[j][i] / sumSquare.floatValue());
            }
        }
    }

    @Override
    public void Reset() {
        Float[][] buffer = initialData;
        initialData = new Float[data.length][data[0].length];
        data = buffer;
    }

    @Override
    public int startColumnIndex() {
        return 0;
    }

    @Override
    public int startRowIndex() {
        return 0;
    }

    @Override
    public Float[][] GetMatrice() {
        return data;
    }
}
