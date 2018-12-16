package PhraseArt.presentation.api.controller.Phrase

import PhraseArt.application.Phrase.PhraseService
import PhraseArt.presentation.api.controller.Support.PublicApiController
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import PhraseArt.query.Dto.Phrase.PhraseQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@RestController
class PhrasesController(@Autowired val phraseService : PhraseService) : PublicApiController() {
    @RequestMapping("/phrases")
    fun list(@RequestParam(name="offset", defaultValue="0") offset: Int): MutableMap<String, List<PhraseQueryDto>> {
        return mutableMapOf("phrases" to phraseService.findAllPhrases(principalName(), offset))
    }

    @RequestMapping("/categories/{categoryId}/phrases")
    fun listNarrowedDownByCategoryId(
            @PathVariable("categoryId") categoryId: String,
            @RequestParam(name="offset", defaultValue="0") offset: Int
    ): MutableMap<String, List<PhraseQueryDto>> {
        return mutableMapOf("phrases" to phraseService.findAllPhrasesByCategoryId(categoryId, principalName(), offset))
    }

    @RequestMapping("/subcategories/{subcategoryId}/phrases")
    fun listNarrowedDownBySubcategoryId(
            @PathVariable("subcategoryId") subcategoryId: String,
            @RequestParam(name="offset", defaultValue="0") offset: Int
    ): MutableMap<String, List<PhraseQueryDto>> {
        return mutableMapOf("phrases" to phraseService.findAllPhrasesBySubcategoryId(subcategoryId, principalName(), offset))
    }

    @RequestMapping("/phrases/{id}")
    fun detail(@PathVariable("id") phraseId: String): MutableMap<String, PhraseQueryDto> {
        val phrase: PhraseQueryDto = phraseService.findPhrase(phraseId, principalName())
        return mutableMapOf("phrase" to phrase)
    }

    private fun principalName(): String? {
        val principal = SecurityContextHolder.getContext().getAuthentication()
        return if (principal.name == "anonymousUser") null else principal.name
    }
}
