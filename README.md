# Chat Voice Filter (Fabric 1.21.7)

Клиентский мод для Fabric, который озвучивает чат с гибкими фильтрами:

- озвучка всех сообщений;
- озвучка только из списка ников;
- озвучка всех кроме списка;
- выбор движка озвучки:
  - `POWERSHELL` (Windows TTS, можно выбрать голос);
  - `VANILLA_NARRATOR` (обычный диктор Minecraft).

## Как получить готовый файл мода (.jar)

1. Открой вкладку **Actions** в репозитории.
2. Запусти workflow **Build Fabric Mod Jar** (кнопка `Run workflow`).
3. После завершения скачай артефакт **chatvoicefilter-mod-jar**.
4. Внутри будет готовый файл `chatvoicefilter-<version>.jar`.
5. Скопируй этот `.jar` в папку `.minecraft/mods` (TLauncher/Fabric).

> Это самый простой вариант: просто скачать готовый `.jar` и закинуть в `mods`.

## Настройка в игре

Открой Mod Menu → Chat Voice Filter.

Доступные настройки:

- **Enable speech** — включить/выключить мод.
- **Speak player name** — добавлять ник в начало озвучки.
- **Speak system messages** — озвучивать системные сообщения.
- **Player filter mode** — `ALL`, `ONLY_LIST`, `EXCLUDE_LIST`.
- **Player list** — список ников (для режимов фильтра).
- **Speech backend** — `POWERSHELL` или `VANILLA_NARRATOR`.
- **Windows voice name** — имя голоса Windows (например `Microsoft Irina Desktop`).
- **Speech rate** — скорость `-10..10`.
- **Volume** — громкость `0..100`.

## Как узнать имена доступных голосов в Windows

В PowerShell:

```powershell
Add-Type -AssemblyName System.Speech
(New-Object System.Speech.Synthesis.SpeechSynthesizer).GetInstalledVoices().VoiceInfo | Select-Object Name, Culture
```

## Локальная сборка

```bash
./gradlew build
```

или

```bash
gradle build
```

Готовый JAR будет в `build/libs`.
