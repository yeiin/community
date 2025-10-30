package com.ktb.community.global.validator;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;
import java.util.function.Predicate;


@Component
@Setter
@ConfigurationProperties(prefix = "security")
public class RouteValidator {

    private List<RouteRule> notSecuredList;
    private final PathMatcher pathMatcher = new AntPathMatcher();

    public Predicate<HttpServletRequest> isSecured = req ->
            notSecuredList.stream().noneMatch(rule ->
                    (rule.getMethod().equals("ALL") || rule.getMethod().equalsIgnoreCase(req.getMethod()))
                            && pathMatcher.match(rule.getPath(), req.getRequestURI()));
}
