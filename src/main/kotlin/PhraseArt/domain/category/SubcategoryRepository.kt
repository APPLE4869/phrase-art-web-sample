package PhraseArt.domain.category

import PhraseArt.domain.phrase.PhraseId

interface SubcategoryRepository {
    fun nextIdentity(): SubcategoryId

    fun subcategoryOfId(id: SubcategoryId): Subcategory?

    fun subcategoryOfCategoryIdAndName(categoryId: CategoryId, name: String): Subcategory?

    fun videoOnDemandsOfNameKeys(nameKeys: List<VideOnDemandNameKeyType>): List<VideoOnDemand>

    fun store(subcategory: Subcategory)

    fun remove(subcategory: Subcategory)
}
