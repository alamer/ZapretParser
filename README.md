# ZapretParser
Parser for https://github.com/zapret-info/z-i csv file with url check
Программа проверяет провайдера ДСИ по базе Росреетсра (CSV файл https://github.com/zapret-info/z-i).
Проверка происходит в несколько потоков. По окончанию проверки выдается XLS отчет.
Список параметров командной строки
-t <количество потоков>
-connect <таймаут на соединение в мс>
-read <таймаут на чтение данных в мс>
