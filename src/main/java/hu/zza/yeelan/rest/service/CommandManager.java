package hu.zza.yeelan.rest.service;

import hu.zza.yeelan.rest.model.Device;
import hu.zza.yeelan.rest.model.Response;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@AllArgsConstructor
@Service
public class CommandManager {
  private final TelnetConnection telnetConnection;

  public Response send(Device device, String method, String... parameters) {
    return telnetConnection.send(device.getAddress(), method, parameters);
  }
}
//power,active_mode,color_mode,bright,nl_br,ct
