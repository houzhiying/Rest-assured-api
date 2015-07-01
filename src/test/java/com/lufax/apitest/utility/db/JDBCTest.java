package com.lufax.apitest.utility.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JDBCTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException {
		Connection conn = JDBCTools.getConnection();
		System.out.println(conn);
	}

}
