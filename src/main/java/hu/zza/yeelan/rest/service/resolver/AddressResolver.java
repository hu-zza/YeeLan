package hu.zza.yeelan.rest.service.resolver;

import hu.zza.yeelan.rest.model.DeviceAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class AddressResolver {

  public DeviceAddress parseString(String ipAddress) {
    log.finer(() -> "Address to parse: " + ipAddress);

    try {
      return new DeviceAddress(parseIpString(ipAddress));
    } catch (UnknownHostException e) {
      log.warning(e.getMessage());
      log.warning(String.format("Can not initialize IP address: '%s'", ipAddress));
      return DeviceAddress.NULL;
    }
  }

  private InetAddress parseIpString(String ipAddress) throws UnknownHostException {
    try (var scanner = new Scanner(ipAddress)) {
      scanner.useDelimiter(Pattern.compile("\\D+"));

      int[] intAddress =
          scanner.tokens()
              .mapToInt(Integer::parseInt)
              .toArray();

      byte[] address = getAsByteArray(intAddress);

      log.finer(() -> "Parsed address: " + Arrays.toString(address));
      return InetAddress.getByAddress(address);
    }
  }

  private static byte[] getAsByteArray(int[] integers) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(integers.length);
    Arrays.stream(integers)
        .mapToObj(i -> (byte) i)
        .forEach(byteBuffer::put);

    return byteBuffer.array();
  }
}
