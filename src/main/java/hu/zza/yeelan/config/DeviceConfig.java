package hu.zza.yeelan.config;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "config.device")
public class DeviceConfig {

  private Map<String, String> addresses;
}
