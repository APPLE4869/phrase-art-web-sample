package PhraseArt.presentation.api.controller.UpdateRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.security.Principal
import PhraseArt.application.UpdateRequest.PhraseUpdateRequestSubmissionService
import PhraseArt.application.UpdateRequest.SubcategoryModificationRequestSubmissionService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import PhraseArt.presentation.api.form.UpdateRequest.PhraseRegistrationRequestForm
import PhraseArt.presentation.api.form.UpdateRequest.PhraseModificationRequestForm
import PhraseArt.presentation.api.form.UpdateRequest.PhraseDeletionRequestForm
import PhraseArt.presentation.api.form.UpdateRequest.SubcategoryModificationRequestForm
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
class UpdateRequestSubmitController(
    @Autowired val phraseUpdateRequestSubmissionService: PhraseUpdateRequestSubmissionService,
    @Autowired val subcategoryModificationRequestSubmissionService: SubcategoryModificationRequestSubmissionService
) : PrivateApiController() {
    @PostMapping("/update_request/submit_phrase_registration_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun submitPhraseRegistrationRequest(@Valid @RequestBody form: PhraseRegistrationRequestForm, principal: Principal) {
        phraseUpdateRequestSubmissionService.submitRegistrationRequest(form, principal.name)
    }

    @PostMapping("/update_request/submit_phrase_modification_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun submitPhraseModificationRequest(@Valid @RequestBody form: PhraseModificationRequestForm, principal: Principal) {
        phraseUpdateRequestSubmissionService.submitModificationRequest(form, principal.name)
    }

    @PostMapping("/update_request/submit_phrase_deletion_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun submitPhraseDeletionRequest(@Valid @RequestBody form: PhraseDeletionRequestForm, principal: Principal) {
        phraseUpdateRequestSubmissionService.submitDeletionRequest(form, principal.name)
    }

    @PostMapping("/update_request/submit_subcategory_modification_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun submitSubcategoryModificationRequest(
        @RequestPart(value = "image") image: MultipartFile?,
        form: SubcategoryModificationRequestForm,
        principal: Principal
    ) {
        subcategoryModificationRequestSubmissionService.submit(form, image, principal.name)
    }
}
