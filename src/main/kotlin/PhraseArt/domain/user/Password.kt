package PhraseArt.domain.user

import PhraseArt.domain.support.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class Password : Value {
    private val value: String

    constructor(aPassword: String) {
        this.assertArgumentNotEmpty(aPassword, "パスワードを入力してください")
        this.assertArgumentLength(aPassword, 8, 64, "パスワードは8文字以上64文字以内にしてください")
        this.assertRegularExpression(aPassword, PASSWORD_REGEX, "パスワードの形式が不正です")
        this.value = aPassword
    }

    companion object {
        // パスワードに利用可能な記号
        // 正規表現の[ ]内に挿入する文字列なので、 [ ] - \ ^ - の6種類は\(バックスラッシュ)でエスケープするようにしている。
        private val ALLOW_PASSWORD_SYMBOL: String = """!"#$%&\'()*+,\-.\\/:;<=>?@\[\]\^_`{|}~"""
        private val PASSWORD_REGEX: Regex = Regex("[a-zA-Z0-9${ALLOW_PASSWORD_SYMBOL}]+")
    }

    fun encryptedPassword(): String {
        return BCryptPasswordEncoder().encode(value)
    }

    fun isEquals(aHashedPassword: String): Boolean {
        return BCryptPasswordEncoder().matches(value, aHashedPassword)
    }
}
