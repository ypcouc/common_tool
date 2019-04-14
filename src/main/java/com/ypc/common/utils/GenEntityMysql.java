package com.ypc.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.dom.DOMDocumentType;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GenEntityMysql {
    private String packageOutPath;//指定实体生成所在包的路径
    private String authorName = "ypc";//作者名字  
    private String tablename;//表名  
    private String[] colnames; // 列名数组  
    private String[] cols; // 表列数组
    private String[] colTypes; //列名类型数组  
    private String[] colRemarks;
    private int size;//列总数
    private String className;//类名
    private String classNameLow;//类名首字母小写
    private String modelName="";//项目model名
    private String userName;//数据库用户名
    private String password;//密码
    public static String ROOT = "";

    //数据库连接
    private  String URL = "?"
            + "characterEncoding=utf-8&connectTimeout=20000&"
            + "zeroDateTimeBehavior=round&&autoReconnect=true";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    //空格
    private static final String newLine = "\r\n";
    private static final String newLine2 = "\r\n\r\n";
    private static final String newLine3 = "\r\n\r\n\r\n";
    private static final String tab = "\t";
    private static final String tab2 = "\t\t";
    private static final String tab3 = "\t\t\t";
    private static final String tab4 = "\t\t\t\t";
    public static final String BR="\n";


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPackageOutPath() {
        return packageOutPath;
    }

    public void setPackageOutPath(String packageOutPath) {
        this.packageOutPath = packageOutPath;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }


    /**
     * 一起生成
     */
    public void genAll(String dburl) {
        try {
            this.genEntity(dburl);
            this.genView();
            this.genQuery();
            this.genMapperXml();
            System.out.println("实体生产成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成实体
     */
    public void genEntity(String dburl) {
        if(StringUtils.isEmpty(dburl)){
            return;
        }
        //创建连接  
        Connection con;
        //查要生成实体类的表  
        // String sql = "select * from " + tablename;
        //PreparedStatement pStemt = null;  
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block  
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(dburl+URL, userName, password);
            // pStemt = con.prepareStatement(sql);
            //ResultSetMetaData rsmd = pStemt.getMetaData(); 
            DatabaseMetaData dbMetaData = con.getMetaData();

            //getTableColumns(null,tablename,dma);
            ResultSet rs = dbMetaData.getColumns(null, null, tablename, "%");
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();

            //int size = rsmd.getColumnCount();   //统计列  
            className = getPropertyName(tablename, true);
            classNameLow = getPropertyName(tablename, false);
            colnames = new String[size]; //列名 
            cols = new String[size]; //列 
            colTypes = new String[size];//列类型  
            colRemarks = new String[size];//列描述
            int i = 0;
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");//列名
                cols[i] = columnName;
                colnames[i] = getPropertyName(columnName, false);
                String dataTypeName = rs.getString("TYPE_NAME");//java.sql.Types类型   名称
                colTypes[i] = dataTypeName;
                String remarks = rs.getString("REMARKS");//列描述
                colRemarks[i] = remarks;
                i++;
            }

            String content = parse();

            try {
                File directory = new File("");
                //System.out.println("绝对路径："+directory.getAbsolutePath());  
                //System.out.println("相对路径："+directory.getCanonicalPath());  
                //String path=this.getClass().getResource("").getPath();  

                //System.out.println(path);  
                //System.out.println("src/?/"+path.substring(path.lastIndexOf("/com/", path.length())) );  
                //String outputPath = directory.getAbsolutePath()+ "/src/"+path.substring(path.lastIndexOf("/com/", path.length()), path.length()) + initcap(tablename) + ".java";  
                String outputPath = directory.getAbsolutePath()+modelName + "/src/main/java/" + this.packageOutPath.replace(".", "/") + "/pojo/" + className + ".java";
                System.out.println(outputPath);
                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//          try {  
//              con.close();  
//          } catch (SQLException e) {  
//              // TODO Auto-generated catch block  
//              e.printStackTrace();  
//          }  
        }
    }

    /**
     * 功能：生成实体类主体代码
     *
     * @return
     */
    private String parse() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ".pojo;");
        sb.append(newLine3);
        //导包
        /*sb.append("import com.ypc.common.codeGenerator.mapperXml.annotations.GenColumn;");
        sb.append(newLine);
        sb.append("import com.ypc.common.codeGenerator.mapperXml.annotations.GenTable;");
        sb.append(newLine);*/
        sb.append("import com.ypc.common.pojo.CommonInfo;");
        /*sb.append(newLine);
        sb.append("import " + this.packageOutPath
                + ".dao." + className + "Mapper;");*/
        sb.append(newLine2);
        //注释部分  
        sb.append("/**" + newLine);
        sb.append(" * " + tablename + " 实体类" + newLine);
        sb.append(" * " + DateUtils.getSystemDateForString("yyyy-MM-dd HH:mm:ss") + " " + this.authorName + newLine);
        sb.append(" */ " + newLine);

        sb.append(newLine);
        //表注解

        /*sb.append("@GenTable(tableName = \"" + tablename + "\", alias = \"t_"
                + classNameLow
                + "\", mapper = " + className + "Mapper.class, columnList = \""
                + classNameLow + "Columns\", resultMap = \"" + classNameLow + "ResultMap\", "
                + "primaryKeyProperty = \"id\", orderEntityPrefix = \""
                + classNameLow + "\", selectCriteria = \"c_search\")");
        sb.append(newLine2);*/

        //实体部分  
        sb.append("public class " + className + " extends CommonInfo {" + newLine);
        processAllAttrs(sb);//属性  
        processAllMethod(sb);//get set方法  
        sb.append(newLine);
        sb.append("}");
        sb.append(newLine);

        return sb.toString();
    }

    /**
     * 功能：生成所有属性
     *
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb) {

        for (int i = 0; i < size; i++) {
            if(cols[i].contains("update") || cols[i].contains("create") || cols[i].contains("fk_company") || cols[i].contains("fk_factory")){
                continue;
            }
            //注解
            sb.append(tab);
            sb.append("/** " + colRemarks[i] + "*/");
            sb.append(newLine);
            sb.append(tab);
            /*sb.append("@GenColumn(columnName = \"" + cols[i] + "\")");
            sb.append(newLine);
            sb.append(tab);*/
            sb.append("private " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";");
            sb.append(newLine);
        }

    }

    /**
     * 功能：生成所有方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {

        sb.append(newLine);
        for (int i = 0; i < size; i++) {
            if(cols[i].contains("update") || cols[i].contains("create") || cols[i].contains("fk_factory") || cols[i].contains("fk_company")){
                continue;
            }
            sb.append(tab + "public void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " " +
                    colnames[i] + "){" + newLine);
            sb.append(tab2 + "this." + colnames[i] + " = " + colnames[i] + ";" + newLine);
            sb.append(tab + "}" + newLine);
            sb.append(tab + "public " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){" + newLine);
            sb.append(tab2 + "return " + colnames[i] + ";" + newLine);
            sb.append(tab + "}" + newLine);
        }

    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    /**
     * 功能：获得列的数据类型
     *
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {
        String result = "";
        if (sqlType.equalsIgnoreCase("bit")) {
            result = "boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            result = "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            result = "Integer";
        } else if (sqlType.equalsIgnoreCase("int")) {
            result = "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            result = "Integer";
        } else if (sqlType.equalsIgnoreCase("float")) {
            result = "Float";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            result = "Double";
        } else if (sqlType.equalsIgnoreCase("image")) {
            result = "Blod";
        } else if (sqlType.equalsIgnoreCase("varbinary")) {
            result = "byte[]";
        }
        else {
            result = "String";
        }

        return result;
    }


    /**
     * 功能：sql类型转mybatis类型
     *
     * @param sqlType
     * @return
     */
    private String sqlType2MybatisType(String sqlType) {
        String result = "";
        if (sqlType.equalsIgnoreCase("bit")) {
            result = "BOOLEAN";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            result = "BIT";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            result = "INTEGER";
        } else if (sqlType.equalsIgnoreCase("int")) {
            result = "INTEGER";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            result = "INTEGER";
        } else if (sqlType.equalsIgnoreCase("float")) {
            result = "FLOAT";
        } else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            result = "DOUBLE";
        } else if (sqlType.equalsIgnoreCase("image")) {
            result = "BLOB";
        } else if (sqlType.equalsIgnoreCase("varbinary")) {
            result = "BIT";
        }else if (sqlType.equalsIgnoreCase("datetime")) {
            result = "TIMESTAMP";
        }else if (sqlType.equalsIgnoreCase("text")) {
            result = "VARCHAR";
        }
        else {
            result = sqlType.toUpperCase();
        }

        return result;
    }

    /**
     * 获得表或视图中的所有列信息
     */
    public void getTableColumns(String schemaName, String tableName, DatabaseMetaData dbMetaData) {

        try {

            ResultSet rs = dbMetaData.getColumns(null, schemaName, tableName, "%");
            rs.last();
            int count = rs.getRow();
            rs.beforeFirst();
            System.out.println(count);
            int m = 0;
            while (rs.next()) {
                m++;
                String tableCat = rs.getString("TABLE_CAT");//表目录（可能为空）
                String tableSchemaName = rs.getString("TABLE_SCHEM");//表的架构（可能为空）
                String tableName_ = rs.getString("TABLE_NAME");//表名
                String columnName = rs.getString("COLUMN_NAME");//列名
                int dataType = rs.getInt("DATA_TYPE"); //对应的java.sql.Types类型
                String dataTypeName = rs.getString("TYPE_NAME");//java.sql.Types类型   名称
                int columnSize = rs.getInt("COLUMN_SIZE");//列大小
                int decimalDigits = rs.getInt("DECIMAL_DIGITS");//小数位数
                int numPrecRadix = rs.getInt("NUM_PREC_RADIX");//基数（通常是10或2）
                int nullAble = rs.getInt("NULLABLE");//是否允许为null
                String remarks = rs.getString("REMARKS");//列描述
                String columnDef = rs.getString("COLUMN_DEF");//默认值
                int sqlDataType = rs.getInt("SQL_DATA_TYPE");//sql数据类型
                int sqlDatetimeSub = rs.getInt("SQL_DATETIME_SUB");   //SQL日期时间分?
                int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");   //char类型的列中的最大字节数
                int ordinalPosition = rs.getInt("ORDINAL_POSITION");  //表中列的索引（从1开始）

                /**
                 * ISO规则用来确定某一列的为空性。
                 * 是---如果该参数可以包括空值;
                 * 无---如果参数不能包含空值
                 * 空字符串---如果参数为空性是未知的
                 */
                String isNullAble = rs.getString("IS_NULLABLE");

                /**
                 * 指示此列是否是自动递增
                 * 是---如果该列是自动递增
                 * 无---如果不是自动递增列
                 * 空字串---如果不能确定它是否
                 * 列是自动递增的参数是未知
                 */
                String isAutoincrement = rs.getString("IS_AUTOINCREMENT");

                System.out.println(tableCat + "-" + tableSchemaName + "-" + tableName_ + "-" + columnName + "-"
                        + dataType + "-" + dataTypeName + "-" + columnSize + "-" + decimalDigits + "-" + numPrecRadix
                        + "-" + nullAble + "-" + remarks + "-" + columnDef + "-" + sqlDataType + "-" + sqlDatetimeSub
                        + charOctetLength + "-" + ordinalPosition + "-" + isNullAble + "-" + isAutoincrement + "-");
            }
            System.out.println(m);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 去掉下划线并且首字符大小
     *
     * @param str
     * @return
     */
    public String getPropertyName(String str, Boolean isClassName) {
        String name = "";
        String per = "";
        if (StringUtils.hasText(str)) {
            if (str.indexOf("_") > -1) {
                per = str.substring(0, str.indexOf("_"));
                str = str.substring(str.indexOf("_") + 1);
            }
            String[] nameArry = str.split("_");
            if (nameArry != null && nameArry.length > 0) {
                for (int i = 0; i < nameArry.length; i++) {
                    if (isClassName) {
                        name += initcap(nameArry[i]);
                    } else {
                        if (i != 0) {
                            name += initcap(nameArry[i]);
                        } else {
                            name += nameArry[i];
                        }
                    }

                }
            }

        }

        if (StringUtils.hasText(per) && per.equals("fk")) {
            name += "Id";
        }

        return name;
    }

    /**
     * 生成view
     */
    private void genView() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ".pojo.view;");
        sb.append(newLine3);
        //导包
        sb.append("import " + this.packageOutPath + ".pojo." + className + ";");
        sb.append(newLine2);
        //注释部分
        sb.append("/**" + newLine);
        sb.append(" * " + tablename + " view展示类" + newLine);
        sb.append(" * " + DateUtils.getSystemDateForString("yyyy-MM-dd HH:mm:ss") + " " + this.authorName + newLine);
        sb.append(" */ " + newLine2);
        sb.append("public class " + className + "View extends "
                + className + " {");
        sb.append(newLine2);
        sb.append("}");
        sb.append(newLine2);

        String content = sb.toString();
        try {
            File directory = new File("");
            String outputPath = directory.getAbsolutePath()+modelName + "/src/main/java/" + this.packageOutPath.replace(".", "/") + "/pojo/view/" + className + "View.java";
            System.out.println(outputPath);
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成query
     */
    private void genQuery() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageOutPath + ".pojo.query;");
        sb.append(newLine3);
        //导包
        sb.append("import com.ypc.common.pojo.query.CommonQuery;");
        sb.append(newLine2);
        //注释部分
        sb.append("/**" + newLine);
        sb.append(" * " + tablename + " query查询" + newLine);
        sb.append(" * " + DateUtils.getSystemDateForString("yyyy-MM-dd HH:mm:ss") + " " + this.authorName + newLine);
        sb.append(" */ " + newLine2);
        sb.append("public class " + className + "Query extends CommonQuery {");
        sb.append(newLine2);

        processQueryAttrs(sb);
        processQueryMethod(sb);

        sb.append("}");
        sb.append(newLine2);

        String content = sb.toString();
        try {
            File directory = new File("");
            String outputPath = directory.getAbsolutePath()+modelName + "/src/main/java/" + this.packageOutPath.replace(".", "/") + "/pojo/query/" + className + "Query.java";
            System.out.println(outputPath);
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 功能：生成所有属性
     *
     * @param sb
     */
    private void processQueryAttrs(StringBuffer sb) {

        for (int i = 0; i < size; i++) {
            if (cols[i].contains("fk") && !(cols[i].contains("fk_company") || cols[i].contains("fk_factory"))) {
                sb.append(tab);
                sb.append("/** " + colRemarks[i] + "*/");
                sb.append(newLine);
                sb.append(tab);
                sb.append("private " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";");
                sb.append(newLine2);
            }
        }

    }

    /**
     * 功能：生成所有方法
     *
     * @param sb
     */
    private void processQueryMethod(StringBuffer sb) {

        for (int i = 0; i < size; i++) {
            if (cols[i].contains("fk")&& !(cols[i].contains("fk_company") || cols[i].contains("fk_factory"))) {
                sb.append(tab + "public void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " " +
                        colnames[i] + "){" + newLine);
                sb.append(tab2 + "this." + colnames[i] + " = " + colnames[i] + ";" + newLine);
                sb.append(tab + "}" + newLine);
                sb.append(tab + "public " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){" + newLine);
                sb.append(tab2 + "return " + colnames[i] + ";" + newLine);
                sb.append(tab + "}" + newLine);
            }
        }

    }


    public void genMapperXml(){
        DocumentFactory df = new DocumentFactory();
        Document doc = df.createDocument();
        Map<String, String> a = new HashMap<String, String>();
        DOMDocumentType dt = new DOMDocumentType();
//			doc.setDocType(dt);
        doc.setDocType(df.createDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd"));
        Element elMapper = doc.addElement("mapper");
        elMapper.addAttribute("namespace", packageOutPath+".dao."+className+"Mapper");
        makeViewResultMap(elMapper);
        elMapper.addText(BR);
        makeSql(elMapper);
        elMapper.addText(BR);
        elMapper.addText(BR);
        makeInsert(elMapper);
        elMapper.addText(BR);
        elMapper.addText(BR);
        makeUpdate(elMapper);
        elMapper.addText(BR);
        elMapper.addText(BR);
        makeDelete(elMapper);
        elMapper.addText(BR);
        elMapper.addText(BR);
        makeSelectById(elMapper);
        elMapper.addText(BR);
        elMapper.addText(BR);
        makeSelect(elMapper);
        elMapper.addText(BR);
        elMapper.addText(BR);
        elMapper.addText(BR);
        OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
        xmlFormat.setEncoding("UTF-8");
        xmlFormat.setIndent("	");
        xmlFormat.setTrimText(false);
        xmlFormat.setLineSeparator("\n");

        try {
            File directory = new File("");
            File file = new File( directory.getAbsolutePath()+modelName + "/src/main/java/" + this.packageOutPath.replace(".", "/")+"/mapper/"+className+ "Mapper.xml");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            XMLWriter xmlWriter = new XMLWriter(out, xmlFormat);
            xmlWriter.write(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成resultMap
     *
     * @param
     * @param
     * @throws Exception
     */
    private Element makeViewResultMap(Element elMapper){
        Element resultMap = null;
        resultMap = elMapper.addElement("resultMap");
        resultMap.addAttribute("id", classNameLow+"ViewResultMap");
        resultMap.addAttribute("type", packageOutPath + ".pojo.view."+className+"View");
        for (int i = 0; i < size; i++) {
            if(cols[i].contains("update") || cols[i].contains("create")){
                continue;
            }
            Element selectResult = null;
            if (cols[i].contains("_id")) {
                selectResult = resultMap.addElement("id");
            } else {
                selectResult = resultMap.addElement("result");
            }
            selectResult.addAttribute("column", "T_" + classNameLow.toUpperCase() + "_" + cols[i]);
            selectResult.addAttribute("jdbcType", sqlType2MybatisType(colTypes[i]));
            selectResult.addAttribute("property", colnames[i]);

        }
        Element otherResult = resultMap.addElement("result");
        otherResult.addAttribute("column", "T_" + classNameLow.toUpperCase() + "_create_by");
        otherResult.addAttribute("jdbcType", "VARCHAR");
        otherResult.addAttribute("property", "createBy");

        otherResult = resultMap.addElement("result");
        otherResult.addAttribute("column", "T_" + classNameLow.toUpperCase() + "_create_time");
        otherResult.addAttribute("jdbcType", "TIMESTAMP");
        otherResult.addAttribute("property", "createTime");

        otherResult = resultMap.addElement("result");
        otherResult.addAttribute("column", "T_" + classNameLow.toUpperCase() + "_update_by");
        otherResult.addAttribute("jdbcType", "VARCHAR");
        otherResult.addAttribute("property", "updateBy");

        otherResult = resultMap.addElement("result");
        otherResult.addAttribute("column", "T_" + classNameLow.toUpperCase() + "_update_time");
        otherResult.addAttribute("jdbcType", "TIMESTAMP");
        otherResult.addAttribute("property", "updateTime");

        return elMapper;
    }

    /**
     * 生成mapper 的sql类型  语句片段,暂时不提供collection的查询语句相应解析，可以手动在关联类中执行生成方法进行改造
     *
     * @param elMapper
     * @param
     * @return
     * @throws Exception
     */
    private Element makeSql(Element elMapper) {
        //根节点对应的sql
        elMapper.addComment("root sql");
        elMapper.addText(BR);
        Element rootSqlElement = elMapper.addElement("sql");
        rootSqlElement.addAttribute("id", classNameLow+"Columns");
        rootSqlElement.addText(BR);
        StringBuilder sqlStr = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if(cols[i].contains("time")){
                sqlStr.append(tab2 + "date_format(T_" + classNameLow.toUpperCase() + "." + cols[i] + ",'%Y-%m-%d %H:%i:%s') "
                        + "T_" + classNameLow.toUpperCase() + "_" + cols[i] + ",");
            }else {
                sqlStr.append(tab2 + "T_" + classNameLow.toUpperCase() + "." + cols[i] + " " + "T_" + classNameLow.toUpperCase() + "_" + cols[i] + ",");
            }
            if(i < size-1){
                sqlStr.append(BR);
            }
        }
        sqlStr.append(tab2);
        rootSqlElement.addText(sqlStr.toString());
        rootSqlElement.addText(BR+tab);
        return elMapper;
    }

    /**
     * 生成insert
     * @param elMapper
     * @param
     * @throws Exception
     */
    private void makeInsert(Element elMapper) {
        Element elInsert = elMapper.addElement("insert");
        elInsert.addAttribute("id", "add" + className);
        elInsert.addAttribute("parameterType", packageOutPath + ".pojo."+className);
        elInsert.addText(BR);
        Element elSelectKey = elInsert.addElement("selectKey");
        elSelectKey.addAttribute("keyProperty", "id");
        elSelectKey.addAttribute("order", "BEFORE");
        elSelectKey.addAttribute("resultType", "java.lang.String");
        elSelectKey.addText(BR);
        elSelectKey.addText(tab3 + "select myuuid() from dual");
        elSelectKey.addText(BR + tab2);
        StringBuilder columnStr = new StringBuilder();
        StringBuilder columnValStr = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if(cols[i].contains("valid") || cols[i].contains("update") || cols[i].contains("create")){
                continue;
            }
            columnStr.append(cols[i]);
            columnValStr.append("#{"+colnames[i]+",jdbcType="+sqlType2MybatisType(colTypes[i])+"}");
            columnStr.append(",");
            columnValStr.append(",");
        }
        columnStr.append("create_by,update_by");
        columnValStr.append("#{createBy,jdbcType=VARCHAR}, #{updateBy,jdbcType=VARCHAR}");

        elInsert.addText(BR);
        elInsert.addText(tab2 + "insert into " + tablename + " (" + columnStr + ")" + BR
                + tab2 + "values " + BR + tab2 + "(" + columnValStr + ")");
        elInsert.addText(BR);
    }

    /**
     * 生成update
     * @param elMapper
     * @param
     * @throws Exception
     */
    private void makeUpdate(Element elMapper) {
        Element elUpdate = elMapper.addElement("update");
        elUpdate.addAttribute("id", "update" + className);
        elUpdate.addAttribute("parameterType", packageOutPath+".pojo."+className);
        elUpdate.addText(BR);
        elUpdate.addText(tab2);
        elUpdate.addText("update " + tablename + " set ");
        Element elTrim = elUpdate.addElement("trim");
        elTrim.addAttribute("suffixOverrides", ",");
        String id ="";
        int idIndex = 0;
        for (int i = 0; i < size; i++) {
            if(cols[i].contains("valid") || cols[i].contains("update") || cols[i].contains("create")){
                continue;
            }
            if (!cols[i].contains("_id")) {
                Element elIf = elTrim.addElement("if");
                elIf.addAttribute("test", colnames[i] + " != null ");
                elIf.addText(cols[i] + "=#{" + colnames[i] + ",jdbcType=" + sqlType2MybatisType(colTypes[i]) + "},");
            }else {
                id = cols[i];
                idIndex = i;
            }
        }
        Element elIf = elTrim.addElement("if");
        elIf.addAttribute("test", "updateBy !=null ");
        elIf.addText("update_by=#{updateBy,jdbcType=VARCHAR},");
        elUpdate.addText(BR);
        elUpdate.addText(tab2);
        elUpdate.addText("where " + id + "=#{" + colnames[idIndex] + ",jdbcType=" + sqlType2MybatisType(colTypes[idIndex]) + "}");
    }

    /**
     * 删除
     * @param elMapper
     * @param
     * @throws Exception
     */
    private void makeDelete(Element elMapper) {
        int idIndex = -1;
        int validIndex = -1;
        for (int i = 0; i < size; i++) {
            if (cols[i].contains("_id")) {
                idIndex = i;
            }
            if(cols[i].contains("valid")){
                validIndex = i;
            }
        }
        Element elDelete = elMapper.addElement("delete");
        elDelete.addAttribute("id", "delete" + className);
        elDelete.addAttribute("parameterType", "java.lang."+sqlType2JavaType(colTypes[idIndex]));
        elDelete.addText(BR);
        elDelete.addText(tab2);
        if (validIndex < 0) {
            elDelete.addText("delete from " + tablename + " where " + cols[idIndex] + " = ");
        }else {
            elDelete.addText("update " + tablename + " set " + cols[validIndex] + " = 0" +
                    " where " + cols[idIndex] + " = ");
        }
        elDelete.addText("#{" + colnames[idIndex] + ",jdbcType=" + sqlType2MybatisType(colTypes[idIndex]) + "}" + BR +tab);
    }

    /**
     * 根据id查询
     * @param elMapper
     */
    private void makeSelectById(Element elMapper) {
        int idIndex = -1;
        for (int i = 0; i < size; i++) {
            if (cols[i].contains("_id")) {
                idIndex = i;
            }
        }
        Element elSelect = elMapper.addElement("select");
        elSelect.addAttribute("id", "get" + className + "ById");
        elSelect.addAttribute("parameterType", "java.lang.String");
        elSelect.addAttribute("resultMap", classNameLow+"ViewResultMap");
        elSelect.addText(BR + tab2 + "select");

        Element elTrim = elSelect.addElement("trim");
        elTrim.addAttribute("suffixOverrides", ",");
        //添加根结点的sql
        Element elRootInclude = elTrim.addElement("include");
        elRootInclude.addAttribute("refid", classNameLow+"Columns");
        StringBuilder tableJoinFragment = new StringBuilder();
        tableJoinFragment.append(BR + tab2 + "from " + tablename + " " + "T_" + classNameLow.toUpperCase() + BR);
        tableJoinFragment.append(tab2 + "where " + "T_" + classNameLow.toUpperCase() + "." + cols[idIndex] + " = #{id}");
        elSelect.addText(tableJoinFragment.toString());
    }

    /**
     * 列表查询
     * @param elMapper
     */
    private void makeSelect(Element elMapper) {
        Element elSelect = elMapper.addElement("select");
        elSelect.addText(tab2);
        elSelect.addAttribute("id", "get" + className + "List");
        elSelect.addAttribute("parameterType", packageOutPath+".pojo.query."+className+"Query");
        elSelect.addAttribute("resultMap", classNameLow+"ViewResultMap");
        elSelect.addText(BR + tab2 + "select");
        Element elTrim = elSelect.addElement("trim");
        elTrim.addAttribute("suffixOverrides", ",");
        //添加根结点的sql
        Element elRootInclude = elTrim.addElement("include");
        elRootInclude.addAttribute("refid", classNameLow+"Columns");

        StringBuilder tableJoinFragment = new StringBuilder();
        elSelect.addText(tab);
        tableJoinFragment.append(BR + tab2 + "from " + tablename + " " + "T_" + classNameLow.toUpperCase() + BR);
        List<Integer> selectList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if(cols[i].contains("fk")){
                selectList.add(i);
                int index = colRemarks[i].indexOf("(");
                if(index > -1){
                    String str = colRemarks[i].substring(index+1,colRemarks[i].length()-1);
                    String[] joinTable = str.split(",");
                    if(joinTable == null || joinTable.length != 2){
                        continue;
                    }
                    tableJoinFragment.append(tab2 +"left join " + joinTable[0]+ " on T_"+cols[i].substring(3));
                    tableJoinFragment.append(" T_" + classNameLow.toUpperCase()+"."+cols[i]+" = T_"+cols[i].substring(3)
                            +"."+joinTable[1]+ BR);
                }
            }
        }

        elSelect.addText(tableJoinFragment.toString());

        int validIndex = -1;
        for (int i = 0; i < size; i++) {
            if(cols[i].contains("valid")){
                validIndex = i;
            }
        }
        Element elWhere = null;
        if (validIndex > -1) {
            elWhere = elSelect.addElement("where");
            elWhere.addText(BR);
            elWhere.addText(tab3+"T_" + classNameLow.toUpperCase()+"."+cols[validIndex]+" = 1 ");
        }

        if(!CollectionUtils.isEmpty(selectList)){
            if (validIndex == -1) {
                elWhere = elSelect.addElement("where");
                elWhere.addText(BR);
            }
            for (int i =0;i<selectList.size();i++){

                Element elIf = elWhere.addElement("if");
                String ifAttr = colnames[selectList.get(i)] +" !=null ";
                if (!colTypes[selectList.get(i)].equals("int")){
                    ifAttr = ifAttr + "and "+ colnames[selectList.get(i)] +" !='' ";
                }
                elIf.addAttribute("test",ifAttr);
                elIf.addText(BR);
                elIf.addText(tab4+"and T_" + classNameLow.toUpperCase()+"."+cols[selectList.get(i)]+" = #{"+ colnames[selectList.get(i)] + "}" +BR+tab3);

            }
        }

        elSelect.addText(BR);
    }


    /**
     * 出口
     * TODO
     *
     * @param args
     */
    public static void main(String[] args) {


    }

}
