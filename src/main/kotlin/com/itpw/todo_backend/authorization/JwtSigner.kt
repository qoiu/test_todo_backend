package com.itpw.todo_backend.authorization

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.UnsupportedEncodingException
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey


@Service
class JwtSigner {
    @Value("\${auth.jwt.issuer}")
    lateinit var issuer: String
    @Value("\${auth.jwt.secret}")
    lateinit var secret: String
    @Value("\${auth.jwt.ttl-in-seconds}")
    lateinit var timeToLive: String

    private var secretKey: SecretKey? = null

    // TODO: заполнить application.properties

    fun generateToken(username: String?): String? {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(timeToLive.toLong()))))
            .signWith(secretKey)
            .compact()
    }


    fun setUpSecretKey() {
        secretKey = try {
            Keys.hmacShaKeyFor(secret.toByteArray(charset("UTF-8")))
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException("Error generating JWT Secret Key", e)
        }
    }

    fun getJwtSubject(jwt: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(jwt)
            .body
            .subject
    }
}