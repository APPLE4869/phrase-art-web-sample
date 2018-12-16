package PhraseArt.infrastructure.domain.updateRequest.decision

import PhraseArt.domain.updateRequest.Decision
import PhraseArt.domain.updateRequest.DecisionId
import PhraseArt.domain.updateRequest.DecisionRepository
import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.domain.user.UserId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyBatisDecisionRepository(
    @Autowired val decisionDomainMapper : DecisionDomainMapper
): DecisionRepository {
    override fun nextIdentity(): DecisionId {
        return DecisionId(UUID.randomUUID().toString().toUpperCase())
    }

    override fun allDecisionsOfUpdateRequestId(updateRequestId: UpdateRequestId): List<Decision> {
        return decisionDomainMapper.allDecisionsOfUpdateRequestId(updateRequestId.value).map {
            daoToDecision(it)
        }
    }

    override fun store(decision: Decision) {
        if (decisionDomainMapper.decisionOfId(decision.id.value) == null)
            decisionDomainMapper.createDecision(decision.id.value, decision.userId.value, decision.updateRequestId.value, decision.result.value)
        else
            decisionDomainMapper.updateDecision(decision.id.value, decision.userId.value, decision.updateRequestId.value, decision.result.value)
    }

    override fun remove(decision: Decision) {
        decisionDomainMapper.deleteDecision(decision.id.value)
    }

    private fun daoToDecision(dao: DecisionDao): Decision {
        return Decision(
            DecisionId(dao.decisionId),
            UserId(dao.userId),
            UpdateRequestId(dao.updateRequestId),
            dao.result
        )
    }
}
