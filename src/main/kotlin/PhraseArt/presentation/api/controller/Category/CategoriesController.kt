package PhraseArt.presentation.api.controller.Category

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import PhraseArt.application.Category.CategoryService
import PhraseArt.presentation.api.controller.Support.PublicApiController
import PhraseArt.query.Dto.Category.CategoryQueryDto
import PhraseArt.support.NotFoundException
import org.springframework.web.bind.annotation.PathVariable

@RestController
class CategoriesController(@Autowired val categoryService : CategoryService) : PublicApiController() {
    @RequestMapping("/categories")
    fun list(): MutableMap<String, List<CategoryQueryDto>> {
        return mutableMapOf("categories" to categoryService.findAllCategories())
    }

    @RequestMapping("/categories/{id}")
    fun detail(@PathVariable("id") categoryId: String): MutableMap<String, CategoryQueryDto> {
        val category: CategoryQueryDto =
                categoryService.findCategory(categoryId) ?:
                throw NotFoundException("category is not found : ${categoryId}")
        return mutableMapOf("category" to category)
    }
}
