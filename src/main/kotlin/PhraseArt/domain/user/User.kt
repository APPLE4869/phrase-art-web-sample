package PhraseArt.domain.user

import PhraseArt.domain.support.Entity
import PhraseArt.support.AssertionConcern
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class User : Entity {
    val id: UserId
    var username: String
        set(value) {
            this.assertArgumentNotEmpty(value, "ユーザー名を入力してください")
            this.assertRegularExpression(value, USERNAME_REGEX, "ユーザー名の形式が不正です")
            this.assertArgumentLength(value, 20, "ユーザー名は20文字以内にしてください")
            field = value
        }
    var hashedPassword: String
        set(value) {
            this.assertArgumentNotEmpty(value, "パスワードを入力してください")
            field = value
        }
    var freezen: Boolean
    var profile: Profile?

    companion object {
        val IMAGE_PATH_SYMBOL = "user"
        private val USERNAME_REGEX: Regex = Regex("[a-zA-Z0-9]+")

        fun create(anUserId: UserId, anUsername: String, aPassword: String): User {
            return User(anUserId, anUsername, Password(aPassword).encryptedPassword(), false, null)
        }
    }

    constructor(anId: UserId, anUsername: String, aHashedPassword: String, aFreezen: Boolean, aProfile: Profile?) {
        this.id = anId
        this.username = anUsername
        this.hashedPassword = aHashedPassword
        this.freezen = aFreezen
        this.profile = aProfile
    }

    fun changeProfileImage(imagePath: String) {
        if (profile != null)
            // profileがimmutableのため、changeImageを実行できない可能性があるのでcastしろと警告を受けるので、
            // nullの時は実行しないことを明示している。より良いやり方が見つかったら改修する。
            profile?.let { it.changeImage(imagePath) }
        else
            this.profile = Profile(imagePath)
    }
}
