package PhraseArt.query.Dto.UpdateRequest

import java.time.LocalDateTime

data class SubcategoryModificationRequestQueryDto(
    val id: String,
    val finished: Boolean,
    val decisionExpiresAt: LocalDateTime,
    val finalDecisionResult: String?,
    val requestedSubcategoryId: String,
    val requestedSubcategoryName: String,
    val requestedSubcategoryIntroduction: String?,
    val requestedSubcategoryImageUrl: String?,
    val requestedVideoOnDemandNameKeys: List<String>?,
    val currentCategoryId: String,
    val currentCategoryName: String,
    val currentCategoryVideoOnDemandAssociated: Boolean,
    val currentSubcategoryName: String,
    val currentSubcategoryIntroduction: String?,
    val currentSubcategoryImageUrl: String?,
    val currentVideoOnDemandNameKeys: List<String>?,
    val approvedCount: Int,
    val rejectedCount: Int
)
