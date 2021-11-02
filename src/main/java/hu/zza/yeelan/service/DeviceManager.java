package hu.zza.yeelan.service;

import hu.zza.yeelan.model.Device;
import hu.zza.yeelan.model.LightMode;
import hu.zza.yeelan.model.Property;
import hu.zza.yeelan.model.Response;
import hu.zza.yeelan.service.command.CommandManager;
import hu.zza.yeelan.service.factory.DeviceFactory;
import hu.zza.yeelan.service.resolver.LightTemplates;
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

  public void updateDevice(Device device) {
    List<String> result;

    for (Property p : propertyList) {
      result = useDevice(device, "get_prop", p.toString())
          .getResult();
      if (result != null && !result.isEmpty()) {
        device.updateState(p, result.get(0));
      }
    }
  }

  public Response useDevice(Device device, String method, String... parameters) {
    return commandManager.send(device, method, parameters);
  }

  public Map<LightMode, Map<String, Integer>> getLightTemplates() {
    return lightTemplates.getTemplates();
  }

  public Map<String, Device> getCatalog() {
    return Map.copyOf(catalog);
  }

  public List<String> getDeviceNameList() {
    return List.copyOf(catalog.keySet());
  }

  public Response useDevice(String deviceName, String method, String... parameters) {
    return useDevice(getDevice(deviceName), method, parameters);
  }

  public Device getDevice(String deviceName) {
    return catalog.getOrDefault(deviceName, Device.NULL);
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

    return useDevice(device, "set_bright", level, "smooth", "3000");
  }

  public Response toggleNightMode(String deviceName) {
    return toggleNightMode(getDevice(deviceName));
  }

  public Response toggleNightMode(Device device) {
    if (device.isCapable(Property.NL_BR)) {
      if ("0".equals(device.getState(Property.ACTIVE_MODE))) {
        return useDevice(device, "set_power", "on", "smooth", "3000", "5");
      } else {
        return useDevice(device, "set_power", "on", "smooth", "3000", "1");
      }
    }
    return Response.getSimpleError(1, 2, "This device has no Night Light mode");
  }
}
