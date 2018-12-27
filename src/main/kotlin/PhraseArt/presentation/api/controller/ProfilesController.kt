package PhraseArt.presentation.api.controller

import PhraseArt.application.ProfileService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import PhraseArt.query.Dto.User.ProfileQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal
import org.springframework.web.multipart.MultipartFile


@RestController
class ProfilesController(@Autowired val profileService : ProfileService) : PrivateApiController() {
    @RequestMapping("/current_profile")
    fun currentProfile(principal: Principal): MutableMap<String, ProfileQueryDto> {
        return mutableMapOf("currentProfile" to profileService.currentProfile(principal.name))
    }

    @PatchMapping("/current_profile/image")
    @ResponseStatus(value = HttpStatus.OK)
    fun uploadImage(@RequestPart(value = "image") image: MultipartFile, principal: Principal) {
        profileService.updateImage(image, principal.name)
    }
}
