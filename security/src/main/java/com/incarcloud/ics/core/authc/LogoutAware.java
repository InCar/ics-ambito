package com.incarcloud.ics.core.authc;

import com.incarcloud.ics.core.principal.Principal;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/7
 */
public interface LogoutAware {
    void onLogout(Principal principal);
}
