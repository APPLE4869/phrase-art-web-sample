package PhraseArt.presentation.api.controller

import PhraseArt.application.UpdateProfileImageService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Principal
import org.springframework.web.multipart.MultipartFile


@RestController
class ProfilesController(@Autowired val updateProfileImageService : UpdateProfileImageService) : PrivateApiController() {
    @PatchMapping("/profileImage")
    fun uploadFile(@RequestPart(value = "image") image: MultipartFile, principal: Principal) {
        updateProfileImageService.updateImage(image, principal.name)
    }
}
