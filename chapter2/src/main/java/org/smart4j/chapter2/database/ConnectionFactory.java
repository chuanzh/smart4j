package org.smart4j.chapter2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropsUtil;

/**
 * 类名：ConnectionFactory
 * 作用：产生数据库连接对象
 * 属性：
 * 方法：Connection getConnection()
 * 作用：返回数据库连接对象
 * 参数：无
 * 返回：数据库连接对象
 * 其它：返回的connection不会自动提交JDBC事务
 *
 **/
public abstract class ConnectionFactory {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(ConnectionFactory.class);
	private static final String DRIVE;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSORD;
	
	/**
	 * 初始化数据
	 */
	static {
		Properties conf = PropsUtil.lodProps("config.properties");
		DRIVE = conf.getProperty("jdbc.driver");

		URL = conf.getProperty("jdbc.url");

		USERNAME = conf.getProperty("jdbc.username");

		PASSORD = conf.getProperty("jdbc.password");
		try {
			Class.forName(DRIVE);
		} catch (ClassNotFoundException e) {
			LOGGER.error("Can not load jdbc drive!",e);
		}
	}
	/**
	 * 获取Connection对象
	 */

	  public static Connection getConnection() throws Exception {
		Connection connection;


		Class.forName(DRIVE).newInstance();

		connection = DriverManager.getConnection(URL,USERNAME,PASSORD); // 创建connection对象

		connection.setAutoCommit(false); // 设置不自动提交事务

		return connection; // 返回connection对象
	}
}
