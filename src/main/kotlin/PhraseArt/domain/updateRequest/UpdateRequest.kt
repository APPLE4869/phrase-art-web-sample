package PhraseArt.domain.updateRequest

import PhraseArt.domain.support.Entity
import PhraseArt.domain.user.User
import PhraseArt.domain.user.UserId
import java.time.LocalDateTime

abstract class UpdateRequest(
    val id: UpdateRequestId,
    val userId: UserId,
    val type: UpdateRequestType,
    var finished: Boolean,
    val expiresDatetime: LocalDateTime,
    var finalDecisionResult: FinalDecisionResultType?,
    var decisions: MutableSet<Decision>
) : Entity() {
    fun comment(aCommentId: UpdateRequestCommentId, anUser: User, aContent: String): UpdateRequestComment {
        return UpdateRequestComment(
            aCommentId,
            anUser.id,
            id,
            aContent
        )
    }

    fun currentDecision(userId: UserId): Decision? {
        for (decision in decisions) {
            if (decision.userId == userId)
                return decision
        }
        return null
    }

    // まだ決定可能な申請かを判定
    fun isDecidable(): Boolean {
        return expiresDatetime > LocalDateTime.now()
    }

    // TODO
    // 今はDecisionRepositoryを利用している関係でDecisionを返却しているが、近いうちにUpdateRequestをStoreすればいいようにして、
    // 返却値はなくす。(rejectも同様)
    fun approve(decisionId: DecisionId, userId: UserId): Decision {
        assertArgumentNull(currentDecision(userId), "すでにこの申請に対して判定済みです")
        val decision = Decision(decisionId, userId, id, "approve")
        decisions.add(decision)
        return decision
    }

    fun reject(decisionId: DecisionId, userId: UserId): Decision {
        assertArgumentNull(currentDecision(userId), "すでにこの申請に対して判定済みです。")
        val decision = Decision(decisionId, userId, id, "reject")
        decisions.add(decision)
        return decision
    }

    fun cancelDecision(decision: Decision) {
        decisions.remove(decision)
    }

    // 最終判定結果を返す
    abstract fun calcFinalDecision(): FinalDecisionResultType

    fun finishRequest(finalDecision: FinalDecisionResultType) {
        this.finished = true
        this.finalDecisionResult = finalDecision
    }

    protected fun approvedDecisions(): List<Decision> {
        return decisions.filter { decision -> decision.result == DecisionResultType.APPROVE }
    }

    protected fun rejectedDecisions(): List<Decision> {
        return decisions.filter { decision -> decision.result == DecisionResultType.REJECT }
    }
}
