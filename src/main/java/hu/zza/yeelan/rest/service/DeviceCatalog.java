package hu.zza.yeelan.rest.service;

import hu.zza.yeelan.rest.config.DeviceConfig;
import hu.zza.yeelan.rest.model.Device;
import hu.zza.yeelan.rest.model.LightMode;
import hu.zza.yeelan.rest.model.Response;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class DeviceCatalog {

  private static final Logger logger = Logger.getLogger(DeviceCatalog.class.getName());
  private final DeviceConfig deviceConfig;
  private final AddressResolver addressResolver;
  private final LightTemplateCatalog lightTemplateCatalog;
  private final ResponseResolver responseResolver;
  private Map<String, Device> catalog = new HashMap<>();

  public DeviceCatalog(DeviceConfig deviceConfig,
      AddressResolver addressResolver,
      ResponseResolver responseResolver,
      LightTemplateCatalog lightTemplateCatalog) {
    this.deviceConfig = deviceConfig;
    this.addressResolver = addressResolver;
    this.lightTemplateCatalog = lightTemplateCatalog;
    this.responseResolver = responseResolver;
    updateCatalog();
  }

  public Response useLightTemplate(String deviceName, String templateName, String... parameters) {
    Device device = getDevice(deviceName);

    return useLightTemplate(device, templateName, parameters);
  }

  public Response useLightTemplate(Device device, String templateName, String... parameters) {
    if (device.isActive()) {
      var properties = getDeviceProperty(device, "active_mode", "nl_br").getResult();
      String[] parameterArray = new String[parameters.length + 1];

      LightMode mode =
          "0".equals(properties.get(0)) || (properties.get(1)).isEmpty()
              ? LightMode.DAY
              : LightMode.NIGHT;

      parameterArray[0] = String.valueOf(lightTemplateCatalog.getLightLevel(mode, templateName));
      System.arraycopy(parameters, 0, parameterArray, 1, parameters.length);

    return useDevice(device, "set_bright", parameterArray);
  }
    return Response.NULL;
}

  public Response useDevice(String deviceName, String command, String... parameters) {
    Device device = getDevice(deviceName);

    return useDevice(device, command, parameters);
  }

  public Response useDevice(Device device, String command, String... parameters) {
    if (device.isActive()) {
      return responseResolver.parseString(
          TelnetConnection.send(device.getIpAddress(), command, parameters));
    }
    return Response.NULL;
  }

  public Response getDeviceProperty(String deviceName, String... properties) {
    Device device = getDevice(deviceName);

    return getDeviceProperty(device, properties);
  }

  public Response getDeviceProperty(Device device, String... properties) {

    if (device.isActive()) {
      return responseResolver.parseString(
          TelnetConnection.send(device.getIpAddress(), "get_prop", properties));
    }
    return Response.NULL;
  }

  public Device getDevice(String name) {
    return catalog.getOrDefault(name, Device.NULL);
  }

  public List<String> getDeviceNameList() {
    return List.copyOf(catalog.keySet());
  }

  public Map<String, Device> getCatalog() {
    return Map.copyOf(catalog);
  }

  public Map<LightMode, Map<String, Integer>> getLightTemplates() {
    return lightTemplateCatalog.getTemplates();
  }

  private void updateCatalog() {
    catalog.clear();
    deviceConfig.getDevices()
        .entrySet()
        .stream()
        .map(this::createDevice)
        .forEach(this::addDeviceToCatalog);
  }

  private Device createDevice(Map.Entry<String, String> deviceConfig) {
    try {
      return new Device(deviceConfig.getKey(), addressResolver.parseString(deviceConfig.getValue()),
          true);
    } catch (UnknownHostException e) {
      logger.warning(
          String.format("Can not initialize '%s' with IP address: '%s'", deviceConfig.getKey(),
              deviceConfig.getValue()));

      return Device.getUnreachableWithName(deviceConfig.getKey());
    }
  }

  private void addDeviceToCatalog(Device device) {
    catalog.put(device.getName(), device);
  }
}
