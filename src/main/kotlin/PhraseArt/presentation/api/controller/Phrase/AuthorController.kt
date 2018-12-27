package PhraseArt.presentation.api.controller.Phrase

import PhraseArt.application.Phrase.AuthorService
import PhraseArt.presentation.api.controller.Support.PublicApiController
import PhraseArt.query.Dto.Phrase.AuthorQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class AuthorController(@Autowired val authorService : AuthorService) : PublicApiController() {
    @GetMapping("/authors/candidates")
    @ResponseStatus(value = HttpStatus.OK)
    fun candidatesList(
        @RequestParam(name="categoryId") categoryId: String?,
        @RequestParam(name="subcategoryName") subcategoryName: String?,
        @RequestParam(name="word") word: String
    ): MutableMap<String, List<AuthorQueryDto>> {
        return mutableMapOf("authors" to authorService.findAllCandidatesAuthors(categoryId, subcategoryName, word))
    }
}
