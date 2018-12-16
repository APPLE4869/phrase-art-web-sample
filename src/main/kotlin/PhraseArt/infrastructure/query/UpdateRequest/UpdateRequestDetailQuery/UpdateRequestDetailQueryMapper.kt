package PhraseArt.infrastructure.query.UpdateRequest.UpdateRequestDetailQuery

import org.apache.ibatis.annotations.Mapper

@Mapper
interface UpdateRequestDetailQueryMapper {
    fun selectOnePhraseRegistrationReqeustById(id: String): PhraseRegistrationRequestDao?

    fun selectOnePhraseModificationReqeustById(id: String): PhraseModificationRequestDao?

    fun selectOnePhraseDeletionReqeustById(id: String): PhraseDeletionRequestDao?
}
