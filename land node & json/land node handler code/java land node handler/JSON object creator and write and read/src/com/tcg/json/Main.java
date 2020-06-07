package com.tcg.json;

import org.json.JSONObject;

public class Main {

	public static void main(String[] args) {
		JSONObject obj = JSONUtils.getJSONObjectFromFile("/withdrawRequest.json");
		String[] names = JSONObject.getNames(obj);
		for(String string : names) {
			System.out.println(string + ": " + obj.get(string));
		}
		
		System.out.println("\n");
		
		JSONObject editBody = obj.getJSONObject("body");
		editBody.put("account", "91142069");
		editBody.put("pin","5314");
		editBody.put("amount", 26.95);
		
		for(String string : names) {
			System.out.println(string + ": " + obj.get(string));
		}
		
		
	}

}
