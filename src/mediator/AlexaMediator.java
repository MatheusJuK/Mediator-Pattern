package mediator;

import devices.CoffeeMachine;
import devices.Light;
import devices.SmartDevice;
import devices.TV;
import java.util.Arrays;

/**
 * Mediador Concreto (Concrete Mediator) - A Alexa.
 * É responsável por interpretar comandos do usuário e coordenar as ações entre os dispositivos.
 * Centraliza a comunicação e a lógica de roteamento (identificação do dispositivo).
 */
public class AlexaMediator implements SmartHomeMediator {

    // Referências aos Colaboradores que o Mediador coordena
    private Light light;
    private CoffeeMachine coffeeMachine;
    private TV tv;

    // Métodos de registro (Setters) - Permitem que o cliente configure o Mediador
    public void setLight(Light light) {
        this.light = light;
    }

    public void setCoffeeMachine(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    public void setTV(TV tv) {
        this.tv = tv;
    }

    /**
     * Implementação do método de comunicação.
     * Recebe um comando (do cliente ou de outro dispositivo) e roteia a ação.
     * @param command O comando de voz completo.
     * @param sender O dispositivo que enviou o comando (null se for o Main/Cliente).
     */
    @Override
    public void sendCommand(String command, SmartDevice sender) {
        if (command == null || command.isBlank()) {
            System.out.println("[ALEXA] Comando vazio. Nenhum comando reconhecido.");
            return;
        }

        System.out.println("\n[ALEXA] Comando recebido de " +
                (sender != null ? sender.getName() : "Cliente/Main") +
                ": \"" + command + "\"");

        // Normaliza o texto para minúsculas para facilitar a correspondência
        String normalized = command.toLowerCase();

        // Quebra a string em subcomandos lógicos usando conectores como "e", ",", ";"
        // Isso permite comandos complexos como "ligar luz e fazer café"
        String[] subCommands = normalized.split("\\s+(e|e|ou|;|,)\\s+");

        for (String subCommand : subCommands) {
            String trimmedCommand = subCommand.trim();
            if (!trimmedCommand.isEmpty()) {
                processSingleCommand(trimmedCommand);
            }
        }
    }

    /**
     * Processa um subcomando, identificando o dispositivo ideal e enviando a mensagem.
     * @param command O comando individual, já normalizado.
     */
    private void processSingleCommand(String command) {

        SmartDevice targetDevice = identifyDeviceByCommand(command);

        if (targetDevice != null) {
            targetDevice.receive(command);
        } else {
            System.out.println("[ALEXA] Não encontrei nenhum dispositivo capaz de executar: \"" + command + "\"");
        }
    }

    /**
     * Identifica o dispositivo ideal com base nas palavras-chave do comando (Lógica de Roteamento).
     * @param command O comando de texto para análise.
     * @return O SmartDevice destinatário ou null se nenhum for encontrado.
     */
    private SmartDevice identifyDeviceByCommand(String command) {
        if (containsAny(command, "luz", "lampada", "claro", "escuro")) {
            return light;
        }

        if (containsAny(command, "café", "cafeteira", "expresso")) {
            return coffeeMachine;
        }

        if (containsAny(command, "tv", "televisão", "canal", "filme")) {
            return tv;
        }

        return null;
    }

    /**
     * Método utilitário para verificar se o texto contém alguma das palavras-chave.
     */
    private boolean containsAny(String text, String... keywords) {
        return Arrays.stream(keywords).anyMatch(text::contains);
    }

    /**
     * Sobrecarga para permitir que o Cliente (Main) chame a Alexa sem especificar o remetente.
     * Esta sobrecarga existe apenas para conveniência do cliente.
     * @param command O comando de voz completo.
     */
    public void sendCommand(String command) {
        sendCommand(command, null);
    }
}