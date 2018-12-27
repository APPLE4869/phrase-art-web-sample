package PhraseArt.support.security

import PhraseArt.support.security.SecurityConstants.EXPIRATION_TIME
import PhraseArt.support.security.SecurityConstants.HEADER_STRING
import PhraseArt.support.security.SecurityConstants.LOGIN_ID
import PhraseArt.support.security.SecurityConstants.LOGIN_URL
import PhraseArt.support.security.SecurityConstants.PASSWORD
import PhraseArt.support.security.SecurityConstants.SECRET
import PhraseArt.support.security.SecurityConstants.TOKEN_PREFIX
import io.jsonwebtoken.Jwts
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import PhraseArt.presentation.api.form.LoginForm
import javax.naming.AuthenticationException

class JWTAuthenticationFilter : UsernamePasswordAuthenticationFilter {
    private val bCryptPasswordEncoder: BCryptPasswordEncoder;

    constructor(authenticationManager: AuthenticationManager, bCryptPasswordEncoder: BCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        // ログイン用のpathを変更する
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher(LOGIN_URL, "POST"))

        // ログイン用のIDとPasswordのパラメータ名を変更する
        setUsernameParameter(LOGIN_ID);
        setPasswordParameter(PASSWORD);
    }

    // 認証の処理
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(req: HttpServletRequest,
                                       res: HttpServletResponse?): Authentication {
        try {
            // requestパラメータからユーザ情報を読み取る
            val loginForm = ObjectMapper().readValue(req.inputStream, LoginForm::class.java)

            return authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            loginForm.username,
                            loginForm.password,
                            ArrayList<GrantedAuthority>())
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    // 認証に成功した場合の処理
    @Throws(IOException::class, ServletException::class)
    protected override fun successfulAuthentication(req: HttpServletRequest,
                                           res: HttpServletResponse,
                                           chain: FilterChain,
                                           auth: Authentication) {
        val token = Jwts.builder()
                .setSubject((auth.getPrincipal() as User).getUsername())
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET.toByteArray())
                .compact()
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
    }
}
