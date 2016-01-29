package com.esc.common.util;

import com.esc.common.Modules.AffiliationResolver.MatrixType;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 * Created by afirsov on 1/29/2016.
 */
public class Matrice2Float implements IMatrice2<Float>{
    public static void main(String[] args)
    {
        Matrice2Float mat = new Matrice2Float(2,2);

        mat.Add(0,0,1.0f);
        mat.Add(0,1,1.0f);
        mat.Add(1,0,1.0f);
        mat.Add(1,1,1.0f);

        mat.NormalizeColumns();
    }

    public Matrice2Float(int x, int y){
        data = new Float[x][y];
        initialData = new Float[x][y];
    }
    private Float[][] data;
    private Float[][] initialData;

    @Override
    public void Add(int x, int y, Float value) {
        if(x<0 || y<0 || x>=data.length || y>=data[0].length)
        {
            //todo: bad coding, enhance
            return;
        }
        data[x][y] = value;
    }

    @Override
    public void NormalizeColumns() {
        for (int i = 0; i<data.length;i++) {
            Double sumSquare = 0.0;
            for (int j = 0; j<data[i].length;j++) {
                sumSquare += Math.pow(data[i][j],2);
            }
            sumSquare = Math.pow(sumSquare,(double)1/2);
            for (int j = 0; j<data[i].length;j++) {
                data[i][j] = sumSquare == 0 || sumSquare == null ? 0.0f :(data[i][j] / sumSquare.floatValue());
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
    public Float[][] GetMatrice() {
        return data;
    }
}
