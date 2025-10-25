package input;

import mediator.SmartHomeMediator;

public class Echo {
    private final SmartHomeMediator mediator;

    public Echo(SmartHomeMediator mediator) {
        this.mediator = mediator;
    }

    public void ouvirComando(String comandoVoz) {
        System.out.println("[ECHO] Captando comando de voz...");
        mediator.sendCommand(comandoVoz);
    }
}
