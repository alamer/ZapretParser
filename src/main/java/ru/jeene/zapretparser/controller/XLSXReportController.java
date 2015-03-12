/*
 $Author$
 $Date$
 $Revision$
 $Source$
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jeene.zapretparser.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.jeene.zapretparser.models.FullReport;
import ru.jeene.zapretparser.models.Model_FullReport;
import ru.jeene.zapretparser.models.Model_NumberReport;
import ru.jeene.zapretparser.models.ResponseResult;
import ru.jeene.zapretparser.utils.DateUtils;
import ru.jeene.zapretparser.utils.FormatUtils;
import ru.jeene.zapretparser.utils.StringUtils;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class XLSXReportController {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(XLSXReportController.class);

    private static String shab_name = "full_report.xlsx";
    private static String report_name = "full_report_!dt!.xlsx";
    private final String MAIN_ZAG_TEMPL = "Отчет по блокировке URL ДСИ согласно Росреестру  CSV на ";
    private final int t1_start = 3;
    private final int t0_start = 3;

    public void WriteReport(FullReport rep, String timestamp_csv) {
        try (FileInputStream inp = new FileInputStream(shab_name)) {
            XSSFWorkbook wb = new XSSFWorkbook(inp); // Declare XSSF WorkBook
            XSSFSheet sheet = wb.getSheet("Детальный отчет");
            XSSFCellStyle cs1 = wb.createCellStyle();
            //cs1.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            cs1.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            cs1.setBorderTop(XSSFCellStyle.BORDER_THIN);
            cs1.setBorderRight(XSSFCellStyle.BORDER_THIN);
            cs1.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            cs1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            cs1.setWrapText(true);
            cs1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
            XSSFFont f = wb.createFont();
            f.setBold(false);
            f.setFontName("Times New Roman");
            f.setFontHeightInPoints((short) 14);
            cs1.setFont(f);
            int cnt = 0;
            XSSFRow row;
            XSSFCell cell;
            for (Model_FullReport m : rep.getList()) {
                int cnt_cell = 0;

                row = sheet.getRow(t1_start - 1 + cnt);
                if (row == null) {
                    row = sheet.createRow(t1_start - 1 + cnt);
                }
                //Записываем URL
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(m.getElement().getUrl());
                cell.setCellStyle(cs1);
                cnt_cell++;

                //Организацию
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(m.getElement().getOrg());
                cell.setCellStyle(cs1);
                cnt_cell++;

                //Номер документа
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(m.getElement().getDoc());
                cell.setCellStyle(cs1);
                cnt_cell++;
                //Дата
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(m.getElement().getDate());
                cell.setCellStyle(cs1);
                cnt_cell++;

                //Результат
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(m.getResult().name());
                cell.setCellStyle(cs1);
                cnt_cell++;

                //Результат (описание)
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(m.getResult().getDesc());
                cell.setCellStyle(cs1);
                cnt_cell++;

                //Результат (код)
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellValue(m.getResult().getCode());
                cell.setCellStyle(cs1);
                cnt_cell++;
                cnt++;
            }
            //Пишем заголовок
            row = sheet.getRow(0);
            cell = row.getCell(0);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(MAIN_ZAG_TEMPL + timestamp_csv);

            //Считаем отчет по ошибкам
            HashMap<ResponseResult, Model_NumberReport> map = rep.reportCountBytype();

            //Выбираем первый лист
            sheet = wb.getSheet("Итог");

            //Пишем итоговый отчет
            cnt = 0;
            for (Map.Entry<ResponseResult, Model_NumberReport> entry : map.entrySet()) {
                ResponseResult key = entry.getKey();
                Model_NumberReport value = entry.getValue();

                int cnt_cell = 0;
                row = sheet.getRow(t0_start - 1 + cnt);
                if (row == null) {
                    row = sheet.createRow(t0_start - 1 + cnt);
                }
                /*//Результат
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(key.name());
                cell.setCellStyle(cs1);
                cnt_cell++;*/

                //Результат (описание)
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(key.getDesc());
                cell.setCellStyle(cs1);
                cnt_cell++;

                /*//Результат (код)
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellValue(key.getCode());
                cell.setCellStyle(cs1);
                cnt_cell++;*/

                //Результат (количество)
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                cell.setCellValue(value.getNumber());
                cell.setCellStyle(cs1);
                cnt_cell++;
                //Результат (процент)
                cell = row.getCell(cnt_cell);
                if (cell == null) {
                    cell = row.createCell(cnt_cell);
                }
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(FormatUtils.FormatDoubleD(value.getPercent()));
                cell.setCellStyle(cs1);
                cnt_cell++;
                cnt++;
            }

            //Пишем заголовок
            row = sheet.getRow(0);
            cell = row.getCell(0);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(MAIN_ZAG_TEMPL + timestamp_csv);

            //Сохраняем файл
            String tmp_out = StringUtils.replaceAll(report_name, "!dt!", DateUtils.DateToString(new Date(System.currentTimeMillis()), "ddMMyyyy_Hms"));
            try (FileOutputStream out = new FileOutputStream(tmp_out)) {
                wb.write(out);
                logger.info("Report file "+tmp_out+" created");
            }

        } catch (Exception ex) {
            logger.error(ex);
        }
    }

}
