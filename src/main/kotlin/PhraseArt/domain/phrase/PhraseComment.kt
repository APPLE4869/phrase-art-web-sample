package PhraseArt.domain.phrase

import PhraseArt.domain.support.Entity
import PhraseArt.domain.user.UserId

// Valueオブジェクトとして扱って、Phraseに入れる方法もあったが、1Phraseあたりコメント数が多くなると、メモリの消費が激しくなるので、
// CommentについてはEntityとして扱い、Phraseの外に切り出すことにした。
class PhraseComment : Entity {
    val id: PhraseCommentId
    val userId: UserId
    val phraseId: PhraseId
    val content: String

    constructor(anId: PhraseCommentId, anUserId: UserId, aPhraseId: PhraseId, aContent: String) {
        this.assertArgumentNotEmpty(aContent, "コメントの内容を入力してください")
        assertArgumentLength(aContent, 500, "コメントは500文字以内で入力してください")
        this.id = anId
        this.userId = anUserId
        this.phraseId = aPhraseId
        this.content = aContent
    }
}
