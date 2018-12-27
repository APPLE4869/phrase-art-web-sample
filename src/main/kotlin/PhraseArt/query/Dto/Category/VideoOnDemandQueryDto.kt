package PhraseArt.query.Dto.Category

data class VideoOnDemandQueryDto(
    val nameKey: String,
    val name: String,
    val imageUrl: String,
    val url: String,
    val appDeepLink: String?
)
