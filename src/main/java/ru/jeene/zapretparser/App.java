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
        ArrayList<Model_CSV> list = loader.parseCSV();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (Model_CSV stroka : list) {
            Runnable worker = new ZapretCheckWorker(stroka.getUrl());
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
    }

    public static void main(String[] args) {
        app = new App();
    }
}