package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestDetailQuery

import java.sql.Timestamp

data class SubcategoryModificationRequestDao(
    val updateRequestId: String,
    val finished: Boolean,
    val decisionExpiresAt: Timestamp,
    val finalDecisionResult: String?,
    val requestedSubcategoryId: String,
    val requestedSubcategoryName: String,
    val requestedSubcategoryIntroduction: String?,
    val requestedSubcategoryImagePath: String?,
    val currentCategoryId: String,
    val currentCategoryName: String,
    val currentCategoryVideoOnDemandAssociated: Boolean,
    val currentSubcategoryName: String,
    val currentSubcategoryIntroduction: String?,
    val currentSubcategoryImagePath: String?,
    val approvedCount: Long,
    val rejectedCount: Long
)
