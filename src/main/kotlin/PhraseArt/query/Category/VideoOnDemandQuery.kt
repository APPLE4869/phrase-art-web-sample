package PhraseArt.query.Category

import PhraseArt.query.Dto.Category.VideoOnDemandQueryDto

interface VideoOnDemandQuery {
    fun findAllVideoOnDemands(): List<VideoOnDemandQueryDto>
}
