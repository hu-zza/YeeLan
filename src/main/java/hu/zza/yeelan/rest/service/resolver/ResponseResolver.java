package hu.zza.yeelan.rest.service.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.zza.yeelan.rest.model.Response;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class ResponseResolver {

  private final ObjectMapper objectMapper;

  public ResponseResolver(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Response parseString(String response) {
    log.finer(() -> String.format("RAW response: %s", response));
    try {
      return objectMapper.readValue(response, Response.class);
    } catch (JsonProcessingException ignored) {
      return Response.NULL;
    }
  }
}
