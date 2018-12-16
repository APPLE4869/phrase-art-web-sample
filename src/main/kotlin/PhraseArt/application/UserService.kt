package PhraseArt.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import PhraseArt.domain.user.User
import PhraseArt.domain.user.UserId
import PhraseArt.domain.user.UserRepository
import PhraseArt.presentation.api.form.SignupForm
import PhraseArt.query.Dto.UserQueryDto
import PhraseArt.query.UserQuery
import PhraseArt.support.NotFoundException

@Service
class UserService(
    @Autowired val userRepository: UserRepository,
    @Autowired val userQuery : UserQuery
) {
    fun signup(user: SignupForm): User {
        if (userRepository.userOfUsername((user.username)) !== null)
            throw IllegalArgumentException("このユーザー名はすでに登録されています")

        val user = User.create(userRepository.nextIdentity(), user.username, user.password)
        userRepository.store(user)

        return user
    }

    fun currentUser(principalName: String): UserQueryDto {
        return userQuery.userOfId(UserId(principalName)) ?:
            throw NotFoundException("該当するユーザーが見つかりませんでした")
    }
}
