package PhraseArt.infrastructure.domain.updateRequest.decision

data class DecisionDao(
    val decisionId: String,
    val userId: String,
    val updateRequestId: String,
    val result: String
)
