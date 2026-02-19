# Chat Voice Filter (Fabric 1.21.7)

Клиентский мод для Fabric, который озвучивает чат с гибкими фильтрами:

- озвучка всех сообщений;
- озвучка только из списка ников;
- озвучка всех кроме списка;
- выбор движка озвучки:
  - `POWERSHELL` (Windows TTS, можно выбрать голос);
  - `VANILLA_NARRATOR` (обычный диктор Minecraft).

---

## Полная инструкция по установке (TLauncher + Fabric)

### 1) Подготовь версию игры

1. Открой **TLauncher**.
2. В списке версий выбери **Fabric 1.21.7**.
3. Один раз запусти игру на этой версии и закрой её (чтобы создались нужные папки).

### 2) Скачай готовый мод `.jar`

Есть 2 варианта:

#### Вариант A (рекомендуется): скачать готовый файл из GitHub Actions

1. Открой вкладку **Actions** в репозитории.
2. Запусти workflow **Build Fabric Mod Jar** (`Run workflow`).
3. Дождись статуса **Success**.
4. Скачай артефакт **chatvoicefilter-mod-jar**.
5. Распакуй архив и возьми файл вида `chatvoicefilter-<version>.jar`.

#### Вариант B: локальная сборка на ПК

```bash
./gradlew build
```

Готовый файл будет в `build/libs`.

### 3) Установи зависимости (обязательно)

Для работы мода в папке `mods` должны быть:

- **Fabric API**
- **Mod Menu**
- **Cloth Config**
- сам `chatvoicefilter-<version>.jar`

> Если не положить зависимости, мод не загрузится.

### 4) Куда кидать моды в TLauncher

Папка обычно такая:

- Windows: `%APPDATA%\.minecraft\mods`
- Linux: `~/.minecraft/mods`
- macOS: `~/Library/Application Support/minecraft/mods`

Если используешь отдельную директорию TLauncher-сборки — клади в её `mods`.

### 5) Запусти игру

1. В TLauncher выбери **Fabric 1.21.7**.
2. Запусти игру.
3. Проверь, что в списке модов есть **Chat Voice Filter**.

---

## Первичная настройка мода

Открой: **Mod Menu → Chat Voice Filter**.

Рекомендуемый быстрый старт:

1. `Enable speech` = ON
2. `Speech backend` = `POWERSHELL` (если Windows) или `VANILLA_NARRATOR`
3. `Player filter mode`:
   - `ALL` — озвучивать всех
   - `ONLY_LIST` — только игроков из списка
   - `EXCLUDE_LIST` — всех, кроме списка
4. В `Player list` добавь нужные ники (если выбран `ONLY_LIST`/`EXCLUDE_LIST`).
5. При необходимости настрой `Windows voice name`, `Speech rate`, `Volume`.

---

## Как настроить «озвучивать только одного игрока»

Пример: только сообщения от `BestFriend123`.

1. `Player filter mode` = `ONLY_LIST`
2. `Player list` = `BestFriend123`
3. Сохрани настройки.

Теперь озвучка будет только для этого ника.

---

## Как узнать имена доступных голосов в Windows

В PowerShell:

```powershell
Add-Type -AssemblyName System.Speech
(New-Object System.Speech.Synthesis.SpeechSynthesizer).GetInstalledVoices().VoiceInfo | Select-Object Name, Culture
```

Скопируй имя из столбца `Name` в настройку `Windows voice name`.

---

## Если мод не работает

Проверь по шагам:

1. Точно запущена **Fabric 1.21.7**.
2. В `mods` лежат зависимости: Fabric API + Mod Menu + Cloth Config.
3. Версии модов подходят под Minecraft 1.21.7.
4. В настройках мода включен `Enable speech`.
5. Если используешь фильтр `ONLY_LIST`, ник добавлен без ошибок.

