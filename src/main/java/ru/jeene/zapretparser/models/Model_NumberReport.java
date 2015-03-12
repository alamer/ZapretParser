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

import lombok.Data;

/**
 *
 * @author ivc_ShherbakovIV
 */
@Data
public class Model_NumberReport {

    private int number;
    private double percent;

    public void addNumber(int overall) {
        number++;
        percent = number / overall * 100;
    }

}
