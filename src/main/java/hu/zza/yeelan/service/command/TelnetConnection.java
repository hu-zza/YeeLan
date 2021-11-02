package hu.zza.yeelan.service.command;

import hu.zza.yeelan.model.Response;
import hu.zza.yeelan.service.resolver.ResponseResolver;
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

  private static final String commandFormat = "{\"id\":%d,\"method\":\"%s\",\"params\":[%s]}";
  private final ResponseResolver responseResolver;

  Response send(InetAddress address, String method, String[] parameters) {
    return send(address, 1, method, parameters);
  }

  Response send(InetAddress address, int id, String method, String[] parameters) {
    try (var socket = new Socket(address, 55443);
        var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        var in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
    ) {
      var toSend = String.format(commandFormat, id, method, getParameterString(parameters));
      log.finer(() -> String.format("Telnet command: %s -> %s", toSend, address.getHostAddress()));
      out.write(toSend + "\r\n");
      out.flush();
      return responseResolver.parseString(in.readLine());

    } catch (IOException e) {
      log.warning(e.toString());
      return Response.getSimpleError(id, 1, "IOException @ TelnetConnection");
    }
  }

  private static String getParameterString(String[] parameters) {
    var joiner = new StringJoiner(",");

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