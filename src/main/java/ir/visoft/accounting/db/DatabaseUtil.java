package ir.visoft.accounting.db;

import ir.visoft.accounting.annotation.EntityField;
import ir.visoft.accounting.entity.BaseEntity;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Amir
 */
public class DatabaseUtil {

    private static Logger log = Logger.getLogger(DatabaseUtil.class.getName());


    public static <T> List<T> getEntity(BaseEntity entity)
            throws SQLException, NoSuchMethodException, NoSuchFieldException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        String query = "SELECT * FROM " + entity.getClass().getSimpleName() + " WHERE ";

        Set<Field> entityFields = findFields(entity.getClass(), EntityField.class);
        for (Field entityField : entityFields) {
            entityField.getName();

            Method getter = null;
            try {
                getter = entity.getClass().getMethod(getGetterMethodName(entityField.getName()));
                Object value = getter.invoke(entity);
                if(value != null && !value.toString().equals("")) {
                    query += getDBFieldName(entityField.getName()) + "='" + getter.invoke(entity) + "' AND ";
                }
            } catch (NoSuchMethodException e) {
                log.error("Problem in database querying...");
                log.error(e.getMessage());
            } catch (InvocationTargetException e) {
                log.error("Problem in database querying...");
                log.error(e.getMessage());
            } catch (IllegalAccessException e) {
                log.error("Problem in database querying...");
                log.error(e.getMessage());
            }
        }
        query = query.substring(0, query.length() - 4);

        return getResult(query, entity);
    }


    public static <T> List<T> getAll(BaseEntity entity) {
        String query = "SELECT * FROM " + entity.getClass().getSimpleName();
        return getResult(query, entity);
    }


    public static Set<Field> findFields(Class<?> clazz, Class<? extends Annotation> annotation) {
        Set<Field> set = new HashSet<Field>();
        Class<?> c = clazz;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(annotation)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }


    public static <T> List<T> getResult(String query, BaseEntity entity) {
        List<Map<String, Object>> rawResults = null;
        List<T> results = new ArrayList<T>();
        try {
            rawResults = runQuery(query);
            for (Map<String, Object> rawResult : rawResults) {
                T result = (T)entity.getClass().getConstructor().newInstance();
                for (String columnName : rawResult.keySet()) {
                    String fieldName = getEntityFieldName(columnName);
                    Method setter = entity.getClass().getMethod(getSetterMethodName(fieldName),
                            entity.getClass().getDeclaredField(fieldName).getType());
                    Object value = rawResult.get(columnName);
                    setter.invoke(result, value);
                }
                results.add(result);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage());
        } catch (InstantiationException e) {
            log.error(e.getMessage());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        } catch (InvocationTargetException e) {
            log.error(e.getMessage());
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage());
        }
        return results;
    }

    public static List<Map<String, Object>> runQuery(String query) throws SQLException {
        QueryRunner run = new QueryRunner();

        ResultSetHandler<List<Map<String, Object>>> h = new ResultSetHandler<List<Map<String, Object>>>() {
            public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {

                List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    ResultSetMetaData meta = rs.getMetaData();
                    int columnCount = meta.getColumnCount();

                    for (int i = 0; i < columnCount; i++) {
                        row.put(meta.getColumnName(i + 1), rs.getObject(i + 1));
                    }

                    rows.add(row);
                }

                return rows;
            }
        };


        Connection connection = Database.getConnection();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if(connection != null) {
            result = run.query(connection, query, h);
        }
        return result;
    }



    private static String getGetterMethodName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
    }

    private static String getSetterMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
    }

    private static String getEntityFieldName(String dbFieldName) {
        String entityFieldName = "";
        String[] fieldParts = dbFieldName.split("_");
        int i = 0;
        for (String fieldPart : fieldParts) {
            if(i == 0) {
                entityFieldName += fieldPart.toLowerCase();
            } else {
                String tempPart = fieldPart.toLowerCase();
                entityFieldName += tempPart.substring(0, 1).toUpperCase() + tempPart.substring(1, tempPart.length());
            }
            i++;
        }
        return entityFieldName;
    }

    private static String getDBFieldName(String entityFieldName) {
        String dbFieldName = "";
        if(entityFieldName.equals(entityFieldName.toLowerCase())) {
            dbFieldName = entityFieldName;
        } else {
            String[] fieldParts = entityFieldName.split("(?=\\p{Upper})");
            for (String fieldPart : fieldParts) {
                dbFieldName += fieldPart + "_";
            }
            dbFieldName = dbFieldName.substring(0, dbFieldName.length() - 1);
        }

        return dbFieldName;
    }
}
