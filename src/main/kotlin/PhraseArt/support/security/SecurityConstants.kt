package PhraseArt.support.security

object SecurityConstants {
    val SECRET = "SAMPLE"
    val EXPIRATION_TIME: Long = 31536000000 // 1year
    val TOKEN_PREFIX = "Bearer "
    val HEADER_STRING = "Authorization"
    const val SIGNUP_URL = "/api/public/signup"
    val LOGIN_URL = "/api/public/login"
    val LOGIN_ID = "username" // defalut:username
    val PASSWORD = "password" // default:password
}
