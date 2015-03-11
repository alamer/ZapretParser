/*
 $Author$
 $Date$
 $Revision$
 $Source$
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.jeene.zapretparser.worker;

import ru.jeene.zapretparser.controller.URLCheckController;
import ru.jeene.zapretparser.models.ResponseResult;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class ZapretCheckWorker implements Runnable {

    private String command;

    public ZapretCheckWorker(String s) {
        this.command = s;
    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getName() + " Start. Command=" + command);
        processCommand();
        //System.out.println(Thread.currentThread().getName() + " End.");
    }

    private void processCommand() {
        try {
            //Thread.sleep(500);
            URLCheckController controller = new URLCheckController();
            ResponseResult tmp = controller.checkUrl(command);
            if (!"-1".equals(tmp)) {
                System.out.println(command + " " + tmp.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.command;
    }

}
