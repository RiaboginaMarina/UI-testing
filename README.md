# Diplom_3

UI-тестирование в Google Chrome и Яндекс.Браузере. Тесты написаны с применением паттерна POM.

В проекте используются Java 11, junit 4.13.2, Selenium 4.10.0, Rest-assured 5.3.0, gson 2.10.1.

Отчет о тестировании сделан с помощью Allure 2.24.0.

Запуск тестов в Google Chrome:
```shell
mvn clean test
```
Запуск тестов в Яндекс.Браузере:
```shell
mvn -Dbrowser=yandex test
```
Посмотреть отчет о тестировании:
```shell
mvn allure:serve
```