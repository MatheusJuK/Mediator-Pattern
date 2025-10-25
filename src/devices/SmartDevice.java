package devices;


public interface SmartDevice {
    String getName();
    void turnOn();
    void turnOff();
    boolean isOn();
}