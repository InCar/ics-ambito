package com.incarcloud.ics.core.subject;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/11
 */
public interface SubjectDAO {
    Subject save(Subject subject);

    void delete(Subject subject);
}
