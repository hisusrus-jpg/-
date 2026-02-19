# Chat Voice Filter (Fabric 1.21.7)

Клиентский мод для Fabric, который озвучивает чат с гибкими фильтрами:

- озвучка всех сообщений;
- озвучка только из списка ников;
- озвучка всех кроме списка;
- выбор движка озвучки:
  - `POWERSHELL` (Windows TTS, можно выбрать голос);
  - `VANILLA_NARRATOR` (обычный диктор Minecraft).

---

## Как скачать готовый мод (для игрока)

1. Открой вкладку **Releases** в репозитории.
2. Выбери последний релиз.
3. Скачай файл `chatvoicefilter-<version>.jar` из **Assets**.
4. Положи файл в папку `.minecraft/mods`.

> Это самый быстрый способ: скачал `.jar` и сразу закинул в `mods`.

---

## Как выпустить релиз (для владельца репозитория)

Есть два способа.

### Вариант A: через тег Git

1. Создай и отправь тег:

```bash
git tag v1.0.0
git push origin v1.0.0
```

2. Workflow **Release Fabric Mod** автоматически:
   - соберёт мод,
   - создаст GitHub Release,
   - прикрепит готовый `.jar` в Assets.

### Вариант B: вручную через Actions

1. Открой **Actions → Release Fabric Mod**.
2. Нажми **Run workflow**.
3. Введи тег, например `v1.0.1`.
4. После завершения открой **Releases** и скачай `.jar` из Assets.

---

## Полная инструкция по установке (TLauncher + Fabric)

### 1) Подготовь версию игры

1. Открой **TLauncher**.
2. В списке версий выбери **Fabric 1.21.7**.
3. Один раз запусти игру на этой версии и закрой её (чтобы создались нужные папки).

### 2) Установи зависимости (обязательно)

Для работы мода в папке `mods` должны быть:

- **Fabric API**
- **Mod Menu**
- **Cloth Config**
- сам `chatvoicefilter-<version>.jar`

> Если не положить зависимости, мод не загрузится.

### 3) Куда кидать моды в TLauncher

Папка обычно такая:

- Windows: `%APPDATA%\.minecraft\mods`
- Linux: `~/.minecraft/mods`
- macOS: `~/Library/Application Support/minecraft/mods`

Если используешь отдельную директорию TLauncher-сборки — клади в её `mods`.

### 4) Запусти игру

1. В TLauncher выбери **Fabric 1.21.7**.
2. Запусти игру.
3. Проверь, что в списке модов есть **Chat Voice Filter**.

---

## Локальная сборка (если хочешь собирать сам)

> В репозитории специально нет бинарных файлов (в т.ч. `gradle-wrapper.jar`), чтобы не возникала ошибка "Бинарные файлы не поддерживаются" при обновлении ветки.

```bash
# Linux/macOS
gradle build
```

```powershell
# Windows (PowerShell)
gradle build
```

Готовый файл будет в `build/libs`.

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
