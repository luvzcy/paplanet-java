package com.wen.auth.config;

import com.wen.auth.filter.JwtAuthenticationFilter;
import com.wen.auth.handler.AccessDeniedHandlerImpl;
import com.wen.auth.handler.AuthenticationEntryPointImpl;
import com.wen.auth.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)//允许跨域
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/test").hasRole("超级管理员")
                        .requestMatchers("/login/**","/user/**","/**").permitAll()
                        .anyRequest().authenticated())

                //添加过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //配置异常处理器
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        authenticationProviders.add(daoAuthenticationProvider());
        return  new ProviderManager(authenticationProviders);
    }

    //将传入的 AuthenticationProvider 数组转换为一个 List 返回。
    private List<AuthenticationProvider> getProviders(AuthenticationProvider... providers) {
        return Arrays.asList(providers);
    }

    /**
     * 创建一个PasswordEncoder类型bean对象
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        //构造方法可以传递整型参数，范围4~31之间，数字越大，强度越高，安全性越好，性能就越差
        return new BCryptPasswordEncoder();
    }



}
