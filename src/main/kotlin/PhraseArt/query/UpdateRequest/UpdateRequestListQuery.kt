package PhraseArt.query.UpdateRequest

import PhraseArt.query.Dto.UpdateRequestList.UpdateRequestQueryDto

interface UpdateRequestListQuery {
    fun findAllRequesting(offset: Int=0): List<UpdateRequestQueryDto>

    fun findAllFinished(offset: Int=0): List<UpdateRequestQueryDto>
}
