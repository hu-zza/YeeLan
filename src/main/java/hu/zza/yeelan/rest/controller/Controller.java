package hu.zza.yeelan.rest.controller;

import hu.zza.yeelan.rest.model.Device;
import hu.zza.yeelan.rest.model.LightMode;
import hu.zza.yeelan.rest.model.Response;
import hu.zza.yeelan.rest.service.DeviceCatalog;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  private final DeviceCatalog deviceCatalog;

  //power,active_mode,color_mode,bright,nl_br,ct

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
  public Map<LightMode, Map<String, Integer>> getLightTemplates() {
    return deviceCatalog.getLightTemplates();
  }

  @GetMapping("/{name}/toggle")
  public Response toggle(@PathVariable String name) {
    return deviceCatalog.useDevice(name, "toggle");
  }

  @GetMapping("/{name}/{template}")
  public Response setBrightnessTemplate(@PathVariable String name, @PathVariable String template) {
    return deviceCatalog.useLightTemplate(name, template, "smooth", "3000");
  }

  @GetMapping("/{name}/less")
  public Response decreaseBrightness(@PathVariable String name) {
    return adjustBrightness(name, "-10");
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

  private Response adjustBrightness(String name, String brightness) {
    return deviceCatalog.useDevice(name, "adjust_bright", brightness, "3000");
  }

  @GetMapping("/test/{name}/{command}/{params}")
  public Response test(@PathVariable String name, @PathVariable String command, @PathVariable String params) {
    return deviceCatalog.useDevice(name, command, params.split("\\W"));
  }
}
