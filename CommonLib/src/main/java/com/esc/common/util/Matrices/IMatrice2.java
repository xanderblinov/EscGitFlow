package com.esc.common.util.Matrices;

/**
 * Created by afirsov on 1/29/2016.
 */
public interface IMatrice2<MatrixCells, MatrixOut> {
    void Add(int x,int y, MatrixOut value);
    void NormalizeColumns();
    void Reset();
    int startColumnIndex();
    int startRowIndex();
    MatrixCells[][] GetMatrice();
}
