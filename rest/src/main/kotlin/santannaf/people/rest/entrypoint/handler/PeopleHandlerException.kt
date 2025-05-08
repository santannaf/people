package santannaf.people.rest.entrypoint.handler

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException
import java.sql.SQLTransientConnectionException
import java.time.format.DateTimeParseException
import java.util.concurrent.TimeoutException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import santannaf.people.core.exception.PeopleNotFoundException

@RestControllerAdvice
class PeopleHandlerException {
    @ExceptionHandler(
        DuplicateKeyException::class,
        IllegalArgumentException::class,
        HttpMessageNotReadableException::class,
        InvalidDefinitionException::class,
        HttpMessageConversionException::class,
        DuplicateKeyException::class,
        SQLTransientConnectionException::class,
        DataIntegrityViolationException::class,
        DateTimeParseException::class
    )
    fun onBusinessException(e: Exception): ResponseEntity<*> {
        return ResponseEntity.unprocessableEntity().build<String>()
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun onMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<*> {
        return ResponseEntity.badRequest().build<String>()
    }

    @ExceptionHandler(PeopleNotFoundException::class)
    fun onPeopleNotFoundException(e: PeopleNotFoundException): ResponseEntity<*> {
        return ResponseEntity.notFound().build<String>()
    }

    @ExceptionHandler(TimeoutException::class)
    fun onTimeoutException(e: TimeoutException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT.value()).build<String>()
    }
}
