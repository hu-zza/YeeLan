package hu.zza.yeelan.rest.model;

public enum Property {
  POWER, BRIGHT, CT, RGB, HUE, SAT, COLOR_MODE,
  FLOWING, DELAYOFF, FLOW_PARAMS, MUSIC_ON, NAME,
  BG_POWER, BG_FLOWING, BG_FLOW_PARAMS, BG_CT,
  BG_LMODE, BG_BRIGHT, BG_RGB, BG_HUE, BG_SAT,
  NL_BR, ACTIVE_MODE;

  public Property parse(String property) {
    return Property.valueOf(property.toUpperCase());
  }

  @Override
  public String toString() {
    return super.toString()
        .toLowerCase();
  }
}
