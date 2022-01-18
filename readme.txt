[![asciicast](https://asciinema.org/a/113463.png)](https://github.com/nmochalova/JavaAppium/blob/master/video/mobile_web.webm)

!!!! Это проект, который собирается при помощи maven и настроен в Jenkins !!!!

Запуск тестов из консоли:
1) перейти в директорию maven, где лежит pom.xml
2) установить переменную среды set PLATFORM=android
3) запустить Appium Server
4) запустить эмулятор
5) в командной строке запустить конкретный тест: mvn -Dtest=ArticleTests#testCompareArticleTitle test
Все тесты: mvn -Dtest=TestSuite test
Один класс: mvn -Dtest=ArticleTests test
6) запуск отчетов: allure serve C:\Work\Git\JavaAppium\target\allure-results\

Запуск тестов из Jenkins
1) Jenkins поднят на http://localhost:8080/ (admin/admin)
2) Предварительно запустить службу Jenkins в службах windows вручную (под пользователем Nataly)

Документация Allure: https://docs.qameta.io/allure/#_about
Параллелизация: https://medium.com/@setpace/parallel-execution-in-appium-1-7-dc60c4802082
wikipedia мобильная версия: https://en.m.wikipedia.org/wiki/Main_Page
Аргументы Appium: https://appium.io/docs/en/writing-running-appium/server-args/
Appium: https://github.com/appium/appium-desktop/releases/tag/v1.20.2
Appium Desired Capabilities: http://appium.io/docs/en/writing-running-appium/caps/


