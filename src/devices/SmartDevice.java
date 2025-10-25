package devices;

import mediator.SmartHomeMediator;

/**
 * Classe base abstrata para todos os dispositivos inteligentes (Colaboradores).
 * Cada dispositivo conhece o Mediador (SmartHomeMediator), mas não interage
 * diretamente com outros Colaboradores.
 */
public abstract class SmartDevice {
    // Referência protegida ao Mediador para enviar comandos.
    protected SmartHomeMediator mediator;
    protected String name;

    public SmartDevice(SmartHomeMediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Permite que o dispositivo envie um comando ao Mediador.
     * Esta é a única forma de um Colaborador se comunicar.
     * @param command O comando a ser enviado.
     */
    public void send(String command) {
        // Envia o comando para o Mediador, passando a si mesmo como remetente.
        mediator.sendCommand(command, this);
    }

    /**
     * Ação executada quando o Mediador envia um comando a este dispositivo.
     * Esta é a única forma de um Colaborador receber comunicação.
     * @param command O comando específico a ser processado pelo dispositivo.
     */
    public abstract void receive(String command);
}