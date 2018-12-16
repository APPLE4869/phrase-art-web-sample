package PhraseArt.query.Dto.UpdateRequest

import java.time.LocalDateTime

data class PhraseModificationRequestQueryDto(
    val id: String,
    val finished: Boolean,
    val decisionExpiresAt: LocalDateTime,
    val finalDecisionResult: String?,
    val requestedCategoryId: String,
    val requestedCategoryName: String,
    val requestedSubcategoryId: String?,
    val requestedSubcategoryName: String?,
    val requestedPhraseContent: String,
    val requestedPhraseAuthorName: String,
    val currentCategoryId: String,
    val currentCategoryName: String,
    val currentSubcategoryId: String?,
    val currentSubcategoryName: String?,
    val currentPhraseContent: String,
    val currentPhraseAuthorName: String,
    val approvedCount: Int,
    val rejectedCount: Int
)
