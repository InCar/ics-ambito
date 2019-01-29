//package com.incarcloud.ics.ambito.security;
//
//import com.incarcloud.ics.ambito.utils.CollectionUtils;
//import com.incarcloud.ics.core.access.DefaultAccessor;
//import com.incarcloud.ics.core.realm.Realm;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//
//import java.io.Serializable;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author ThomasChan
// * @version 1.0
// * @description
// * @date 2019/1/21
// */
//public class JdbcAccessor extends DefaultAccessor {
//
//
//    private JdbcTemplate jdbcTemplate;
//
//
//    public JdbcAccessor(Realm realm) {
//        super(realm);
//    }
//
//    @Override
//    protected boolean verifyAccessible(Collection<String> orgCodes, Serializable dataId, String tableName) {
//        if(CollectionUtils.isEmpty(orgCodes)){
//            return false;
//        }
//        List<Object> args = new ArrayList<>();
//        args.add(dataId);
//        args.addAll(orgCodes);
//        String str =  orgCodes.stream().map(e -> "?").collect(Collectors.joining(","));
//        List<Object> query = jdbcTemplate.query("select * from " + tableName + " where id = ? and org_code in ("+str+")", new RowMapper<Object>() {
//            @Override
//            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return rs.getObject(rowNum);
//            }
//        }, args.toArray());
//        return CollectionUtils.isNotEmpty(query);
//    }
//
//}
