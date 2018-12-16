package PhraseArt.infrastructure.domain.phrase.phrase

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.phrase.*
import PhraseArt.domain.user.UserId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class MyBatisPhraseRepository(
        @Autowired private val phraseMapper: PhraseDomainMapper
) : PhraseRepository {
    override fun nextIdentity(): PhraseId {
        return PhraseId(uuid())
    }

    override fun phraseOfId(id: PhraseId): Phrase? {
        return phraseMapper.selectOneById(id.value)?.let { daoToPhrase(it) }
    }

    override fun phraseOf(
        categoryId: CategoryId,
        subcategoryId: SubcategoryId?,
        content: String,
        authorName: String
    ): Phrase? {
        return phraseMapper.selectOneBy(categoryId.value, subcategoryId?.value, content, authorName)?.let { daoToPhrase(it) }
    }

    override fun store(phrase: Phrase) {
        if (phraseMapper.selectOneById(phrase.id.value) == null)
            phraseMapper.insert(phrase.id.value, phrase.categoryId.value, phrase.subcategoryId?.value, phrase.authorName, phrase.content)
        else
            phraseMapper.update(phrase.id.value, phrase.categoryId.value, phrase.subcategoryId?.value, phrase.authorName, phrase.content)

        storeLikes(phrase.id, phrase.likes)
        storeFavorite(phrase.id, phrase.favorites)
    }

    // TODO : PhraseにひもづくCommentやLike, Favoriteも全て削除するようにする。
    override fun remove(phrase: Phrase) {
        return phraseMapper.deleteById(phrase.id.value)
    }

    private fun daoToPhrase(dao: PhraseDao): Phrase {
        return Phrase(
            PhraseId(dao.id),
            CategoryId(dao.categoryId),
            SubcategoryId(dao.subcategoryId),
            dao.content,
            dao.authorName,
            likesOfPhraseId(dao.id),
            favoritesOfPhraseId(dao.id)
        )
    }

    // TODO : Bulk Insertではないため、負荷が高くなりやすいので、より良いやり方を見つけたら改修する。
    private fun storeLikes(phraseId: PhraseId, likes: MutableSet<PhraseLike>) {
        likes.forEach { like ->
            phraseMapper.insertLikeWhereNotExists(uuid(), like.phraseId.value, like.userId.value)
        }

        val userIds = likes.map { it.userId.value }
        phraseMapper.deleteLikesWhereNotIn(phraseId.value, userIds)
    }

    // TODO : Bulk Insertではないため、負荷が高くなりやすいので、より良いやり方を見つけたら改修する。
    private fun storeFavorite(phraseId: PhraseId, favorites: MutableSet<PhraseFavorite>) {
        favorites.forEach { favorite ->
            phraseMapper.insertLikeWhereNotExists(uuid(), favorite.phraseId.value, favorite.userId.value)
        }

        val userIds = favorites.map { it.userId.value }
        phraseMapper.deleteFavoritesWhereNotIn(phraseId.value, userIds)
    }

    private fun likesOfPhraseId(phraseId: String): MutableSet<PhraseLike> {
        return phraseMapper.selectAllLikesByPhraseId(phraseId).map {
            PhraseLike(PhraseId(it.phraseId), UserId(it.userId))
        }.toMutableSet()
    }

    private fun favoritesOfPhraseId(phraseId: String): MutableSet<PhraseFavorite> {
        return phraseMapper.selectAllFavoritesByPhraseId(phraseId).map {
            PhraseFavorite(PhraseId(it.phraseId), UserId(it.userId))
        }.toMutableSet()
    }

    private fun uuid(): String {
        return UUID.randomUUID().toString().toUpperCase()
    }
}
