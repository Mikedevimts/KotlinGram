# Сборка KotlinGram в облаке (GitHub Actions)

## 1. Подготовка (на ноутбуке, интернет не нужен)
Закодируйте ключ подписи в base64:
- Windows (PowerShell): `[Convert]::ToBase64String([IO.File]::ReadAllBytes("kotlingram.jks")) | Set-Clipboard`
- Linux/macOS: `base64 -w0 kotlingram.jks`

Результат — одна длинная строка, это значение секрета `KEYSTORE_B64`.

## 2. Репозиторий
1. На github.com создайте **приватный** репозиторий.
2. Загрузите в него проект (без `API_KEYS` и `*.jks` — они в `.gitignore`).
3. Положите файл `.github/workflows/build.yml` из этого архива в репозиторий.

## 3. Секреты
Settings → Secrets and variables → Actions → New repository secret:

| Имя | Значение |
|---|---|
| APP_ID | api_id с my.telegram.org |
| APP_HASH | api_hash с my.telegram.org (без кавычек) |
| MAPS_V2_API | можно не создавать |
| SIGNING_KEY_STORE_PASSWORD | пароль хранилища |
| SIGNING_KEY_ALIAS | kotlingram |
| SIGNING_KEY_PASSWORD | пароль ключа |
| KEYSTORE_B64 | строка из шага 1 |

## 4. Запуск
Вкладка Actions → Build KotlinGram → Run workflow → flavor `arm64` → Run.
Через ~30–60 минут в конце страницы запуска появится артефакт **KotlinGram-apk** — скачайте и установите.
