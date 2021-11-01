package hu.zza.yeelan.rest.service;

import hu.zza.yeelan.rest.model.Device;
import hu.zza.yeelan.rest.model.LightMode;
import hu.zza.yeelan.rest.model.Property;
import hu.zza.yeelan.rest.model.Response;
import hu.zza.yeelan.rest.service.factory.DeviceFactory;
import hu.zza.yeelan.rest.service.resolver.LightTemplates;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@AllArgsConstructor
@Service
public class DeviceManager {

  private static final List<Property> propertyList = Arrays.asList(Property.values());
  private static final String[] propertyArray =
      propertyList.stream()
          .map(Property::toString)
          .toArray(String[]::new);

  private final DeviceFactory deviceFactory;
  private final LightTemplates lightTemplates;
  private final CommandManager commandManager;
  private final Map<String, Device> catalog = new HashMap<>();

  @PostConstruct
  public void init() {
    updateCatalog();
    updateDevices();
  }

  private void updateCatalog() {
    catalog.clear();
    deviceFactory.getPredefinedDevices()
        .forEach(d -> catalog.put(d.getName(), d));
  }

  private void updateDevices() {
    catalog.values()
        .forEach(this::updateDevice);
  }

  public List<String> getDeviceNameList() {
    return List.copyOf(catalog.keySet());
  }

  public Map<String, Device> getCatalog() {
    return Map.copyOf(catalog);
  }

  public Map<LightMode, Map<String, Integer>> getLightTemplates() {
    return lightTemplates.getTemplates();
  }

  public Device getDevice(String name) {
    return catalog.getOrDefault(name, Device.NULL);
  }

  public void updateDevice(Device device) {
    List<String> result;

    for (Property p : propertyList) {
      result = commandManager.send(device, "get_prop", p.toString())
          .getResult();
      if (result != null && !result.isEmpty()) {
        device.updateState(p, result.get(0));
      }
    }
  }

  public Response useDevice(String deviceName, String method, String... parameters) {
    return commandManager.send(catalog.get(deviceName), method, parameters);
  }

  public Response useLightTemplate(String deviceName, String templateName) {
    return useLightTemplate(getDevice(deviceName), templateName);
  }

  public Response useLightTemplate(Device device, String templateName) {
    LightMode mode;
    if (device.isCapable(Property.NL_BR)) {
      mode =
          "0".equals(device.getState(Property.ACTIVE_MODE)) ? LightMode.DAY : LightMode.NIGHT;
    } else {
      mode = LightMode.DAY;
    }

    String level = String.valueOf(lightTemplates.getLightLevel(mode, templateName));

    return commandManager.send(device, "set_bright", level, "smooth", "3000");
  }
}
