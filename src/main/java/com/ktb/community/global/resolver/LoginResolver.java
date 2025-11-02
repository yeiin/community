package com.ktb.community.global.resolver;

import com.ktb.community.global.annotation.Login;
import com.ktb.community.global.provider.SessionProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginResolver implements HandlerMethodArgumentResolver {

    private final SessionProvider sessionProvider;

    public LoginResolver(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return sessionProvider.getLoginSession();
    }
}

