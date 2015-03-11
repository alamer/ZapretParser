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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import ru.jeene.zapretparser.models.Model_CSV;
import ru.jeene.zapretparser.utils.FileUtils;
import ru.jeene.zapretparser.utils.HTTPUtils;

/**
 *
 * @author ivc_ShherbakovIV
 */
public class CSVLoadController {

    private String loadfile() {
        //return FileUtils.readPage("D:/dump.csv");
        HTTPSCheckController c = new HTTPSCheckController();
        try {
            return c.getContent("https://raw.githubusercontent.com:443/zapret-info/z-i/master/dump.csv");
//ldPage("raw.githubusercontent.com", 443, "/zapret-info/z-i/master/dump.csv");
        } catch (KeyManagementException ex) {
            Logger.getLogger(CSVLoadController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CSVLoadController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVLoadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public ArrayList<Model_CSV> parseCSV() {
        String file = loadfile();
        ArrayList<Model_CSV> res = new ArrayList<>();
        res.clear();
        try {
            Pattern regex = Pattern.compile("^([^;]+);([^;]+);(.+);([^;]+);([^;]+);([^;]+)$", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.MULTILINE);
            Matcher regexMatcher = regex.matcher(file);
            while (regexMatcher.find()) {
                //System.out.println(regexMatcher.group(1));
                Model_CSV m = new Model_CSV();
                String[] asset_url = regexMatcher.group(3).split("\\|");
                for (String string : asset_url) {
                    String[] asset = regexMatcher.group(1).split("\\|");
                    List<String> assetList = new ArrayList();
                    Collections.addAll(assetList, asset);
                    m.setIp((ArrayList<String>) assetList);
                    m.setDomain(regexMatcher.group(2));
                    m.setUrl(string);
                    m.setOrg(regexMatcher.group(4));
                    m.setDoc(regexMatcher.group(5));
                    m.setDate(regexMatcher.group(6));
                    res.add(m);
                }

                //System.out.println(m.getOrg());
            }
            System.out.println("Считано строк: " + res.size());
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }
        return res;
    }

}
