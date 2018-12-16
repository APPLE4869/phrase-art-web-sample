package PhraseArt.presentation.api.controller.Phrase

import PhraseArt.application.Phrase.PhraseCommentService
import PhraseArt.presentation.api.controller.Support.PublicApiController
import PhraseArt.query.Dto.Phrase.PhraseCommentQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class PhraseCommentListController(@Autowired val phraseCommentService : PhraseCommentService) : PublicApiController() {
    @GetMapping("/phrases/{phraseId}/comments/previous")
    @ResponseStatus(value = HttpStatus.OK)
    fun priviousList(
        @PathVariable("phraseId") phraseId: String,
        @RequestParam(name="offset", defaultValue="0") offset: Int,
        @RequestParam(name="latest_comment_id", required = false) latestCommentId: String?
    ): MutableMap<String, List<PhraseCommentQueryDto>> {
        return mutableMapOf("comments" to phraseCommentService.previousList(phraseId, offset, latestCommentId))
    }

    @GetMapping("/phrases/{phraseId}/comments/following")
    @ResponseStatus(value = HttpStatus.OK)
    fun followingList(
        @PathVariable("phraseId") phraseId: String,
        @RequestParam(name="latest_comment_id") latestCommentId: String
    ): MutableMap<String, List<PhraseCommentQueryDto>> {
        return mutableMapOf("comments" to phraseCommentService.followingList(phraseId, latestCommentId))
    }
}
