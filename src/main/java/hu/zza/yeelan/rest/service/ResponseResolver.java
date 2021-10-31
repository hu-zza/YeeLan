package hu.zza.yeelan.rest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.zza.yeelan.rest.model.Response;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResponseResolver {
  private final ObjectMapper objectMapper;

  public ResponseResolver(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Response parseString(String response) {
    try {
      return objectMapper.readValue(response, Response.class);
    } catch (JsonProcessingException e) {
      return new Response(0, List.of("JSON error"));
    }
  }
}
