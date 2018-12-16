package PhraseArt.domain.updateRequest

enum class FinalDecisionResultType(val value: String) {
    APPROVE("approve"), REJECT("reject");

    companion object {
        // enumへの変換を行う
        fun fromValue(value: String?): FinalDecisionResultType? {
            return when (value) {
                "approve" -> APPROVE
                "reject" -> REJECT
                else -> null
            }
        }
    }
}
