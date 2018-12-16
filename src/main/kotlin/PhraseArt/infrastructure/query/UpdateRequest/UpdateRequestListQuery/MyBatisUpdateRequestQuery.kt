package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestListQuery

import PhraseArt.domain.updateRequest.UpdateRequestType
import PhraseArt.query.UpdateRequest.UpdateRequestListQuery
import PhraseArt.query.Dto.UpdateRequestList.PhraseUpdateRequestQueryDto
import PhraseArt.query.Dto.UpdateRequestList.SubcategoryModificationRequestQueryDto
import PhraseArt.query.Dto.UpdateRequestList.UpdateRequestQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisUpdateRequestQuery(
    @Autowired private val updateRequestQueryMapper : UpdateRequestQueryMapper
) : UpdateRequestListQuery {
    override fun findAllRequesting(offset: Int): List<UpdateRequestQueryDto> {
        return updateRequestQueryMapper.selectAllRequesting(20, offset).mapNotNull {
            daoToUpdateRequest(it)
        }
    }

    override fun findAllFinished(offset: Int): List<UpdateRequestQueryDto> {
        return updateRequestQueryMapper.selectAllFinished(20, offset).mapNotNull {
            daoToUpdateRequest(it)
        }
    }

    private fun daoToUpdateRequest(dao: UpdateRequestListItemDao): UpdateRequestQueryDto? {
        when (dao.type) {
            UpdateRequestType.PHRASE_REGISTRATION.value -> { // 名言登録申請オブジェクト
                return PhraseUpdateRequestQueryDto(
                    dao.updateRequestId,
                    dao.userId,
                    dao.type,
                    dao.finished,
                    dao.decisionExpiresAt.toLocalDateTime(),
                    dao.finalDecisionResult,
                    dao.approvedCount.toInt(),
                    dao.rejectedCount.toInt(),
                    dao.prRequestedCategoryId as String,
                    dao.prcCategoryName as String,
                    dao.prRequestedSubcategoryId,
                    dao.prRequestedSubcategoryName,
                    dao.prRequestedPhraseContent as String,
                    dao.prRequestedPhraseAuthorName as String
                )
            }
            UpdateRequestType.PHRASE_MODIFICATION.value -> { // 名言修正申請オブジェクト
                return PhraseUpdateRequestQueryDto(
                    dao.updateRequestId,
                    dao.userId,
                    dao.type,
                    dao.finished,
                    dao.decisionExpiresAt.toLocalDateTime(),
                    dao.finalDecisionResult,
                    dao.approvedCount.toInt(),
                    dao.rejectedCount.toInt(),
                    dao.pmcCategoryId as String,
                    dao.pmcCategoryName as String,
                    dao.pmRequestedSubcategoryId,
                    dao.pmRequestedSubcategoryName,
                    dao.pmRequestedPhraseContent as String,
                    dao.pmRequestedPhraseAuthorName as String
                )
            }
            UpdateRequestType.PHRASE_DELETION.value -> { // 名言削除申請オブジェクト
                return PhraseUpdateRequestQueryDto(
                    dao.updateRequestId,
                    dao.userId,
                    dao.type,
                    dao.finished,
                    dao.decisionExpiresAt.toLocalDateTime(),
                    dao.finalDecisionResult,
                    dao.approvedCount.toInt(),
                    dao.rejectedCount.toInt(),
                    dao.pdcCategoryId as String,
                    dao.pdcCategoryName as String,
                    dao.pdSubcategoryId,
                    dao.pdSubcategoryName,
                    dao.pdPhraseContent as String,
                    dao.pdAuthorName as String
                )
            }
            UpdateRequestType.SUBCATEGORY_MODIFICATION.value -> { // サブカテゴリー修正申請オブジェクト
                return SubcategoryModificationRequestQueryDto(
                    dao.updateRequestId,
                    dao.userId,
                    dao.type,
                    dao.finished,
                    dao.decisionExpiresAt.toLocalDateTime(),
                    dao.finalDecisionResult,
                    dao.approvedCount.toInt(),
                    dao.rejectedCount.toInt(),
                    dao.smcCategoryId as String,
                    dao.smcCategoryName as String,
                    dao.smRequestedSubcategoryId as String,
                    dao.smRequestedSubcategoryName as String,
                    dao.smRequestedSubcategoryIntroduction as String
                )
            }
            else -> {
                // TODO : ここに入った場合、不正データが混じっていることを意味するので、ログやエラー通知処理をやる。
                return null
            }
        }
    }
}
