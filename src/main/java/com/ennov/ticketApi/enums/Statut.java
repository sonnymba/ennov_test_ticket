package com.ennov.ticketApi.enums;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum Statut {

  EN_COURS("En cours"),
  TERMINE("Terminé"),
  ANNULE("Annulé");

  private final String value;

  Statut(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Optional<Statut> toEnum(String label) {
    if (label == null) {
      return Optional.empty();
    }

    for (Statut mine : Statut.values()) {
      if (label.equals(mine.getValue())) {
        return Optional.of(mine);
      }
    }

    throw new IllegalArgumentException("no supported");
  }

  public static List<Statut> orderedValues = new ArrayList<>();

  static {
    orderedValues.addAll(Arrays.asList(Statut.values()));
  }
}
