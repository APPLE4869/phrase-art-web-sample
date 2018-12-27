package PhraseArt.domain.updateRequest.phrase

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.Subcategory
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.category.VideoOnDemand
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.domain.updateRequest.*
import PhraseArt.domain.user.UserId
import java.time.LocalDateTime

class SubcategoryModificationRequest: UpdateRequest {
    // TODO : プロパティが多くて可読性も落ちてきたので、requestedとcurrentの2種のValueオブジェクトに切り出す予定。
    val requestedSubcategoryId: SubcategoryId
    var requestedSubcategoryName: String
        set(value) {
            this.assertArgumentNotEmpty(value, "サブカテゴリー名を入力してください")
            this.assertArgumentLength(value, 36, "サブカテゴリー名は36文字以内にしてください")
            field = value
        }
    var requestedSubcategoryIntroduction: String?
        set(value) {
            this.assertArgumentLength(value, 1000, "紹介文は1000文字以内にしてください")
            field = value
        }
    var requestedSubcategoryImagePath: String?
        set(value) {
            this.assertArgumentLength(value, 100, "画像パスは100文字以内にしてください")
            field = value
        }
    val currentCategoryId: CategoryId
    var currentSubcategoryName: String
        set(value) {
            this.assertArgumentNotEmpty(value, "サブカテゴリー名を入力してください")
            this.assertArgumentLength(value, 36, "サブカテゴリー名は36文字以内にしてください")
            field = value
        }
    var currentSubcategoryIntroduction: String?
        set(value) {
            this.assertArgumentLength(value, 1000, "紹介文は1000文字以内にしてください")
            field = value
        }
    var currentSubcategoryImagePath: String?
        set(value) {
            this.assertArgumentLength(value, 100, "画像パスは100文字以内にしてください")
            field = value
        }
    var videoOnDemands: MutableList<VideoOnDemand>?

    constructor(
        anId: UpdateRequestId,
        anUserId: UserId,
        aType: UpdateRequestType,
        finished: Boolean,
        expiresDatetime: LocalDateTime,
        aFinalDecisionResult: FinalDecisionResultType?,
        decisions: MutableSet<Decision>,
        aRequestedSubcategoryId: SubcategoryId,
        aRequestedSubcategoryName: String,
        aRequestedSubcategoryIntroduction: String?,
        aRequestedSubcategoryImagePath: String?,
        aCurrentCategoryId: CategoryId,
        aCurrentSubcategoryName: String,
        aCurrentSubcategoryIntroduction: String?,
        aCurrentSubcategoryImagePath: String?,
        videoOnDemands: MutableList<VideoOnDemand>?
    ): super(anId, anUserId, aType, finished, expiresDatetime, aFinalDecisionResult, decisions) {
        this.decisions = decisions
        this.requestedSubcategoryId = aRequestedSubcategoryId
        this.requestedSubcategoryName = aRequestedSubcategoryName
        this.requestedSubcategoryIntroduction = aRequestedSubcategoryIntroduction
        this.requestedSubcategoryImagePath = aRequestedSubcategoryImagePath
        this.currentCategoryId = aCurrentCategoryId
        this.currentSubcategoryName = aCurrentSubcategoryName
        this.currentSubcategoryIntroduction = aCurrentSubcategoryIntroduction
        this.currentSubcategoryImagePath = aCurrentSubcategoryImagePath
        this.videoOnDemands = videoOnDemands
    }

    companion object {
        fun create(
            anId: UpdateRequestId,
            anUserId: UserId,
            aRequestedSubcategoryId: SubcategoryId,
            aRequestedSubcategoryName: String,
            aRequestedSubcategoryIntroduction: String?,
            aRequestedSubcategoryImagePath: String?,
            currentSubcategory: Subcategory,
            videoOnDemands: List<VideoOnDemand>?
        ): SubcategoryModificationRequest {
            return SubcategoryModificationRequest(
                anId,
                anUserId,
                UpdateRequestType.SUBCATEGORY_MODIFICATION,
                false,
                LocalDateTime.now().plusMinutes(240),
                null,
                mutableSetOf(),
                aRequestedSubcategoryId,
                aRequestedSubcategoryName,
                aRequestedSubcategoryIntroduction,
                aRequestedSubcategoryImagePath,
                currentSubcategory.category.id,
                currentSubcategory.name,
                currentSubcategory.introduction,
                currentSubcategory.imagePath,
                videoOnDemands?.toMutableList()
            )
        }
    }

    // 最終判定結果を返す
    // TODO : 現状はただの多数決だが、今後はユーザーごとにスコアを持たせて、そのスコアの総合値で判定する仕様に変更する。
    override fun calcFinalDecision(): FinalDecisionResultType {
        if (approvedDecisions().count() > rejectedDecisions().count())
            return FinalDecisionResultType.APPROVE
        return FinalDecisionResultType.REJECT
    }
}
