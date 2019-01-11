package com.incarcloud.ics.ambito.utils.sqlInit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;

@Component
public class InitSqlScript {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 初始化sql到数据库
     */
    public void executeSqlFile() {

        //加载sql文件的路径
        String file = "src/main/resources/db/migration/";
        File[] files = new File(file).listFiles();

        for (File file1 : files) {
            if (file1.isFile() && file1.exists()) { //判断文件是否存在
                //初始化sql到数据库
                String sqlStr = readFileByLines(file1.getPath());
                if (sqlStr.length() > 0) {
                    //处理一个sql文件里面包含有多个sql
                    String[] split = sqlStr.split(";");
                    if (split.length > 2) {
                        for (String s : split) {
                            if (!StringUtils.isEmpty(s) && !s.equals(" ")) {
                                jdbcTemplate.execute(s);
                            }
                        }
                    } else {
                        jdbcTemplate.execute(sqlStr);
                    }
                }
            }
        }


    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param filePath
     * @return
     */
    public String readFileByLines(String filePath) {
        StringBuffer str = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
            String temp = null;
            //一次读入一行，直到读入Null为文件结束
            while ((temp = reader.readLine()) != null) {
                str = str.append(" " + temp);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str.toString();
    }


}
