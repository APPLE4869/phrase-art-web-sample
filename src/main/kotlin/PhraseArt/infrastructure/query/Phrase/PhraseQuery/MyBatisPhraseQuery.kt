package PhraseArt.infrastructure.query.Phrase.PhraseQuery

import PhraseArt.domain.user.UserId
import PhraseArt.query.Phrase.PhraseQuery
import PhraseArt.query.Dto.Phrase.PhraseQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MyBatisPhraseQuery(
    @Autowired private val phraseMapper: PhraseQueryMapper
) : PhraseQuery {
    override fun findAllPhrases(userId: UserId?, offset: Int): List<PhraseQueryDto> {
        return phraseMapper.selectAll(userId?.value, 20, offset).map {
            daoToPhrase(it)
        }
    }

    override fun findAllFavoritePhraseByUserId(userId: UserId, offset: Int): List<PhraseQueryDto> {
        return phraseMapper.selectAllFavoriteByUserId(userId.value, 20, offset).map {
            daoToPhrase(it)
        }
    }

    override fun findAllPhraseBySearchWord(userId: UserId?, searchWord: String, offset: Int): List<PhraseQueryDto> {
        return phraseMapper.selectAllBySearchWord(userId?.value, searchWord, 20, offset).map {
            daoToPhrase(it)
        }
    }

    override fun findAllPhrasesByCategoryId(categoryId: String, userId: UserId?, offset: Int): List<PhraseQueryDto> {
        return phraseMapper.selectAllByCategoryId(categoryId, userId?.value, 20, offset).map {
            daoToPhrase(it)
        }
    }

    override fun findAllPhrasesBySubcategoryId(subcategoryId: String, userId: UserId?, offset: Int): List<PhraseQueryDto> {
        return phraseMapper.selectAllBySubcategoryId(subcategoryId, userId?.value, 20, offset).map {
            daoToPhrase(it)
        }
    }

    override fun findPhrase(id: String, userId: UserId?): PhraseQueryDto? {
        return phraseMapper.selectOneById(id, userId?.value)?.let { daoToPhrase(it) }
    }

    private fun daoToPhrase(dao: PhraseDao): PhraseQueryDto {
        return PhraseQueryDto(
            dao.phraseId,
            dao.categoryId,
            dao.categoryName,
            dao.subcategoryId,
            dao.subcategoryName,
            dao.phraseContent,
            dao.authorName,
            dao.likeCount.toInt(),
            dao.favoriteCount.toInt(),
            dao.commentCount.toInt(),
            dao.currentUserLiked == "1",
            dao.currentUserFavorited == "1"
        )
    }
}
