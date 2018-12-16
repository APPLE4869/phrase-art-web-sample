package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestDetailQuery

import java.sql.Timestamp

data class PhraseDeletionRequestDao(
    val updateRequestId: String,
    val finished: Boolean,
    val decisionExpiresAt: Timestamp,
    val finalDecisionResult: String?,
    val categoryId: String,
    val categoryName: String,
    val subcategoryId: String?,
    val subcategoryName: String?,
    val phraseContent: String,
    val phraseAuthorName: String,
    val approvedCount: Long,
    val rejectedCount: Long
)
