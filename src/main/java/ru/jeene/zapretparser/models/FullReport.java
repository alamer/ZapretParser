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
package ru.jeene.zapretparser.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 *
 * @author ivc_ShherbakovIV
 */
@Data
public class FullReport {

    List<Model_FullReport> list;

    /**
     *
     */
    public FullReport() {
        list = Collections.synchronizedList(new ArrayList());
    }

    /**
     *
     * @param m
     */
    public void putReport(Model_FullReport m) {
        list.add(m);
    }

    /**
     *
     * @return
     */
    public String reportNonBlocked() {
        StringBuilder res = new StringBuilder();
        res.append("Отчет по незаблокированным URL\r\n");
        //Iterate over all list to find and count
        return res.toString();
    }

    /**
     * Отчет по всем типам ошибок (количество)
     *
     * @return
     */
    public String reportCountBytype() {
        StringBuilder res = new StringBuilder();
        res.append("Отчет по всем типам ошибок URL\r\n");
        HashMap<ResponseResult, Integer> map = new HashMap<>();
        for (Model_FullReport model_FullReport : list) {
            ResponseResult r = model_FullReport.getResult();            
            if (map.get(r) == null) {
                map.put(r, 0);
            }
            int current = map.get(r);
            map.put(r, current + 1);
        }
        for (Map.Entry<ResponseResult, Integer> entry : map.entrySet()) {
            ResponseResult key = entry.getKey();
            Integer value = entry.getValue();
            res.append(key.getCode()).append("           ").append(key.getDesc()).append("           ").append(value).append("\r\n");
        }
        //Iterate over all list to find and count
        return res.toString();
    }

}
