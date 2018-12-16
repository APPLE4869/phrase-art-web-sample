package PhraseArt.infrastructure.domain.category.subcategory

import PhraseArt.domain.category.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyBatisSubcategoryRepository(
    @Autowired private val subcategoryDomainMapper : SubcategoryDomainMapper
) : SubcategoryRepository {
    override fun nextIdentity(): SubcategoryId {
        return SubcategoryId(uuid())
    }

    override fun subcategoryOfId(id: SubcategoryId): Subcategory? {
        return subcategoryDomainMapper.selectOneById(id.value)?.let { daoToSubcategory(it) }
    }

    override fun subcategoryOfCategoryIdAndName(categoryId: CategoryId, name: String): Subcategory? {
        return subcategoryDomainMapper.selectOneByCategoryIdAndName(categoryId.value, name)?.let { daoToSubcategory(it) }
    }

    override fun videoOnDemandsOfNameKeys(nameKeys: List<VideOnDemandNameKeyType>): List<VideoOnDemand> {
        return subcategoryDomainMapper.selectAllVIdeoOnDemandsByNameKeys(nameKeys.map{ it.value }).map {
            daoToVideoOnDemand(it)
        }
    }

    override fun store(subcategory: Subcategory) {
        if (subcategoryDomainMapper.selectOneById(subcategory.id.value) == null) {
            val subcategories = subcategoryDomainMapper.selectAllByCategoryId(subcategory.category.id.value)
            val sequence = subcategories.count() + 1

            subcategoryDomainMapper.insert(subcategory.id.value, subcategory.category.id.value, subcategory.name, subcategory.imagePath, subcategory.introduction, sequence)
        } else {
            subcategoryDomainMapper.update(subcategory.id.value, subcategory.category.id.value, subcategory.name, subcategory.imagePath, subcategory.introduction)
        }

        if (subcategory.category.isAssociatedVideoOnDemand()) {
            storeVideoOnDemands(subcategory)
        }
    }

    override fun remove(subcategory: Subcategory) {
        // TODO
    }

    private fun daoToSubcategory(dao: SubcategoryDomainDao): Subcategory {
        var videoOnDemands: MutableList<VideoOnDemand>? = null
        if (dao.categoryVideoOnDemandAssociated) {
            videoOnDemands = videoOnDemandOfSubcategory(dao.subcategoryId)
        }

        return Subcategory(
            SubcategoryId(dao.subcategoryId),
            Category(
                CategoryId(dao.categoryId),
                dao.categoryName,
                dao.categoryImagePath,
                dao.categorySequence,
                dao.categoryVideoOnDemandAssociated
            ),
            dao.subcategoryName,
            dao.subcategoryImagePath,
            dao.subcategoryIntroduction,
            videoOnDemands
        )
    }

    private fun daoToVideoOnDemand(dao: VideoOnDemandDomainDao): VideoOnDemand {
        return VideoOnDemand(
            VideOnDemandNameKeyType.fromValue(dao.nameKey) ?: throw IllegalArgumentException("動画配信サービスの名前キーが不正です"),
            dao.name,
            dao.imagePath,
            dao.url,
            dao.sequence
        )
    }

    private fun videoOnDemandOfSubcategory(subcategoryId: String): MutableList<VideoOnDemand> {
        return subcategoryDomainMapper.selectAllVideoOnDemandsBySubcategoryId(subcategoryId).map {
            daoToVideoOnDemand(it)
        }.toMutableList()
    }

    // TODO : 一旦中間テーブルの全てのレコードを削除してから再登録する方法で実装しているが、より効率的な方法がわかれば改修する。
    private fun storeVideoOnDemands(subcategory: Subcategory) {
        subcategoryDomainMapper.deleteAllSubcategoryVideoOnDemandBySubcategoryId(subcategory.id.value)

        val videoOnDemands = subcategory.videoOnDemands?.toList()
        if (videoOnDemands == null || videoOnDemands.count() == 0) {
            return
        }

        videoOnDemands.forEach { videoOnDemand ->
            val videoOnDemand = subcategoryDomainMapper.selectOneVideOnDemandByNameKey(videoOnDemand.nameKey.value) ?:
                throw IllegalArgumentException("動画配信サービスの名前キーが不正です")
            subcategoryDomainMapper.insertSubcategoryVideoOnDemand(uuid(), subcategory.id.value, videoOnDemand.id)
        }
    }

    private fun uuid(): String {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
