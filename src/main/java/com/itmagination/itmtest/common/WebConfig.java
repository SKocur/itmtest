package com.itmagination.itmtest.common;

import com.itmagination.itmtest.interceptor.HttpTraceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
class WebConfig implements WebMvcConfigurer {

    private final HttpTraceInterceptor httpTraceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpTraceInterceptor);
    }
}
