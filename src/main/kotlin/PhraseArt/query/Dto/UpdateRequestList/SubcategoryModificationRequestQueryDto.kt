package PhraseArt.query.Dto.UpdateRequestList

import java.time.LocalDateTime

data class SubcategoryModificationRequestQueryDto(
    override val id: String,
    override val userId: String,
    override val type: String,
    override val finished: Boolean,
    override val decisionExpiresAt: LocalDateTime,
    override val finalDecisionResult: String?,
    override val approvedCount: Int,
    override val rejectedCount: Int,
    override val commentCount: Int,
    val categoryId: String,
    val categoryName: String,
    val subcategoryId: String,
    val subcategoryName: String,
    val subcategoryIntroduction: String
) : UpdateRequestQueryDto
