package PhraseArt.presentation.api.controller.UpdateRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import PhraseArt.application.UpdateRequest.UpdateRequestService
import PhraseArt.query.Dto.UpdateRequestList.UpdateRequestQueryDto
import org.springframework.web.bind.annotation.*
import PhraseArt.presentation.api.controller.Support.PublicApiController

@RestController
class UpdateRequestListController(
        @Autowired val updateRequestService: UpdateRequestService
) : PublicApiController() {
    @GetMapping("/update_requests/requesting")
    @ResponseStatus(value = HttpStatus.OK)
    fun requestingList(@RequestParam(name="offset", defaultValue="0") offset: Int): MutableMap<String, List<UpdateRequestQueryDto>> {
        return mutableMapOf("updateRequests" to updateRequestService.requestingList(offset))
    }

    @GetMapping("/update_requests/finished")
    @ResponseStatus(value = HttpStatus.OK)
    fun finishedList(@RequestParam(name="offset", defaultValue="0") offset: Int): MutableMap<String, List<UpdateRequestQueryDto>> {
        return mutableMapOf("updateRequests" to updateRequestService.finishedList(offset))
    }
}
