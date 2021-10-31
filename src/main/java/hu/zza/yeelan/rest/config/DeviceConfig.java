package hu.zza.yeelan.rest.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config")
public class DeviceConfig {

  private Map<String, String> devices;

  public DeviceConfig(Map<String, String> devices) {
    this.devices = devices;
  }

  public Map<String, String> getDevices() {
    return Map.copyOf(devices);
  }

  void setDevices(Map<String, String> devices) {
    this.devices = devices;
  }
}
