package hu.zza.yeelan.rest.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
  private final byte[] addressArray = {(byte)192, (byte)168, 1, 107};
  private final InetAddress device;

  Controller() throws UnknownHostException {
    device = InetAddress.getByAddress(addressArray);
  }

  @GetMapping("/toggle")
  public void toggle() {
    TelnetClient.send(device, "toggle", "");
  }
}
