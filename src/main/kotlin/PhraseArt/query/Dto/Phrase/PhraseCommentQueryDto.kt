package PhraseArt.query.Dto.Phrase

import java.sql.Timestamp

data class PhraseCommentQueryDto(
    val id: String,
    val userId: String,
    val username: String,
    val userImageUrl: String?,
    val content: String,
    val createdAt: Timestamp
)
