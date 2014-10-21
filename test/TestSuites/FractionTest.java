/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TestSuites;

import fraccalc.backEnd.Fraction;
import fraccalc.backEnd.NotAFractionException;

/**
 *
 * @author s-yinb
 */
public class FractionTest {
public void test(){
    try{
            Fraction test1 = new Fraction(1,0,1);
            Fraction test2 = new Fraction(1,0,0);
            Fraction test3 = new Fraction("Hello world");
            Fraction test4 = new Fraction("1_2/3");
            Fraction test5 = new Fraction("1");
            Fraction test6 = new Fraction("2/3");
            Fraction test7 = new Fraction("2/0");
            System.out.println(test1.toString());
            System.out.println(test2.toString());
            System.out.println(test3.toString());
            System.out.println(test4.toString());
            System.out.println(test5.toString());
            System.out.println(test6.toString());
            System.out.println(test7.toString());
    }catch(NotAFractionException e){
        System.out.println(e.reason);
    }finally{
    
}
}
}
