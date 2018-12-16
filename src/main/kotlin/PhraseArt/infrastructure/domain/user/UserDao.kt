package PhraseArt.infrastructure.domain.user

data class UserDao(
    val id: String,
    val username: String,
    val hashedPassword: String,
    val freezen: Boolean,
    val profileImagePath: String?
)
