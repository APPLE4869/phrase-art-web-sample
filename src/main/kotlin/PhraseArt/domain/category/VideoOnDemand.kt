package PhraseArt.domain.category

data class VideoOnDemand(
    val nameKey: VideOnDemandNameKeyType,
    val name: String,
    val imagePath: String,
    val url: String,
    val sequence: Int
)
