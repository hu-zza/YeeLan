package hu.zza.yeelan.rest.model;

import java.util.List;

public class Response {
  public static final Response NULL = new Response(-1, List.of());
  private int id;
  private List<String> result;

  public Response(int id, List<String> result) {
    this.id = id;
    this.result = result;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<String> getResult() {
    return result;
  }

  public void setResult(List<String> result) {
    this.result = result;
  }
}
