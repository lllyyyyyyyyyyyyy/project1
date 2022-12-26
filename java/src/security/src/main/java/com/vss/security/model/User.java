package com.vss.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.*;
import java.util.Iterator;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

//    private static String SHEET = "UserImport";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="status")
    public UserEnum userEnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserEnum getUserEnum() {
        return userEnum;
    }

    public void setUserEnum(String userEnum) {
//        Us = userEnum;
        this.userEnum = UserEnum.valueOf(userEnum);
    }
//
//    Workbook workbook = new XSSFWorkbook(inputStream);
//    Sheet sheet = workbook.getSheet(SHEET);
//    Iterator<Row> rows = sheet.iterator();
////
//while (rows.hasNext()) {
//        Row currentRow = rows.next();
//
//        Iterator<Cell> cellsInRow = currentRow.iterator();
//
//        while (cellsInRow.hasNext()) {
//            Cell currentCell = cellsInRow.next();
//
//            // each cell case
//            id = (long) currentCell.getNumericCellValue();
//            title = currentCell.getStringCellValue();
//            published = currentCell.getBooleanCellValue();
//        }
//
//        workbook.close();

//        Sheet sheet = workbook.getSheet(SHEET);

}
