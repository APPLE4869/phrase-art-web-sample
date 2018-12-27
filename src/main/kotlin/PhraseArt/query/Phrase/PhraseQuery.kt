package PhraseArt.query.Phrase

import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.Phrase.PhraseQueryDto

interface PhraseQuery {
    fun findAllPhrases(userId: UserId?, offset: Int=0): List<PhraseQueryDto>

    fun findAllFavoritePhraseByUserId(userId: UserId, offset: Int=0): List<PhraseQueryDto>

    fun findAllPhraseBySearchWord(userId: UserId?, searchWord: String, offset: Int=0): List<PhraseQueryDto>

    fun findAllPhrasesByCategoryId(categoryId: String, userId: UserId?, offset: Int=0): List<PhraseQueryDto>

    fun findAllPhrasesBySubcategoryId(subcategoryId: String, userId: UserId?, offset: Int=0): List<PhraseQueryDto>

    fun findPhrase(id: String, userId: UserId?): PhraseQueryDto?
}
