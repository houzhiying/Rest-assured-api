package com.lufax.apitest.helper.common;

public class TestMethod {
	public static void main(String[] args){
//		String mobileNum = CommonMethod.generateMobileNum();
//		String idCard = CommonMethod.generateIdCard();
//		String bankCard = CommonMethod.generateBankCard();
		String otpCode = CommonMethod.getDynamicCode("13624580789");
		String mobileToken = CommonMethod.getMobileToken();
//		System.out.println(mobileNum);
//		System.out.println(idCard);
//		System.out.println(bankCard);
		System.out.println(mobileToken);
	}

}
