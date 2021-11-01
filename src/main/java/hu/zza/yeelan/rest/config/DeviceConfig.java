package hu.zza.yeelan.rest.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config.device")
public class DeviceConfig {

  private Map<String, String> addresses;

  public DeviceConfig(Map<String, String> devices) {
    this.addresses = devices;
  }

  public Map<String, String> getAddresses() {
    return Map.copyOf(addresses);
  }

  void setAddresses(Map<String, String> addresses) {
    this.addresses = addresses;
  }
}
