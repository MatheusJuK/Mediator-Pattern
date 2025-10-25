package devices;

public class ArCondicionado implements SmartDevice {
    private boolean on = false;

    @Override
    public String getName() {
        return "Ar Condicionado";
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOn() {
        if (!on) {
            on = true;
            System.out.println("[AR] Ar-condicionado ligado ❄️");
        } else {
            System.out.println("[AR] Ar-condicionado já está ligado.");
        }
    }

    @Override
    public void turnOff() {
        if (on) {
            on = false;
            System.out.println("[AR] Ar-condicionado desligado.");
        }
    }
}
