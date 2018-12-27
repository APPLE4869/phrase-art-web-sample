package PhraseArt.query.Dto.UpdateRequest

import java.time.LocalDateTime

data class UpdateRequestCommentQueryDto(
    val id: String,
    val userId: String,
    val username: String,
    val userImageUrl: String?,
    val content: String,
    val createdAt: LocalDateTime
)
