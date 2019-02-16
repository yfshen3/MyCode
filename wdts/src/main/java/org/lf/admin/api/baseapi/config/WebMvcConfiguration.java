package org.lf.admin.api.baseapi.config;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.lf.admin.api.baseapi.core.HtmlTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * web相关配置（拦截器，跨域等）
 *
 * @author sunwill
 * @date 2018/04/25
 */
@Configuration
@Primary
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private AppHandlerExceptionResolver appHandlerExceptionResolver;

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(appHandlerExceptionResolver);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludePathPatterns = getExcludeCommonPathPatterns();

        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) {
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) {
                if (!(handler instanceof HandlerMethod)) {
                    return;
                }
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                HtmlTitle annotation = handlerMethod.getMethodAnnotation(HtmlTitle.class);
                if (null == annotation) {
                    return;
                }
                modelAndView.addObject("HtmlTitle", annotation.title());
            }

            @Override
            public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

            }
        }).excludePathPatterns(excludePathPatterns);
    }



    private String[] getExcludeCommonPathPatterns() {
        String[] urls = {
                "/v2/api-docs",
                "/swagger-resources/**",
                "/cache/**",
                "/api/log/save",
                "/open_api/**"
        };
        return urls;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/cache/**").addResourceLocations(
                "classpath:/META-INF/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
