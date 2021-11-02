package hu.zza.yeelan.model;

import java.io.IOException;
import java.net.InetAddress;
import lombok.Data;

@Data
public class DeviceAddress {

  public static final DeviceAddress NULL = new DeviceAddress(InetAddress.getLoopbackAddress());

  private final InetAddress ipAddress;
  private boolean reachable;

  public DeviceAddress(InetAddress ipAddress) {
    this.ipAddress = ipAddress;
    updateReachable();
  }

  public void updateReachable() {
    if (ipAddress.isLoopbackAddress()) {
      reachable = false;
    } else {
      pingAndSetReachable();
    }
  }

  private void pingAndSetReachable() {
    try {
      reachable = ipAddress.isReachable(1000);
    } catch (IOException ignored) {
      reachable = false;
    }
  }
}
