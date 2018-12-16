package PhraseArt.presentation.api.form.UpdateRequest

import org.springframework.web.multipart.MultipartFile

data class SubcategoryModificationRequestForm(
    val id: String = "",
    val image: MultipartFile? = null,
    val name: String = "",
    val videoOnDemandNameKeys: List<String>,
    val introduction: String = ""
)
