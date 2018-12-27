package PhraseArt.presentation.api.controller.Phrase

import PhraseArt.application.Phrase.PhraseService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import PhraseArt.query.Dto.Phrase.PhraseQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class PrivatePhrasesController(@Autowired val phraseService : PhraseService) : PrivateApiController() {
    @RequestMapping("/phrases/favorite")
    fun facoriteList(principal: Principal, @RequestParam(name="offset", defaultValue="0") offset: Int): MutableMap<String, List<PhraseQueryDto>> {
        return mutableMapOf("phrases" to phraseService.findAllFavoritePhraseByUserId(principal.name, offset))
    }
}
