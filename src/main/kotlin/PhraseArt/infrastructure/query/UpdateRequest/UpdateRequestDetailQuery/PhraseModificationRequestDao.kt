package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestDetailQuery

import java.sql.Timestamp

data class PhraseModificationRequestDao(
    val updateRequestId: String,
    val finished: Boolean,
    val decisionExpiresAt: Timestamp,
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
    val approvedCount: Long,
    val rejectedCount: Long
)
