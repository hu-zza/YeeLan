package hu.zza.yeelan.rest.model;

import java.net.InetAddress;

public class Device {

  public static final Device NULL = new Device("Non-existent device",
      InetAddress.getLoopbackAddress(), false);
  
  private String name;
  private InetAddress ipAddress;
  private boolean active;

  public Device(String name, InetAddress ipAddress, boolean active) {
    this.name = name;
    this.ipAddress = ipAddress;
    this.active = active;
  }

  public static Device getUnreachableWithName(String name) {
    return new Device(name, InetAddress.getLoopbackAddress(), false);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InetAddress getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(InetAddress ipAddress) {
    this.ipAddress = ipAddress;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
