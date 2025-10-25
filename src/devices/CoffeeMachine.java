package devices;

import mediator.SmartHomeMediator;

/**
 * Colaborador concreto (Concrete Colleague) - Cafeteira.
 * Lida com comandos de preparo de café.
 */
public class CoffeeMachine extends SmartDevice {

    public CoffeeMachine(SmartHomeMediator mediator, String name) {
        super(mediator, name);
    }

    /**
     * Processa o comando recebido do Mediador.
     * @param command O comando em minúsculas e já segmentado.
     */
    @Override
    public void receive(String command) {
        if (command.contains("5 minutos") || command.contains("depois")) {
            System.out.println("[CAFETEIRA] " + name + ": Preparando café em 5 minutos ☕ (Atraso ativado)");
        }
        else if (command.contains("café") || command.contains("fazer") || command.contains("preparar")) {
            System.out.println("[CAFETEIRA] " + name + ": Café pronto! ☕ (Preparação Imediata)");
        } else {
            System.out.println("[CAFETEIRA] " + name + ": Não entendi o comando (" + command + ").");
        }
    }
}