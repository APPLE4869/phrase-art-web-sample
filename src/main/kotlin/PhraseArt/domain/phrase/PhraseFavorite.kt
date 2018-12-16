package PhraseArt.domain.phrase

import PhraseArt.domain.user.UserId

data class PhraseFavorite(
    val phraseId: PhraseId,
    val userId: UserId
)
