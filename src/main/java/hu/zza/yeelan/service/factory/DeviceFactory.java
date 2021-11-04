package hu.zza.yeelan.service.factory;

import hu.zza.yeelan.config.DeviceBlueprint;
import hu.zza.yeelan.config.DeviceConfig;
import hu.zza.yeelan.model.Device;
import hu.zza.yeelan.model.Property;
import hu.zza.yeelan.service.resolver.AddressResolver;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeviceFactory {

  private final DeviceConfig deviceConfig;
  private final AddressResolver addressResolver;

  public List<Device> getPredefinedDevices() {
    return deviceConfig.getList().entrySet().stream().map(this::createDevice).toList();
  }

  private Device createDevice(Entry<String, DeviceBlueprint> entry) {
    DeviceBlueprint blueprint = entry.getValue();

    Map<String, List<Property>> profiles = deviceConfig.getPropertyProfile();

    Set<Property> properties =
        blueprint.getPropertyProfile().stream()
            .flatMap(profile -> profiles.get(profile).stream())
            .collect(Collectors.toSet());

    return new Device(
        entry.getKey(), addressResolver.parseString(blueprint.getAddress()), properties);
  }
}
