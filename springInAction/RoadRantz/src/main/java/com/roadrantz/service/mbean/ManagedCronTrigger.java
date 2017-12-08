package com.roadrantz.service.mbean;

import java.util.Date;

public interface ManagedCronTrigger {
  public void setCronExpression(String ce);
  public String getCronExpression();
  public void setNextFireTime(Date date);
  public Date getNextFireTime();
}
