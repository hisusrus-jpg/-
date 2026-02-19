package ru.tlauncher.chatvoicefilter;

import net.minecraft.client.MinecraftClient;

import java.io.IOException;

public final class SpeechService {
    public static void speak(ChatVoiceConfig config, String text) {
        switch (config.backend) {
            case POWERSHELL -> speakPowershell(config, text);
            case VANILLA_NARRATOR -> speakVanilla(text);
        }
    }

    private static void speakVanilla(String text) {
        MinecraftClient.getInstance().getNarratorManager().narrate(text);
    }

    private static void speakPowershell(ChatVoiceConfig config, String text) {
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            speakVanilla(text);
            return;
        }

        String escapedText = escapeForSingleQuote(text);
        String voice = escapeForSingleQuote(config.windowsVoiceName);
        int rate = Math.max(-10, Math.min(10, config.rate));
        int volume = Math.max(0, Math.min(100, config.volume));

        String script = "$s = New-Object System.Speech.Synthesis.SpeechSynthesizer;"
                + (voice.isBlank() ? "" : "$s.SelectVoice('" + voice + "');")
                + "$s.Rate=" + rate + ";"
                + "$s.Volume=" + volume + ";"
                + "$s.Speak('" + escapedText + "');"
                + "$s.Dispose();";

        try {
            new ProcessBuilder(
                    "powershell",
                    "-NoProfile",
                    "-NonInteractive",
                    "-ExecutionPolicy", "Bypass",
                    "-Command",
                    "Add-Type -AssemblyName System.Speech;" + script
            ).start();
        } catch (IOException exception) {
            ChatVoiceFilterMod.LOGGER.warn("Failed to use PowerShell TTS, falling back to vanilla narrator", exception);
            speakVanilla(text);
        }
    }

    private static String escapeForSingleQuote(String raw) {
        return raw.replace("'", "''");
    }
}
