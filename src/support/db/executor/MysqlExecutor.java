/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db.executor;

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import support.StringAdapter;

/**
 * операции с БД
 *
 * @author obydennaya_office
 */
final class MysqlExecutor implements QueryExecutor {

    private Connection connection;
    public static Double maxQueryTime = 0.00;
    public static String maxQueryText = "";
    /**
     * текст запроса по результату
     *
     * @var String
     */
    private String queryText = "";
    /**
     * время выполнения запроса
     */
    private Double queryTime = 0.00;
    /**
     * массив ошибок
     *
     * @var HashMap<String>
     */
    private List<String> error = new ArrayList();
    /**
     * результат выполнения запроса
     */
    private ResultSet queryResult;

    private Statement st;
    /**
     * результат выплнения запроса
     */
    private List<Row> resultList = new ArrayList();

    /**
     * Возвращает ошибки
     */
    @Override
    public List<String> getError() {
        return error;
    }

    /**
     * Возвращает текст запроса
     */
    @Override
    public String getQueryText() {
        return queryText;
    }

    @Override
    public Double getQueryTime() {
        return queryTime;
    }

    /**
     * возвращает результат запроса в виде ArrayList
     *
     * @return - массив результатов запроса
     * @throws Exception
     */
    @Override
    public List<Row> getResultList() throws Exception {
        if (resultList.isEmpty()) {
            saveResult();
        }
        return resultList;
    }

    static MysqlExecutor getInstance(Connection con, String queryText) {
        return new MysqlExecutor(con, queryText);
    }

    /**
     *
     * @param con объект Connection
     * @param queryText текст SQL-запроса
     */
    private MysqlExecutor(Connection con, String queryText) {
        connection = con;
        this.queryText = queryText;
    }

    /**
     *
     * @return Boolean
     */
    @Override
    public Boolean update() {
        Boolean result = false;
        if (validateQuery() != false) {
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(queryText);
                result = true;
            } catch (Exception e) {
                error.add(StringAdapter.getStackTraceException(e));
            }
        }
        return result;
    }

    @Override
    public Boolean select() {
        Boolean result = false;
        if (validateQuery() != false) {
            try {
                st = connection.createStatement();
                long time1 = Calendar.getInstance().getTime().getTime();
                queryResult = st.executeQuery(queryText);
                long time2 = Calendar.getInstance().getTime().getTime();
                Double diff = (time2 - time1) / 1000.00;
                queryTime = diff;
                if (maxQueryTime < diff) {
                    maxQueryTime = Double.valueOf(diff);
                    maxQueryText = queryText;
                }
                result = true;
            } catch (Exception e) {
                error.add(StringAdapter.getStackTraceException(e) + queryText);
            }
        }
        return result;
    }

    /**
     * Валидирует текст запроса на спец символы
     */
    @Override
    public String checkParamByWords(String param) {
        param = param.replaceAll("select", "");
        param = param.replaceAll("order", "");
        param = param.replaceAll("where", "");
        param = param.replaceAll("from", "");
        param = param.replaceAll("delete", "");
        param = param.replaceAll("and", "");
        param = param.replaceAll("or", "");
        return param;
    }

    Boolean validateQuery() {
        return true;
    }

    @Override
    public Double getMaxQueryTime() {
        return maxQueryTime;
    }

    @Override
    public String getMaxQueryText() {
        return maxQueryText;
    }

    @Override
    public void resetMaxQueryInfo() {
        maxQueryText = "";
        maxQueryTime = 0.00;
    }

    private void saveResult() throws Exception {
        try {
            if (queryResult != null) {
                ResultSetMetaData metaData = queryResult.getMetaData();
                int columnCount = metaData.getColumnCount();
                queryResult.beforeFirst();
                while (queryResult.next()) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        Object value = queryResult.getObject(i);
                        int type = metaData.getColumnType(i);
                        try {
                            if ((type == Types.TIMESTAMP) & !(value == null)) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                value = (Object) format.format(value);
                            }
                        } catch (Exception e) {
                            error.add("error " + value + " " + type);
                            error.add(StringAdapter.getStackTraceException(e));
                        }

                        map.put(columnName, value);
                    }
                    resultList.add(Row.getInstance(map));
                }
            } else {
                error.add("resList is null");
            }
        } catch (SQLException e) {
            throw new Exception("ошибка в объекте QueryExecutor при получении результатов запроса " + e.getMessage());
        } finally {
            if (queryResult != null) {
                queryResult.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }
}
