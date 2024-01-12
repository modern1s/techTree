package com.sparta.techTree.common.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

    @Configuration
    @EnableWebSecurity
    class SecurityConfig(
        private val jwtTokenProvider: JwtTokenProvider
    ) {
        @Bean
        fun filterChain(http: HttpSecurity): SecurityFilterChain {
            http
                .httpBasic { it.disable() }
                .csrf { it.disable() }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .authorizeHttpRequests {
                    it.requestMatchers("/auth/signup", "/auth/login").anonymous()
                        .requestMatchers("/auth/info/**").hasRole("MEMBER")
                        .anyRequest().permitAll()
                }
                .addFilterBefore(
                    JwtAuthenticationFilter(jwtTokenProvider),
                    UsernamePasswordAuthenticationFilter::class.java
                )
            return http.build()
        }

        @Bean
        fun PasswordEncoder(): PasswordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder()

}