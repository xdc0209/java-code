package com.tickettodrive;

public interface CitationService {
  public Citation[] getCitationsForVehicle(
      String state, String plateNumber);
}
