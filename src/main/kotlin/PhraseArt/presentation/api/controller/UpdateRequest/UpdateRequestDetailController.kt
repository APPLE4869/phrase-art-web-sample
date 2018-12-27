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
        val (request, decision) =
            updateRequestService.phraseRegistraionRequestOfId(updateRequestId, principalName())

        return mutableMapOf("phraseRegistrationRequest" to request, "phraseDecision" to decision)
    }

    @GetMapping("/update_request/{updateRequestId}/phrase_modification_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun phraseModificationRequestDetail(@PathVariable("updateRequestId") updateRequestId: String): MutableMap<String, Any?> {
        val (request, decision) =
            updateRequestService.phraseModificationRequestOfId(updateRequestId, principalName())

        return mutableMapOf("phraseModificationRequest" to request, "phraseDecision" to decision)
    }

    @GetMapping("/update_request/{updateRequestId}/phrase_deletion_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun phraseDeletionRequestDetail(@PathVariable("updateRequestId") updateRequestId: String): MutableMap<String, Any?> {
        val (request, decision) =
            updateRequestService.phraseDeletionRequestOfId(updateRequestId, principalName())

        // TODO : 2つ目のphraseDecisionというキー名はupdateRequestDecisionに変更する。
        // phraseDecisionになっているのは、元々のデータ構造だとその名前になっていたため。(アプリ側のリリースを考慮して改修する。)
        return mutableMapOf("phraseDeletionRequest" to request, "phraseDecision" to decision)
    }

    @GetMapping("/update_request/{updateRequestId}/subcategory_modification_request")
    @ResponseStatus(value = HttpStatus.OK)
    fun subcategoryModificationRequestDetail(@PathVariable("updateRequestId") updateRequestId: String): MutableMap<String, Any?> {
        val (request, decision) =
            updateRequestService.subcategoryModificationRequestOfId(updateRequestId, principalName())

        return mutableMapOf("subcategoryModificationRequest" to request, "updateRequestDecision" to decision)
    }

    private fun principalName(): String? {
        val principal = SecurityContextHolder.getContext().getAuthentication()
        return if (principal.name == "anonymousUser") null else principal.name
    }
}
