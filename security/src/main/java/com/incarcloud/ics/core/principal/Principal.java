package com.incarcloud.ics.core.principal;

import java.io.Serializable;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/19 5:13 PM
 * @Version 1.0
 */
public interface Principal extends Serializable {
    String getUserIdentity();
}
