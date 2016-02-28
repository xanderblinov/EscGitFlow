package com.esc.common.Modules.ExcelUtil;

import com.sun.javaws.exceptions.InvalidArgumentException;
import jxl.Sheet;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

import java.io.IOException;

/**
 * Created by afirsov on 2/3/2016.
 */
public interface IExcel {
    void AddSheet(Sheet sheet) throws IOException, WriteException, InvalidArgumentException;
    Sheet GetSheet(int index) throws IOException, WriteException, InvalidArgumentException;
    void CreateSheet(String name) throws IOException, WriteException, InvalidArgumentException;
    void PutString(String sheetName, int column, int row, String value) throws WriteException;
    void PutFormula(String sheetName, int column, int row, String value) throws WriteException;
    void Save() throws IOException, WriteException;
    void Close() throws IOException, WriteException;
    void SaveAndClose() throws IOException, WriteException;
}
