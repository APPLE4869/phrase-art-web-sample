package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestListQuery

import java.sql.Timestamp

data class UpdateRequestListItemDao(
        // UpdateRequest共通のプロパティ
        val updateRequestId: String,
        val userId: String,
        val type: String,
        val finished: Boolean,
        val decisionExpiresAt: Timestamp,
        val finalDecisionResult: String?,

        // 名言登録申請用のプロパティ
        val prRequestedCategoryId: String?,
        val prRequestedSubcategoryId: String?,
        val prRequestedSubcategoryName: String?,
        val prRequestedPhraseContent: String?,
        val prRequestedPhraseAuthorName: String?,
        val prcCategoryName: String?,

        // 名言修正申請用のプロパティ
        val pmRequestedSubcategoryId: String?,
        val pmRequestedSubcategoryName: String?,
        val pmRequestedPhraseContent: String?,
        val pmRequestedPhraseAuthorName: String?,
        val pmcCategoryId: String?,
        val pmcCategoryName: String?,

        // 名言削除申請用のプロパティ
        val pdPhraseContent: String?,
        val pdAuthorName: String?,
        val pdcCategoryId: String?,
        val pdcCategoryName: String?,
        val pdSubcategoryId: String?,
        val pdSubcategoryName: String?,

        // サブカテゴリー修正申請用のプロパティ
        val smRequestedSubcategoryId: String?,
        val smRequestedSubcategoryName: String?,
        val smRequestedSubcategoryIntroduction: String?,
        val smcCategoryId: String?,
        val smcCategoryName: String?,

        // Decisionの件数
        val approvedCount: Long,
        val rejectedCount: Long
)
