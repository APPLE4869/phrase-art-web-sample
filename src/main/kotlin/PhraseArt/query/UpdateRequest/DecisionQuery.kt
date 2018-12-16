package PhraseArt.query.UpdateRequest

import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.UpdateRequest.PhraseDecisionQueryDto

interface DecisionQuery {
    fun findByUserIdAndPhraseRequestId(userId: UserId, PhraseRequestId: UpdateRequestId): PhraseDecisionQueryDto?
}
