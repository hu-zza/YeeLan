package hu.zza.yeelan.rest.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class AddressResolver {

  private static Logger logger = Logger.getLogger(AddressResolver.class.getName());

  public InetAddress parseString(String ipAddress) throws UnknownHostException {
    logger.finer(() -> "Address to parse: " + ipAddress);

    try (var scanner = new Scanner(ipAddress)) {
      scanner.useDelimiter(Pattern.compile("\\D+"));

      int[] intAddress =
          scanner.tokens()
              .mapToInt(Integer::parseInt)
              .toArray();

      byte[] address = getAsByteArray(intAddress);

      logger.finer(() -> "Parsed address: " + Arrays.toString(address));
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
