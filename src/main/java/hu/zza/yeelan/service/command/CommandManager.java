package hu.zza.yeelan.service.command;

import hu.zza.yeelan.model.Device;
import hu.zza.yeelan.model.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommandManager {

  private final TelnetConnection telnetConnection;

  public Response send(Device device, String method, String... parameters) {
    return telnetConnection.send(device.getAddress(), method, parameters);
  }
}
