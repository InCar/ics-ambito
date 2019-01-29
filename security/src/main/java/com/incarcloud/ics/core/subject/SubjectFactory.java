package com.incarcloud.ics.core.subject;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/12
 */
public interface SubjectFactory {
    Subject createSubject(SubjectContext subjectContext);
}
