package PhraseArt.presentation.api.controller.Support

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/private")
open class PrivateApiController : ExceptionController()
