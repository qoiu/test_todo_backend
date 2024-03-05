package com.itpw.todo_backend

import com.itpw.todo_backend.authorization.SecurityFilter
import com.itpw.todo_backend.utils.*
import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CommonsRequestLoggingFilter

@SpringBootApplication
class TodoBackendApplication{

	companion object{
		val log = LoggerFactory.getLogger(TodoBackendApplication::class.java)
	}
}

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
	fun logFilter(): CommonsRequestLoggingFilter {
		return CommonsRequestLoggingFilter().apply {
			setIncludeQueryString(true)
			setIncludePayload(true)
			setIncludeHeaders(true)
			setMaxPayloadLength(100_000)
		}
	}

	@Bean
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
			.requestMatchers("/media/get/**", "/authorization/**", "/articles/articles/by_id/**", "/ws/**", "/itpw-docs/**", "/mono/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
		return http.build()
	}


	@Bean
	fun corsConfigurationSource(): CorsConfigurationSource {
		val configuration = CorsConfiguration()
		configuration.allowedOriginPatterns = listOf("*")
		configuration.setMaxAge(3600L)
		configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
		configuration.addAllowedHeader("*")
		val source = UrlBasedCorsConfigurationSource()
		source.registerCorsConfiguration("/**", configuration)
		return source
	}


	@Bean
	fun pagingFormatter(): PagingFormatter {
		return PagingFormatter()
	}

}


@ControllerAdvice
class GlobalExceptionHandler {
	@ExceptionHandler(Exception::class)
	fun handleException(e: Exception): ResponseEntity<DetailsResponse> {
		e.printStackTrace()
		return ResponseEntity.badRequest().body(DetailsResponse("Произошла ошибка"))
	}

	@ExceptionHandler(ConstraintViolationException::class)
	fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<DetailsResponse> {
		return ResponseEntity.badRequest().body(DetailsResponse(e.constraintViolations.first().message))
	}

	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<DetailsResponse> {
		return ResponseEntity.badRequest().body(DetailsResponse(e.bindingResult?.fieldErrors?.first()?.defaultMessage ?: ""))
	}

	@ExceptionHandler(DetailException::class)
	fun handleDetailException(e: DetailException): ResponseEntity<DetailsResponse> {
		return ResponseEntity.badRequest().body(DetailsResponse(e.details))
	}


	@ExceptionHandler(NotFoundException::class)
	fun handleNotFoundException(e: NotFoundException): ResponseEntity<DetailsResponse> {
		return ResponseEntity.status(404).body(DetailsResponse(e.details))
	}

	@ExceptionHandler(ForbiddenException::class)
	fun handleForbiddenException(e: ForbiddenException): ResponseEntity<DetailsResponse> {
		return ResponseEntity.status(403).body(DetailsResponse(e.details))
	}
}