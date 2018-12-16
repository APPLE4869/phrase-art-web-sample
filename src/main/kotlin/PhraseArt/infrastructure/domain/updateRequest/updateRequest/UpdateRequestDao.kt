package PhraseArt.infrastructure.domain.updateRequest.updateRequest

import java.sql.Timestamp

data class UpdateRequestDao(
    // UpdateRequest(更新申請)共通のプロパティ
    val updateRequestId: String,
    val userId: String,
    val type: String,
    val finished: Boolean,
    val decisionExpiresAt: Timestamp,
    val finalDecisionResult: String?,

    // PhraseRegistrationRequest(名言登録申請)用のプロパティ
    val prRequestedCategoryId: String?,
    val prRequestedSubcategoryId: String?,
    val prRequestedSubcategoryName: String?,
    val prRequestedPhraseContent: String?,
    val prRequestedPhraseAuthorName: String?,

    // PhraseModificationRequest(名言修正申請)用のプロパティ
    val pmRequestedPhraseId: String?,
    val pmRequestedCategoryId: String?,
    val pmRequestedSubcategoryId: String?,
    val pmRequestedSubcategoryName: String?,
    val pmRequestedPhraseContent: String?,
    val pmRequestedPhraseAuthorName: String?,
    val pmCurrentCategoryId: String?,
    val pmCurrentSubcategoryId: String?,
    val pmCurrentSubcategoryName: String?,
    val pmCurrentPhraseContent: String?,
    val pmCurrentPhraseAuthorName: String?,

    // PhraseDeletionRequest(名言削除申請)用のプロパティ
    val pdRequestedPhraseId: String?,
    val pdCurrentCategoryId: String?,
    val pdCurrentSubcategoryId: String?,
    val pdCurrentSubcategoryName: String?,
    val pdCurrentPhraseContent: String?,
    val pdCurrentPhraseAuthorName: String?,

    // SubcategoryModificationRequest(サブカテゴリー修正申請)用のプロパティ
    val smRequestedSubcategoryId: String?,
    val smRequestedSubcategoryName: String?,
    val smRequestedSubcategoryIntroduction: String?,
    val smRequestedSubcategoryImagePath: String?,
    val smCurrentCategoryId: String?,
    val smCurrentSubcategoryName: String?,
    val smCurrentSubcategoryIntroduction: String?,
    val smCurrentSubcategoryImagePath: String?
)
