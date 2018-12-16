package PhraseArt.presentation.api.controller.UpdateRequest

import PhraseArt.application.UpdateRequest.UpdateRequestCommentService
import PhraseArt.presentation.api.controller.Support.PublicApiController
import PhraseArt.query.Dto.UpdateRequest.UpdateRequestCommentQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class UpdateRequestCommentListController(@Autowired val updateRequestCommentService : UpdateRequestCommentService) : PublicApiController() {
    @GetMapping("/updateRequests/{updateRequestId}/comments/previous")
    @ResponseStatus(value = HttpStatus.OK)
    fun priviousList(
        @PathVariable("updateRequestId") updateRequestId: String,
        @RequestParam(name="offset", defaultValue="0") offset: Int,
        @RequestParam(name="latest_comment_id", required = false) latestCommentId: String?
    ): MutableMap<String, List<UpdateRequestCommentQueryDto>> {
        return mutableMapOf("comments" to updateRequestCommentService.previousList(updateRequestId, offset, latestCommentId))
    }

    @GetMapping("/updateRequests/{updateRequestId}/comments/following")
    @ResponseStatus(value = HttpStatus.OK)
    fun followingList(
        @PathVariable("updateRequestId") updateRequestId: String,
        @RequestParam(name="latest_comment_id") latestCommentId: String
    ): MutableMap<String, List<UpdateRequestCommentQueryDto>> {
        return mutableMapOf("comments" to updateRequestCommentService.followingList(updateRequestId, latestCommentId))
    }
}
