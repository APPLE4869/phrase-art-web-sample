package PhraseArt.infrastructure.domain.category.category

data class CategoryDomainDao(
    val id: String,
    val name: String,
    val imagePath: String?,
    val sequence: Int,
    val videoOnDemandAssociated: Boolean
)
