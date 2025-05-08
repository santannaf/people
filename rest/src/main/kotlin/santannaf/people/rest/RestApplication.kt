package santannaf.people.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["santannaf"])
class RestApplication

fun main(args: Array<String>) {
    runApplication<RestApplication>(args = args)
}
