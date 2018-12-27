package PhraseArt.application.UpdateRequest

import PhraseArt.domain.category.*
import PhraseArt.domain.user.UserRepository
import PhraseArt.presentation.api.form.UpdateRequest.PhraseRegistrationRequestForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import PhraseArt.domain.user.UserId
import PhraseArt.support.ForbiddenException
import PhraseArt.domain.phrase.Phrase
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.domain.phrase.PhraseRepository
import PhraseArt.domain.updateRequest.*
import PhraseArt.domain.updateRequest.phrase.*
import PhraseArt.presentation.api.form.UpdateRequest.PhraseDeletionRequestForm
import PhraseArt.presentation.api.form.UpdateRequest.PhraseModificationRequestForm
import PhraseArt.query.UpdateRequest.UpdateRequestListQuery
import PhraseArt.support.NotFoundException
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateRequestFinishDecisionService(
    @Autowired val categoryRepository : CategoryRepository,
    @Autowired val phraseRepository: PhraseRepository,
    @Autowired val updateRequestRepository : UpdateRequestRepository,
    @Autowired val subcategoryRepository: SubcategoryRepository
) {
    // TODO : ひとまず数が多くないのでトランザクションをまとめているが、数が増えるようなら分割するように改修する。
    @Transactional
    fun finishExpiredRequests() {
        val requests = updateRequestRepository.allUnfinishedExpiredRequest()

        requests.forEach{ request ->
            val finalDecision = request.calcFinalDecision()

            if (finalDecision == FinalDecisionResultType.APPROVE) {
                when (request.type) {
                    UpdateRequestType.PHRASE_REGISTRATION -> // 名言登録申請の場合
                        performForApprovedPhraseRegistrationRequest(request as PhraseRegistrationRequest)
                    UpdateRequestType.PHRASE_MODIFICATION -> // 名言修正申請の場合
                        performForApprovedPhraseModificationRequest(request as PhraseModificationRequest)
                    UpdateRequestType.PHRASE_DELETION -> // 名言削除申請の場合
                        performForApprovedPhraseDeletionRequest(request as PhraseDeletionRequest)
                    else -> // サブカテゴリー修正申請の場合
                        performForApprovedSubcategoryModificationRequest(request as SubcategoryModificationRequest)
                }
            }

            request.finishRequest(finalDecision)
            updateRequestRepository.store(request)
        }
    }

    // 名言登録申請の承認時用の処理
    @Transactional
    private fun performForApprovedPhraseRegistrationRequest(request: PhraseRegistrationRequest) {
        changeSubcategoryIdOrNothing(request)

        val phrase = Phrase.createFromRegistrationRequest(phraseRepository.nextIdentity(), request)
        phraseRepository.store(phrase)
    }

    // 名言修正申請の承認時用の処理
    @Transactional
    private fun performForApprovedPhraseModificationRequest(request: PhraseModificationRequest) {
        val phrase = phraseRepository.phraseOfId(request.requestedPhraseId) ?: return // 該当する名言が存在しないときはスキップ
        changeSubcategoryIdOrNothing(request)

        phrase.reflectModificationRequest(request)
        phraseRepository.store(phrase)
    }

    // 名言削除申請の承認時用の処理
    @Transactional
    private fun performForApprovedPhraseDeletionRequest(request: PhraseDeletionRequest) {
        val phrase = phraseRepository.phraseOfId(request.requestedPhraseId) ?: return // 該当する名言が存在しないときはスキップ

        phraseRepository.remove(phrase)
    }

    // サブカテゴリー修正申請の承認時用の処理
    @Transactional
    private fun performForApprovedSubcategoryModificationRequest(request: SubcategoryModificationRequest) {
        val subcategory = subcategoryRepository.subcategoryOfId(request.requestedSubcategoryId) ?: return

        subcategory.reflectModificationRequest(request)

        subcategoryRepository.store(subcategory)
    }

    // TODO : changeSubcategoryId をしているが、結局Storeで保存することはない実装で、相当イマイチなので、他の案を考えて修正する。(仕様変更も検討)
    @Transactional
    private fun changeSubcategoryIdOrNothing(request: PhraseUpdateRequestIncludedSubcategory) {
        // サブカテゴリーIDが申請時に登録されている、またはサブカテゴリー名が未記入の場合
        if (request.requestedSubcategoryId != null || request.requestedSubcategoryName == null) return

        val registeredSubcategory =
            subcategoryRepository.subcategoryOfCategoryIdAndName(request.requestedCategoryId, request.requestedSubcategoryName as String)

        // 同様の内容のサブカテゴリーがすでに登録されている場合
        if (registeredSubcategory != null) {
            request.changeSubcategoryId(registeredSubcategory.id)
            return
        }

        val category = categoryRepository.categoryOfId(request.requestedCategoryId) ?:
            throw NotFoundException("該当するカテゴリーは存在しません")

        // 新規登録
        val newSubcategory = Subcategory(
            subcategoryRepository.nextIdentity(),
            category,
            request.requestedSubcategoryName as String,
            null,
            null,
            null
        )
        subcategoryRepository.store(newSubcategory)

        request.changeSubcategoryId(newSubcategory.id)
    }
}
