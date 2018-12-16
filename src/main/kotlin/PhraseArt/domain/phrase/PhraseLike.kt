package PhraseArt.domain.phrase

import PhraseArt.domain.user.UserId

data class PhraseLike(
    val phraseId: PhraseId,
    val userId: UserId
)
