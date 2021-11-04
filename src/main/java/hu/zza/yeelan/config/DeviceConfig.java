package hu.zza.yeelan.config;

import hu.zza.yeelan.model.Property;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "config.device")
public class DeviceConfig {

  private Map<String, DeviceBlueprint> list;
  private Map<String, List<Property>> propertyProfile;
}
