package santannaf.people.core.exception

class PeopleNotFoundException(override val message: String = "People not found") : RuntimeException(message)
