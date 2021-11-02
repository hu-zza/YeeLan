package hu.zza.yeelan.service.resolver;

import hu.zza.yeelan.config.LightConfig;
import hu.zza.yeelan.model.LightMode;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LightTemplates {

  private final LightConfig lightConfig;

  private final Map<LightMode, Map<String, Integer>> templates = new EnumMap<>(LightMode.class);

  public LightTemplates(LightConfig lightConfig) {
    this.lightConfig = lightConfig;
    updateCatalog();
  }

  private void updateCatalog() {
    templates.clear();
    templates.put(LightMode.DAY, lightConfig.getDay());
    templates.put(LightMode.NIGHT, lightConfig.getNight());
  }

  public int getLightLevel(LightMode lightMode, String title) {
    return templates.getOrDefault(lightMode, Map.of())
        .getOrDefault(title, 1);
  }

  public Map<LightMode, Map<String, Integer>> getTemplates() {
    return Map.copyOf(templates);
  }
}
