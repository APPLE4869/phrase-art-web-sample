package PhraseArt.application.UpdateRequest

import PhraseArt.domain.updateRequest.UpdateRequestId
import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.UpdateRequest.PhraseDecisionQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseDeletionRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseModificationRequestQueryDto
import PhraseArt.query.Dto.UpdateRequest.PhraseRegistrationRequestQueryDto
import PhraseArt.query.UpdateRequest.UpdateRequestListQuery
import PhraseArt.query.Dto.UpdateRequestList.UpdateRequestQueryDto
import PhraseArt.query.UpdateRequest.DecisionQuery
import PhraseArt.query.UpdateRequest.UpdateRequestDetailQuery
import PhraseArt.support.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UpdateRequestService(
        @Autowired val updateRequestQuery : UpdateRequestListQuery,
        @Autowired val phraseRegistrationRequestQuery : UpdateRequestDetailQuery,
        @Autowired private val phraseDecisionQuery : DecisionQuery
) {
    fun requestingList(offset: Int): List<UpdateRequestQueryDto> {
        return updateRequestQuery.findAllRequesting(offset)
    }

    fun finishedList(offset: Int): List<UpdateRequestQueryDto> {
        return updateRequestQuery.findAllFinished(offset)
    }

    fun phraseRegistraionRequestOfId(updateRequestId: String, principalName: String?): Pair<PhraseRegistrationRequestQueryDto, PhraseDecisionQueryDto?> {
        val request = phraseRegistrationRequestQuery.phraseRegistrationRequestOfId(UpdateRequestId(updateRequestId)) ?:
            throw NotFoundException("該当する名言登録申請は存在しません。")

        if (principalName == null) {
            return Pair(request, null)
        }

        val decision = phraseDecisionQuery.findByUserIdAndPhraseRequestId(UserId(principalName), UpdateRequestId(updateRequestId))

        return Pair(request, decision)
    }

    fun phraseModificationRequestOfId(updateRequestId: String, principalName: String?): Pair<PhraseModificationRequestQueryDto, PhraseDecisionQueryDto?> {
        val request = phraseRegistrationRequestQuery.phraseModificationRequestOfId(UpdateRequestId(updateRequestId)) ?:
            throw NotFoundException("該当する名言登録申請は存在しません。")

        if (principalName == null) {
            return Pair(request, null)
        }

        val decision = phraseDecisionQuery.findByUserIdAndPhraseRequestId(UserId(principalName), UpdateRequestId(updateRequestId))

        return Pair(request, decision)
    }

    fun phraseDeletionRequestOfId(updateRequestId: String, principalName: String?): Pair<PhraseDeletionRequestQueryDto, PhraseDecisionQueryDto?> {
        val request = phraseRegistrationRequestQuery.phraseDeletionRequestOfId(UpdateRequestId(updateRequestId)) ?:
            throw NotFoundException("該当する名言登録申請は存在しません。")

        if (principalName == null) {
            return Pair(request, null)
        }

        val decision = phraseDecisionQuery.findByUserIdAndPhraseRequestId(UserId(principalName), UpdateRequestId(updateRequestId))

        return Pair(request, decision)
    }
}
