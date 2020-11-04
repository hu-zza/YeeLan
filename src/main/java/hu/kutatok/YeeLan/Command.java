package hu.kutatok.YeeLan;

import java.util.HashMap;

import static hu.kutatok.YeeLan.Parameter.*;

class Command
{
    static final HashMap<String, Parameter[]> COMMANDS = new HashMap<>();
    static final Parameter[] PARAMS = new Parameter[4];
    
    static
    {
        PARAMS[0] = EMPTY;
        PARAMS[1] = EMPTY;
        PARAMS[2] = EMPTY;
        PARAMS[3] = EMPTY;
        COMMANDS.put("toggle", PARAMS.clone());
        COMMANDS.put("set_default", PARAMS.clone());
        COMMANDS.put("exit", PARAMS.clone());

        PARAMS[0] = CRON_MODE;
        COMMANDS.put("cron_get", PARAMS.clone());
        COMMANDS.put("cron_del", PARAMS.clone());
        
        PARAMS[1] = TIMER;
        COMMANDS.put("cron_add", PARAMS.clone());
    
        PARAMS[0] = ADJUST;
        PARAMS[1] = DURATION;
        COMMANDS.put("adjust_bright", PARAMS.clone());
        COMMANDS.put("adjust_ct", PARAMS.clone());
    
        PARAMS[0] = ACTION;
        PARAMS[1] = PROPERTY;
        COMMANDS.put("set_adjust", PARAMS.clone());
    
        PARAMS[0] = COLOR_TEMPERATURE;
        PARAMS[1] = EFFECT;
        PARAMS[2] = DURATION;
        COMMANDS.put("set_ct_abx", PARAMS.clone());
    
        PARAMS[0] = BRIGHTNESS;
        COMMANDS.put("set_bright", PARAMS.clone());
    
        PARAMS[0] = POWER;
        PARAMS[3] = MODE;
        COMMANDS.put("set_power", PARAMS.clone());
    }
}
