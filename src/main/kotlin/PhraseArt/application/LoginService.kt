package PhraseArt.application

import PhraseArt.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.User.withUsername
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class LoginService(
        @Autowired val userRepository: UserRepository
) : UserDetailsService {

    @ResponseBody
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.userOfUsername(username)
            ?: throw UsernameNotFoundException(username)

        // このオブジェクトはJWTに格納されるものなので、
        // usernameの代わりにidを設定する。
        return User.withUsername(user.id.value)
                .password(user.hashedPassword)
                .authorities("GENERAL")
                .build()
    }
}
