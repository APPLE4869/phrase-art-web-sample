package PhraseArt.domain.phrase

import PhraseArt.domain.user.User
import org.springframework.stereotype.Component

@Component
class PhraseCommentDomainService {
    // ユーザーがコメント可能であることを確認
    fun assertUserCommentable(user: User) {
        // TODO : 一定時間内の投稿数が以上に多い場合は、ここで例外を発生させる。
    }
}
