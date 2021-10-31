package hu.zza.yeelan.rest.service;

import hu.zza.yeelan.rest.model.Device;
import hu.zza.yeelan.rest.config.DeviceConfig;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class DeviceCatalog {

  private static final Logger logger = Logger.getLogger(DeviceCatalog.class.getName());
  private DeviceConfig deviceConfig;
  private AddressResolver addressResolver;
  private Map<String, Device> catalog = new HashMap<>();

  public DeviceCatalog(DeviceConfig deviceConfig,
      AddressResolver addressResolver) {
    this.deviceConfig = deviceConfig;
    this.addressResolver = addressResolver;
    updateCatalog();
  }

  public Device getDevice(String name) {
    return catalog.getOrDefault(name, Device.NULL);
  }

  public void useDevice(String deviceName, String command, String... parameters) {
    Device device = getDevice(deviceName);

    if (device.isActive()) {
      TelnetConnection.send(device.getIpAddress(), command, parameters);
    }
  }

  public boolean isDeviceActive(String name) {
    return getDevice(name).isActive();
  }

  public List<String> getDeviceNameList() {
    return List.copyOf(catalog.keySet());
  }

  public Map<String, Device> getCatalog() {
    return Map.copyOf(catalog);
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
