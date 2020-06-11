package com.thoughtworks.capability.gtb.vo;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author itutry
 * @create 2020-05-21_16:26
 */
public enum EventType {

  UPLOAD("U"), DOWNLOAD("D");

  private String code;

  EventType(String code) {
    this.code = code;
  }

  @JsonValue
  public String getCode(){
    return code;
  }

}
