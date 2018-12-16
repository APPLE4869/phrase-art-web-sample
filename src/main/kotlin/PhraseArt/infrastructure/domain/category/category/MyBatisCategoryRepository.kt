package PhraseArt.infrastructure.domain.category.category

import PhraseArt.domain.category.*
import PhraseArt.infrastructure.domain.category.subcategory.SubcategoryDomainMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyBatisCategoryRepository(
    @Autowired private val categoryDomainMapper : CategoryDomainMapper
) : CategoryRepository {
    override fun categoryOfId(id: CategoryId): Category? {
        return categoryDomainMapper.selectOneById(id.value)?.let { daoToCategory(it) }
    }

    private fun daoToCategory(dao: CategoryDomainDao): Category {
        return Category(
            CategoryId(dao.id),
            dao.name,
            dao.imagePath,
            dao.sequence,
            dao.videoOnDemandAssociated
        )
    }
}
