# Meter Reading System

Это веб-приложение для управления показаниями счетчиков.

## Основные функции

1. Регистрация и вход в систему:
   - Администратор: может просматривать актуальные показания всех пользователей, просматривать поданные показания за конкретный месяц, просматривать историю поданных показаний конкретного пользователя, добавлять новый вид счетчика
   - Пользователь: может подавать показания, просматривать актуальные показания, просматривать историю поданных показаний за конкретный месяц, просматривать всю историю поданных показаний.

2. Подача показаний счетчиков:
   - Возможность выбора типа счетчика (горячая вода, холодная вода, отопление и добавленные админом типы).
   - Проверка на предыдущие поданные показания в текущем месяце.

3. Хранение данных:
   - Система хранит информацию о пользователях, показаниях счетчиков и аудитах.

## Требования к установке

1. Java Development Kit (JDK) 17 или выше.
2. Spring Boot 3.2.2
3. Docker и Docker Compose для развертывания базы данных в контейнере.

## Запуск приложения

1. Клонируйте репозиторий на свой компьютер.
2. Соберите JAR файл из исходного кода и запустите через класс который имеет метод main и помечен аннотацией @SpringBootApplication.
3. Убедитесь, что Docker и Docker Compose установлены на вашем компьютере.
4. Запустите Docker Compose для развертывания базы данных в контейнере:
   - docker-compose up -d

## Ссылки на ПР:
1. https://github.com/l3egaliev/Y-Lab/pull/2 - HM1
2. https://github.com/l3egaliev/Y-Lab/pull/3 - HM2
3. https://github.com/l3egaliev/Y-Lab/pull/4 - HM3
4. https://github.com/l3egaliev/Y-Lab/pull/5 - HM4
5. https://github.com/l3egaliev/Y-Lab/pull/6 - HM5
## Связь с разработчиком

- [Рахим] - rahimbekabdumomunov@gmail.com
