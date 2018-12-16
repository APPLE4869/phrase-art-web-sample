package PhraseArt.query

import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.UserQueryDto

interface UserQuery {
    fun userOfId(id: UserId): UserQueryDto?
}
