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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
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
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(App.class);
    @Option(name = "-t", usage = "Sets number of threads")        // no usage
    private int THREAD_NUMBER = 10;

    @Option(name = "-connect", usage = "Ms to connect")        // no usage
    private int TIMEOUT_CONNECT = 3000;
    @Option(name = "-read", usage = "Ms to read")        // no usage
    private int TIMEOUT_READ = 3000;

    public App(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException ex) {
            logger.error(ex);
        }
        logger.info("Loading CSV file....");
        CSVLoadController loader = new CSVLoadController();
        logger.info("Complete....");
        String f = loader.loadfile();
        logger.info("Parsing CSV....");
        ArrayList<Model_CSV> list = loader.parseCSV(f);
        logger.info("CSV file number of urls: " + list.size());
        logger.info("Complete....");
        String timestamp_csv = loader.timestapFromCSV(f);
        logger.info("CSV file date: " + timestamp_csv);
        FullReport rep = new FullReport();

        logger.info("Thread number: " + THREAD_NUMBER);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);
        logger.info("Working....");
        for (Model_CSV stroka : list) {
            Runnable worker = new ZapretCheckWorker(stroka, rep,TIMEOUT_CONNECT,TIMEOUT_READ);
            executor.execute(worker);
        }
        /*for (int i = 0; i < 10; i++) {
         Runnable worker = new ZapretCheckWorker("" + i);
         executor.execute(worker);
         }*/
        executor.shutdown();
        while (!executor.isTerminated()) {
            System.out.print("\rThinking... " + rep.getList().size() + " of " + list.size());
            System.out.flush();

        }
        logger.info("Finished all threads");
        //System.out.println(rep.reportCountBytype());
        XLSXReportController c = new XLSXReportController();
        logger.info("Generating reports...");
        c.WriteReport(rep, timestamp_csv);
        logger.info("Complete....");
    }

    public static void main(String[] args) {

        app = new App(args);
    }
}
