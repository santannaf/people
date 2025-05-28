package santannaf.people.rest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import santannaf.demo.analyse.queries.analysequeries.annotation.EnableQueryAnalysis

@SpringBootApplication(scanBasePackages = ["santannaf"])
@EnableQueryAnalysis(appName = "customer-web")
class RestApplication

fun main(args: Array<String>) {
    runApplication<RestApplication>(args = args)
}
