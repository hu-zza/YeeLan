package hu.zza.yeelan.rest.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringJoiner;
import java.util.logging.Logger;

public interface TelnetConnection {
  Logger logger = Logger.getLogger(TelnetConnection.class.getName());
  String COMMAND_FORMAT = "{\"id\":1,\"method\":\"%s\",\"params\":[%s]}";

  static String send(InetAddress address, String method, String... parameters) {
    try (Socket yeeSocket = new Socket(address, 55443);
        BufferedWriter out = new BufferedWriter(
            new OutputStreamWriter(yeeSocket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(yeeSocket.getInputStream()))
    ) {
      var toSend = String.format(COMMAND_FORMAT, method, getParameterString(parameters));
      logger.finer(() -> "Telnet command: " + toSend);
      logger.finer(() -> "Send to device: " + address.getHostAddress());
      out.write(toSend + "\r\n");
      out.flush();
      return in.readLine();

    } catch (IOException e) {
      System.err.printf("%nIOException at hu.zza.yeelan.rest.TelnetClient:%n%n%s%n%n", e);
      return "";
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