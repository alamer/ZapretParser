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

/**
 *
 * @author ivc_ShherbakovIV
 */
public class URLCheckController {

    public String checkUrl(String url) {
        if (url.contains("https://")) {
            return "https://";
        } else {
            return "http://";
        }
    }

}
