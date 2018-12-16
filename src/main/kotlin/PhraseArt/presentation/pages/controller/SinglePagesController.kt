package PhraseArt.presentation.pages.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class SinglePagesController {
  @GetMapping("/policy")
  fun policy(): String {
    return "SinglePages/policy"
  }
}
