package devices;

public class Cafeteira implements SmartDevice {
    private boolean on = false;

    @Override
    public String getName() {
        return "Cafeteira";
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOn() {
        on = true;
        System.out.println("[CAFETEIRA] Preparando café ☕");
    }

    @Override
    public void turnOff() {
        on = false;
        System.out.println("[CAFETEIRA] Desligada.");
    }
}
