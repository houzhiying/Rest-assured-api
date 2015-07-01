package com.lufax.apitest.h5;

import static org.hamcrest.Matchers.equalTo;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

import com.jayway.restassured.path.json.JsonPath;

import static com.jayway.restassured.path.json.JsonPath.with;

import java.io.InputStream;

import org.databene.benerator.anno.Source;
import org.junit.Test;

import com.lufax.apitest.helper.common.CommonMethod;

public class VerifyDynamicCodeTest {
	String url = "http://m.lufax.com/m/quickpay/sendDynamicCode";

	@Test
	@Source()
	public void sendDynamicCode_right() {
		System.out.println("发送短信动态码接口:有效用例");
		InputStream jsonschma = Thread.currentThread().getContextClassLoader().getResourceAsStream("json/sendDynamicCode.json");
		given().
			param("mobileToken", CommonMethod.getMobileToken()).
			param("mobileNo",CommonMethod.generateMobileNum()).
		when().
			post(url).
		then().
			statusCode(200).
			body("ok", equalTo(true)).
			body("code", equalTo(0)).
			body("dataMap.resultMsg", equalTo("成功")).
			body("dataMap.resultId", equalTo("00")).
			body(matchesJsonSchema(jsonschma));
		}
	
	@Test
	public void getMobileToken(){
		String response = given().
				param("mobileToken", CommonMethod.getMobileToken()).
				param("mobileNo",CommonMethod.generateMobileNum()).
			when().
				post(url).asString();
		String mobileToken = with(response).getString("dataMap.mobileToken");
		System.out.println(mobileToken);
	}

}
