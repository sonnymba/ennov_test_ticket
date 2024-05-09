package com.ennov.ticketapi.enums;


import java.util.Arrays;

public enum Status {

  EN_COURS("EN_COURS"), TERMINE("TERMINE"), ANNULE("ANNULE");

  private  String text;

  Status(String text) {
    this.text = text;
  }
  public static Status fromText(String text) {
    return Arrays.stream(values())
            .filter(bl -> bl.text.equalsIgnoreCase(text))
            .findFirst()
            .orElse(null);
  }

}
