package ru.tlauncher.chatvoicefilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ChatVoiceConfig {
    public enum FilterMode {
        ALL,
        ONLY_LIST,
        EXCLUDE_LIST
    }

    public enum Backend {
        POWERSHELL,
        VANILLA_NARRATOR
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("chatvoicefilter.json");

    public boolean enabled = true;
    public boolean includePlayerName = true;
    public boolean includeSystemMessages = false;
    public FilterMode filterMode = FilterMode.ALL;
    public List<String> playerList = new ArrayList<>();

    public Backend backend = Backend.POWERSHELL;
    public String windowsVoiceName = "";
    public int rate = 0;
    public int volume = 100;

    public static ChatVoiceConfig load() {
        if (Files.notExists(CONFIG_PATH)) {
            ChatVoiceConfig config = new ChatVoiceConfig();
            config.save();
            return config;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            ChatVoiceConfig config = GSON.fromJson(reader, ChatVoiceConfig.class);
            return config != null ? config : new ChatVoiceConfig();
        } catch (IOException exception) {
            ChatVoiceFilterMod.LOGGER.error("Failed to load config", exception);
            return new ChatVoiceConfig();
        }
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException exception) {
            ChatVoiceFilterMod.LOGGER.error("Failed to save config", exception);
        }
    }

    public boolean isAllowedPlayer(String playerName) {
        if (playerName == null) {
            return includeSystemMessages;
        }

        String normalized = playerName.toLowerCase();
        boolean contains = playerList.stream().map(String::toLowerCase).anyMatch(normalized::equals);

        return switch (filterMode) {
            case ALL -> true;
            case ONLY_LIST -> contains;
            case EXCLUDE_LIST -> !contains;
        };
    }
}
