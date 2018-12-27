package PhraseArt.query.Dto.UpdateRequestList

import java.time.LocalDateTime

interface UpdateRequestQueryDto {
    val id: String
    val userId: String
    val type: String
    val finished: Boolean
    val decisionExpiresAt: LocalDateTime
    val finalDecisionResult: String?
    val approvedCount: Int
    val rejectedCount: Int
    val commentCount: Int
}
