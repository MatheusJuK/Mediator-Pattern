package devices;

public class TV implements SmartDevice {
    private boolean on = false;

    @Override
    public String getName() {
        return "TV";
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOn() {
        if (!on) {
            on = true;
            System.out.println("[TV] TV ligada 📺");
        } else {
            System.out.println("[TV] Já estava ligada.");
        }
    }

    @Override
    public void turnOff() {
        on = false;
        System.out.println("[TV] TV desligada.");
    }
}
