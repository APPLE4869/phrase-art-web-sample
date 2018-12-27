package PhraseArt.domain.updateRequest.phrase

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.phrase.Phrase
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.domain.updateRequest.*
import PhraseArt.domain.user.UserId
import java.time.LocalDateTime

class PhraseDeletionRequest : UpdateRequest {
    val requestedPhraseId: PhraseId
    val currentCategoryId: CategoryId
    val currentSubcategoryId: SubcategoryId?
    var currentSubcategoryName: String?
        set(value) {
            this.assertArgumentLength(value, 36, "サブカテゴリー名は36文字以内にしてください")
            field = value
        }
    var currentPhraseContent: String
        set(value) {
            this.assertArgumentNotEmpty(value, "名言の内容を入力してください")
            this.assertArgumentLength(value, 500, "名言の内容は500文字以内にしてください")
            field = value
        }
    var currentPhraseAuthorName: String
        set(value) {
            this.assertArgumentNotEmpty(value, "作者名を入力してください")
            this.assertArgumentLength(value, 36, "作者は36文字以内にしてください")
            field = value
        }

    constructor(
        anId: UpdateRequestId,
        anUserId: UserId,
        aType: UpdateRequestType,
        finished: Boolean,
        expiresDatetime: LocalDateTime,
        aFinalDecisionResult: FinalDecisionResultType?,
        decisions: MutableSet<Decision>,
        aRequestedPhraseId: PhraseId,
        aCurrentCategoryId: CategoryId,
        aCurrentSubcategoryId: SubcategoryId?,
        aCurrentSubcategoryName: String?,
        aCurrentPhraseContent: String,
        aCurrentPhraseAuthorName: String
    ): super(anId, anUserId, aType, finished, expiresDatetime, aFinalDecisionResult, decisions) {
        this.requestedPhraseId = aRequestedPhraseId
        this.currentCategoryId = aCurrentCategoryId
        this.currentSubcategoryId = aCurrentSubcategoryId
        this.currentSubcategoryName = aCurrentSubcategoryName
        this.currentPhraseContent = aCurrentPhraseContent
        this.currentPhraseAuthorName = aCurrentPhraseAuthorName
    }

    companion object {
        fun create(
            anId: UpdateRequestId,
            anUserId: UserId,
            aRequestedPhraseId: PhraseId,
            currentPhrase: Phrase,
            currentSubcategoryName: String? // TODO : PhraseがSubcategoryオブジェクトを保有する状態に改修する。
        ): PhraseDeletionRequest {
            return PhraseDeletionRequest(
                anId,
                anUserId,
                UpdateRequestType.PHRASE_DELETION,
                false,
                LocalDateTime.now().plusMinutes(240),
                null,
                mutableSetOf(),
                aRequestedPhraseId,
                currentPhrase.categoryId,
                currentPhrase.subcategoryId,
                currentSubcategoryName,
                currentPhrase.content,
                currentPhrase.authorName
            )
        }
    }

    // 最終判定結果を返す
    // TODO : 現状はただ数を見ているだけだが、今後はユーザーごとにスコアを持たせて、そのスコアの総合値で判定する仕様に変更する。
    override fun calcFinalDecision(): FinalDecisionResultType {
        if (approvedDecisions().count() > rejectedDecisions().count() * 3)
            return FinalDecisionResultType.APPROVE
        return FinalDecisionResultType.REJECT
    }
}
