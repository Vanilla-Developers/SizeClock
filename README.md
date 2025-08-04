<!-- Верхний блок для переключения языков -->
<div align="center">
  <b><a href="#-sizeclock-ru">Русский</a></b>
  •
  <b><a href="#-sizeclock-en">English</a></b>
</div>

---

<!-- Русская версия -->
<div id="-sizeclock-ru">

# ⭐ SizeClock ⭐

**SizeClock** — это плагин для серверов Minecraft (Spigot/Paper), который добавляет в игру уникальный предмет — **Часы Размера**. С их помощью игроки могут изменять свой размер или размер других существ, используя накопленный опыт.

Плагин добавляет в игру новую механику, идеально подходящую для RPG-серверов, ивентов или просто для того, чтобы дать игрокам новые забавные возможности.

---

## 🚀 Возможности

- **Изменение размера**: Увеличивайте или уменьшайте себя и других мобов.
- **Часы Размера**: Уникальный предмет с кастомной текстурой для управления размером.
- **Система опыта**: Используйте уровни опыта, чтобы "заряжать" часы. Каждое изменение размера расходует один заряд.
- **GUI-меню**: Удобный интерфейс для настройки часов:
    - Пополнение запаса опыта.
    - Выбор желаемого размера с помощью удобного слайдера.
- **Анимации и звуки**: Плавные эффекты при изменении размера.
- **Гибкость**: Легко настраивается и используется.
- **Команды**: Простая команда для получения часов.
- **Права**: Возможность ограничить доступ к часам через права (`permissions`).

---

## 🎮 Как пользоваться

### 1. Получение Часов

Чтобы получить **Часы Размера**, администратор должен прописать команду:
```
/sizeclock give
```
Для этого требуются права `sizeclock.give`.

### 2. Зарядка Часов

Часы используют опыт в качестве "топлива".

1. **Откройте меню**: Возьмите часы в руку и нажмите **Shift + ПКМ**.
2. **Выберите количество опыта**: В верхнем ряду меню используйте `+` и `-`, чтобы выбрать, сколько уровней опыта вы хотите перевести в часы.
3. **Залейте опыт**: Нажмите на бутылочку с опытом, чтобы подтвердить перенос.

### 3. Изменение размера

1. **Выберите размер**: В том же меню (Shift + ПКМ) в нижнем ряду выберите желаемый размер на слайдере.
2. **Примените эффект**:
   - Чтобы изменить **свой** размер, возьмите часы в руку и нажмите **ПКМ**.
   - Чтобы изменить размер **другого существа**, нажмите на него **ПКМ**, держа часы в руке.

Повторный клик **ПКМ** вернёт размер к стандартному (1.0), также потратив один заряд опыта.

---

## 🛠️ Установка

1. Скачайте последнюю версию плагина из раздела [Релизы](https://github.com/Vanilla-Developers/SizeClock/releases).
2. Поместите `.jar` файл в папку `plugins` вашего сервера.
3. Перезагрузите или запустите сервер.
4. (Опционально) Установите ресурс-пак, идущий в комплекте с плагином, чтобы видеть кастомную текстуру часов.

---

## ⚙️ Команды и Права

| Команда          | Описание                  | Права (`Permission`) |
|------------------|---------------------------|----------------------|
| `/sizeclock give`| Выдаёт игроку Часы Размера. | `sizeclock.give`     |

---

## 📝 Планы на будущее

- [ ] Конфигурационный файл для настройки стоимости, максимального/минимального размера и т.д.
- [ ] Поддержка разных языков.
- [ ] Возможность создавать разные типы часов с разными параметрами.

---

## ❤️ Поддержка

Если у вас есть идеи по улучшению плагина или вы нашли баг, создайте `Issue` в [этом репозитории](https://github.com/Vanilla-Developers/SizeClock/issues)!

</div>

---
<br>

<!-- Английская версия -->
<div id="-sizeclock-en">

# ⭐ SizeClock ⭐

**SizeClock** is a plugin for Minecraft servers (Spigot/Paper) that adds a unique item to the game: the **Size Clock**. With it, players can change their own size or the size of other creatures using accumulated experience.

The plugin introduces a new mechanic, perfect for RPG servers, events, or just to give players fun new abilities.

---

## 🚀 Features

- **Size-Shifting**: Grow or shrink yourself and other mobs.
- **The Size Clock**: A unique item with a custom texture to control size.
- **Experience System**: Use experience levels to "charge" the clock. Each size change consumes one charge.
- **GUI Menu**: A user-friendly interface to configure the clock:
    - Refill the experience storage.
    - Select the desired size with a convenient slider.
- **Animations and Sounds**: Smooth effects during size changes.
- **Flexibility**: Easy to configure and use.
- **Commands**: A simple command to get the clock.
- **Permissions**: Restrict access to the clock via permissions.

---

## 🎮 How to Use

### 1. Getting the Clock

To get a **Size Clock**, an administrator must use the command:
```
/sizeclock give
```
This requires the `sizeclock.give` permission.

### 2. Charging the Clock

The clock uses experience as "fuel".

1. **Open the menu**: Hold the clock and press **Shift + Right-Click**.
2. **Select amount of experience**: In the top row of the menu, use `+` and `-` to select how many experience levels you want to transfer to the clock.
3. **Infuse experience**: Click the experience bottle to confirm the transfer.

### 3. Changing Size

1. **Select size**: In the same menu (Shift + Right-Click), use the slider in the bottom row to select your desired size.
2. **Apply the effect**:
   - To change **your own size**, hold the clock and **Right-Click**.
   - To change the size of **another creature**, **Right-Click** on it while holding the clock.

A second **Right-Click** will reset the size to default (1.0), also consuming one experience charge.

---

## 🛠️ Installation

1. Download the latest version of the plugin from the [Releases](https://github.com/Vanilla-Developers/SizeClock/releases) section.
2. Place the `.jar` file into your server's `plugins` folder.
3. Restart or start your server.
4. (Optional) Install the resource pack included with the plugin to see the custom clock texture.

---

## ⚙️ Commands & Permissions

| Command          | Description                | Permission         |
|------------------|----------------------------|--------------------|
| `/sizeclock give`| Gives the player a Size Clock. | `sizeclock.give`   |

---

## 📝 Future Plans

- [ ] Configuration file to customize costs, min/max size, etc.
- [ ] Multi-language support.
- [ ] Ability to create different types of clocks with different parameters.

---

## ❤️ Support

If you have ideas for improving the plugin or have found a bug, please create an [Issue](https://github.com/Vanilla-Developers/SizeClock/issues)!

</div>
