/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TestSuites;

import fraccalc.backEnd.Fraction;

/**
 *
 * @author s-yinb
 */
public class FractionTest {
    try{
    Fraction test1 = new Fraction("5_2/3");
    Fraction test2 = new Fraction(4,3,2);
    Fraction test3 = new Fraction("2_3/0");
}catch(NotAFractionException ritika){
    
}
}
