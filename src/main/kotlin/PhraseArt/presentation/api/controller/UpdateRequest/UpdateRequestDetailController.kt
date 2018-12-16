package PhraseArt.presentation.api.controller.UpdateRequest

import PhraseArt.application.UpdateRequest.UpdateRequestService
import PhraseArt.presentation.api.controller.Support.PublicApiController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
class UpdateRequestDetailController(
        @Autowired val updateRequestService: UpdateRequestService
) : PublicApiController() {
    @GetMapping("/update_request/{updateRequestId}/phrase_registration_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun phraseRegistrationRequestDetail(@PathVariable("updateRequestId") updateRequestId: String): MutableMap<String, Any?> {
        val principal = SecurityContextHolder.getContext().getAuthentication()
        val principalName = if (principal.name == "anonymousUser") null else principal.name
        val (request, decision) = updateRequestService.phraseRegistraionRequestOfId(updateRequestId, principalName)

        return mutableMapOf("phraseRegistrationRequest" to request, "phraseDecision" to decision)
    }

    @GetMapping("/update_request/{updateRequestId}/phrase_modification_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun phraseModificationRequestDetail(@PathVariable("updateRequestId") updateRequestId: String): MutableMap<String, Any?> {
        val principal = SecurityContextHolder.getContext().getAuthentication()
        val principalName = if (principal.name == "anonymousUser") null else principal.name
        val (request, decision) = updateRequestService.phraseModificationRequestOfId(updateRequestId, principalName)

        return mutableMapOf("phraseModificationRequest" to request, "phraseDecision" to decision)
    }

    @GetMapping("/update_request/{updateRequestId}/phrase_deletion_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun phraseDeletionRequestDetail(@PathVariable("updateRequestId") updateRequestId: String): MutableMap<String, Any?> {
        val principal = SecurityContextHolder.getContext().getAuthentication()
        val principalName = if (principal.name == "anonymousUser") null else principal.name
        val (request, decision) = updateRequestService.phraseDeletionRequestOfId(updateRequestId, principalName)

        return mutableMapOf("phraseDeletionRequest" to request, "phraseDecision" to decision)
    }
}
