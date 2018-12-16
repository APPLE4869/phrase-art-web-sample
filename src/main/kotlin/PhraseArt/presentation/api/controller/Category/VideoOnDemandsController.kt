package PhraseArt.presentation.api.controller.Category

import PhraseArt.application.Category.VideoOnDemandService
import PhraseArt.presentation.api.controller.Support.PublicApiController
import PhraseArt.query.Dto.Category.VideoOnDemandQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VideoOnDemandsController(@Autowired val videoOnDemandService : VideoOnDemandService) : PublicApiController() {
    @RequestMapping("/video_on_demands")
    fun list(): MutableMap<String, List<VideoOnDemandQueryDto>> {
        return mutableMapOf("videoOnDemands" to videoOnDemandService.findAll())
    }
}
