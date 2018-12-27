package PhraseArt.domain.phrase

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.SubcategoryId
import PhraseArt.domain.support.Entity
import PhraseArt.domain.updateRequest.phrase.PhraseModificationRequest
import PhraseArt.domain.updateRequest.phrase.PhraseRegistrationRequest
import PhraseArt.domain.user.User

class Phrase : Entity {
    val id: PhraseId
    var categoryId: CategoryId
    var subcategoryId: SubcategoryId?
    var content: String
        set(value) {
            this.assertArgumentNotEmpty(value, "内容を入力してください")
            this.assertArgumentLength(value, 500, "内容は500文字以内にしてください")
            field = value
        }
    var authorName: String
        set(value) {
            this.assertArgumentNotEmpty(value, "作者を入力してください")
            this.assertArgumentLength(value, 36, "作者は36文字以内にしてください")
            field = value
        }
    val likes: MutableSet<PhraseLike>
    val favorites: MutableSet<PhraseFavorite>

    constructor(
        anId: PhraseId,
        categoryId: CategoryId,
        aSubcategoryId: SubcategoryId?,
        aContent: String,
        anAuthorName: String,
        likes: MutableSet<PhraseLike>,
        favorites: MutableSet<PhraseFavorite>
    ) {
        this.id = anId
        this.categoryId = categoryId
        this.subcategoryId = aSubcategoryId
        this.content = aContent
        this.authorName = anAuthorName
        this.likes = likes
        this.favorites = favorites
    }

    companion object {
        // 登録申請を元に名言を作成する
        fun createFromRegistrationRequest(aPhraseId: PhraseId, request: PhraseRegistrationRequest): Phrase {
            return Phrase(
                aPhraseId,
                request.requestedCategoryId,
                request.requestedSubcategoryId,
                request.requestedPhraseContent,
                request.requestedPhraseAuthorName,
                mutableSetOf(),
                mutableSetOf()
            )
        }
    }

    fun comment(aCommentId: PhraseCommentId, anUser: User, aContent: String): PhraseComment {
        return PhraseComment(
            aCommentId,
            anUser.id,
            id,
            aContent
        )
    }

    fun like(user: User) {
        likes.add(PhraseLike(id, user.id))
    }

    fun unlike(user: User) {
        run loop@{
            likes.forEach { like ->
                if (like.userId == user.id) {
                    likes.remove(like)
                    return@loop
                }
            }
        }
    }

    fun favorite(user: User) {
        favorites.add(PhraseFavorite(id, user.id))
    }

    // TODO : unfavoriteという単語が微妙なので、落ち着いたらより良いものに修正する。(unlikeの方も同様)
    fun unfavorite(user: User) {
        run loop@{
            favorites.forEach { favorite ->
                if (favorite.userId == user.id) {
                    favorites.remove(favorite)
                    return@loop
                }
            }
        }
    }

    // 修正申請の内容を反映させる
    fun reflectModificationRequest(request: PhraseModificationRequest) {
        this.categoryId = request.requestedCategoryId
        this.subcategoryId = request.requestedSubcategoryId
        this.content = request.requestedPhraseContent
        this.authorName = request.requestedPhraseAuthorName
    }
}
