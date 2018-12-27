package PhraseArt.infrastructure.query.Category.subcategoryQuery

import PhraseArt.infrastructure.query.Category.videoOnDemandQuery.VideoOnDemandQueryDao
import PhraseArt.query.Category.SubcategoryQuery
import PhraseArt.query.Dto.Category.SubcategoryQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MyBatisSubcategoryQuery(
    @Autowired private val subcategoryMapper: SubcategoryQueryMapper
) : SubcategoryQuery {
    @Value("\${aws.bucket_name}")
    val bucketName: String = ""

    @Value("\${aws.s3_root_url}")
    val s3RootUrl: String = ""

    override fun findAllSubcategories(categoryId: String, offset: Int): List<SubcategoryQueryDto> {
        return subcategoryMapper.selectAllByCategoryId(categoryId, 20, offset).map {
            daoToSubcategory(it, null)
        }
    }

    override fun findSubcategory(id: String): SubcategoryQueryDto? {
        return subcategoryMapper.selectOneById(id)?.let {
            val videoOnDemandNameKeys = subcategoryMapper.selectAllVideoOnDemandNameKeysBySubcategoryId(it.id)
            daoToSubcategory(it, videoOnDemandNameKeys)
        }
    }

    override fun findAllCandidatesSubcategories(categoryId: String?, word: String): List<SubcategoryQueryDto> {
        return subcategoryMapper.selectAllByCategoryIdAndWord(categoryId, word, 10).map {
            daoToSubcategory(it, null)
        }
    }

    private fun daoToSubcategory(dao: SubcategoryQueryDao, videoOnDemandNameKeys: List<String>?): SubcategoryQueryDto {
        val image =
            if (dao.imagePath != null)
                "${s3RootUrl}/${bucketName}/${dao.imagePath}"
            else
                null

        return SubcategoryQueryDto(
            dao.id,
            dao.categoryId,
            dao.categoryName,
            dao.videoOnDemandAssociated,
            dao.name,
            image,
            dao.introduction,
            videoOnDemandNameKeys
        )
    }
}
