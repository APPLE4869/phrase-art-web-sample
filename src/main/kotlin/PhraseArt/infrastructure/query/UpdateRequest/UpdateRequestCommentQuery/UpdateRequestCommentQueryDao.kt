package PhraseArt.infrastructure.query.Phrase.PhraseCommentQuery

import java.sql.Timestamp

data class UpdateRequestCommentQueryDao(
    val commentId: String,
    val userId: String,
    val username: String,
    val userImagePath: String?,
    val content: String,
    val createAt: Timestamp
)
