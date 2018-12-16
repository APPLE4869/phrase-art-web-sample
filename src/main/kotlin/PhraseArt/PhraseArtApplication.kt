package PhraseArt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class PhraseArtApplication

fun main(args: Array<String>) {
    runApplication<PhraseArtApplication>(*args)
}
