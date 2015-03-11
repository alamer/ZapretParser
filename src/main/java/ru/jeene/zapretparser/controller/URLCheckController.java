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

    public ResponseResult checkUrl(String url) {
        ResponseResult res = ResponseResult.UNKNOWN;
        if (url.contains("https://")) {
            HTTPSCheckController c = new HTTPSCheckController();
            return c.checkUrl(url);
        } else {
            HTTPCheckController c = new HTTPCheckController();

            return c.checkUrl(url);

        }

    }

}
