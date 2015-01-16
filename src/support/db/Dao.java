/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import support.DateAdapter;
import support.StringAdapter;
import support.commons.DAO;
import support.db.executor.ExecutorFabric;
import support.db.executor.QueryExecutor;
import support.db.executor.Row;
import support.db.select.SelectMysql;
import support.enums.ColumnTypes;
import support.enums.DbTypes;
import support.filterValidator.ChainValidator;
import support.filterValidator.entity.ValidatorAbstract;

/**
 *
 * @author Кот
 */
@DAO
public class Dao {

    Connection connection;

    private Dao(Connection connection) {
        this.connection = connection;
    }

    public static Dao getInstance(Connection connection) {
        return new Dao(connection);
    }

    public List<Row> find(Object ob) throws Exception {;
        Table tb = Persistence.getInstance().createTableFromAnnotationObject(ob);
        SelectMysql selectMysql = SelectMysql.getInstance();
        if (selectMysql.findAll(connection, tb)) {
            List<Row> res = selectMysql.getResultList();
            return res;
        } else {
            throw new Exception(StringAdapter.getStringFromList(selectMysql.getError()));
        }
    }

    public Object save(Object ob) throws Exception {
        Object id = null;
        if (ob.getClass().isAnnotationPresent(support.commons.db.Table.class)) {
            support.commons.db.Table tabl = (support.commons.db.Table) ob.getClass().getAnnotation(support.commons.db.Table.class);
            String mysqlQuery = "insert into " + tabl.name() + " set ";
            Field[] fds = ob.getClass().getDeclaredFields();
            int cnt = 0;
            for (Field fd : fds) {
                if (fd.isAnnotationPresent(support.commons.db.Column.class)) {
                    if (!fd.isAnnotationPresent(support.commons.db.Primary.class)) {
                        support.commons.db.Column col = fd.getAnnotation(support.commons.db.Column.class);
                        if (cnt > 0) {
                            mysqlQuery += " ,";
                        }
                        if (fd.get(ob) == null) {
                            mysqlQuery += col.name() + "=NULL";
                        } else {
                            Object value = fd.get(ob);
                            if (col.type().equals(ColumnTypes.DATETIME)) {
                                if (fd.getType().equals(Date.class)) {
                                    value = DateAdapter.getDateInMysql((Date) value);
                                }
                            }
                            mysqlQuery += col.name() + "='" + value + "'";
                        }
                        cnt++;
                    } else {
                        Object value = fd.get(ob);
                        if (value != null) {
                            throw new Exception("Primary key must be null but " + value);
                        }
                    }
                }
            }
            QueryExecutor qe = ExecutorFabric.getExecutor(connection, mysqlQuery, DbTypes.MySQL);
            qe.update();
            if (!qe.getError().isEmpty()) {
                throw new Exception(StringAdapter.getStringFromList(qe.getError()));
            } else {
                QueryExecutor query = ExecutorFabric.getExecutor(connection, "SELECT LAST_INSERT_ID() id", DbTypes.MySQL);
                query.select();
                if (query.getError().isEmpty()) {
                    if (query.getResultList().isEmpty()) {
                        throw new Exception("Insert does not success");
                    } else {
                        id = query.getResultList().get(0).get("id");
                    }
                } else {
                    throw new Exception(StringAdapter.getStringFromList(query.getError()));
                }
            }
        } else {
            throw new Exception("only @Table annotation object resolve");
        }
        return id;
    }

