package hu.zza.yeelan;

import java.util.StringJoiner;

import static hu.zza.yeelan.ParameterType.ENUM;
import static hu.zza.yeelan.ParameterType.INDEXED_ENUM;
import static hu.zza.yeelan.ParameterType.INT_ENUM;
import static hu.zza.yeelan.ParameterType.INT_RANGE;
import static hu.zza.yeelan.ParameterType.NULL;


enum Parameter {
    EMPTY("", "", NULL, ""),
    
    COLOR_TEMPERATURE("Color temperature", "", INT_RANGE, "K", "2700", "6500"),
    
    BRIGHTNESS("Brightness", "", INT_RANGE, "%", "1", "100"),
    
    DURATION("Duration", "", INT_RANGE, "ms", "30", "120000"),
    
    EFFECT("Effect", "", ENUM, "", "smooth", "sudden"),
    
    POWER("Power", "", ENUM, "", "on", "off"),
    
    MODE("Mode", "", INDEXED_ENUM, "", "Normal", "CT mode", "RGB mode", "HSV mode", "Color flow", "Night light"),
    
    CRON_MODE("Cron mode", "", INT_ENUM, "", "0"),
    
    TIMER("Timer", "", INT_RANGE, "min", "1", ""),
    
    ADJUST("Adjust", "", INT_RANGE, "%", "-100", "100"),
    
    ACTION("Action", "", ENUM, "", "increase", "decrease", "circle"),
    
    PROPERTY("Property", "", ENUM, "", "bright", "ct");
    
    final         String        name;
    final         String        description;
    final         ParameterType type;
    final         String        unit;
    private final String[]      values;
    
    
    Parameter(String name, String description, ParameterType type, String unit, String... values) {
        this.name        = name;
        this.description = description;
        this.type        = type;
        this.unit        = unit;
        this.values      = values;
    }
    
    
    String getValuesAsString() {
        StringJoiner joiner = new StringJoiner(", ");
        
        for (String s : this.values) {
            joiner.add(s);
        }
        
        return joiner.toString();
    }
    
    
    String getValue(int index) {
        return this.values[index];
    }
}
