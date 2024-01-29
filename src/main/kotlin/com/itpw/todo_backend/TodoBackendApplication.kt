package com.itpw.todo_backend

import com.itpw.todo_backend.authorization.SecurityFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@SpringBootApplication
class TodoBackendApplication

fun main(args: Array<String>) {
	runApplication<TodoBackendApplication>(*args)
}

@Configuration
@EnableConfigurationProperties()
@EnableScheduling
class AppConfiguration @Autowired constructor(
	private val filter: SecurityFilter
){

	@Bean
	@Throws(java.lang.Exception::class)
	fun configure(http: HttpSecurity): SecurityFilterChain? {
		http
			.cors {  }.csrf().disable().exceptionHandling()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
//			.authorizeHttpRequests {
//				it.requestMatchers("/media/get/**", "/hello/**","/authorization/**", "/articles/articles/by_key/**", "/articles/articles/by_id/**", "/ws/**", "/itpw-docs/**", "/mono/**").permitAll()
//					.anyRequest().authenticated()
//			}
			.authorizeHttpRequests()
			.requestMatchers("/media/get/**", "/authorization/**", "/hello/**", "/articles/articles/by_id/**", "/ws/**", "/itpw-docs/**", "/mono/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
		return http.build()
	}
}

