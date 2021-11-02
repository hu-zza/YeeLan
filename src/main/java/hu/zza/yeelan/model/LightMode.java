package hu.zza.yeelan.model;

public enum LightMode {
  DAY, NIGHT;

  public static LightMode parse(String mode) {
    try {
      return LightMode.valueOf(mode.toUpperCase());
    } catch (Exception e) {
      return LightMode.NIGHT;
    }
  }

  @Override
  public String toString() {
    return super.toString()
        .toLowerCase();
  }
}
