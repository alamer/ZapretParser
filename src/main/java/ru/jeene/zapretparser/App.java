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
package ru.jeene.zapretparser;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ru.jeene.zapretparser.controller.CSVLoadController;
import ru.jeene.zapretparser.controller.XLSXReportController;
import ru.jeene.zapretparser.models.FullReport;
import ru.jeene.zapretparser.models.Model_CSV;
import ru.jeene.zapretparser.worker.ZapretCheckWorker;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class App {

    private static App app;

    public App() {
        CSVLoadController loader = new CSVLoadController();
        String f = loader.loadfile();

        ArrayList<Model_CSV> list = loader.parseCSV(f);
        String timestamp_csv = loader.timestapFromCSV(f);
        FullReport rep = new FullReport();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (Model_CSV stroka : list) {
            Runnable worker = new ZapretCheckWorker(stroka, rep);
            executor.execute(worker);
        }
        /*for (int i = 0; i < 10; i++) {
         Runnable worker = new ZapretCheckWorker("" + i);
         executor.execute(worker);
         }*/
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
        //System.out.println(rep.reportCountBytype());
        XLSXReportController c = new XLSXReportController();
        c.WriteReport(rep,timestamp_csv);
    }

    public static void main(String[] args) {
        app = new App();
    }
}
