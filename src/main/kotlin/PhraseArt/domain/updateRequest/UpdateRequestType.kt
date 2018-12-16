package PhraseArt.domain.updateRequest

enum class UpdateRequestType(val value: String) {
    PHRASE_REGISTRATION("PhraseRegistrationRequest"),
    PHRASE_MODIFICATION("PhraseModificationRequest"),
    PHRASE_DELETION("PhraseDeletionRequest"),
    SUBCATEGORY_MODIFICATION("SubcategoryModificationRequest")
}
