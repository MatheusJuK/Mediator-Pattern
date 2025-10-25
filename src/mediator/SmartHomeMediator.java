package mediator;

import devices.SmartDevice;

/**
 * Interface do Mediador (Mediator) - Define a interface de comunicação
 * para os Colaboradores (SmartDevice).
 */
public interface SmartHomeMediator {

    /**
     * Método principal para comunicação.
     * O Mediador usa este método para rotear a mensagem para os Colaboradores apropriados.
     * Os Colaboradores (SmartDevice) usam este método para se comunicar com o Mediador.
     * * @param command O comando de texto completo a ser processado.
     * @param sender O dispositivo que enviou o comando (pode ser null se for o cliente).
     */
    void sendCommand(String command, SmartDevice sender);
}