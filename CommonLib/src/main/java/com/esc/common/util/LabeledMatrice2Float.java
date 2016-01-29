package com.esc.common.util;

import com.esc.common.Modules.AffiliationResolver.MatrixType;
import org.apache.commons.math3.linear.MatrixUtils;

import java.util.Arrays;

/**
 * Created by afirsov on 1/29/2016.
 */
public class LabeledMatrice2Float implements IMatrice2<String>{
    public static void main(String[] args)
    {
        LabeledMatrice2Float mat = new LabeledMatrice2Float(2,2);

        mat.Add(0,0,"1");
        mat.Add(0,1,"1");
        mat.Add(1,0,"1");
        mat.Add(1,1,"1");

        mat.NormalizeColumns();
    }

    public LabeledMatrice2Float(int x, int y){
        Initialize(x,y);
    }

    private void Initialize(int x, int y){
        data = new String[x + 1][y + 1];
        initialData = new String[x + 1][y + 1];

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

        for(int i = 0; i<data.length;i++){
            for(int j = 0; j<data.length;j++){
                Add(i,j,Float.toString(data[i][j]));
            }
        }
    }

    private String[][] data;
    private String[][] initialData;

    @Override
    public void Add(int x, int y, String value) {
        if(x + 1 < 0 || x + 1 == 0 || x + 1 > data.length||
                y+1 < 0 | y + 1 == 0 || y+1>data[0].length)
        {
            //todo: bad coding, enhance
            return;
        }
        try{
            Float.parseFloat(value);
        }
        catch(Exception e)
        {
            //todo: bad coding, enhance
            return;
        }
        data[x + 1][y + 1] = "Value:" + value;
    }

    @Override
    public void NormalizeColumns() {
        for (int i = 1; i<data.length;i++) {
            Double sumSquare = 0.0;
            for (int j = 1; j<data[i].length;j++) {
                sumSquare += Math.pow(Double.parseDouble(data[i][j].split(":")[1]),2);
            }
            sumSquare = Math.pow(sumSquare,(double)1/2);
            for (int j = 1; j<data[i].length;j++) {
                data[i][j] = "Value:" + (sumSquare == 0 || sumSquare == null ? 0.0f :
                        Float.parseFloat(data[i][j].split(":")[1])/ sumSquare.floatValue());
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
    public String[][] GetMatrice() {
        return Arrays.copyOfRange(data, 1, data.length);
    }
}
