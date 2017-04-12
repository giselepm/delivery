package de.com.allpago.delivery

import de.com.allpago.delivery.domain.Graph
import de.com.allpago.delivery.domain.Person
import de.com.allpago.delivery.domain.Route

class BestRouteCalculator {

    private final List<Person> people
    private final List<Route> routes
    private Set<Person> settledPeople
    private Set<Person> unSettledPeople
    private Map<Person, Person> predecessors
    private Map<Person, Integer> smallestHards


    BestRouteCalculator(Graph graph) {
        this.people = new ArrayList<Person>(graph.people)
        this.routes = new ArrayList<Route>(graph.routes)
    }

    public void calculateBestRoutes(Person source) {
        settledPeople = []
        unSettledPeople = []
        smallestHards = [:]
        predecessors = [:]

        people.forEach { smallestHards.put(it, Integer.MAX_VALUE) }
        smallestHards.put(source, 0)

        updateSmallestHards(source)

        while (unSettledPeople.size() > 0) {
            Person person = getPersonWithSmallestHard(unSettledPeople)
            settledPeople.add(person)
            unSettledPeople.remove(person)
            updateSmallestHards(person)
        }
    }

    public Integer getBestRouteHard(Person target) {
        return smallestHards.get(target)
    }

    public LinkedList<Person> getBestRoute(Person target) {
        LinkedList<Person> path = new LinkedList<Person>()
        Person step = target

        if (!predecessors.get(step)) {
            return null
        }

        path.add(step)
        while (predecessors.get(step)) {
            step = predecessors.get(step)
            path.add(step)
        }

        Collections.reverse(path)
        return path
    }

    private void updateSmallestHards(Person origin) {
        List<Person> adjacentPeople = getNeighbors(origin)

        adjacentPeople.findAll {
            getBestRouteHard(it) > (getBestRouteHard(origin) + getRouteHard(origin, it))
        }.forEach { Person target ->
            smallestHards.put(target, getBestRouteHard(origin) + getRouteHard(origin, target))
            predecessors.put(target, origin)
            unSettledPeople.add(target)
        }

    }

    private int getRouteHard(Person origin, Person target) {
        return routes.find { it.source == origin && it.destination == target }?.hard

    }

    private List<Person> getNeighbors(Person person) {
        List<Person> neighbors = new ArrayList<Person>()

        routes.findAll {
            it.source == person && !isSettled(it.destination)
        }.forEach {
            neighbors.add(it.destination)
        }

        return neighbors
    }

    private Person getPersonWithSmallestHard(Set<Person> people) {
        Person minimum = null
        people.forEach { Person person ->
            if (!minimum) {
                minimum = person
            } else {
                if (getBestRouteHard(person) < getBestRouteHard(minimum)) {
                    minimum = person
                }
            }
        }
        return minimum
    }

    private boolean isSettled(Person Person) {
        return settledPeople.contains(Person)
    }

}
