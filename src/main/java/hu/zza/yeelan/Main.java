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
    private final static String RANGE_REQUEST_TEMPLATE = "%n[ %s - %s ] (Unit: %s)%n%S: ";
    private final static String ENUM_REQUEST_TEMPLATE  = "%n[ %s ]%n%S: ";
    private final static String LOGO                   = "__  __          __             \n" + "\\ \\/ /__  ___  / /  ____ _____ \n" + " \\  / _ \\/ _ \\/ /  / __ `/ __ \\\n" + " / /  __/  __/ /__/ /_/ / / / /\n" + "/_/\\___/\\___/_____|__,_/_/ /_/ \n";
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
                    params = composeParams(command);
                    
                    if (!"".equals(params))
                    {
                        TelnetClient.send(Yee_B, command, params);
                    }
                }
            }
        }
    }
    
    private static String composeParams(String command)
    {
        if (!COMMANDS.containsKey(command))
        {
            System.out.printf("%nInvalid command name: %s%n", command);
            return "";
        }
        
        Parameter[]  parameters   = COMMANDS.get(command);
        StringJoiner paramsJoiner = new StringJoiner(",");
        
        for (int i = 0; i < 4; i++)
        {
            if (parameters[i].type != ParameterType.NULL)
            {
                paramsJoiner.add(requestParameter(parameters[i]));
            }
        }
        return paramsJoiner.toString();
    }
    
    private static String requestParameter(Parameter parameter)
    {
        switch (parameter.type)
        {
            case INT_RANGE:
                System.out.printf(RANGE_REQUEST_TEMPLATE,
                                  parameter.getValue(0),
                                  parameter.getValue(1),
                                  parameter.unit,
                                  parameter.name
                );
                break;
            
            case ENUM:
            case INT_ENUM:
            case INDEXED_ENUM:
                System.out.printf(ENUM_REQUEST_TEMPLATE, parameter.getValuesAsString(), parameter.name);
                break;
            
            default:
                System.err.printf("%nNot suitable parameter! @ hu.zza.yeelan.Main.requestParameter()%n%n");
                return "";
        }
        
        String input = SCANNER
                               .nextLine()
                               .strip();
        
        return parameter.type == ParameterType.ENUM ? String.format("\"%s\"", input) : input;
    }
}
