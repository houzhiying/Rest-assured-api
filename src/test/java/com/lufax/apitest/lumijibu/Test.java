package com.lufax.apitest.lumijibu;

public class Test {

	public static void main(String[] args) {
		LumijibuTest lumijibuTest = new LumijibuTest();
		for (int i = 0;i <= 30;i ++){
			lumijibuTest.insertHistoryRecord();
		}
		System.out.println("insert over!");
	}

}
