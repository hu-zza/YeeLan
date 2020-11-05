package hu.zza.yeelan;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringJoiner;

import static hu.zza.yeelan.Command.COMMANDS;


public class Main
{
    final static Scanner SCANNER = new Scanner(System.in);
    private final static String LOGO                   = "__  __          __             \n" +
                                                         "\\ \\/ /__  ___  / /  ____ _____ \n" +
                                                         " \\  / _ \\/ _ \\/ /  / __ `/ __ \\\n" +
                                                         " / /  __/  __/ /__/ /_/ / / / /\n" +
                                                         "/_/\\___/\\___/_____|__,_/_/ /_/ \n";
    private static InetAddress Yee_A;
    private static InetAddress Yee_B;
    private static boolean     waitingForUserInput = true;
    
    static
    {
        try
        {
            Yee_A = InetAddress.getByAddress(new byte[] {(byte) 192, (byte) 168, 1, 107});
            Yee_B = InetAddress.getByAddress(new byte[] {(byte) 192, (byte) 168, 1, 108});
        }
        catch (UnknownHostException e)
        {
            System.err.printf("%nUnknownHostException at hu.zza.yeelan.Main:%n%n%s%n%n", e);
        }
    }
    
    public static void main(String... args)
    {
        System.out.println(LOGO);
        
        String[] commandArray = COMMANDS
                                        .keySet()
                                        .toArray(new String[0]);
        Arrays.sort(commandArray);
        
        StringJoiner joiner = new StringJoiner(", ");
        for (String s : commandArray)
        {
            joiner.add(s);
        }
        
        String commandStringList = joiner.toString();
        String command;
        String params;
        
        try (SCANNER)
        {
            while (waitingForUserInput)
            {
                System.out.printf("AVAILABLE COMMANDS:%n%s%n%n> ", commandStringList);
                
                command = SCANNER
                                  .nextLine()
                                  .strip();
                System.out.println();
                
                if ("exit".equals(command))
                {
                    waitingForUserInput = false;
                }
                else
                {
                    params = ParametersBuilder.composeParams(command);
                    
                    if (!"".equals(params))
                    {
                        TelnetClient.send(Yee_B, command, params);
                    }
                }
            }
        }
    }
    
}
