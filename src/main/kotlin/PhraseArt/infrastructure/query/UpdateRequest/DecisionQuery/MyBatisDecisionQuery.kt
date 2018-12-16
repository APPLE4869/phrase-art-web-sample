package PhraseArt.infrastructure.query.UpdateRequest.DecisionQuery

import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.domain.user.UserId
import PhraseArt.query.UpdateRequest.DecisionQuery
import PhraseArt.query.Dto.UpdateRequest.PhraseDecisionQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisDecisionQuery(
        @Autowired private val phraseDecisionQueryMapper : DecisionQueryMapper
) : DecisionQuery {
    override fun findByUserIdAndPhraseRequestId(userId: UserId, updateRequestId: UpdateRequestId): PhraseDecisionQueryDto? {
        return phraseDecisionQueryMapper.findByUserIdAndPhraseRequestId(userId.value, updateRequestId.value)?.let { daoToPhraseDecision(it) }
    }

    private fun daoToPhraseDecision(dao: DecisionDao): PhraseDecisionQueryDto {
        return PhraseDecisionQueryDto(dao.result)
    }
}
