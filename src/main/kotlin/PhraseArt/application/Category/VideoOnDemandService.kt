package PhraseArt.application.Category

import PhraseArt.query.Category.VideoOnDemandQuery
import PhraseArt.query.Dto.Category.VideoOnDemandQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VideoOnDemandService(
    @Autowired val videoOnDemandQuery : VideoOnDemandQuery
) {
    fun findAll(): List<VideoOnDemandQueryDto> {
        return videoOnDemandQuery.findAllVideoOnDemands()
    }
}
