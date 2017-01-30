package com.exam.logic;

import com.exam.logic.actions.LocaleAction;
import com.exam.logic.actions.LoginAction;
import com.exam.logic.actions.RegistrationAction;
import com.exam.logic.actions.friends.*;
import com.exam.logic.actions.profile.ProfileAction;
import com.exam.logic.actions.profile.ProfileEditGetAction;
import com.exam.logic.actions.profile.ProfileEditPostAction;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j
public class ActionFactory {
    private Map<String, Action> actions = new ConcurrentHashMap<>();

    public ActionFactory() {
        actions.put("GET/data/my", (r, s) -> "/jsp/data/my.jsp");
        actions.put("GET/logout", (r, s) -> {
            r.getSession().invalidate();
            return "/WEB-INF/jsp/not_auth/login.jsp";
        });
        actions.put("GET/not_auth/locale", new LocaleAction());
        actions.put("POST/j_security_check", new LoginAction());
        actions.put("GET/not_auth/registration", (r, s) -> "/WEB-INF/jsp/not_auth/registration.jsp");
        actions.put("POST/not_auth/registration", new RegistrationAction());
        actions.put("GET/profile", new ProfileAction());
        actions.put("GET/profile/edit", new ProfileEditGetAction());
        actions.put("POST/profile/edit", new ProfileEditPostAction());
        actions.put("GET/friends", new FriendsListAction());
        actions.put("GET/friends/search", new SearchFriendsAction());
        actions.put("POST/friends/incoming/accept", new AcceptIncomingAction());
        actions.put("POST/friends/request/send", new SendRequestAction());
        actions.put("POST/friends/cancel", new CancelAction());
        actions.put("GET/friends/incoming", new GetIncomingAction());
        actions.put("GET/friends/request", new GetRequestAction());
    }

    public Action getAction(HttpServletRequest request) {
        StringBuilder actionKey = new StringBuilder(request.getMethod());
        actionKey.append(request.getServletPath());
        if (request.getPathInfo() != null) actionKey.append(request.getPathInfo());

        log.debug("New request: " + actionKey.toString());
        Action action = actions.get(actionKey.toString());
        return action != null ? action : (r, s) -> "/WEB-INF/jsp/404.jsp";
//        return Optional
//                .ofNullable(actions.get(actionKey.toString()))
//                .orElse((r, s) -> "/WEB-INF/jsp/404.jsp");
    }
}