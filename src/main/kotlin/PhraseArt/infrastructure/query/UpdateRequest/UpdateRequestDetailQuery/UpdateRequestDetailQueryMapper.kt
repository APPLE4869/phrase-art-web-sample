package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestDetailQuery

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface UpdateRequestDetailQueryMapper {
    fun selectOnePhraseRegistrationReqeustById(@Param("id") id: String): PhraseRegistrationRequestDao?

    fun selectOnePhraseModificationReqeustById(@Param("id") id: String): PhraseModificationRequestDao?

    fun selectOnePhraseDeletionReqeustById(@Param("id") id: String): PhraseDeletionRequestDao?

    fun selectOneSubcategoryModificationReqeustById(@Param("id") id: String): SubcategoryModificationRequestDao?

    fun selectAllVideoOnDemandNameKeysBySubcategoryId(@Param("subcategoryId") subcategoryId: String): List<String>

    fun selectAllVideoOnDemandNameKeysByUpdateRequestId(@Param("updateRequestId") updateRequestId: String): List<String>
}
