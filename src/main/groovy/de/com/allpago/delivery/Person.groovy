package de.com.allpago.delivery

class Person {
    private String name

    Person(String name) {
        this.name = name
    }

    static Person getAndAddPerson(String name, List<Person> people) {
        Person person = people.find { it.name == name }

        if (!person) {
            person = new Person(name)
            people.add(person)
        }

        return person
    }

}
