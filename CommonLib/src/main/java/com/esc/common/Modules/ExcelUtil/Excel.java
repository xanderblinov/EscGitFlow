package com.esc.common.Modules.ExcelUtil;

import com.sun.istack.internal.Nullable;
import com.sun.javaws.exceptions.InvalidArgumentException;
import jxl.*;
import jxl.write.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by afirsov on 2/3/2016.
 */
public class Excel implements IExcel{
    private WritableWorkbook workbook;
    private int lastSheetNumber;

    public Excel(String path) throws IOException, WriteException {
        workbook = Workbook.createWorkbook(new File(path));
    }

    @Override
    public void AddSheet(Sheet sheet) throws IOException, WriteException, InvalidArgumentException {
        workbook.importSheet(sheet.getName(),lastSheetNumber, sheet);
        lastSheetNumber++;
    }

    @Override
    public WritableSheet GetSheet(int index) throws IOException, WriteException, InvalidArgumentException {
        workbook.copySheet(index,"Copy" + index,lastSheetNumber);
        lastSheetNumber++;
        return workbook.getSheet(lastSheetNumber);
    }

    @Override
    public void CreateSheet(String name) throws IOException, WriteException, NullPointerException, InvalidArgumentException {
        if(name.length() > 31){
            throw new InvalidArgumentException(new String[]{"name", "Sheet name should not exceed 31 character length."});
        }
        workbook.createSheet(name, lastSheetNumber);
        lastSheetNumber++;
    }

    @Override
    public void PutString(String sheetName, int column, int row, String value) throws WriteException {
        Label label = new Label(column, row, value);
        workbook.getSheet(sheetName).addCell(label);
    }

    @Override
    public void PutFormula(String sheetName, int column, int row, String value) throws WriteException {
        Formula label = new Formula(column, row, value);
        workbook.getSheet(sheetName).addCell(label);
    }

    @Override
    public void Save() throws IOException, WriteException {
        workbook.write();
    }

    @Override
    public void Close() throws IOException, WriteException {
        workbook.close();
    }

    @Override
    public void SaveAndClose() throws IOException, WriteException {
        workbook.write();
        workbook.close();
    }
}
