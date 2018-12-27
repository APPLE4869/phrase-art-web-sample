package PhraseArt.application

import PhraseArt.domain.user.Password
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import PhraseArt.domain.user.User
import PhraseArt.domain.user.UserId
import PhraseArt.domain.user.UserRepository
import PhraseArt.presentation.api.form.PasswordForm
import PhraseArt.presentation.api.form.SignupForm
import PhraseArt.presentation.api.form.UsernameForm
import PhraseArt.query.Dto.User.UserQueryDto
import PhraseArt.query.User.UserQuery
import PhraseArt.support.BadRequestException
import PhraseArt.support.ForbiddenException
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

    fun updateUsername(form: UsernameForm, principalName: String) {
        val user = userRepository.userOfId(UserId(principalName)) ?:
            throw ForbiddenException("申請する権限がありません")

        val aUsername =  form.username
        if (userRepository.userOfUsername(aUsername) != null)
            throw BadRequestException("同じユーザー名がすでに登録済みです")

        user.changeUsername(aUsername)

        userRepository.store(user)
    }

    fun updatePassword(form: PasswordForm, principalName: String) {
        val user = userRepository.userOfId(UserId(principalName)) ?:
            throw ForbiddenException("申請する権限がありません")

        user.changePassword(Password(form.currentPassword), Password(form.newPassword), Password(form.confirmNewPassword))

        userRepository.store(user)
    }
}
