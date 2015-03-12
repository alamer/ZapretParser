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

import ru.jeene.zapretparser.models.ResponseResult;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class URLCheckController {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(URLCheckController.class);

    public ResponseResult checkUrl(String url,int connect,int read) {
        ResponseResult res = ResponseResult.UNKNOWN;
        if (url.contains("https://")) {
            HTTPSCheckController c = new HTTPSCheckController(connect,read);
            return c.checkUrl(url);
        } else {
            HTTPCheckController c = new HTTPCheckController(connect,read);

            return c.checkUrl(url);

        }

    }

}
