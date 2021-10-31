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

  public InetAddress parseString(String address) throws UnknownHostException {
    logger.finer(() -> "Address to parse: " + address);

    try (var scanner = new Scanner(address)) {
      scanner.useDelimiter(Pattern.compile("\\D+"));

      byte[] addressArray = convertIntArrayToByteArray(scanner.tokens()
          .mapToInt(Integer::parseInt)
          .toArray());

      logger.finer(() -> "Parsed address: " + Arrays.toString(addressArray));

      return InetAddress.getByAddress(addressArray);
    }
  }

  private static byte[] convertIntArrayToByteArray(int[] integers) {
    ByteBuffer byteBuffer = ByteBuffer.allocate(integers.length);
    Arrays.stream(integers)
        .mapToObj(i -> (byte) i)
        .forEach(byteBuffer::put);

    return byteBuffer.array();
  }
}
