package com.tickettodrive;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * A POJO implementation of a Citation service.
 * 
 * From Listing 8.1
 * 
 * @author wallsc
 */
public class CitationServiceImpl implements CitationService {
   private static final Logger LOGGER = Logger
                     .getLogger(CitationServiceImpl.class);

   public CitationServiceImpl() {}

   public Citation[] getCitationsForVehicle(String state, String plateNumber) {
      LOGGER.info("In getCitationsForVehicle()");

      Citation[] citations = new Citation[2];
      // TODO - implement real citation lookup. For now, return dummy values
      citations[0] = new Citation();
      citations[0].setDate(new Date(103, 2, 3)); // yeah, I know it's
      // deprecated.
      citations[0].setViolationCode("PKG123");
      citations[0].setDescription("Parked blocking a fire hydrant");
      citations[1] = new Citation();
      citations[1].setDate(new Date(105, 6, 9)); // yeah, I know it's
      // deprecated.
      citations[1].setViolationCode("SPD110");
      citations[1].setDescription("Driving 98MPH in a school zone");

      return citations;
   }
}
