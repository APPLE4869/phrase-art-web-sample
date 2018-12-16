package PhraseArt.infrastructure.domain.category.subcategory

data class VideoOnDemandDomainDao(
    val id: String,
    val nameKey: String,
    val name: String,
    val imagePath: String,
    val url: String,
    val sequence: Int
)
