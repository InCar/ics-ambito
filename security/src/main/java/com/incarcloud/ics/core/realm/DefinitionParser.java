//package com.incarcloud.ics.core.realm;
//
//import com.incarcloud.ics.core.principal.SimplePrincipal;
//import com.incarcloud.ics.core.org.Organization;
//import com.incarcloud.ics.core.org.SimpleOrganization;
//import com.incarcloud.ics.core.privilege.Privilege;
//import com.incarcloud.ics.core.privilege.SimplePrivilege;
//import com.incarcloud.ics.core.role.String;
//import com.incarcloud.ics.core.role.SimpleRole;
//import com.incarcloud.ics.core.subject.Account;
//import com.incarcloud.ics.core.subject.SimpleAccount;
//import com.incarcloud.ics.core.utils.StringUtils;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * @Description
// * @Author ThomasChan
// * @Date 2018/12/21
// * @Version 1.0
// */
//public class DefinitionParser {
//
//    public static final String EQUAL_TOKEN = "=";
//    public static final String SPLIT_TOKEN = ",";
//    public static final String LINE_SPERATOR = "\n";
//
//
//    public static List<String> parseRoles(String text){
//        if(StringUtils.isBlank(text)){
//            return Collections.emptyList();
//        }
//
//        return Arrays.stream(text.split(LINE_SPERATOR)).map(DefinitionParser::parseRole).collect(Collectors.toList());
//    }
//
//    public static SimpleRole parseRole(String line) {
//        if(StringUtils.isBlank(line)){
//            return null;
//        }
//
//        String[] splits = line.split(EQUAL_TOKEN);
//        SimpleRole simpleRole = new SimpleRole(splits[0]);
//        if(StringUtils.isBlank(splits[1])){
//            return simpleRole;
//        }
//
//        List<String> privilgeList = Arrays.asList(splits[1].split(SPLIT_TOKEN));
//        List<Privilege> simplePrivileges = privilgeList.stream().map(SimplePrivilege::new).collect(Collectors.toList());
//        simpleRole.setPrivileges(simplePrivileges);
//        return simpleRole;
//    }
//
//
//    public static List<Account> parseUsers(String text){
//        if(StringUtils.isBlank(text)){
//            return Collections.emptyList();
//        }
//
//        String[] split = text.split(LINE_SPERATOR);
//        return Arrays.stream(split).map(DefinitionParser::parseUser).collect(Collectors.toList());
//    }
//
//
//    public static Account parseUser(String line) {
//        if(StringUtils.isBlank(line)){
//            return null;
//        }
//
//        String[] splits = line.split(EQUAL_TOKEN);
//        SimpleAccount simpleAccount = new SimpleAccount(new SimplePrincipal(splits[0]), "");
//        String userinfo = splits[1];
//        if(StringUtils.isBlank(userinfo)){
//            return simpleAccount;
//        }
//
//        String[] passwdAndRole = splits[1].split(SPLIT_TOKEN);
//        for(int i = 0; i < passwdAndRole.length; i++){
//            if(i == 0){
//                simpleAccount.setCredential(passwdAndRole[0]);
//            }else {
//                simpleAccount.getRoles().add(new SimpleRole(passwdAndRole[i]));
//            }
//        }
//        return simpleAccount;
//    }
//
//
//    public static Map<String,List<Organization>> parseUserOrganizations(String text){
//        if(StringUtils.isBlank(text)){
//            return Collections.emptyMap();
//        }
//
//        Map<String,List<Organization>> res = new HashMap<>();
//        Arrays.stream(text.split(LINE_SPERATOR)).forEach(e->{
//            res.putAll(parseUserOrganization(e));
//        });
//        return res;
//    }
//
//    public static Map<String,List<Organization>> parseUserOrganization(String line) {
//        if(StringUtils.isBlank(line)){
//            return Collections.emptyMap();
//        }
//
//        String[] splits = line.split(EQUAL_TOKEN);
//        if(StringUtils.isBlank(splits[0])){
//            return Collections.emptyMap();
//        }
//
//        Map<String,List<Organization>> results = new HashMap<>();
//        List<Organization> collect = Arrays.stream(splits[1].split(SPLIT_TOKEN)).map(SimpleOrganization::new).collect(Collectors.toList());
//        results.put(splits[0], collect);
//        return results;
//    }
//
//}
