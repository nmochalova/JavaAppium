!!!! Это проект, который собирается при помощи maven и настроен в Jenkins !!!!


Запуск тестов из консоли:
1) перейти в директорию maven, где лежит pom.xml
2) установить переменную среды set PLATFORM=android
3) запустить Appium Server
4) запустить эмулятор
5) в командной строке запустить тест mvn -Dtest=ArticleTests#testCompareArticleTitle test