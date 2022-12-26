package com.vss.lynt.file;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.vss.lynt.model.ClassStudent;
import com.vss.lynt.service.ClassStudentService;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class StudentOfClassExcelExport {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ClassStudent> classStudents;

    @Autowired
    ClassStudentService classStudentService;
    public StudentOfClassExcelExport(List<ClassStudent> classStudents) {
        this.classStudents = classStudents;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {

        sheet = workbook.createSheet("Student");
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

        createCell(row, 0, "Student Id", style);
        createCell(row, 1, "Username", style);
        createCell(row, 2, "Date Of Birth", style);
        createCell(row, 3, "Address", style);
        createCell(row, 4, "Email", style);
        createCell(row, 5, "Fullname", style);
        createCell(row, 6, "Password", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
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

        for (ClassStudent classStudent : classStudents) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, classStudent.getStudent().getStudentId(), style);
            createCell(row, columnCount++, classStudent.getStudent().getUserName(), style);
            createCell(row, columnCount++, classStudent.getStudent().getDateOfBirth(), style);
            createCell(row, columnCount++, classStudent.getStudent().getAddress(), style);
            createCell(row, columnCount++, classStudent.getStudent().getEmail(), style);
            createCell(row, columnCount++, classStudent.getStudent().getFullName(), style);
            createCell(row, columnCount++, classStudent.getStudent().getPassword(), style);

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
