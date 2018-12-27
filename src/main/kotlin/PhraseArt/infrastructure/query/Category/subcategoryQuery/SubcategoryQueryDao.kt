package PhraseArt.infrastructure.query.Category.subcategoryQuery

data class SubcategoryQueryDao(
    val id: String,
    val categoryId: String,
    val categoryName: String,
    val videoOnDemandAssociated: Boolean,
    val name: String,
    val imagePath: String?,
    val introduction: String?,
    val sequence: Int
)
