package PhraseArt.presentation.api.controller.UpdateRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import PhraseArt.application.UpdateRequest.DecisionService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class DecisionController(
    @Autowired val decisionService: DecisionService
) : PrivateApiController() {
    @PostMapping("/update_requests/{updateRequestId}/approve")
    @ResponseStatus(value = HttpStatus.OK)
    fun approve(@PathVariable("updateRequestId") updateRequestId: String, principal: Principal) {
        decisionService.approve(updateRequestId, principal.name)
    }

    @PostMapping("/update_requests/{updateRequestId}/reject")
    @ResponseStatus(value = HttpStatus.OK)
    fun reject(@PathVariable("updateRequestId") updateRequestId: String, principal: Principal) {
        decisionService.reject(updateRequestId, principal.name)
    }
}
