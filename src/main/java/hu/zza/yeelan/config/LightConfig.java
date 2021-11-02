package hu.zza.yeelan.config;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "config.light")
public class LightConfig {

  private Map<String, Integer> day;
  private Map<String, Integer> night;
}
