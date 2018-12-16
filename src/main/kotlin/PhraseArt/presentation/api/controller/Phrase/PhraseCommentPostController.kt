package PhraseArt.presentation.api.controller.Phrase

import PhraseArt.application.Phrase.PhraseCommentService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import PhraseArt.presentation.api.form.Phrase.PhraseCommentForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
class PhraseCommentPostController(@Autowired val phraseCommentService : PhraseCommentService) : PrivateApiController() {
    @PostMapping("/phrases/comment")
    @ResponseStatus(value = HttpStatus.OK)
    fun submit(@Valid @RequestBody form: PhraseCommentForm, principal: Principal) {
        phraseCommentService.post(form, principal.name)
    }
}
