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
import ru.jeene.zapretparser.models.FullReport;
import ru.jeene.zapretparser.models.Model_CSV;
import ru.jeene.zapretparser.models.Model_FullReport;
import ru.jeene.zapretparser.models.ResponseResult;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class ZapretCheckWorker implements Runnable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ZapretCheckWorker.class);

    private Model_CSV command;
    private FullReport rep;

    public ZapretCheckWorker(Model_CSV s, FullReport rep) {
        this.command = s;
        this.rep = rep;
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
            ResponseResult tmp = controller.checkUrl(command.getUrl());
            Model_FullReport m = new Model_FullReport();
            m.setResult(tmp);
            m.setElement(command);
            rep.putReport(m);
            //logger.info(command + " " + tmp.getDesc());

        } catch (Exception ex) {
            logger.error(command);
            logger.error(ex);
        }
    }

}
