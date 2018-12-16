package PhraseArt.support.security

import PhraseArt.support.security.SecurityConstants.HEADER_STRING
import PhraseArt.support.security.SecurityConstants.SECRET
import PhraseArt.support.security.SecurityConstants.TOKEN_PREFIX
import java.util.ArrayList
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.core.userdetails.UserDetailsService

class JWTAuthorizationFilter : BasicAuthenticationFilter {
    constructor(authenticationManager: AuthenticationManager): super(authenticationManager) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(req: HttpServletRequest,
                                  res: HttpServletResponse,
                                  chain: FilterChain) {
        val header = req.getHeader(HEADER_STRING)

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res)
            return
        }

        // AuthorizationヘッダのBearer Prefixである場合
        val authentication = getAuthentication(req)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_STRING)

        if (token != null) {
            // JWTをパース
            val user = Jwts.parser()
                    .setSigningKey(SECRET.toByteArray())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject()

            return if (user != null) {
                // TODO : Credentials(第二引数)に電子署名を入れるようにする。
                UsernamePasswordAuthenticationToken(user, null, ArrayList<GrantedAuthority>())
            } else null
        }

        return null
    }
}
