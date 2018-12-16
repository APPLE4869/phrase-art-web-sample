package PhraseArt.domain.category

import PhraseArt.domain.support.Entity
import PhraseArt.domain.updateRequest.phrase.SubcategoryModificationRequest

class Subcategory : Entity {
    val id: SubcategoryId
    var category: Category
    var name: String
        set(value) {
            this.assertArgumentNotEmpty(value, "サブカテゴリー名を入力してください")
            this.assertArgumentLength(value, 36, "サブカテゴリー名は36文字以内にしてください")
            field = value
        }
    var imagePath: String?
        set(value) {
            this.assertArgumentLength(value, 100, "画像パスは100文字以内にしてください")
            field = value
        }
    var introduction: String?
        set(value) {
            this.assertArgumentLength(value, 1000, "紹介文は1000文字以内にしてください")
            field = value
        }
    var videoOnDemands: MutableList<VideoOnDemand>?
        set(value) {
            if (!category.isAssociatedVideoOnDemand())
                this.assertArgumentNull(value, "対象のカテゴリーは動画配信サービスに対応していません")
            field = value
        }

    constructor(
        anId: SubcategoryId,
        aCategory: Category,
        aName: String,
        anImagePath: String?,
        anIntroduction: String?,
        videoOnDemands: MutableList<VideoOnDemand>?
    ) {
        this.id = anId
        this.category = aCategory
        this.name = aName
        this.imagePath = anImagePath
        this.introduction = anIntroduction
        this.videoOnDemands = videoOnDemands
    }

    // TODO : 画像も項目に含める
    fun isAnyChanged(aName: String, anIntroduction: String, videoOnDemands: List<VideoOnDemand>?): Boolean {
        if (this.name != aName) return true

        if (this.introduction != anIntroduction) return true

        // videoOnDemandsの比較については、sequence順に必ず並んでいることを担保しているとして、比較の際に順番のことは考えないでよいとしている。
        if (this.videoOnDemands?.map { it.nameKey } != videoOnDemands?.map { it.nameKey }) return true

        return false
    }

    fun reflectModificationRequest(aRequest: SubcategoryModificationRequest) {
        this.name = aRequest.requestedSubcategoryName
        this.introduction = aRequest.requestedSubcategoryIntroduction
        this.imagePath = aRequest.requestedSubcategoryImagePath
        // this.videoOnDemands = aRequest.videoOnDemands TODO
    }
}
