package hu.zza.yeelan.rest.model;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Response {
  public static final Response NULL = new Response();
  static {
    var details = new ErrorDetails();
    details.setMessage("JSON parsing error");
    NULL.setError(details);
  }

  private int id;
  private List<String> result;
  private ErrorDetails error;
  private Map<String, String> params;
}