    public void delete(Object ob) throws Exception {
        if (ob.getClass().isAnnotationPresent(support.commons.db.Table.class)) {
            support.commons.db.Table tabl = (support.commons.db.Table) ob.getClass().getAnnotation(support.commons.db.Table.class);
            String mysqlQuery = "delete from " + tabl.name() + " where ";
            Field[] fds = ob.getClass().getDeclaredFields();
            Boolean havePrimary = false;
            for (Field fd : fds) {
                if (fd.isAnnotationPresent(support.commons.db.Column.class)) {
                    if (fd.isAnnotationPresent(support.commons.db.Primary.class)) {
                        havePrimary = true;
                        support.commons.db.Column col = fd.getAnnotation(support.commons.db.Column.class);
                        if (fd.get(ob) != null) {
                            Object value = fd.get(ob);
                            mysqlQuery += col.name() + "='" + value + "'";
                        } else {
                            throw new Exception("Primary key is null");
                        }
                    }
                }
            }
            if (havePrimary == false) {
                int cnt=0;
                for (Field fd : fds) {
                    if (fd.isAnnotationPresent(support.commons.db.Column.class)) {
                        support.commons.db.Column col = fd.getAnnotation(support.commons.db.Column.class);
                        if (fd.get(ob) != null) {
                            Object value = fd.get(ob);
                            if(cnt>0){
                                mysqlQuery +=" , ";
                            }
                                mysqlQuery += col.name() + "='" + value + "'";
                            } 
                        cnt++;
                    }
                }
            }

            QueryExecutor qe = ExecutorFabric.getExecutor(connection, mysqlQuery, DbTypes.MySQL);
            qe.update();
            if (!qe.getError().isEmpty()) {
                throw new Exception(StringAdapter.getStringFromList(qe.getError()));
            }
        } else {
            throw new Exception("only @Table annotation object resolve");
        }
    }

    public void update(Object ob) throws Exception {
        if (ob.getClass().isAnnotationPresent(support.commons.db.Table.class)) {
            support.commons.db.Table tabl = (support.commons.db.Table) ob.getClass().getAnnotation(support.commons.db.Table.class);
            String mysqlQuery = "update " + tabl.name() + " set ";
            String whereCondition = " where ";
            Field[] fds = ob.getClass().getDeclaredFields();
            int cnt = 0;
            for (Field fd : fds) {
                if (fd.isAnnotationPresent(support.commons.db.Column.class)) {
                    if (fd.isAnnotationPresent(support.commons.db.Primary.class)) {
                        support.commons.db.Column col = fd.getAnnotation(support.commons.db.Column.class);
                        if (fd.get(ob) != null) {
                            Object value = fd.get(ob);
                            whereCondition += col.name() + "='" + value + "' ";
                        } else {
                            throw new Exception("Primary key is null");
                        }
                    } else {
                        support.commons.db.Column col = fd.getAnnotation(support.commons.db.Column.class);
                        if (col.isEdit() == true) {
                            if (cnt > 0) {
                                mysqlQuery += ", ";
                            }
                            if (fd.get(ob) != null) {
                                Object value = fd.get(ob);
                                mysqlQuery += col.name() + "='" + value + "' ";
                            } else {
                                Object value = fd.get(ob);
                                mysqlQuery += col.name() + " =NULL ";
                            }
                            cnt++;
                        }
                    }
                }
            }
            mysqlQuery = mysqlQuery + whereCondition;
            QueryExecutor qe = ExecutorFabric.getExecutor(connection, mysqlQuery, DbTypes.MySQL);
            qe.update();
            if (!qe.getError().isEmpty()) {
                throw new Exception(StringAdapter.getStringFromList(qe.getError()) + qe.getQueryText());
            }
        } else {
            throw new Exception("only @Table annotation object resolve");
        }
    }

    public List<Row> findByValues(Object ob) throws Exception {
        List<Row> result = new ArrayList();
        if (ob.getClass().isAnnotationPresent(support.commons.db.Table.class)) {
            support.commons.db.Table tabl = (support.commons.db.Table) ob.getClass().getAnnotation(support.commons.db.Table.class);
            String mysqlQuery = "select * from " + tabl.name() + " where ";
            Field[] fds = ob.getClass().getDeclaredFields();
            int cnt = 0;
            for (Field fd : fds) {
                if (fd.isAnnotationPresent(support.commons.db.Column.class)) {
                    if (!fd.isAnnotationPresent(support.commons.db.Primary.class)) {
                        support.commons.db.Column col = fd.getAnnotation(support.commons.db.Column.class);
                        if (fd.get(ob) != null) {
                            if (cnt > 0) {
                                mysqlQuery += " and ";
                            }
                            mysqlQuery += col.name() + "='" + fd.get(ob) + "'";
                            cnt++;
                        }

                    }
                }
            }
            QueryExecutor qe = ExecutorFabric.getExecutor(connection, mysqlQuery, DbTypes.MySQL);
            qe.select();
            if (!qe.getError().isEmpty()) {
                throw new Exception(StringAdapter.getStringFromList(qe.getError()));
            } else {
                result = qe.getResultList();
            }
        } else {
            throw new Exception("only @Table annotation object resolve");
        }
        return result;
    }

    public Connection getConnection() {
        return connection;
    }
}
