package PhraseArt.infrastructure.domain.updateRequest.updateRequest

import PhraseArt.infrastructure.domain.category.subcategory.VideoOnDemandDomainDao
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import java.time.LocalDateTime

// TODO : ポリモーフィックをやっている分、メソッド数が多くなってしまっているので、対象のクラスごとにMapperを分けるなどして対応する。
@Mapper
interface UpdateRequestDomainMapper {
    fun selectOneById(
        @Param("updateRequestId") updateRequestId: String
    ): UpdateRequestDao?

    fun selectOneUnfinishedPhraseRegistrationRequestByPhraseColumns(
        @Param("requestedCategoryId") requestedCategoryId: String,
        @Param("requestedSubcategoryName") requestedSubcategoryName: String,
        @Param("requestedPhraseContent") requestedPhraseContent: String,
        @Param("requestedAuthorName") requestedAuthorName: String
    ): UpdateRequestDao?

    fun selectOneUnfinishedPhraseModificationRequestByPhraseColumns(
        @Param("requestedCategoryId") requestedCategoryId: String,
        @Param("requestedSubcategoryName") requestedSubcategoryName: String,
        @Param("requestedPhraseContent") requestedPhraseContent: String,
        @Param("requestedAuthorName") requestedAuthorName: String
    ): UpdateRequestDao?

    fun selectOneUnfinishedModificationRequestByPhraseId(@Param("phraseId") phraseId: String): UpdateRequestDao?

    fun selectOneUnfinishedDeletionRequestByPhraseId(@Param("phraseId") phraseId: String): UpdateRequestDao?

    fun selectOneUnfinishedSubcategoryModificationRequestBySubcategoryId(@Param("subcategoryId") subcategoryId: String): UpdateRequestDao?

    fun selectAllWhereUnfinishedAndExpired(
        @Param("now") now: LocalDateTime
    ): List<UpdateRequestDao>

    fun updateRequestById(
        @Param("id") id: String,
        @Param("finished") finished: Boolean,
        @Param("finalDecisionResult") finalDecisionResult: String?
    )

    fun insertRequest(
        @Param("id") id: String,
        @Param("userId") userId: String,
        @Param("type") type: String,
        @Param("finished") finished: Boolean,
        @Param("decisionExpiresAt") decisionExpiresAt: LocalDateTime,
        @Param("finalDecisionResult") finalDecisionResult: String?
    )

    fun insertPhraseRegistrationRequest(
        @Param("id") id: String,
        @Param("updateRequestId") updateRequestId: String,
        @Param("requestedCategoryId") requestedCategoryId: String,
        @Param("requestedSubcategoryId") requestedSubcategoryId: String?,
        @Param("requestedSubcategoryName") requestedSubcategoryName: String?,
        @Param("requestedPhraseContent") requestedPhraseContent: String,
        @Param("requestedPhraseAuthorName") requestedPhraseAuthorName: String
    )

    fun insertPhraseModificationRequest(
        @Param("id") id: String,
        @Param("updateRequestId") updateRequestId: String,
        @Param("requestedPhraseId") requestedPhraseId: String,
        @Param("requestedCategoryId") requestedCategoryId: String,
        @Param("requestedSubcategoryId") requestedSubcategoryId: String?,
        @Param("requestedSubcategoryName") requestedSubcategoryName: String?,
        @Param("requestedPhraseContent") requestedPhraseContent: String,
        @Param("requestedPhraseAuthorName") requestedPhraseAuthorName: String,
        @Param("currentCategoryId") currentCategoryId: String,
        @Param("currentSubcategoryId") currentSubcategoryId: String?,
        @Param("currentSubcategoryName") currentSubcategoryName: String?,
        @Param("currentPhraseContent") currentPhraseContent: String,
        @Param("currentPhraseAuthorName") currentPhraseAuthorName: String
    )

    fun insertPhraseDeletionRequest(
        @Param("id") id: String,
        @Param("updateRequestId") updateRequestId: String,
        @Param("requestedPhraseId") requestedPhraseId: String,
        @Param("currentCategoryId") currentCategoryId: String,
        @Param("currentSubcategoryId") currentSubcategoryId: String?,
        @Param("currentSubcategoryName") currentSubcategoryName: String?,
        @Param("currentPhraseContent") currentPhraseContent: String,
        @Param("currentPhraseAuthorName") currentPhraseAuthorName: String
    )

    fun insertSubcategoryModificationRequest(
        @Param("id") id: String,
        @Param("updateRequestId") updateRequestId: String,
        @Param("requestedSubcategoryId") requestedSubcategoryId: String,
        @Param("requestedSubcategoryName") requestedSubcategoryName: String,
        @Param("requestedSubcategoryIntroduction") requestedSubcategoryIntroduction: String?,
        @Param("requestedSubcategoryImagePath") requestedSubcategoryImagePath: String?,
        @Param("currentCategoryId") currentCategoryId: String,
        @Param("currentSubcategoryName") currentSubcategoryName: String,
        @Param("currentSubcategoryIntroduction") currentPhraseContent: String?,
        @Param("currentSubcategoryImagePath") currentPhraseAuthorName: String?
    )

    //----- 以下、VideoOnDemand関係のメソッド -----//

    fun selectAllVideoOnDemandsByUpdateRequestId(
        @Param("updateRequestId") updateRequestId: String
    ): List<VideoOnDemandDomainDao>

    fun selectOneVideoOnDemandByNameKey(
        @Param("nameKey") nameKey: String
    ): VideoOnDemandDomainDao?

    fun insertRequestVideoOnDemand(
        @Param("id") id: String,
        @Param("updateRequestId") updateRequestId: String,
        @Param("videoOnDemandId") videoOnDemandId: String
    )

    fun deleteAllRequestVideoOnDemandBySubcategoryId(
        @Param("updateRequestId") updateRequestId: String
    )
}
