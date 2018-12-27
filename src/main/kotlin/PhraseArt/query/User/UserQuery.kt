package PhraseArt.query.User

import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.User.UserQueryDto

interface UserQuery {
    fun userOfId(id: UserId): UserQueryDto?
}
