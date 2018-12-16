package PhraseArt.domain.category

import PhraseArt.domain.support.Entity

class Category : Entity {
    val id: CategoryId
    var name: String
        set(value) {
            this.assertArgumentNotEmpty(value, "カテゴリー名を入力してください")
            this.assertArgumentLength(value, 36, "カテゴリー名は36文字以内にしてください")
            field = value
        }
    var imagePath: String?
    var sequence: Int
    val videoOnDemandAssociated: Boolean

    constructor(anId: CategoryId, aName: String, anImagePath: String?, aSequence: Int, videoOnDemandAssociated: Boolean) {
        this.id = anId
        this.name = aName
        this.imagePath = anImagePath
        this.sequence = aSequence
        this.videoOnDemandAssociated = videoOnDemandAssociated
    }

    fun isAssociatedVideoOnDemand(): Boolean {
        return videoOnDemandAssociated
    }
}
