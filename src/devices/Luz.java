package devices;

public class Luz implements SmartDevice {
    private boolean on = false;
    private final String name;

    public Luz(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOn() {
        if (!on) {
            on = true;
            System.out.println("[LUZ] " + name + " foi ligada 💡");
        } else {
            System.out.println("[LUZ] " + name + " já está ligada.");
        }
    }

    @Override
    public void turnOff() {
        if (on) {
            on = false;
            System.out.println("[LUZ] " + name + " foi desligada.");
        }
    }
}