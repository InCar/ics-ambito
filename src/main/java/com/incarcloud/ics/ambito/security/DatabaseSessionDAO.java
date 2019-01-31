package com.incarcloud.ics.ambito.security;

import com.incarcloud.ics.ambito.condition.impl.StringCondition;
import com.incarcloud.ics.ambito.dao.SessionBeanDao;
import com.incarcloud.ics.ambito.entity.SessionBean;
import com.incarcloud.ics.ambito.utils.CollectionUtils;
import com.incarcloud.ics.ambito.utils.SerializationUtils;
import com.incarcloud.ics.core.exception.UnknownSessionException;
import com.incarcloud.ics.core.session.AbstractSessionDAO;
import com.incarcloud.ics.core.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 
 * @author ThomasChan
 * @version 1.0
 * @date 2019/1/14
 */
@Component
public class DatabaseSessionDAO extends AbstractSessionDAO {

    @Autowired
    private SessionBeanDao sessionBeanDao;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        SessionBean sessionBean = new SessionBean((String)sessionId, SerializationUtils.serialize(session));
        sessionBeanDao.save(sessionBean);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        List<SessionBean> sessionBeans = sessionBeanDao.query(new StringCondition("id", sessionId, StringCondition.Handler.EQUAL));
        if(CollectionUtils.isEmpty(sessionBeans)){
            return null;
        }
        return (Session) SerializationUtils.deserialize(sessionBeans.get(0).getSession());
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        SessionBean sessionBean = sessionBeanDao.get(session.getId());
        sessionBean.setSession(SerializationUtils.serialize(session));
        sessionBeanDao.update(sessionBean);
    }

    @Override
    public void delete(Session session) {
        sessionBeanDao.delete(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        List<SessionBean> query = sessionBeanDao.query();
        return query.stream().map(e->(Session)SerializationUtils.deserialize(e.getSession())).collect(Collectors.toList());
    }
}
