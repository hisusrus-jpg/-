package ru.tlauncher.chatvoicefilter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ChatVoiceFilterMod implements ClientModInitializer {
    public static final String MOD_ID = "chatvoicefilter";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static ChatVoiceConfig config;

    public static ChatVoiceConfig config() {
        return config;
    }

    @Override
    public void onInitializeClient() {
        config = ChatVoiceConfig.load();

        ClientReceiveMessageEvents.CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
            handleMessage(message, sender.getName());
        });

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if (!overlay) {
                handleMessage(message, null);
            }
        });

        LOGGER.info("ChatVoiceFilter initialized");
    }

    private static void handleMessage(Text message, String playerName) {
        if (!config.enabled || !config.isAllowedPlayer(playerName)) {
            return;
        }

        String raw = message.getString();
        if (raw.isBlank()) {
            return;
        }

        String textToSpeak = config.includePlayerName && playerName != null
                ? playerName + " says: " + raw
                : raw;

        SpeechService.speak(config, textToSpeak);
    }
}
