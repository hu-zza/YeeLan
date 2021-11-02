package hu.zza.yeelan.model;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Response {

  public static final Response NULL = getSimpleError(-1, 0, "NULL");

  private int id;
  private List<String> result;
  private ErrorDetails error;
  private Map<String, String> params;

  public static Response getSimpleError(int id, int code, String message) {
    Response result = new Response();
    result.setError(new ErrorDetails());
    result.id = id;
    result.error.setCode(code);
    result.error.setMessage(message);
    return result;
  }
}
