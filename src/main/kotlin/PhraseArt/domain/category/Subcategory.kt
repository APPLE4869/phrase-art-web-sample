package PhraseArt.domain.category

import PhraseArt.domain.support.Entity
import PhraseArt.domain.updateRequest.phrase.SubcategoryModificationRequest
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.springframework.web.multipart.MultipartFile

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

    companion object {
        val IMAGE_PATH_SYMBOL = "subcategory"
    }

    fun isAnyChanged(aRequestedName: String, aRequestedImage: MultipartFile?, aRequestedIntroduction: String, aRequestedVideoOnDemands: List<VideoOnDemand>?): Boolean {
        if (name != aRequestedName) return true

        if (aRequestedImage != null) return true

        if (introduction != aRequestedIntroduction) return true

        val currentVideoOnDemandsNameKeys = videoOnDemands?.map { it.nameKey }?.sorted() ?: emptyList()
        val requestedVideoOnDemandsNameKeys = aRequestedVideoOnDemands?.map { it.nameKey }?.sorted() ?: emptyList()

        if (!currentVideoOnDemandsNameKeys.equals(requestedVideoOnDemandsNameKeys)) return true

        return false
    }

    fun reflectModificationRequest(aRequest: SubcategoryModificationRequest) {
        this.name = aRequest.requestedSubcategoryName
        this.introduction = aRequest.requestedSubcategoryIntroduction
        this.imagePath = aRequest.requestedSubcategoryImagePath
        this.videoOnDemands = aRequest.videoOnDemands
    }
}
