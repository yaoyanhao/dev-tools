package utils;

import constant.TestConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by vector01.yao on 2017/5/18.
 * 数据库执行工具
 */
public class DataBaseHelper {
    private static final Logger logger = LoggerFactory.getLogger(DataBaseHelper.class);

    /**
     * properties配置key'DB_PASSWORD'对应value
     */
    private static String db_password = "";

    /**
     * properties配置key'DB_USERNAME'对应valuedb.properties
     */
    private static String db_username = "";

    /**
     * properties配置key'DBSERVER_URL'对应value
     */
    private static String db_url = "";

    /**
     * properties配置key'DB_DRIVER'对应value
     */
    private static String db_driver = "";

    /**
     * 去注释正则表达式
     */
    private static final String PATTERN_REGEX = "(?ms)('(?:''|[^'])*')|--.*?$|/\\*.*?\\*/";


    public static void initDB(String propertyLocation){
        Map<String, String> propsMap = PropertiesUtil.resolveDbConfig(propertyLocation);;

        if (propsMap.get(TestConstants.DBSERVER_URL) != null) {
            db_url = propsMap.get(TestConstants.DBSERVER_URL).toString();
        }
        if (propsMap.get(TestConstants.DB_DRIVER) != null) {
            db_driver = propsMap.get(TestConstants.DB_DRIVER).toString();
        }
        if (propsMap.get(TestConstants.DB_PASSWORD) != null) {
            db_password = propsMap.get(TestConstants.DB_PASSWORD).toString();
        }
        if (propsMap.get(TestConstants.DB_USERNAME) != null) {
            db_username = propsMap.get(TestConstants.DB_USERNAME).toString();
        }
    }


    /**
     * 执行sql
     *
     * @param dbSqlLocation :sql文件所在位置
     */
    public static void executeSQL(String dbSqlLocation) {
        logger.info("initDb is beginning! [" + dbSqlLocation + "]");
        Map<String, String[]> props = resolveSqlConfig(dbSqlLocation);
        if (props != null) {
            Connection conn = null;
            try {
                Class.forName(db_driver).newInstance();
                conn = DriverManager.getConnection(db_url, db_username, db_password);
                Statement stmt = conn.createStatement();

                String[] sqls = props.get(TestConstants.ALL_SYMBOL);
                if (sqls != null && sqls.length > 0) {
                    for (String prop : sqls) {
                        stmt.addBatch(prop);
                    }

                    stmt.executeBatch();
                    stmt.close();
                    conn.close();
                    logger.info("sql is initialized!");
                }
            } catch (SQLException e) {
                logger.error("initDb SQLException :" + e.getMessage());
            } catch (Exception e) {
                logger.error("initDb Exception :" + e.getMessage());
            }
        }
        logger.info("initDb is ending!");
    }


    /**
     * 获取db.sql内容
     *
     * @param dbSqlLocation sql文件位置
     * @return String
     */
    private static Map<String, String[]> resolveSqlConfig(String dbSqlLocation) {
        Map<String, String[]> result = new HashMap<String, String[]>();

        List<String> atomList = new ArrayList<String>();

        InputStream is = DataBaseHelper.class.getClassLoader().getResourceAsStream(dbSqlLocation);
        if (is != null) {
            InputStreamReader reader = null;
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                reader = new InputStreamReader(is, "utf-8");
                br = new BufferedReader(reader);
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                logger.error("Could not resolve InputStreamReader :" + e.getMessage());
            } finally {
                IOUtil.closeStream(is, reader, br);
            }

            String presult = Pattern.compile(PATTERN_REGEX).matcher(sb.toString()).replaceAll("$1");
            result.put(TestConstants.ALL_SYMBOL, presult.split(TestConstants.SPLIT_ELEMENT));
            return result;
        }
        return null;
    }

}
