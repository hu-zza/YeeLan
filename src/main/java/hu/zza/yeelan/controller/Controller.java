package hu.zza.yeelan.controller;

import hu.zza.yeelan.model.Device;
import hu.zza.yeelan.model.LightMode;
import hu.zza.yeelan.model.Response;
import hu.zza.yeelan.service.DeviceManager;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class Controller {

  private final DeviceManager deviceManager;

  @GetMapping("/lights")
  public Map<LightMode, Map<String, Integer>> getLightTemplates() {
    return deviceManager.getLightTemplates();
  }

  @GetMapping("/catalog")
  public Map<String, Device> getCatalog() {
    return deviceManager.getCatalog();
  }

  @GetMapping("/devices")
  public List<String> getDevices() {
    return deviceManager.getDeviceNameList();
  }

  @GetMapping("/device/{name}")
  public Device getDevice(@PathVariable String name) {
    return deviceManager.getDevice(name);
  }

  @GetMapping("/{name}/toggle")
  public Response togglePower(@PathVariable String name) {
    return deviceManager.useDevice(name, "toggle");
  }

  @GetMapping("/{name}/switch")
  public Response toggleNightMode(@PathVariable String name) {
    return deviceManager.toggleNightMode(name);
  }

  @GetMapping("/{name}/{template}")
  public Response setBrightnessTemplate(@PathVariable String name, @PathVariable String template) {
    return deviceManager.useLightTemplate(name, template);
  }

  @GetMapping("/{name}/less")
  public Response decreaseBrightness(@PathVariable String name) {
    return adjustBrightness(name, "-10");
  }

  private Response adjustBrightness(String name, String brightness) {
    return deviceManager.useDevice(name, "adjust_bright", brightness, "3000");
  }

  @GetMapping("/{name}/less/{brightness}")
  public Response decreaseBrightness(@PathVariable String name, @PathVariable int brightness) {
    return adjustBrightness(name, String.valueOf(-brightness));
  }

  @GetMapping("/{name}/more")
  public Response increaseBrightness(@PathVariable String name) {
    return adjustBrightness(name, "10");
  }

  @GetMapping("/{name}/more/{brightness}")
  public Response increaseBrightness(@PathVariable String name, @PathVariable int brightness) {
    return adjustBrightness(name, String.valueOf(brightness));
  }

  @GetMapping("/test/{name}/{command}/{params}")
  public Response test(@PathVariable String name, @PathVariable String command,
      @PathVariable String params) {
    return deviceManager.useDevice(name, command, params.split("\\W"));
  }
}
