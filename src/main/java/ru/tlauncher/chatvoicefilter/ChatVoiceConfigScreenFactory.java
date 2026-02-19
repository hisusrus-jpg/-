package ru.tlauncher.chatvoicefilter;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ConfigScreenFactory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public final class ChatVoiceConfigScreenFactory implements ConfigScreenFactory<Screen> {
    @Override
    public Screen create(Screen parent) {
        ChatVoiceConfig config = ChatVoiceFilterMod.config();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("chatvoicefilter.title"))
                .setSavingRunnable(config::save);

        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("chatvoicefilter.category.general"));
        ConfigCategory speech = builder.getOrCreateCategory(Text.translatable("chatvoicefilter.category.speech"));
        ConfigEntryBuilder entries = builder.entryBuilder();

        general.addEntry(entries.startBooleanToggle(Text.translatable("chatvoicefilter.enabled"), config.enabled)
                .setDefaultValue(true)
                .setSaveConsumer(value -> config.enabled = value)
                .build());

        general.addEntry(entries.startBooleanToggle(Text.translatable("chatvoicefilter.includePlayerName"), config.includePlayerName)
                .setDefaultValue(true)
                .setSaveConsumer(value -> config.includePlayerName = value)
                .build());

        general.addEntry(entries.startBooleanToggle(Text.translatable("chatvoicefilter.includeSystem"), config.includeSystemMessages)
                .setDefaultValue(false)
                .setSaveConsumer(value -> config.includeSystemMessages = value)
                .build());

        general.addEntry(entries.startEnumSelector(Text.translatable("chatvoicefilter.filterMode"), ChatVoiceConfig.FilterMode.class, config.filterMode)
                .setDefaultValue(ChatVoiceConfig.FilterMode.ALL)
                .setSaveConsumer(value -> config.filterMode = value)
                .build());

        general.addEntry(entries.startStrList(Text.translatable("chatvoicefilter.playerList"), config.playerList)
                .setDefaultValue(List.of())
                .setSaveConsumer(value -> config.playerList = value)
                .build());

        speech.addEntry(entries.startEnumSelector(Text.translatable("chatvoicefilter.backend"), ChatVoiceConfig.Backend.class, config.backend)
                .setDefaultValue(ChatVoiceConfig.Backend.POWERSHELL)
                .setSaveConsumer(value -> config.backend = value)
                .build());

        speech.addEntry(entries.startStrField(Text.translatable("chatvoicefilter.voice"), config.windowsVoiceName)
                .setDefaultValue("")
                .setSaveConsumer(value -> config.windowsVoiceName = value)
                .build());

        speech.addEntry(entries.startIntSlider(Text.translatable("chatvoicefilter.rate"), config.rate, -10, 10)
                .setDefaultValue(0)
                .setSaveConsumer(value -> config.rate = value)
                .build());

        speech.addEntry(entries.startIntSlider(Text.translatable("chatvoicefilter.volume"), config.volume, 0, 100)
                .setDefaultValue(100)
                .setSaveConsumer(value -> config.volume = value)
                .build());

        return builder.build();
    }
}
