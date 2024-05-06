package com.ennov.ticketApi.enums;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Status {

  EN_COURS("En cours"),
  TERMINE("Terminé"),
  ANNULE("Annulé");

  private final String value;

  Status(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Optional<Status> toEnum(String label) {
    if (label == null) {
      return Optional.empty();
    }

    for (Status mine : Status.values()) {
      if (label.equals(mine.getValue())) {
        return Optional.of(mine);
      }
    }

    throw new IllegalArgumentException("no supported");
  }

  public static List<Status> orderedValues = new ArrayList<>();

  static {
    orderedValues.addAll(Arrays.asList(Status.values()));
  }
}
