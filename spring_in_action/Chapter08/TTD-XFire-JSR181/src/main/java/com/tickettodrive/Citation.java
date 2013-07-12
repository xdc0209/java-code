package com.tickettodrive;

import java.io.Serializable;
import java.util.Date;

public class Citation implements Serializable {
   private Date date;
   private String violationCode;
   private String description;

   public Citation() {}

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getViolationCode() {
      return violationCode;
   }

   public void setViolationCode(String violationCode) {
      this.violationCode = violationCode;
   }
}
