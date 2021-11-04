package hu.zza.yeelan.model;

import java.net.InetAddress;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class Device {

  public static final Device NULL = new Device("Non-existent device", DeviceAddress.NULL, Set.of());

  private final String name;
  private final DeviceAddress deviceAddress;
  private final Set<Property> mainProperties;

  private final Map<Property, String> state = new EnumMap<>(Property.class);

  public Map<Property, String> getState() {
    return Map.copyOf(state);
  }

  public String getState(Property property) {
    return state.getOrDefault(property, "");
  }

  public String updateState(Property property, String value) {
    return state.put(property, value);
  }

  public InetAddress getAddress() {
    return deviceAddress.getIpAddress();
  }

  public boolean isReachable() {
    return deviceAddress.isReachable();
  }

  public boolean isCapable(Property property) {
    return !"".equals(state.getOrDefault(property, ""));
  }
}
