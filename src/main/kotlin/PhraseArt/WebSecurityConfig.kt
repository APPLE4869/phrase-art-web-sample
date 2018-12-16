package PhraseArt

import PhraseArt.support.security.JWTAuthenticationFilter
import PhraseArt.support.security.JWTAuthorizationFilter
import PhraseArt.support.security.SecurityConstants.LOGIN_URL
import PhraseArt.support.security.SecurityConstants.SIGNUP_URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig: WebSecurityConfigurerAdapter() {
    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // 認可の設定
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/policy", "/api/public/**", LOGIN_URL, SIGNUP_URL)
                .permitAll()
                .anyRequest()
                .authenticated()

        // CSRFの設定 (APIでの認証になるので、CSRFの設定はしない)
        http.csrf().disable()

        // ログインの設定
        http.addFilter(JWTAuthenticationFilter(authenticationManager(), bCryptPasswordEncoder()))
                .addFilter(JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Autowired
    @Throws(Exception::class)
    fun configureAuth(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService>(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder())
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
