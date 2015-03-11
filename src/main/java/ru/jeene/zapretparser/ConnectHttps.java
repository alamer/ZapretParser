package ru.jeene.zapretparser;

import ru.jeene.zapretparser.controller.HTTPSCheckController;

public class ConnectHttps {

    public static void main(String[] args) throws Exception {
        HTTPSCheckController c = new HTTPSCheckController();
        System.out.print(c.getContent("https://alisa.esrr.oao.rzd:4848"));
    }
}
