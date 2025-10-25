import mediator.AlexaMediator;
import devices.Light;
import devices.CoffeeMachine;
import devices.TV;

/**
 * Simulação de uma casa inteligente usando o padrão Mediator.
 * O cliente (usuário) interage diretamente com o Mediador (Alexa).
 */
public class Main {
    public static void main(String[] args) {
        AlexaMediator alexa = new AlexaMediator();


        Light light = new Light(alexa, "Luz da Sala");
        CoffeeMachine coffeeMachine = new CoffeeMachine(alexa, "Cafeteira");
        TV tv = new TV(alexa, "TV da Sala");


        alexa.setLight(light);
        alexa.setCoffeeMachine(coffeeMachine);
        alexa.setTV(tv);

        System.out.println("=== TESTES DE COMANDOS COMPLEXOS ===");


        System.out.println("------------------------------------");
        alexa.sendCommand("Fazer café, acender luz da sala");

        System.out.println("------------------------------------");
        alexa.sendCommand("Desligar TV; apagar luz");

        System.out.println("------------------------------------");
        alexa.sendCommand("Tocar música");

        System.out.println("------------------------------------");

        alexa.sendCommand("Desligar luz e fazer café");
    }
}