package hu.zza.yeelan;

import java.util.StringJoiner;

import static hu.zza.yeelan.Command.COMMANDS;
import static hu.zza.yeelan.Main.SCANNER;
import static hu.zza.yeelan.ParameterType.ENUM;
import static hu.zza.yeelan.ParameterType.NULL;


class ParametersBuilder {
    private final static String RANGE_REQUEST_TEMPLATE = "%n[ %s - %s ] (Unit: %s)%n%S: ";
    private final static String ENUM_REQUEST_TEMPLATE  = "%n[ %s ]%n%S: ";
    
    
    static String composeParams(String command) {
        if (!COMMANDS.containsKey(command)) {
            System.out.printf("%nInvalid command name: %s%n", command);
            return "";
        }
        
        Parameter[]  parameters   = COMMANDS.get(command);
        StringJoiner paramsJoiner = new StringJoiner(",");
        
        for (int i = 0; i < 4; i++) {
            if (parameters[i].type != NULL) {
                paramsJoiner.add(requestParameter(parameters[i]));
            }
        }
        return paramsJoiner.toString();
    }
    
    
    private static String requestParameter(Parameter parameter) {
        switch (parameter.type) {
            case INT_RANGE:
                System.out.printf(RANGE_REQUEST_TEMPLATE, parameter.getValue(0), parameter.getValue(1), parameter.unit,
                                  parameter.name);
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
        
        String input = SCANNER.nextLine()
                              .strip();
        
        return parameter.type == ENUM ? String.format("\"%s\"", input) : input;
    }
}
