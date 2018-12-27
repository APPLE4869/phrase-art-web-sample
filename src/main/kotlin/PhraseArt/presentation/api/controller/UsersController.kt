package PhraseArt.presentation.api.controller

import PhraseArt.application.UserService
import PhraseArt.presentation.api.controller.Support.PrivateApiController
import PhraseArt.presentation.api.form.PasswordForm
import PhraseArt.presentation.api.form.UsernameForm
import PhraseArt.query.Dto.User.UserQueryDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
class UsersController(@Autowired val userService : UserService) : PrivateApiController() {
    @RequestMapping("/currentUser") // TODO : URLはスネークケースにしたいので、ここもアプリ側と一緒に修正する。
    fun currentUser(principal: Principal): MutableMap<String, UserQueryDto> {
        return mutableMapOf("currentUser" to userService.currentUser(principal.name))
    }

    @PatchMapping("/current_user/username")
    @ResponseStatus(value = HttpStatus.OK)
    fun updateUsername(@Valid @RequestBody form: UsernameForm, principal: Principal) {
        userService.updateUsername(form, principal.name)
    }

    @PatchMapping("/current_user/password")
    @ResponseStatus(value = HttpStatus.OK)
    fun updatePassword(@Valid @RequestBody form: PasswordForm, principal: Principal) {
        userService.updatePassword(form, principal.name)
    }
}
