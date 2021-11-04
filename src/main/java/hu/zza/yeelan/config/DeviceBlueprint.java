package hu.zza.yeelan.config;

import java.util.List;
import lombok.Data;

@Data
public class DeviceBlueprint {
  private String address;
  private List<String> propertyProfile;
}
