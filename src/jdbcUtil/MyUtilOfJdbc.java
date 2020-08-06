package jdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author jiangzl
 */
public class MyUtilOfJdbc {
    private Connection connection;
    private Statement statement;

    static String databaseOfDriver   = "com.mysql.jdbc.Driver";
    static String databaseOfUrl      = "jdbc:mysql://localhost:3306/mywechat?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC";
    static String databaseOfUsername = "root";
    static String databaseOfPassword = "jzl123456";

    public MyUtilOfJdbc(String dbName){
        try {
            Class.forName(databaseOfDriver);
            connection = DriverManager.getConnection(databaseOfUrl,
                    databaseOfUsername, databaseOfPassword);
            String sqlUseDB = "use " + dbName + ";";
            statement = connection.createStatement();
            statement.execute(sqlUseDB);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 功能：从 tableName 里查询 eleOfSelect，并且把查询的结果返回
     * @param eleOfSelect
     * @param tableName
     * @param whereBy
     * @return HashMap
     * @throws SQLException
     */
    public HashMap<String, String> selectFromTable(String[] eleOfSelect, String tableName, HashMap<String, String> whereBy) throws SQLException {
        HashMap<String, String> ret = new HashMap<>(1);
        StringBuffer sqlSelect = new StringBuffer("select ");
        sqlSelect.append(eleOfSelect[0]);
        // select ele1, ele2, ele3 from tableName
        sqlSelect = sqlSelect.append(" from ");
        sqlSelect = sqlSelect.append(tableName + " where ");
        // where 'where1' = where1 , 'where2' = where2
        Iterator<String> iterator = whereBy.keySet().iterator();
        if (iterator.hasNext()){
            String key = iterator.next();
            String var = whereBy.get(key);
            sqlSelect = sqlSelect.append(key + " = " + "'" + var + "';");
        }
        // generate the ret
        ResultSet resultSet = this.getStatement().executeQuery(sqlSelect.toString());
        if(resultSet.next()){
            ret.put(eleOfSelect[0], resultSet.getString(eleOfSelect[0]));
        }
        return ret;
    }

    /**
     * 功能：插入信息到对应的表中
     * @param intoVar
     * @param tableName
     * @param valueVar
     * @throws SQLException
     */
    public boolean insertIntoTable(String[] intoVar, String tableName, ArrayList<String> valueVar) throws SQLException {
        boolean ret = false;
        // 生成插入表的 sql 语句
        StringBuffer sqlInsert = new StringBuffer("insert into ");
        sqlInsert.append(tableName);
        sqlInsert.append(" (");
        int len = intoVar.length;
        for(int i = 0;i < len;++i){
            sqlInsert.append(intoVar[i]);
            if(i != len - 1) {
                sqlInsert.append(", ");
            }
            else{
                sqlInsert.append(") values (");
            }
        }
        Iterator<String> iterator = valueVar.iterator();
        while (iterator.hasNext()){
            sqlInsert.append("'");
            sqlInsert.append(iterator.next());
            if(iterator.hasNext()){
                sqlInsert.append("', ");
            }
            else{
                sqlInsert.append("');");
            }
        }
        // 执行插入的sql语句，并返回结果
        try {
            int resultOfSql = getStatement().executeUpdate(sqlInsert.toString());
            ret = resultOfSql == 1 ? true : false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public void exitJdbcSystem(){
        try {
            if(null != connection){
                connection.close();
            }
            if(null != statement){
                statement.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

}
