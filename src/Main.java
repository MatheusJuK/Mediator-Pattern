import devices.*;
import input.Echo;
import mediator.AlexaMediator;

public class Main {
    public static void main(String[] args) {
        AlexaMediator alexa = new AlexaMediator();

        alexa.addDevice(new Luz("Luz da Sala"));
        alexa.addDevice(new Cafeteira());
        alexa.addDevice(new ArCondicionado());
        alexa.addDevice(new TV());
        alexa.addDevice(new Relogio());

        Echo echo = new Echo(alexa);

        // ================== TESTES DE COMANDOS ==================
        String[] comandos = {
                "Ligar luz da sala",
                "Ligar ar condicionado",
                "Ligar TV e cafeteira",
                "Desligar TV",
                "Apagar luz da sala",
                "Quero fazer um café e ligar o ar daqui a 10 segundos",
                "Preparar café e ligar luz",
                "Desligar ar condicionado e cafeteira"
        };

        System.out.println("========== TESTES DE COMANDOS SMART HOME ==========\n");

        for (String comando : comandos) {
            echo.ouvirComando(comando);
            try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        }

        // Mantém programa ativo para esperar execuções agendadas
        try { Thread.sleep(15000); } catch (InterruptedException ignored) {}
    }
}
