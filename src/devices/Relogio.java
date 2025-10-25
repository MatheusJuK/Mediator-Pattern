package devices;

public class Relogio implements SmartDevice {
    private final boolean on = true; // Sempre ativo

    @Override
    public String getName() {
        return "Relógio";
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOn() {
        // Sempre ligado
    }

    @Override
    public void turnOff() {
        // Nunca desliga
    }
}
