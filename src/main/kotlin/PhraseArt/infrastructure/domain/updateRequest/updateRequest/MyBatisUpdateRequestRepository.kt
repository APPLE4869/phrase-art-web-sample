package PhraseArt.infrastructure.domain.updateRequest.updateRequest

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.Subcategory
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.domain.updateRequest.*
import PhraseArt.domain.updateRequest.phrase.PhraseRegistrationRequest
import PhraseArt.domain.user.UserId
import PhraseArt.infrastructure.domain.updateRequest.decision.DecisionDomainMapper
import PhraseArt.infrastructure.domain.updateRequest.decision.DecisionDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import PhraseArt.domain.updateRequest.UpdateRequestType
import PhraseArt.domain.updateRequest.phrase.PhraseDeletionRequest
import PhraseArt.domain.updateRequest.phrase.PhraseModificationRequest
import PhraseArt.domain.updateRequest.phrase.SubcategoryModificationRequest

// TODO : 1クラスで受け持つ責務がかなり大きいので、各申請用のクラスにそれぞれのロジックを切り出す。
@Component
class MyBatisUpdateRequestRepository(
    @Autowired val updateRequestDomainMapper : UpdateRequestDomainMapper,
    @Autowired val decisionDomainMapper : DecisionDomainMapper
): UpdateRequestRepository {
    override fun nextIdentity(): UpdateRequestId {
        return UpdateRequestId(uuid())
    }

    override fun allUnfinishedExpiredRequest(): List<UpdateRequest> {
        return updateRequestDomainMapper.selectAllWhereUnfinishedAndExpired(LocalDateTime.now()).mapNotNull {
            //
            val decisions = decisionDomainMapper.allDecisionsOfUpdateRequestId(it.updateRequestId)
            daoToUpdateRequest(it, decisions)
        }
    }

    override fun store(updateRequest: UpdateRequest) {
        if (updateRequestDomainMapper.selectOneById(updateRequest.id.value) == null) {
            updateRequestDomainMapper.insertRequest(
                updateRequest.id.value,
                updateRequest.userId.value,
                updateRequest.type.value,
                updateRequest.finished,
                updateRequest.expiresDatetime,
                updateRequest.finalDecisionResult?.value
            )

            when (updateRequest.type) {
                UpdateRequestType.PHRASE_REGISTRATION -> { // 名言登録申請の場合
                    updateRequest as PhraseRegistrationRequest
                    updateRequestDomainMapper.insertPhraseRegistrationRequest(
                        uuid(),
                        updateRequest.id.value,
                        updateRequest.requestedCategoryId.value,
                        updateRequest.requestedSubcategoryId?.value,
                        updateRequest.requestedSubcategoryName,
                        updateRequest.requestedPhraseContent,
                        updateRequest.requestedPhraseAuthorName
                    )
                }
                UpdateRequestType.PHRASE_MODIFICATION -> { // 名言修正申請の場合
                    updateRequest as PhraseModificationRequest
                    updateRequestDomainMapper.insertPhraseModificationRequest(
                        uuid(),
                        updateRequest.id.value,
                        updateRequest.requestedPhraseId.value,
                        updateRequest.requestedCategoryId.value,
                        updateRequest.requestedSubcategoryId?.value,
                        updateRequest.requestedSubcategoryName,
                        updateRequest.requestedPhraseContent,
                        updateRequest.requestedPhraseAuthorName,
                        updateRequest.currentCategoryId.value,
                        updateRequest.currentSubcategoryId?.value,
                        updateRequest.currentSubcategoryName,
                        updateRequest.currentPhraseContent,
                        updateRequest.currentPhraseAuthorName
                    )
                }
                UpdateRequestType.PHRASE_DELETION -> { // 名言削除申請の場合
                    updateRequest as PhraseDeletionRequest
                    updateRequestDomainMapper.insertPhraseDeletionRequest(
                        uuid(),
                        updateRequest.id.value,
                        updateRequest.requestedPhraseId.value,
                        updateRequest.currentCategoryId.value,
                        updateRequest.currentSubcategoryId?.value,
                        updateRequest.currentSubcategoryName,
                        updateRequest.currentPhraseContent,
                        updateRequest.currentPhraseAuthorName
                    )
                }
                UpdateRequestType.SUBCATEGORY_MODIFICATION -> { // サブカテゴリー修正申請の場合
                    updateRequest as SubcategoryModificationRequest
                    updateRequestDomainMapper.insertSubcategoryModificationRequest(
                        uuid(),
                        updateRequest.id.value,
                        updateRequest.requestedSubcategoryId.value,
                        updateRequest.requestedSubcategoryName,
                        updateRequest.requestedSubcategoryIntroduction,
                        updateRequest.requestedSubcategoryImagePath,
                        updateRequest.currentCategoryId.value,
                        updateRequest.currentSubcategoryName,
                        updateRequest.currentSubcategoryIntroduction,
                        updateRequest.currentSubcategoryImagePath
                    )
                }
            }
        } else {
            // 可変なカラムはupdate_requestsのfinishedとfinal_decision_resultのみなので、update処理については、更新対象を絞っている。
            // ただし、Storeがそのような知識を持っているべきなのかは微妙な気がするので、運用していて違和感があれば仕様を変更する。
            updateRequestDomainMapper.updateRequestById(updateRequest.id.value, updateRequest.finished, updateRequest.finalDecisionResult?.value)
        }
    }

    override fun requestOfId(updateRequestId: UpdateRequestId): UpdateRequest? {
        return updateRequestDomainMapper.selectOneById(updateRequestId.value)?.let {
            val decisions = decisionDomainMapper.allDecisionsOfUpdateRequestId(it.updateRequestId)
            daoToUpdateRequest(it, decisions)
        }
    }

    override fun phraseRegistrationRequestOf(
        categoryId: CategoryId,
        subcategoryName: String,
        phraseContent: String,
        authorName: String
    ): UpdateRequest? {
        return updateRequestDomainMapper.selectOneUnfinishedPhraseRegistrationRequestByPhraseColumns(
            categoryId.value,
            subcategoryName,
            phraseContent,
            authorName
        )?.let {
            val decisions = decisionDomainMapper.allDecisionsOfUpdateRequestId(it.updateRequestId)
            daoToUpdateRequest(it, decisions)
        }
    }

    override fun phraseModificationRequestOf(
        categoryId: CategoryId,
        subcategoryName: String,
        phraseContent: String,
        authorName: String
    ): UpdateRequest? {
        return updateRequestDomainMapper.selectOneUnfinishedPhraseModificationRequestByPhraseColumns(
            categoryId.value,
            subcategoryName,
            phraseContent,
            authorName
        )?.let {
            val decisions = decisionDomainMapper.allDecisionsOfUpdateRequestId(it.updateRequestId)
            daoToUpdateRequest(it, decisions)
        }
    }

    override fun phraseModificationRequestOfPhraseId(phraseId: PhraseId): UpdateRequest? {
        return updateRequestDomainMapper.selectOneUnfinishedModificationRequestByPhraseId(phraseId.value)?.let {
            val decisions = decisionDomainMapper.allDecisionsOfUpdateRequestId(it.updateRequestId)
            daoToUpdateRequest(it, decisions)
        }
    }

    override fun phraseDeletionRequestOfPhraseId(phraseId: PhraseId): UpdateRequest? {
        return updateRequestDomainMapper.selectOneUnfinishedDeletionRequestByPhraseId(phraseId.value)?.let {
            val decisions = decisionDomainMapper.allDecisionsOfUpdateRequestId(it.updateRequestId)
            daoToUpdateRequest(it, decisions)
        }
    }

    override fun subcategoryModificationRequestOfSubcategoryId(subcategoryId: SubcategoryId): UpdateRequest? {
        return null
    }

    private fun daoToUpdateRequest(dao: UpdateRequestDao, decisions: List<DecisionDao>): UpdateRequest? {
        when (dao.type) {
            UpdateRequestType.PHRASE_REGISTRATION.value -> { // 名言登録申請の場合
                return PhraseRegistrationRequest(
                    UpdateRequestId(dao.updateRequestId),
                    UserId(dao.userId),
                    UpdateRequestType.PHRASE_REGISTRATION,
                    dao.finished,
                    dao.decisionExpiresAt.toLocalDateTime(),
                    FinalDecisionResultType.fromValue(dao.finalDecisionResult),
                    daoToDecisions(decisions),
                    CategoryId(dao.prRequestedCategoryId as String),
                    dao.prRequestedSubcategoryId?.let { SubcategoryId(it) },
                    dao.prRequestedSubcategoryName as String,
                    dao.prRequestedPhraseContent as String,
                    dao.prRequestedPhraseAuthorName as String
                )
            }
            UpdateRequestType.PHRASE_MODIFICATION.value -> { // 名言修正申請の場合
                return PhraseModificationRequest(
                    UpdateRequestId(dao.updateRequestId),
                    UserId(dao.userId),
                    UpdateRequestType.PHRASE_MODIFICATION,
                    dao.finished,
                    dao.decisionExpiresAt.toLocalDateTime(),
                    FinalDecisionResultType.fromValue(dao.finalDecisionResult),
                    daoToDecisions(decisions),
                    PhraseId(dao.pmRequestedPhraseId as String),
                    CategoryId(dao.pmRequestedCategoryId as String),
                    dao.pmRequestedSubcategoryId?.let { SubcategoryId(it) },
                    dao.pmRequestedSubcategoryName,
                    dao.pmRequestedPhraseContent as String,
                    dao.pmRequestedPhraseAuthorName as String,
                    CategoryId(dao.pmCurrentCategoryId as String),
                    dao.pmCurrentSubcategoryId?.let { SubcategoryId(it) },
                    dao.pmCurrentSubcategoryName,
                    dao.pmCurrentPhraseContent as String,
                    dao.pmCurrentPhraseAuthorName as String
                )
            }
            UpdateRequestType.PHRASE_DELETION.value -> { // 名言削除申請の場合
                return PhraseDeletionRequest(
                    UpdateRequestId(dao.updateRequestId),
                    UserId(dao.userId),
                    UpdateRequestType.PHRASE_DELETION,
                    dao.finished,
                    dao.decisionExpiresAt.toLocalDateTime(),
                    FinalDecisionResultType.fromValue(dao.finalDecisionResult),
                    daoToDecisions(decisions),
                    PhraseId(dao.pdRequestedPhraseId as String),
                    CategoryId(dao.pdCurrentCategoryId as String),
                    dao.pdCurrentSubcategoryId?.let { SubcategoryId(it) },
                    dao.pdCurrentSubcategoryName,
                    dao.pdCurrentPhraseContent as String,
                    dao.pdCurrentPhraseAuthorName as String
                )
            }
            UpdateRequestType.SUBCATEGORY_MODIFICATION.value -> { // サブカテゴリー修正申請の場合
                return SubcategoryModificationRequest(
                    UpdateRequestId(dao.updateRequestId),
                    UserId(dao.userId),
                    UpdateRequestType.SUBCATEGORY_MODIFICATION,
                    dao.finished,
                    dao.decisionExpiresAt.toLocalDateTime(),
                    FinalDecisionResultType.fromValue(dao.finalDecisionResult),
                    daoToDecisions(decisions),
                    SubcategoryId(dao.smRequestedSubcategoryId as String),
                    dao.smRequestedSubcategoryName as String,
                    dao.smRequestedSubcategoryIntroduction,
                    dao.smRequestedSubcategoryImagePath,
                    CategoryId(dao.pdCurrentCategoryId as String),
                    dao.smCurrentSubcategoryName as String,
                    dao.smCurrentSubcategoryIntroduction,
                    dao.smCurrentSubcategoryImagePath
                )
            }
            else -> {
                // TODO : ここに入った場合、不正データが混じっていることを意味するので、ログやエラー通知処理をやる。
                return null
            }
        }
    }

    private fun daoToDecisions(decisions: List<DecisionDao>): MutableSet<Decision> {
        return decisions.map {
            Decision(
                DecisionId(it.decisionId),
                UserId(it.userId),
                UpdateRequestId(it.updateRequestId),
                it.result
            )
        }.toMutableSet()
    }

    private fun uuid(): String {
        return UUID.randomUUID().toString().toUpperCase()
    }
}
