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
package ru.jeene.zapretparser.controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class URLCheckController {

    public String checkUrl(String url) {
        String res = "Unknown";
        if (url.contains("https://")) {
            return "-1";
        } else {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();

                if (responseCode != 200) {
                    res = String.valueOf(responseCode);

                } else {
                    //
                    String current_url = connection.getURL().toString();
                    if (current_url.contains("zapret-info.dsi.ru")) {
                        res = "Blocked";
                    } else {
                        res = "Worked";
                    }
                }
            } catch (UnknownHostException ex) {
                res = "UnknownHost";
            } catch (ConnectException ex) {
                res = "Timeout";
            } catch (IOException ex) {
                System.out.println(ex);
            }
            return res;
        }

    }

}
