# KotlinGram

**Модифицированный Telegram-клиент (неофициальный).**
Автор и создатель: Майк Дивемц — https://t.me/MIKEDEVIM
Официальный канал: https://t.me/KotlinGramOfficial

> Неофициальный проект. KotlinGram не имеет никакого отношения к языку Kotlin, JetBrains и Telegram.
> Название Kotlin и талисман Kodee — торговые марки JetBrains s.r.o.
> Изображения талисмана используются с https://kotlinlang.org/docs/kotlin-brand-assets.html#kotlin-logo

Telegram-мод на основе [exteraGram 9.6.6](https://github.com/exteraSquad/exteraGram) (GPLv2).
Лицензия сохраняется: **GNU GPL v2**, исходники KotlinGram обязаны оставаться открытыми.

## Что добавлено
| Файл | Изменение |
|---|---|
| `com/kotlingram/messenger/KotlinConfig.java` | Хранение настроек (SharedPreferences `kotlingram`) |
| `com/kotlingram/messenger/KotlinGramPreferencesActivity.java` | Страница настроек KotlinGram |
| `com/kotlingram/messenger/AboutKotlinGramActivity.java` | Отдельная страница «О KotlinGram» (версия, автор, ссылки, дисклеймер) |
| `MainPreferencesActivity.java` | Пункт «KotlinGram» в настройках мода |
| `MessagesController.java` | Хуки режима призрака (4 места) |
| `DrawerProfileCell.java` | Декоративная звезда Fake Premium в боковом меню |

## Режим призрака (хуки в MessagesController)
- `updateTimerProc` — не отправляется `account.updateStatus(offline=false)`.
- `sendTyping` — не отправляется `messages.setTyping`.
- `completeReadTask` — не отправляется `messages.readHistory` / `channels.readHistory`.
- `markMessageContentAsRead` — не отправляется «прослушано» для голосовых/кружков.

## Fake Premium
Только локальный значок рядом с вашим именем. `UserConfig.isPremium()` НЕ меняется,
серверные Premium-функции не включаются.

## Что ещё нужно перед сборкой
1. Создать `API_KEYS` (свой APP_ID / APP_HASH с my.telegram.org, ключ Maps, данные подписи).
2. Сменить `applicationId`/название приложения в `TMessagesProj/build.gradle` и `strings.xml`,
   чтобы не конфликтовать с exteraGram и Telegram.
3. ~~Заменить `config/extera.jks` на СВОЙ ключ~~ — сделано: `config/kotlingram.jks`.
4. Убрать/заменить `google-services.json` (он принадлежит exteraGram).
5. Обновить ссылки на каналы/GitHub в `MainPreferencesActivity` и апдейтер (иначе мод будет
   предлагать обновления exteraGram).

## Талисман (mascot)
Ресурсы: `res/drawable-nodpi/kg_mascot_{smile,neutral,surprised,worried,cry}.png`,
хелпер `com/kotlingram/messenger/KotlinMascot.java` (enum `Mood`).

| Эмоция | Где используется / можно использовать |
|---|---|
| smile | экран приветствия, иконка приложения, успех, режим призрака выключен |
| surprised | страница интро «Быстрый», «Вау»-моменты, новая функция/обновление |
| neutral | страница «Мощный», режим призрака включён, пустые списки |
| worried | страница «Безопасный», предупреждения, нет соединения |
| cry | ошибки, неудачный вход, «ничего не найдено» |

Реально подключено: интро-экран (вместо анимации самолётика), шапка страницы KotlinGram,
иконка приложения (adaptive + legacy mipmap).

## Сторонние сервисы (отключены)
- **Апдейтер exteraGram вырезан:** удалены `preferences/updater/*` и `utils/UpdaterUtils.java`, убраны вызовы из `LaunchActivity` и `MainPreferencesActivity`.
- **Официальный апдейтер Telegram** (`LaunchActivity.checkAppUpdate`) выключен флагом `KotlinConfig.OFFICIAL_UPDATES_ENABLED = false`.
- **Firebase отключён** флагом `KotlinConfig.FIREBASE_ENABLED = false`: не инициализируются Analytics/Crashlytics,
  FCM-пуши (`hasServices()` = false), Remote Config заменён на DNS TXT. Плагины google-services/crashlytics закомментированы в `build.gradle`,
  `google-services.json` переименован в `google-services.json.disabled`. Зависимости оставлены, чтобы проект компилировался.
- Чтобы включить обратно: положить свой `google-services.json`, раскомментировать плагины, поставить флаг в `true`.
- Без FCM пуш-уведомления работают через собственное фоновое соединение Telegram (Настройки → Уведомления → «Фоновое соединение»).

## Заглушки пакета boost
В публичных исходниках exteraGram нет закрытого пакета `com.exteragram.messenger.boost`, хотя код его импортирует.
Добавлены заглушки (`BoostController`, `EncryptionHelper`, `BaseEncryptor`, `ZalgoFilter`): Boost-функции
(шифрование сообщений, Zalgo-фильтр) в KotlinGram отключены. Строка про `boost/` убрана из `.gitignore`,
иначе заглушки не попадут в репозиторий и облачная сборка упадёт.
