package PhraseArt.domain.updateRequest

// TODO : DecisionはUpdateRequestの集約の中に含める予定なので、その改修をする際にこのRepositoryは消す。
interface DecisionRepository {
    fun nextIdentity(): DecisionId

    fun allDecisionsOfUpdateRequestId(updateRequestId: UpdateRequestId): List<Decision>

    fun store(decision: Decision)

    fun remove(decision: Decision)
}
