package hu.zza.yeelan.rest.model;

import lombok.Data;

@Data
public class ErrorDetails {

  private int code;
  private String message;
}
