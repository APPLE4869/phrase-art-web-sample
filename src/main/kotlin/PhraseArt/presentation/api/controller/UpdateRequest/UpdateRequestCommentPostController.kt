package PhraseArt.presentation.api.controller.UpdateRequest

import PhraseArt.application.UpdateRequest.UpdateRequestCommentService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import PhraseArt.presentation.api.form.UpdateRequest.UpdateRequestCommentForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
class UpdateRequestCommentPostController(@Autowired val updateRequestCommentService : UpdateRequestCommentService) : PrivateApiController() {
    @PostMapping("/updateRequests/comment")
    @ResponseStatus(value = HttpStatus.OK)
    fun submit(@Valid @RequestBody form: UpdateRequestCommentForm, principal: Principal) {
        updateRequestCommentService.post(form, principal.name)
    }
}
