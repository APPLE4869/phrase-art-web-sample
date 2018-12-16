package PhraseArt.query.Dto.UpdateRequest

import java.sql.Timestamp

data class UpdateRequestCommentQueryDto(
    val id: String,
    val userId: String,
    val username: String,
    val userImageUrl: String?,
    val content: String,
    val createdAt: Timestamp
)
