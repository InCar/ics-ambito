package com.incarcloud.ics.core.realm;

import com.incarcloud.ics.core.authc.AuthenticateToken;
import com.incarcloud.ics.core.authc.CredentialMatcher;
import com.incarcloud.ics.core.authc.MD5PasswordMatcher;
import com.incarcloud.ics.core.exception.AccountNotExistsException;
import com.incarcloud.ics.core.exception.AuthenticationException;
import com.incarcloud.ics.core.subject.Account;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author ThomasChan
 * @Date 2018/12/20
 * @Version 1.0
 */
public abstract class SimpleAccountRealm extends CacheRealm {

    /**
     * 缓存所有用户信息
     */
    protected final Map<String,Account> users;

    public SimpleAccountRealm() {
        this(new MD5PasswordMatcher());
    }

    public SimpleAccountRealm(CredentialMatcher credentialMatcher) {
        super(credentialMatcher);
        this.setCacheEnabled(false);
        this.users = new ConcurrentHashMap<>();
    }

    protected void addAccount(Account user){
        this.users.put(user.getPrincipal().getUserIdentity(), user);
    }

    protected void addAccounts(List<Account> userList){
        userList.forEach(this::addAccount);
    }

    public Account getAccountInfo(String username){
        return this.users.get(username);
    }

    @Override
    protected Account doGetUserDetail(AuthenticateToken token) throws AuthenticationException {
        Account account = getAccountInfo(token.getPrincipal());
        if(account == null) {
            throw new AccountNotExistsException();
        }else {
            addAccount(account);
        }
        return account;
    }



}
