package hu.kutatok.YeeLan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

class TelnetClient
{
    private static final String COMMAND_FORMAT = "{\"id\":1,\"method\":\"%s\",\"params\":[%s]}";
    
    public static void send(InetAddress device, String method, String params)
    {
        try (
                Socket yeeSocket = new Socket(device, 55443);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(yeeSocket.getOutputStream()));
                BufferedReader in = new BufferedReader(new InputStreamReader(yeeSocket.getInputStream()));
        ) {
            var toSend = String.format(COMMAND_FORMAT, method, params);
            // System.out.println(toSend);
            out.write(toSend);
            out.write("\r\n");
            out.flush();
            //System.out.println(in.readLine());
        } catch (IOException e) {
            System.err.printf("IOException at YeeLan.hu.kutatok.YeeLan.TelnetClient.class: %s", e);
        }
    }
}
