package PhraseArt.presentation.api.controller.Category

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import PhraseArt.application.Category.SubcategoryService
import PhraseArt.presentation.api.controller.Support.PublicApiController
import PhraseArt.query.Dto.Category.SubcategoryQueryDto
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import PhraseArt.support.NotFoundException

@RestController
class SubcategoriesController(@Autowired val subcategoryService : SubcategoryService) : PublicApiController() {
    @RequestMapping("/categories/{categoryId}/subcategories")
    fun list(@PathVariable("categoryId") categoryId: String, @RequestParam(name="offset", defaultValue="0") offset: Int): MutableMap<String, List<SubcategoryQueryDto>> {
        return mutableMapOf("subcategories" to  subcategoryService.findAllSubcategories(categoryId, offset))
    }

    @RequestMapping("/subcategories/{id}")
    fun detail(@PathVariable("id") subcategoryId: String): MutableMap<String, SubcategoryQueryDto> {
        val subcategory: SubcategoryQueryDto =
                subcategoryService.findSubcategory(subcategoryId) ?:
                throw NotFoundException("subcategory is not found : ${subcategoryId}")
        return mutableMapOf("subcategory" to subcategory)
    }

    // 候補一覧
    @RequestMapping("/subcategories/candidates")
    fun candidatesList(
        @RequestParam(name="categoryId") categoryId: String?,
        @RequestParam(name="word") word: String
    ): MutableMap<String, List<SubcategoryQueryDto>> {
        return mutableMapOf("subcategories" to subcategoryService.findAllCandidatesSubcategories(categoryId, word))
    }
}
