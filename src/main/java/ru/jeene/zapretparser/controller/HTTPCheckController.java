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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import ru.jeene.zapretparser.models.ResponseResult;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class HTTPCheckController {

    public ResponseResult checkUrl(String url_string) {
        ResponseResult res = ResponseResult.UNKNOWN;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url_string).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                res = ResponseResult.ERROR_NOT_SET;

            } else {
                //
                String current_url = connection.getURL().toString();
                if (!current_url.contains("zapret-info.dsi.ru") && connection.getContentLength() > 0) {
                    res = ResponseResult.WORKED;
                } else {
                    res = ResponseResult.BLOCKED;
                }
            }
        } catch (UnknownHostException ex) {
            res = ResponseResult.UNKNOWN_HOST;
        } catch (SocketTimeoutException | ConnectException ex) {
            res = ResponseResult.TIMEOUT;
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return res;
    }

}
