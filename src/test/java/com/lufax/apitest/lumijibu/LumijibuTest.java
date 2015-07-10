package com.lufax.apitest.lumijibu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Random;

import com.lufax.apitest.utility.GetDate;
import com.lufax.apitest.utility.db.DAO;
import com.lufax.apitest.utility.db.JDBCTools;

public class LumijibuTest {
	public LumijibuTest(){
		
	}
	
	public void insertHistoryRecord(){
		Connection conn = null;
		PreparedStatement ps = null;
		Random random = new Random();
		
		try {
			conn = JDBCTools.getConnection();
			String user_id = "588244";
			int steps = random.nextInt(3000);
			int awards = (steps < 1000)?10:20;
			String award_type = "LUMI";
			int days = random.nextInt(100);
			days = -days;
			String award_date = GetDate.getDate(days);
			String created_at = GetDate.getDate(days);
			String created_by = "sys";
			String updated_by = "sys";
			String updated_at = GetDate.getDate(days);
			Object args[] = new Object[9];
			String sql = "insert into game_lumi_sports_history(user_id,steps,awards,award_type,award_date,created_by,created_at,updated_by,updated_at) value(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			for (int i = 0;i < args.length;i ++){
				ps.setObject(1, user_id);
				ps.setObject(2, steps);
				ps.setObject(3, awards);
				ps.setObject(4, award_type);
				ps.setObject(5, award_date);
				ps.setObject(6, created_by);
				ps.setObject(7, created_at);
				ps.setObject(8, updated_by);
				ps.setObject(9, updated_at);
			}
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseBD(ps, conn);
		}
	}

}
