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

  private DeviceCatalog deviceCatalog;

  Controller(DeviceCatalog deviceCatalog) {
    this.deviceCatalog = deviceCatalog;
  }

  @GetMapping("/{name}/toggle")
  public void toggle(@PathVariable String name) {
    deviceCatalog.useDevice(name, "toggle");
  }

  @GetMapping("/devices")
  public List<String> getDevices() {
    return deviceCatalog.getDeviceNameList();
  }

  @GetMapping("/catalog")
  public Map<String, Device> getCatalog() {
    return deviceCatalog.getCatalog();
  }
}
