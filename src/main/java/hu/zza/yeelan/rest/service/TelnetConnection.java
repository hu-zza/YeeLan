package hu.zza.yeelan.rest.service;

import hu.zza.yeelan.rest.model.Response;
import hu.zza.yeelan.rest.service.resolver.ResponseResolver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringJoiner;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@AllArgsConstructor
@Service
public class TelnetConnection {
  private final ResponseResolver responseResolver;
  private static final String commandFormat = "{\"id\":1,\"method\":\"%s\",\"params\":[%s]}";

  public Response send(InetAddress address, String method, String... parameters) {
    try (Socket yeeSocket = new Socket(address, 55443);
        BufferedWriter out = new BufferedWriter(
            new OutputStreamWriter(yeeSocket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(yeeSocket.getInputStream()))
    ) {
      var toSend = String.format(commandFormat, method, getParameterString(parameters));
      log.finer(() -> "Telnet command: " + toSend);
      log.finer(() -> "Send to device: " + address.getHostAddress());
      out.write(toSend + "\r\n");
      out.flush();
      return responseResolver.parseString(in.readLine());

    } catch (IOException e) {
      System.err.printf("%nIOException at hu.zza.yeelan.rest.TelnetClient:%n%n%s%n%n", e);
      return null; //TODO
    }
  }

  private static String getParameterString(String[] parameters) {
    StringJoiner joiner = new StringJoiner(",");

    for (String param : parameters) {
      try {
        joiner.add(String.valueOf(Integer.parseInt(param)));
      } catch (NumberFormatException ignored) {
        joiner.add(String.format("\"%s\"", param));
      }
    }

    return joiner.toString();
  }
}