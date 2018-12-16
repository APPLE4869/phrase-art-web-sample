package PhraseArt.infrastructure.domain.category.subcategory

data class SubcategoryDomainDao(
    val subcategoryId: String,
    val categoryId: String,
    val categoryName: String,
    val categoryImagePath: String?,
    val categorySequence: Int,
    val categoryVideoOnDemandAssociated: Boolean,
    val subcategoryName: String,
    val subcategoryImagePath: String?,
    val subcategoryIntroduction: String?
)
