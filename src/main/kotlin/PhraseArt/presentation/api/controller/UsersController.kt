package PhraseArt.presentation.api.controller

import PhraseArt.application.UserService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import PhraseArt.query.Dto.UserQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UsersController(@Autowired val userService : UserService) : PrivateApiController() {
    @RequestMapping("/currentUser")
    fun currentUser(principal: Principal): MutableMap<String, UserQueryDto> {
        return mutableMapOf("currentUser" to userService.currentUser(principal.name))
    }
}
