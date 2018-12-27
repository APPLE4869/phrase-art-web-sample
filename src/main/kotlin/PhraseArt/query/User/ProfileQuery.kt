package PhraseArt.query.User

import PhraseArt.domain.user.UserId
import PhraseArt.query.Dto.User.ProfileQueryDto

interface ProfileQuery {
    fun profileOfUserId(userId: UserId): ProfileQueryDto?
}
