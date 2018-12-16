package PhraseArt.domain.updateRequest

import PhraseArt.domain.support.Entity
import PhraseArt.domain.user.UserId

// Valueオブジェクトとして扱って、UpdateRequestに入れる方法もあったが、１Requestあたりのコメント数が多くなると、メモリの消費が激しくなるので、
// CommentについてはEntityとして扱い、UpdateRequestの外に切り出すことにした。
class UpdateRequestComment : Entity {
    val id: UpdateRequestCommentId
    val userId: UserId
    val updateRequestId: UpdateRequestId
    val content: String

    constructor(anId: UpdateRequestCommentId, anUserId: UserId, anUpdateRequestId: UpdateRequestId, aContent: String) {
        this.assertArgumentNotEmpty(aContent, "コメントの内容を入力してください")
        assertArgumentLength(aContent, 500, "コメントは500文字以内で入力してください")

        this.id = anId
        this.userId = anUserId
        this.updateRequestId = anUpdateRequestId
        this.content = aContent
    }
}
