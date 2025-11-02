package com.ktb.community.global.provider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SessionProvider {

    @Value("${spring.session.session_exp_time}")
    private int sessionTime;

    private final HttpServletRequest request;

    public SessionProvider(HttpServletRequest request) {
        this.request = request;
    }

    public void setLoginSession(final long memberId) {
        HttpSession session = request.getSession();
        session.setAttribute("memberId", memberId);
        extendSession(session);
    }

    public String getSessionId(){
        HttpSession session = request.getSession();
        return session.getId();
    }

    public Long getLoginSession(){
        HttpSession session = request.getSession();
        extendSession(session);
        return (Long) session.getAttribute("memberId");
    }

    private void extendSession(HttpSession session) {
        session.setMaxInactiveInterval(sessionTime);
    }

    public void removeSession() {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
