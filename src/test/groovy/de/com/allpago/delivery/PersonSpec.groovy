package de.com.allpago.delivery

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class PersonSpec extends Specification {

    def "getAndAddPerson adds a person when the list sent is empty and returns it"() {
        given:
        List people = []

        when:
        Person person = Person.getAndAddPerson("Gisele", people)

        then:
        people.size() == 1
        people.first() == person
    }

    def "getAndAddPerson adds a person when it doesn't exist in the list sent and returns it"() {
        given:
        Person aPerson = new Person("Gisele")
        List people = [aPerson]


        when:
        Person personReturned = Person.getAndAddPerson("Mary", people)

        then:
        people.size() == 2
        people.containsAll([aPerson, personReturned])
    }

    def "getAndAddPerson returns a person when it exists in the list sent"() {
        given:
        Person aPerson = new Person("Gisele")
        List people = [aPerson]

        when:
        Person person = Person.getAndAddPerson("Gisele", people)

        then:
        people.size() == 1
        people.first() == person
    }

}
