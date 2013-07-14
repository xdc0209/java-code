package com.roadrantz.mvc;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.roadrantz.domain.Rant;
import com.roadrantz.domain.Vehicle;

public class RantExcelView extends AbstractExcelView {
  protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {
    
    Collection rants = (Collection) model.get("rants");
    Vehicle vehicle = (Vehicle) model.get("vehicle");
    
    HSSFSheet sheet = createSheet(workbook, vehicle.getPlateNumber());
    
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setDataFormat(
        HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
    
    int rowNum = 1;
    for (Iterator iter = rants.iterator(); iter.hasNext();) {
      Rant rant = (Rant) iter.next();
      rowNum = addRantRow(sheet, cellStyle, rowNum, rant);
    }
  }

  private int addRantRow(HSSFSheet sheet, HSSFCellStyle cellStyle, 
      int rowNum, Rant rant) {
    HSSFRow row = sheet.createRow(rowNum++);
    row.createCell((short) 0).setCellValue(rant.getPostedDate());
    row.createCell((short) 1).setCellValue(rant.getRantText());
    row.getCell((short) 1).setCellStyle(cellStyle);
    return rowNum;
  }

  private HSSFSheet createSheet(HSSFWorkbook workbook, 
      String plateNumber) {
    HSSFSheet sheet = workbook.createSheet("Rants for " + plateNumber);
    
    HSSFRow header = sheet.createRow(0);
    header.createCell((short) 0).setCellValue("Date");
    header.createCell((short) 1).setCellValue("Text");
    return sheet;
  }
}
