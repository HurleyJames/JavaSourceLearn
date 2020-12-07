package ac.hurley.db.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hbase.thirdparty.org.apache.commons.collections4.CollectionUtils;
import org.apache.hive.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class HBaseTest {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(HBaseTest.class);

    public static void main(String[] args) {

    }

    public static Connection getConnection() {
        try {
            // 获取配置，new一个配置对象
            Configuration configuration = getConfiguration();
            // 检查配置
            HBaseAdmin.available(configuration);
            return ConnectionFactory.createConnection(configuration);
        } catch (IOException | ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    private static Configuration getConfiguration() {
        try {
            Properties props = PropertiesLoaderUtils.loadAllProperties("hbase.properties");
            String clientPort = props.getProperty("hbase.zookeeper.property.clientPort");
            String quorum = props.getProperty("hbase.zookeeper.quorum");

            logger.info("connect to zookeeper {}:{}", quorum, clientPort);

            Configuration config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.property.clientPort", clientPort);
            config.set("hbase.zookeeper.quorum", quorum);
            return config;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建表
     *
     * @param connection
     * @param tableName
     * @param columnFamilies
     * @throws IOException
     */
    public static void createTable(Connection connection, TableName tableName, String... columnFamilies) throws IOException {
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            if (admin.tableExists(tableName)) {
                logger.warn("table:{} exists!", tableName.getName());
            } else {
                TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(tableName);
                for (String columnFamily : columnFamilies) {
                    builder.setColumnFamily(ColumnFamilyDescriptorBuilder.of(columnFamily));
                }
                admin.createTable(builder.build());
                logger.info("create table:{} success!", tableName.getName());
            }
        } finally {
            if (admin != null) {
                admin.close();
            }
        }
    }

    /**
     * 插入数据
     *
     * @param connection
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param data
     * @throws IOException
     */
    public static void put(Connection connection, TableName tableName,
                           String rowKey, String columnFamily,
                           String column, String data) throws IOException {
        Table table = null;
        try {
            table = connection.getTable(tableName);
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column),
                    Bytes.toBytes(data));
            table.put(put);
        } finally {
            if (table != null) {
                table.close();
            }
        }
    }

    /**
     * 根据提供的row、key、column读取单元格
     *
     * @param connection
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @return
     * @throws IOException
     */
    public static String getCell(Connection connection, TableName tableName,
                                 String rowKey, String columnFamily,
                                 String column) throws IOException {
        Table table = null;
        try {
            table = connection.getTable(tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));

            Result result = table.get(get);
            List<Cell> cells = result.listCells();

            if (CollectionUtils.isEmpty(cells)) {
                return null;
            }
            String value = new String(CellUtil.cloneValue(cells.get(0)), "UTF-8");
            return value;
        } finally {
            if (table != null) {
                table.close();
            }
        }
    }

    /**
     * 根据rowKey读取一行的数据
     *
     * @param connection
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public static Map<String, String> getRow(Connection connection, TableName tableName,
                                             String rowKey) throws IOException {
        Table table = null;
        try {
            table = connection.getTable(tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get);
            List<Cell> cells = result.listCells();

            if (CollectionUtils.isEmpty(cells)) {
                return Collections.emptyMap();
            }
            Map<String, String> objectMap = new HashMap<>();
            for (Cell cell : cells) {
                String qualifier = new String(CellUtil.cloneQualifier(cell));
                String value = new String(CellUtil.cloneValue(cell), "UTF-8");
                objectMap.put(qualifier, value);
            }
            return objectMap;
        } finally {
            if (table != null) {
                table.close();
            }
        }
    }

    /**
     * 删除表
     *
     * @param connection
     * @param tableName
     * @throws IOException
     */
    public static void deleteTable(Connection connection, TableName tableName) throws IOException {
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            if (admin.tableExists(tableName)) {
                admin.disableTable(tableName);
                admin.deleteTable(tableName);
            }
        } finally {
            if (admin != null) {
                admin.close();
            }
        }
    }

    /**
     * 添加一列族到已有的表
     *
     * @param connection
     * @throws MasterNotRunningException
     * @throws IOException
     */
    public static void addColumn(Connection connection) throws MasterNotRunningException, IOException {
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            // 初始化列描述对象
            HColumnDescriptor columnDescriptor = new HColumnDescriptor("contactDetails");
            // 添加一个列族
            admin.addColumn(TableName.valueOf("employee"), columnDescriptor);
            System.out.println("column added");
        } finally {
            if (admin != null) {
                admin.close();
            }
        }
    }

    /**
     * 删除列族
     *
     * @param connection
     * @throws MasterNotRunningException
     * @throws IOException
     * @throws ServiceException
     */
    public static void deleteColumn(Connection connection) throws MasterNotRunningException,
            IOException, ServiceException {
        Configuration conf = HBaseConfiguration.create();
        HBaseAdmin.available(conf);
        Admin admin = null;
        try {
            admin = connection.getAdmin();
            admin.deleteColumn(TableName.valueOf("TableName"), "contactDetails".getBytes(Charset.forName("UTF-8")));
            System.out.println("列族已删除");
        } finally {
            if (admin != null) {
                admin.close();
            }
        }
    }

}
