package PhraseArt.query.Dto.Category

data class SubcategoryQueryDto(
    val id: String,
    val categoryId: String,
    val categoryName: String,
    val videoOnDemandAssociated: Boolean,
    val name: String,
    val imageUrl: String?,
    val introduction: String?,
    val videoOnDemandNameKeys: List<String>?
)
