package com.lufax.apitest.helper.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.lufax.apitest.utility.HttpRequestUtil;
import com.lufax.apitest.utility.IdCardGenerator;
import com.lufax.apitest.utility.db.JDBCTools;

public class CommonMethod {
		private static final String mobileAppApi = "http://mobile.lufax.app/mobile-app/service/quick-pay/initial";
	
	/**
	 * 生成11位手机号码
	 * @return String
	 */
	public static String generateMobileNum(){
		String mobileNum = "137" + String.valueOf(10000000 + (int) (Math.random() * 8000000));
		return mobileNum;
	}
	
	/**
	 * 生成18位身份证号码
	 * @return String
	 */
	public static String generateIdCard(){
		String idCard = new IdCardGenerator().generate();
		return idCard;
	}
	
	/**
	 * 生成银行卡号
	 * @return String
	 */
	public static String generateBankCard(){
		String bankAccount = "623058000002195"+(1000+(int)(Math.random()*9000)); 
		return bankAccount;
	}
	
	/**
	 * 获取手机号校验码
	 * @param mobileNum
	 * @return otpCode
	 */
	public static String getDynamicCode(String mobileNum){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String otpCode = null;
		
		try {
			conn = JDBCTools.getConnection();
			String sql = "select otp_code from one_time_password where mobile_no=" + mobileNum + "";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()){
				otpCode = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseBD(rs, ps, conn);
		}
		return otpCode;
	}
	
	/**
	 * 首次获取mobileToken
	 * @return mobileToken
	 */
	public static String getMobileToken(){
		 HttpResponse initResult = HttpRequestUtil.executeGETMethod(mobileAppApi);
         Header[] headers = initResult.getAllHeaders();
         String mobileToken = null;
         for (Header h : headers) {
             if (h.getName().equals("mobileToken")) {
            	 mobileToken = h.getValue();
                 break;
             }
         }
		return mobileToken;
	}
	
}
