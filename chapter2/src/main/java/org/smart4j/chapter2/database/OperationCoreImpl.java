package org.smart4j.chapter2.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类名：OperationCoreImplements
 * 
 * 作用: 该类实现IOperationCore接口的所有方法
 * 
 * 创建人：
 * 
 */
public class OperationCoreImpl implements IOperationCore {
	public static final Logger LOGGER = LoggerFactory
			.getLogger(OperationCoreImpl.class);
	
	protected Connection connection = null;
	protected Statement statement = null;
	protected PreparedStatement ps =null;
	protected ResultSet resultSet = null;
	protected ResultSetMetaData resultSetMetaData = null;
	protected static OperationCoreImpl operationCoreImpl = null;

	/**
	 * Singleton 即单例(态)模式,用来生成对象唯一实例的方法
	 * 
	 * @return OperationCoreImplements的一个实例
	 * @throws Exception
	 */
	public static OperationCoreImpl createFactory() throws Exception {
		if (operationCoreImpl == null)
			operationCoreImpl = new OperationCoreImpl();
		return operationCoreImpl;
	}

	/** @exception Exception */
	private OperationCoreImpl() throws Exception {
		init();
	}

	/**
	 * 负责初始化Connection连接
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception {
		connection = ConnectionFactory.getConnection();
	}

	/**
	 * 释放系统连接资源 <br>
	 * 一旦关闭,数据库的操作将全部无效
	 */
	public void dispose() {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException e) {
			LOGGER.error("dispose resultSet fail",e);
			e.printStackTrace();
		}
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
			LOGGER.error("dispose statement fail",e);
			e.printStackTrace();
		}
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			LOGGER.error("dispose connection fail",e);
			e.printStackTrace();
		}

	}

	/**
	 * statement sql查询语句
	 * 
	 * @param queryString查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * 
	 * @exception SQLException
	 */
	public ResultSet executeQuery(String queryString) {
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(queryString);
		} catch (SQLException e) {
			resultSet = null;
			LOGGER.error("executeQuery "+queryString+" fail",e);
			e.printStackTrace();
		}
		return resultSet;
	}


	/**
	 * PreparedStatement sql查询语句
	 * 
	 * @param queryString查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * 
	 * @exception SQLException
	 */
	public ResultSet executePreparedStatementQuery(String queryString) {
		try {
			ps = connection.prepareStatement(queryString);
			resultSet = ps.executeQuery();
		} catch (SQLException e) {
			resultSet = null;
			LOGGER.error("method executePreparedStatementQuery executeQuery "+queryString+" fail",e);
			e.printStackTrace();
		}
		return resultSet;
	}
	/**
	 * sql更新语句
	 * 
	 * @param updateString
	 *            数据库更新语句
	 * @return 更新数据库影响行数
	 * 
	 * @exception SQLException
	 */
	public int executeUpdate(String updateString) {
		int effectedRows = 0;
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			effectedRows = statement.executeUpdate(updateString);
			connection.commit();
		} catch (SQLException ex) {
			//System.out.println("数据库写操作失败!");
			LOGGER.error("method executeUpdate executeQuery "+updateString+" fail",ex);
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					LOGGER.error("method executeUpdate executeQuery "+updateString+" fail and jdbc trans fail",e);
//					System.out.println("JDBC事务回滚失败");
					e.printStackTrace();
				}
			}
		}
		return effectedRows;
	}

	/**
	 * 读取queryString查询结果集<code>ResultSet</code>表中的所有列名
	 * 
	 * @param queryString
	 *            用于返回<code>ResultSet</code>结果集的语句
	 * @return 表中的所有列名
	 * @throws SQLException
	 */
	public Collection<String> getColumnNames(String queryString) {

		ArrayList<String> ColumnNames = new ArrayList<String>();
		try {
			resultSet = executeQuery(queryString);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int j = resultSetMetaData.getColumnCount();
			for (int k = 0; k < j; k++) {
				ColumnNames.add(resultSetMetaData.getColumnName(k + 1));
			}
		} catch (SQLException e) {
			ColumnNames = null;
			LOGGER.error("method getColumnNames executeQuery "+queryString+" fail",e);
			e.printStackTrace();
		}
		return ColumnNames;
	}

	/**
	 * 读取queryString查询结果集<code>ResultSet</code>表中的所有字段类型名称
	 * 
	 * @param queryString
	 *            用于返回查询结果集的语句
	 * @return 表中的所有字段类型名称
	 * @throws SQLException
	 */
	public Collection<String> getColumnTypeNames(String queryString) {

		ArrayList<String> ColumnNames = new ArrayList<String>();
		try {
			resultSet = executeQuery(queryString);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int j = resultSetMetaData.getColumnCount();
			for (int k = 0; k < j; k++) {
				ColumnNames.add(resultSetMetaData.getColumnTypeName(k + 1));
			}
		} catch (SQLException e) {
			ColumnNames = null;
			LOGGER.error("method getColumnTypeNames executeQuery "+queryString+" fail",e);
			e.printStackTrace();
		}
		return ColumnNames;
	}

	/**
	 * 读取列名
	 * 
	 * @param columIndex
	 *            列索引
	 * @param queryString
	 *            提供ResultSet二维表的查询字符串
	 * @return ResultSet表中的指定的列名
	 * 
	 * @exception SQLException
	 */
	public String getColumnName(int columIndex, String queryString) {
		String columnName = null;
		try {
			resultSet = executeQuery(queryString);
			resultSetMetaData = resultSet.getMetaData();
			columnName = resultSetMetaData.getColumnName(columIndex + 1);
		} catch (SQLException e) {
			LOGGER.error("getColumnTypeNames getColumnName executeQuery "+queryString+" fail",e);
			e.printStackTrace();
		}
		return columnName;
	}

	/**
	 * 读取列数个数
	 * 
	 * @param queryString
	 *            查询语句
	 * @return Transact-SQL 查询后的虚拟表的列数
	 * 
	 * @exception SQLException
	 */
	public int getColumnCount(String queryString) {
		int columnCount = 0;
		try {
			resultSet = executeQuery(queryString);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			columnCount = resultSetMetaData.getColumnCount();
		} catch (SQLException e) {
			LOGGER.error("getColumnTypeNames getColumnCount executeQuery "+queryString+" fail",e);
			e.printStackTrace();
		}
		return columnCount;
	}

	/**
	 * 读取行个数
	 * 
	 * @param queryString
	 *            查询语句
	 * @return Transact-SQL 查询后的虚拟表的行数
	 * 
	 * @exception SQLException
	 */
	public int getRowCount(String queryString) {
		int rowCount = 0;
		try {
			resultSet = executeQuery(queryString);
			while (resultSet.next())
				rowCount = resultSet.getInt(1);
		} catch (SQLException e) {
			LOGGER.error("getColumnTypeNames getRowCount executeQuery "+queryString+" fail",e);
			e.printStackTrace();
		}
		return rowCount;
	}

	/**
	 * 获取ResultSet二维表中指定位置的值,目前只支持mysql
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param columnIndex
	 *            列索引
	 * @param queryString
	 *            产生一个ResultSet结果集的查询语句
	 * @return 指定位置的数据记录
	 * 
	 * @exception SQLException
	 */
	public Object getValueAt(int rowIndex, int columnIndex, String queryString) {
		Object values = null;
		try {
			resultSet = executeQuery(queryString);
			// 指针下移一行
			resultSet.absolute(rowIndex + 1);
			values = resultSet.getObject(columnIndex + 1);
		} catch (SQLException e) {
			LOGGER.error("getColumnTypeNames getValueAt executeQuery "+queryString+" fail",e);
			e.printStackTrace();
		}
		return values;
	}

}
