package com.application.ProgramX;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringApplicationTests {
	@Autowired
	DataSource dataSource;
	@Test
	void contextLoads() throws SQLException {
		Connection conn=dataSource.getConnection();
		Assertions.assertThat(conn).isNotNull();
	}

}
