package devices;

import mediator.SmartHomeMediator;

/**
 * Colaborador concreto (Concrete Colleague) - Televisão.
 * Lida com comandos de ligar, desligar e mudar de canal.
 */
public class TV extends SmartDevice {
    private boolean isOn = false;

    public TV(SmartHomeMediator mediator, String name) {
        super(mediator, name);
    }

    /**
     * Processa o comando recebido do Mediador.
     * @param command O comando em minúsculas e já segmentado.
     */
    @Override
    public void receive(String command) {
        if (command.contains("ligar") || command.contains("assistir")) {
            turnOn();
        } else if (command.contains("desligar") || command.contains("parar")) {
            turnOff();
        } else if (command.contains("canal") && isOn) {
            changeChannel();
        } else {
            System.out.println("[TV] " + name + ": Comando não reconhecido (" + command + ").");
        }
    }

    private void turnOn() {
        if (!isOn) {
            isOn = true;
            System.out.println("[TV] " + name + " foi ligada 📺");
        } else {
            System.out.println("[TV] " + name + " já está ligada.");
        }
    }

    private void turnOff() {
        if (isOn) {
            isOn = false;
            System.out.println("[TV] " + name + " foi desligada 🔌");
        } else {
            System.out.println("[TV] " + name + " já está desligada.");
        }
    }

    private void changeChannel() {
        if (isOn) {
            System.out.println("[TV] " + name + ": Mudando de canal 📡");
        } else {
            System.out.println("[TV] " + name + ": Impossível mudar de canal, a TV está desligada.");
        }
    }
}