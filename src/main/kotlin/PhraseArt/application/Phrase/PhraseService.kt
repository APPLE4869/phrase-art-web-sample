package PhraseArt.application.Phrase

import PhraseArt.domain.phrase.Phrase
import PhraseArt.domain.phrase.PhraseId
import PhraseArt.domain.phrase.PhraseRepository
import PhraseArt.domain.user.User
import PhraseArt.domain.user.UserId
import PhraseArt.domain.user.UserRepository
import PhraseArt.query.Phrase.PhraseQuery
import PhraseArt.query.Dto.Phrase.PhraseQueryDto
import PhraseArt.support.ForbiddenException
import PhraseArt.support.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PhraseService(
    @Autowired val phraseQuery : PhraseQuery,
    @Autowired val userRepository: UserRepository,
    @Autowired val phraseRepository: PhraseRepository
) {
    fun findAllPhrases(principalName: String?, offset: Int=0): List<PhraseQueryDto> {
        return phraseQuery.findAllPhrases(userIdOrNull(principalName), offset)
    }

    fun findAllPhrasesByCategoryId(categoryId: String, principalName: String?, offset: Int=0): List<PhraseQueryDto> {
        return phraseQuery.findAllPhrasesByCategoryId(categoryId, userIdOrNull(principalName), offset)
    }

    fun findAllPhrasesBySubcategoryId(subcategoryId: String, principalName: String?, offset: Int=0): List<PhraseQueryDto> {
        return phraseQuery.findAllPhrasesBySubcategoryId(subcategoryId, userIdOrNull(principalName), offset)
    }

    fun findPhrase(id: String, principalName: String?): PhraseQueryDto {
        return phraseQuery.findPhrase(id, userIdOrNull(principalName)) ?:
            throw NotFoundException("該当する名言は存在しません。")
    }

    @Transactional
    fun like(phraseId: String, principalName: String) {
        val (user, phrase) = userAndPhrasePair(UserId(principalName), PhraseId(phraseId))

        phrase.like(user)

        phraseRepository.store(phrase)
    }

    @Transactional
    fun unlike(phraseId: String, principalName: String) {
        val (user, phrase) = userAndPhrasePair(UserId(principalName), PhraseId(phraseId))

        phrase.unlike(user)

        phraseRepository.store(phrase)
    }

    @Transactional
    fun favorite(phraseId: String, principalName: String) {
        val (user, phrase) = userAndPhrasePair(UserId(principalName), PhraseId(phraseId))

        phrase.favorite(user)

        phraseRepository.store(phrase)
    }

    @Transactional
    fun unfavorite(phraseId: String, principalName: String) {
        val (user, phrase) = userAndPhrasePair(UserId(principalName), PhraseId(phraseId))

        phrase.unfavorite(user)

        phraseRepository.store(phrase)
    }

    private fun userIdOrNull(principalName: String?): UserId? {
        return principalName?.let { UserId(principalName) }
    }

    private fun userAndPhrasePair(userId: UserId, phraseId: PhraseId): Pair<User, Phrase> {
        val user = userRepository.userOfId(userId) ?:
            throw ForbiddenException("申請する権限がありません")

        val phrase = phraseRepository.phraseOfId(phraseId) ?:
            throw NotFoundException("該当する名言は存在しません。")

        return Pair(user, phrase)
    }
}
