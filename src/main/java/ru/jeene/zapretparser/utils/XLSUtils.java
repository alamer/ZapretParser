/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jeene.zapretparser.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Работа с XLS файлом
 *
 *
 * @category
 * @author Ivan V. Scherbakov <antilamer87@mail.ru,ivc_ShherbakovIV@esrr.rzd>
 * @changedby $Author$
 * @version SVN: $Id$
 * @revision SVN: $Revision$
 * @link       $HeadURL$
 * @date $Date$
 * @copyright (c) 2009-2012 by
 * @see
 * @since
 */
public class XLSUtils {

    public static XLSUtils instance;

    public XLSUtils() {
        instance = this;
    }

    public static XLSUtils getInstance() {

        if (instance == null) {
            instance = new XLSUtils();
        }
        return instance;

    }

    public Workbook loadWorkbook(String fname) throws FileNotFoundException, IOException {
        Workbook w = new org.apache.poi.hssf.usermodel.HSSFWorkbook(new FileInputStream(fname));
        return w;
    }

    public String copyWorkbook(String from, String to) throws FileNotFoundException, FileNotFoundException, IOException {
        String res = "";
        FileUtils.copyFile(from, to);
        return to;
    }

    public Workbook copyAndLoadWorkbook(String from, String to) throws IOException {
        copyWorkbook(from, to);
        Workbook w;
        w = new org.apache.poi.hssf.usermodel.HSSFWorkbook(new FileInputStream(to));
        return w;
    }

    public Sheet loadSheet(Workbook w, int num) {
        Sheet res = w.getSheetAt(num);
        return res;
    }

    public Sheet loadSheet(Workbook w, String list_name) {
        Sheet res = w.getSheet(list_name);
        return res;
    }

    public Row loadRow(Sheet sheet, int num) {
        Row res;
        res = sheet.getRow(num);
        return res;
    }

    public Cell loadCell(Row r, int num) {
        Cell res;
        res = r.getCell(num);
        return res;
    }

    public String loadValue(Cell c) {
        String res;
        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return (c.getStringCellValue());

            case Cell.CELL_TYPE_FORMULA:
                //return c.getCellFormula().toString();
                try{
                    return String.format("%.2f", c.getNumericCellValue());
                }catch(Exception ee)
                {
                    return "0";
                }

            case Cell.CELL_TYPE_NUMERIC:
                return String.format("%.2f", c.getNumericCellValue());

            case Cell.CELL_TYPE_STRING: {
                return c.getRichStringCellValue().toString();
            }
        }
        return null;
    }

    public String loadValue(Cell c, int i) {
        String res;
        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return (c.getStringCellValue());

            case Cell.CELL_TYPE_FORMULA:
                //return c.getCellFormula().toString();
                return String.format("%." + i + "f", c.getNumericCellValue());

            case Cell.CELL_TYPE_NUMERIC:
                return String.format("%." + i + "f", c.getNumericCellValue());
            //return String.valueOf(c.getNumericCellValue());

            case Cell.CELL_TYPE_STRING: {
                return c.getRichStringCellValue().toString();
            }
        }
        return null;
    }

    public String loadValue(Workbook w, int sheet, int row, int column) {
        String res;
        Sheet s = w.getSheetAt(sheet);
        Row r = s.getRow(row);
        Cell c = r.getCell(column);
        res = loadValue(c);
        return res;
    }

    /*
     * Поиск листа с нужным именем
     * 
     */
    public Sheet findByName(Workbook w, String name) {
        Sheet res=null;
        int cnt = w.getNumberOfSheets();
        name=StringUtils.replaceAll(name, " ", "");
        for (int i = 0; i < cnt; i++) {
            Sheet tmp = w.getSheetAt(i);
            String key =StringUtils.replaceAll(tmp.getSheetName(), " ", "");
            boolean foundMatch = false;
            try {
                Pattern regex = Pattern.compile("("+name+")$", Pattern.MULTILINE);
                Matcher regexMatcher = regex.matcher(key);
                foundMatch = regexMatcher.find();
                if (foundMatch)
                {
                    res=w.getSheetAt(i);
                    break;                    
                }
            } catch (PatternSyntaxException ex) {
                // Syntax error in the regular expression
            }

        }
        return res;
    }
    
    /*
     * Поиск листа с нужным именем
     * 
     */
    public int findByNameInt(Workbook w, String name) {
        int res=-1;
        int cnt = w.getNumberOfSheets();
        name=StringUtils.replaceAll(name, " ", "");
        for (int i = 0; i < cnt; i++) {
            Sheet tmp = w.getSheetAt(i);
            String key = StringUtils.replaceAll(tmp.getSheetName(), " ", "");
            boolean foundMatch = false;
            try {
                Pattern regex = Pattern.compile("("+name+")$", Pattern.MULTILINE);
                Matcher regexMatcher = regex.matcher(key);
                foundMatch = regexMatcher.find();
                if (foundMatch)
                {
                    res=i;
                    break;                    
                }
            } catch (PatternSyntaxException ex) {
                // Syntax error in the regular expression
            }

        }
        if (res==-1)
        {
            System.out.println("ERROR!!!"+name);
        }
        return res;
    }    
    public int findCol(Sheet sheet, String cellContent1, String cellContent2) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    if (cell.getRichStringCellValue().getString().trim().equals(cellContent1)) {
                        int cId = cell.getColumnIndex();
                        int rowN = row.getRowNum()+1;
                        Row rr = sheet.getRow(rowN);
                        Cell cc = rr.getCell(cId);
                        if (cc.getRichStringCellValue().getString().trim().equals(cellContent2)){
                            return cId;
                        }
                    }
                }
            }
        }
        return 0;
    }
}
