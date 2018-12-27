package PhraseArt.query.Dto.Phrase

import java.time.LocalDateTime

data class PhraseCommentQueryDto(
    val id: String,
    val userId: String,
    val username: String,
    val userImageUrl: String?,
    val content: String,
    val createdAt: LocalDateTime
)
