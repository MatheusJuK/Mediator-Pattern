package mediator;

import devices.*;
import java.util.*;
import java.util.regex.*;
import java.text.Normalizer;

public class AlexaMediator implements SmartHomeMediator {
    private final Map<String, SmartDevice> devices = new HashMap<>();

    // Pré-compilamos padrões para detecção mais robusta
    private final Pattern pCafeteira = Pattern.compile("\\b(cafe|cafeteira)\\b");
    private final Pattern pAr = Pattern.compile("\\b(ar|condicionado|arcondicionado)\\b");
    private final Pattern pLuz = Pattern.compile("\\b(luz|sala)\\b");
    private final Pattern pTV = Pattern.compile("\\b(tv|televisao|televisão|televisao)\\b");

    private final Pattern pLigar = Pattern.compile("\\b(ligar|ligue|abrir|preparar|fazer|liga)\\b");
    private final Pattern pDesligar = Pattern.compile("\\b(desligar|desliga|apagar|fechar)\\b");

    public void addDevice(SmartDevice device) {
        devices.put(device.getName().toLowerCase(), device);
    }

    @Override
    public void sendCommand(String command) {
        System.out.println("\n[Alexa] 🎤 Comando recebido: \"" + command + "\"");
        if (command == null || command.trim().isEmpty()) return;

        // Normaliza: remove acentos e deixa tudo lowercase
        command = removeAccents(command).toLowerCase().trim();

        // Split em subcomandos — non-capturing group e \b para "e" isolado
        String[] subCommands = command.split("\\s*(?:\\be\\b|,|;|\\bentao\\b)\\s*");

        for (String raw : subCommands) {
            String sub = raw.trim();
            if (shouldIgnoreSubcommand(sub)) continue;
            handleSubCommand(sub);
        }
    }

    private void handleSubCommand(String cmd) {
        int delay = parseDelay(cmd);

        boolean wantsLigar = pLigar.matcher(cmd).find();
        boolean wantsDesligar = pDesligar.matcher(cmd).find();

        // Se não tiver verbo explícito, assumimos ação "ligar" para dispositivos como cafeteira/café
        boolean assumeLigar = !wantsLigar && !wantsDesligar;

        // coletamos dispositivos alvo sem duplicação (Set)
        Set<SmartDevice> targets = new LinkedHashSet<>();

        if (pCafeteira.matcher(cmd).find()) {
            SmartDevice d = devices.get("cafeteira");
            if (d != null) targets.add(d);
        }
        if (pAr.matcher(cmd).find()) {
            SmartDevice d = devices.get("ar condicionado");
            if (d != null) targets.add(d);
        }
        if (pLuz.matcher(cmd).find()) {
            SmartDevice d = devices.get("luz da sala");
            if (d != null) targets.add(d);
        }
        if (pTV.matcher(cmd).find()) {
            SmartDevice d = devices.get("tv");
            if (d != null) targets.add(d);
        }

        if (targets.isEmpty()) {
            System.out.println("[Alexa] ⚠️ Nenhum dispositivo correspondente encontrado em: \"" + cmd + "\"");
            return;
        }

        // Prepara ações (cada dispositivo apenas uma vez)
        List<Runnable> actions = new ArrayList<>();
        for (SmartDevice device : targets) {
            if (wantsLigar) actions.add(() -> safeExecute(device, true));
            else if (wantsDesligar) actions.add(() -> safeExecute(device, false));
            else if (assumeLigar) actions.add(() -> safeExecute(device, true));
        }

        // Execução com delay (simulado)
        if (delay > 0) {
            System.out.println("[Alexa] ⏳ Ações agendadas para daqui a " + delay + " segundos (simulação)...");
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    actions.forEach(Runnable::run);
                    System.out.println("[Alexa] ✅ Execução concluída: \"" + cmd + "\"");
                }
            }, delay * 1000L);
        } else {
            actions.forEach(Runnable::run);
        }
    }

    // Executa ligar/desligar de forma segura (evita redundância) com mensagens claras
    private void safeExecute(SmartDevice device, boolean turnOn) {
        String name = device.getName();
        if (turnOn) {
            if (!device.isOn()) {
                device.turnOn();
            } else {
                if(name.equals("Cafeteira")) {
                    System.out.println("[CAFETEIRA] Preparando café ☕");
                }else {
                    System.out.println("[" + name + "] " + name + " já está ligado.");
                }
            }
        } else {
            if (device.isOn()) {
                device.turnOff();
            } else {
                System.out.println("[" + name + "] " + name + " já está desligado.");
            }
        }
    }

    // Extrai delay (em segundos) da string; mapeia minutos para simulação (1min->10s) e aplica cap
    private int parseDelay(String cmd) {
        Matcher m = Pattern.compile("(\\d+)\\s*(minuto|minutos|segundo|segundos)").matcher(cmd);
        if (m.find()) {
            int value = Integer.parseInt(m.group(1));
            String unit = m.group(2);
            if (unit.startsWith("minut")) {
                int simulated = Math.max(1, value) * 10; // 1 minuto -> 10s (simulação)
                return Math.min(simulated, 30);
            } else {
                return Math.min(value, 30);
            }
        }
        return 0;
    }

    // Remove acentos
    private String removeAccents(String s) {
        String normalized = Normalizer.normalize(s, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }

    // Ignora pedaços muito curtos ou palavras de enchimento
    private boolean shouldIgnoreSubcommand(String s) {
        if (s == null) return true;
        s = s.trim();
        if (s.length() < 3) return true; // evita ruído como "d", "pr", etc.
        String[] fillers = {"por favor", "por favor.", "por favor,", "porfavor", "ok", "obrigado", "obrigada"};
        for (String f : fillers) if (s.contains(f)) return true;
        return false;
    }
}
