package PhraseArt.domain.updateRequest

import PhraseArt.domain.support.Entity
import PhraseArt.domain.user.UserId
import java.lang.IllegalArgumentException

class Decision : Entity {
    val id: DecisionId
    var userId: UserId
    var updateRequestId: UpdateRequestId
    var result: DecisionResultType

    constructor(anId: DecisionId, anUserId: UserId, anUpdateRequestId: UpdateRequestId, aResult: String) {
        this.id = anId
        this.userId = anUserId
        this.updateRequestId = anUpdateRequestId
        this.result = when(aResult) { // TODO : DecisionResultTypeの方にメソッドを作って、ここはシンプルにする。
            "approve" -> DecisionResultType.APPROVE
            "reject" -> DecisionResultType.REJECT
            else -> throw IllegalArgumentException("判定の値が不正です")
        }
    }

    fun isApproved(): Boolean {
        return result == DecisionResultType.APPROVE
    }

    fun isRejected(): Boolean {
        return result == DecisionResultType.REJECT
    }
}
