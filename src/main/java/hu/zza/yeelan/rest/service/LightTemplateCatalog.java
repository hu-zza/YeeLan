package hu.zza.yeelan.rest.service;

import hu.zza.yeelan.rest.config.LightConfig;
import hu.zza.yeelan.rest.model.LightMode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LightTemplateCatalog {

  private LightConfig lightConfig;

  private Map<LightMode, Map<String, Integer>> lightTemplates = new HashMap<>();

  public LightTemplateCatalog(LightConfig lightConfig) {
    this.lightConfig = lightConfig;
    updateCatalog();
  }

  private void updateCatalog() {
    lightTemplates.clear();
    lightTemplates.put(LightMode.DAY, lightConfig.getDay());
    lightTemplates.put(LightMode.NIGHT, lightConfig.getNight());
  }

  public int getLightLevel(LightMode lightMode, String title) {
    return lightTemplates.getOrDefault(lightMode, Map.of())
        .getOrDefault(title, 1);
  }

  public Map<LightMode, Map<String, Integer>> getTemplates() {
    return Map.copyOf(lightTemplates);
  }
}
