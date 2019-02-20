package com.incarcloud.ics.ambito.utils.sqlInit;


import com.incarcloud.ics.ambito.utils.StringUtils;
import com.incarcloud.ics.core.utils.ResourceUtils;
import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class InitSqlScript {

    private static Logger logger = LoggerFactory.getLogger(InitSqlScript.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 初始化sql到数据库
     */
    public void executeSqlFile() {
        //加载sql文件的路径
        List<InputStream> sqlFiles = ResourceUtils.getResourcesAsStreams(getClass(), "db/migration");
        for (InputStream sql : sqlFiles) {
            String sqlStr = readFileByLines(sql);
            logger.debug("Init sql: " + sqlStr);
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

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     *
     * @param is
     * @return
     */
    public String readFileByLines(InputStream is) {
        StringBuffer str = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
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
