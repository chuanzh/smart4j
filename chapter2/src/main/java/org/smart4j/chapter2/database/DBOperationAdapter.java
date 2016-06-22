package org.smart4j.chapter2.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 类名：DBOperationAdapter 作用: 该类中的所有方法对用户透明,提供数据库操作的常用方法
 * 
 * 说明：该类使用了Adapter模式与 Singleton模式, 类自身为Adpater,OperationCoreImplements为Adapte类;
 * 实例化类自生对象的时候用到了Singleton模式, 即DBOperationAdapter.getInstance()
 * 
 * 创建人：
 */
public class DBOperationAdapter extends ConnectionFactory {
	private static IOperationCore operationCoreImpl = null;
	private static DBOperationAdapter dbOperationAdapter = null;

	private DBOperationAdapter() {
		try {
			operationCoreImpl = OperationCoreImpl.createFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DBOperationAdapter getInstance() {
		if (dbOperationAdapter == null)
			dbOperationAdapter = new DBOperationAdapter();
		return dbOperationAdapter;
	}

	/**
	 * statement sql查询语句
	 * 
	 * @param queryString查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * 
	 * @exception SQLException
	 */
	public ResultSet executeQuery(String queryString) throws SQLException {
		return operationCoreImpl.executeQuery(queryString);
	}
	/**
	 * PreparedStatement sql查询语句
	 * 
	 * @param queryString查询语句
	 * @return 返回一个<code>ResultSet</code>结果集
	 * 
	 * @exception SQLException
	 */
	public ResultSet executePreparedStatementQuery(String queryString) throws SQLException {
		return operationCoreImpl.executePreparedStatementQuery(queryString);
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
	public int executeUpdate(String updateString) throws SQLException {
		return operationCoreImpl.executeUpdate(updateString);
	}

	/**
	 * sql删除语句:updateString
	 * 
	 * @param deleteString
	 *            数据库插入语句
	 * @return 删除数据影响行数
	 * 
	 * @exception SQLException
	 */
	public int executeDelete(String deleteString) throws SQLException {
		return operationCoreImpl.executeUpdate(deleteString);
	}

	/**
	 * sql插入语句:insertString
	 * 
	 * @param insertString
	 *            数据库插入语句
	 * @return 插入数据影响行数
	 * 
	 * @exception SQLException
	 */
	public int executeInsert(String insertString) throws SQLException {
		return operationCoreImpl.executeUpdate(insertString);
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
	public int getRowCount(String queryString) throws SQLException {
		return operationCoreImpl.getRowCount(queryString);
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
	public int getColumnCount(String queryString) throws SQLException {
		return operationCoreImpl.getColumnCount(queryString);
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
	public String getColumnName(int columIndex, String queryString)
			throws SQLException {
		return operationCoreImpl.getColumnName(columIndex, queryString);
	}

	/**
	 * 读取queryString查询结果集<code>ResultSet</code>表中的所有字段类型名称
	 * 
	 * @param queryString
	 *            用于返回查询结果集的语句
	 * @return 表中的所有字段类型名称
	 * @throws SQLException
	 */
	public Collection<?> getColumnTypeNames(String queryString)
			throws SQLException {
		return operationCoreImpl.getColumnTypeNames(queryString);
	}

	/**
	 * 读取queryString查询结果集<code>ResultSet</code>表中的所有列名
	 * 
	 * @param queryString
	 *            用于返回<code>ResultSet</code>结果集的语句
	 * @return 表中的所有列名
	 * @throws SQLException
	 */
	public Collection<String> getColumnNames(String queryString)
			throws SQLException {
		return operationCoreImpl.getColumnNames(queryString);
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
	public Object getValueAt(int rowIndex, int columnIndex, String queryString)
			throws SQLException {
		return operationCoreImpl.getValueAt(rowIndex, columnIndex, queryString);
	}

	/**
	 * 释放系统连接资源 <br>
	 * 一旦关闭,数据库的操作将全部无效
	 * 
	 * @exception SQLException
	 *                如果关闭失败将抛出<code>SQLException</code>
	 */
	public void dispose() throws SQLException {
		operationCoreImpl.dispose();
	}
	/**
	 * 根据条件组织查询是传入的sql语句
	 * 
	 */
	public String chooseSQL(String start_time,String end_time,String tableName){
		String querysql = "";
		if ((start_time == null || start_time.equals(""))&& end_time != null && !end_time.equals("")){
			querysql="select * from "+tableName+" where update_time<to_date('"+end_time+"','YYYY-MM-DD HH24:MI:SS')";
		}else if((end_time == null || end_time.equals(""))&& start_time != null && !start_time.equals("")){
			querysql="select * from "+tableName+" where update_time>to_date('"+start_time+"','YYYY-MM-DD HH24:MI:SS')";
		}else if(start_time != null && !start_time.equals("") && end_time != null && !end_time.equals("")){
			querysql="select * from "+tableName+" where update_time>to_date('"+start_time+"','YYYY-MM-DD HH24:MI:SS') and update_time<to_date('"+end_time+"','YYYY-MM-DD HH24:MI:SS')";
		} else {
			querysql="select * from "+tableName; 
		}
		System.out.println(querysql);
		return querysql;
		
	}
	/**
	 * 根据条件组织查询是传入的sql语句
	 * 
	 */
	public String chooseSQL(String start_time,String end_time,String dict_type,String tableName){
		String querysql = "";
         if(dict_type==null||dict_type.equals("")){
        	 querysql = chooseSQL(start_time, end_time,tableName);
         }else{
        	 querysql = chooseSQL(start_time, end_time,tableName);
        	 querysql=querysql+" and dict_type="+dict_type;
         }
		System.out.println(querysql);
		return querysql;
		
	}
}
