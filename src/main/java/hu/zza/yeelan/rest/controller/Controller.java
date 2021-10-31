package hu.zza.yeelan.rest.controller;

import hu.zza.yeelan.rest.model.Device;
import hu.zza.yeelan.rest.service.DeviceCatalog;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private final DeviceCatalog deviceCatalog;

  private static final Map<String, Integer> lightTemplates = Map.of(
      "min", 1,
      "moon", 2,
      "night", 6,
      "calm", 10,
      "light", 20,
      "mid", 50,
      "great", 75,
      "max", 100);

  Controller(DeviceCatalog deviceCatalog) {
    this.deviceCatalog = deviceCatalog;
  }

  @GetMapping("/catalog")
  public Map<String, Device> getCatalog() {
    return deviceCatalog.getCatalog();
  }

  @GetMapping("/devices")
  public List<String> getDevices() {
    return deviceCatalog.getDeviceNameList();
  }

  @GetMapping("/lights")
  public Map<String, Integer> getLightTemplates() {
    return lightTemplates;
  }

  @GetMapping("/{name}/toggle")
  public void toggle(@PathVariable String name) {
    deviceCatalog.useDevice(name, "toggle");
  }

  @GetMapping("/{name}/{template}")
  public void setBrightnessTemplate(@PathVariable String name, @PathVariable String template) {
    int level = lightTemplates.getOrDefault(template, 20);
    deviceCatalog.useDevice(name, "set_bright", String.valueOf(level), "smooth", "3000");
  }

  @GetMapping("/{name}/less")
  public void decreaseBrightness(@PathVariable String name) {
    adjustBrightness(name, "-10");
  }

  @GetMapping("/{name}/less/{brightness}")
  public void decreaseBrightness(@PathVariable String name, @PathVariable int brightness) {
    adjustBrightness(name, String.valueOf(-brightness));
  }

  @GetMapping("/{name}/more")
  public void increaseBrightness(@PathVariable String name) {
    adjustBrightness(name, "10");
  }

  @GetMapping("/{name}/more/{brightness}")
  public void increaseBrightness(@PathVariable String name, @PathVariable int brightness) {
    adjustBrightness(name, String.valueOf(brightness));
  }

  private void adjustBrightness(String name, String brightness) {
    deviceCatalog.useDevice(name, "adjust_bright", brightness, "3000");
  }
}
