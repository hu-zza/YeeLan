package hu.zza.yeelan.rest.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config.light")
public class LightConfig {

  private Map<String, Integer> day;
  private Map<String, Integer> night;

  public LightConfig(Map<String, Integer> day,
      Map<String, Integer> night) {
    this.day = day;
    this.night = night;
  }

  public Map<String, Integer> getDay() {
    return day;
  }

  public void setDay(Map<String, Integer> day) {
    this.day = day;
  }

  public Map<String, Integer> getNight() {
    return night;
  }

  public void setNight(Map<String, Integer> night) {
    this.night = night;
  }
}
