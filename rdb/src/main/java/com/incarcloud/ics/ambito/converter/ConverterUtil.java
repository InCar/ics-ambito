package com.incarcloud.ics.ambito.converter;

import com.incarcloud.ics.ambito.condition.Condition;
import com.incarcloud.ics.ambito.jdbc.WhereSqlEntity;
import com.incarcloud.ics.exception.AmbitoException;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/26
 */
@SuppressWarnings("unchecked")
public class ConverterUtil {

    public static void collectionAppend(Condition condition, WhereSqlEntity sqlEntity, String s) {
        if (condition.val instanceof Collection) {
            Collection val = (Collection) condition.val;
            if (!val.isEmpty()) {
                String str = (String) val.stream().map(e -> "?" + ",").collect(Collectors.joining("", s, ""));
                sqlEntity.append(str);
                sqlEntity.deleteLastChar();
                sqlEntity.append(")");
            }else {
                throw new AmbitoException("In condition cannot set empty collection");
            }
        } else {
            throw new RuntimeException("Only collection val is allowed");
        }
    }

}
