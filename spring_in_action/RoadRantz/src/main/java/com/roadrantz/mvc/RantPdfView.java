package com.roadrantz.mvc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import com.roadrantz.domain.Rant;

public class RantPdfView extends AbstractPdfView {
  protected void buildPdfDocument(Map model, Document document, 
      PdfWriter pdfWriter, HttpServletRequest request, 
      HttpServletResponse response) throws Exception {
    
    List rants = (List) model.get("rants");
    Table rantTable = new Table(4);
    rantTable.setWidth(90);
    rantTable.setBorderWidth(1);
    
    rantTable.addCell("State");
    rantTable.addCell("Plate");
    rantTable.addCell("Date Posted");
    rantTable.addCell("Text");

    for (Iterator iter = rants.iterator(); iter.hasNext();) {
      Rant rant = (Rant) iter.next();
      
      rantTable.addCell(rant.getVehicle().getState());
      rantTable.addCell(rant.getVehicle().getPlateNumber());
      rantTable.addCell(rant.getPostedDate().toString());
      rantTable.addCell(rant.getRantText());
    }
    
    document.add(rantTable);
  }
}
