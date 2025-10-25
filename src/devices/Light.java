package devices;

import mediator.SmartHomeMediator;

/**
 * Colaborador concreto (Concrete Colleague) - Lâmpada Inteligente.
 * Lida apenas com comandos específicos para luz (ligar/desligar).
 */
public class Light extends SmartDevice {
    private boolean isOn = false;

    public Light(SmartHomeMediator mediator, String name) {
        super(mediator, name);
    }

    /**
     * Processa o comando recebido do Mediador.
     * A lógica de *parsing* é mínima, focada apenas nas ações de luz.
     * @param command O comando em minúsculas e já segmentado pelo Mediador.
     */
    @Override
    public void receive(String command) {
        if (command.contains("desligar") || command.contains("apagar")) {
            turnOff();
        } else if (command.contains("ligar") || command.contains("acender")) {
            turnOn();
        } else {
            // Caso receba um comando genérico do Mediador que não é uma ação de luz
            System.out.println("[LÂMPADA] " + name + ": Comando não reconhecido (" + command + ").");
        }
    }

    private void turnOn() {
        if (!isOn) {
            isOn = true;
            System.out.println("[LÂMPADA] " + name + " foi ligada 💡");
        } else {
            System.out.println("[LÂMPADA] " + name + " já está ligada.");
        }
    }

    private void turnOff() {
        if (isOn) {
            isOn = false;
            System.out.println("[LÂMPADA] " + name + " foi desligada 📴");
        } else {
            System.out.println("[LÂMPADA] " + name + " já está desligada.");
        }
    }
}