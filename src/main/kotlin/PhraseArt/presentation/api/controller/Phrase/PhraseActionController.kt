package PhraseArt.presentation.api.controller.Phrase

import PhraseArt.application.Phrase.PhraseCommentService
import PhraseArt.application.Phrase.PhraseService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import PhraseArt.presentation.api.form.Phrase.PhraseCommentForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
class PhraseActionController(@Autowired val phraseService : PhraseService) : PrivateApiController() {
    @PostMapping("/phrases/{phraseId}/like")
    @ResponseStatus(value = HttpStatus.OK)
    fun like(@PathVariable("phraseId") phraseId: String, principal: Principal) {
        phraseService.like(phraseId, principal.name)
    }

    @PostMapping("/phrases/{phraseId}/unlike")
    @ResponseStatus(value = HttpStatus.OK)
    fun unlike(@PathVariable("phraseId") phraseId: String, principal: Principal) {
        phraseService.unlike(phraseId, principal.name)
    }

    @PostMapping("/phrases/{phraseId}/favorite")
    @ResponseStatus(value = HttpStatus.OK)
    fun favorite(@PathVariable("phraseId") phraseId: String, principal: Principal) {
        phraseService.favorite(phraseId, principal.name)
    }

    @PostMapping("/phrases/{phraseId}/unfavorite")
    @ResponseStatus(value = HttpStatus.OK)
    fun unfavorite(@PathVariable("phraseId") phraseId: String, principal: Principal) {
        phraseService.unfavorite(phraseId, principal.name)
    }
}
