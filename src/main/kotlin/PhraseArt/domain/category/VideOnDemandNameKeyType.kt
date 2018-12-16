package PhraseArt.domain.category

enum class VideOnDemandNameKeyType(val value: String) {
    HULU("hulu"), AMAZON_PRIME("amazon-prime");

    companion object {
        // enumへの変換を行う
        fun fromValue(value: String?): VideOnDemandNameKeyType? {
            return when (value) {
                "hulu" -> HULU
                "amazon-prime" -> AMAZON_PRIME
                else -> null
            }
        }
    }
}
