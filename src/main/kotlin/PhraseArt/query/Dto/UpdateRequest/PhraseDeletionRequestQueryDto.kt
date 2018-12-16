package PhraseArt.query.Dto.UpdateRequest

import java.time.LocalDateTime

data class PhraseDeletionRequestQueryDto(
    val id: String,
    val finished: Boolean,
    val decisionExpiresAt: LocalDateTime,
    val finalDecisionResult: String?,
    val categoryId: String,
    val categoryName: String,
    val subcategoryId: String?,
    val subcategoryName: String?,
    val phraseContent: String,
    val phraseAuthorName: String,
    val approvedCount: Int,
    val rejectedCount: Int
)
