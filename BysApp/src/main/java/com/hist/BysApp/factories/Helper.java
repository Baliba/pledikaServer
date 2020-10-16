/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.factories;

/**
 *
 * @author User
 */
public class Helper {
    public static String generateCode(String n, String p,Long id){
        String code = n.charAt(0) +""+p.charAt(0)+"-"+(id+1002);
        code = code.toUpperCase();
        return  code;       
    }

	public static String generateCode(String code, Long nu) {
		// TODO Auto-generated method stub
		return null;
	}
}
