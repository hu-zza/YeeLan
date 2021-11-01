package hu.zza.yeelan.rest.service.factory;

import hu.zza.yeelan.rest.config.DeviceConfig;
import hu.zza.yeelan.rest.model.Device;
import hu.zza.yeelan.rest.service.resolver.AddressResolver;
import java.util.List;
import java.util.Map.Entry;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@AllArgsConstructor
@Service
public class DeviceFactory {

  private final DeviceConfig deviceConfig;
  private final AddressResolver addressResolver;

  public List<Device> getPredefinedDevices() {
    return
        deviceConfig.getAddresses()
            .entrySet()
            .stream()
            .map(this::createDevice)
            .toList();
  }

  private Device createDevice(Entry<String, String> entry) {
    return new Device(entry.getKey(), addressResolver.parseString(entry.getValue()));
  }
}
