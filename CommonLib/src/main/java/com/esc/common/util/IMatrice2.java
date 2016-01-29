package com.esc.common.util;

/**
 * Created by afirsov on 1/29/2016.
 */
public interface IMatrice2<T> {
    void Add(int x,int y, T value);
    void NormalizeColumns();
    void Reset();
    T[][] GetMatrice();
}
