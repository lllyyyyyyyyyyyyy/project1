package com.vss.lynt.file;
import java.io.IOException;
import java.sql.Time;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.vss.lynt.model.Schedule;
import com.vss.lynt.model.UserRole;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ScheduleExcelExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Schedule> schedules;

    public ScheduleExcelExporter(List<Schedule> schedules) {
        this.schedules = schedules;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Schedule");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontName("Courier New");
        font.setColor(HSSFColor.HSSFColorPredefined.VIOLET.getIndex());
        font.setFontHeight(20);
        style.setFont(font);

        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.BIG_SPOTS);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
//        font.setFontName("Courier New");
//        font.setBold(true);
//        font.setUnderline(Font.U_SINGLE);
//        font.setColor(HSSFColorPredefined.DARK_RED.getIndex());

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        createCell(row, 0, "Time", style);
        createCell(row, 1, "MON", style);
        createCell(row, 2, "TUE", style);
        createCell(row, 3, "WED", style);
        createCell(row, 4, "THU", style);
        createCell(row, 5, "FRI", style);
        createCell(row, 6, "SAT", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Time) {
            cell.setCellValue(value.toString());
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(16);
        font.setFontName("Courier New");
        font.setColor(HSSFColor.HSSFColorPredefined.BROWN.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        for (Schedule schedule : schedules) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, schedule.getTime(), style);
            createCell(row, columnCount++, schedule.getMon(), style);
            createCell(row, columnCount++, schedule.getTue(), style);
            createCell(row, columnCount++, schedule.getWed(), style);
            createCell(row, columnCount++, schedule.getThu(), style);
            createCell(row, columnCount++, schedule.getFri(), style);
            createCell(row, columnCount++, schedule.getSat(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
