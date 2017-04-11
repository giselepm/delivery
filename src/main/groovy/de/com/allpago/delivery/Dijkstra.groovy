package de.com.allpago.delivery

class Dijkstra {

    private final List<Person> people
    private final List<Route> routes
    private Set<Person> settledPeople
    private Set<Person> unSettledPeople
    private Map<Person, Person> predecessors
    private Map<Person, Integer> smallestHards


    Dijkstra(Graph graph) {
        this.people = new ArrayList<Person>(graph.people)
        this.routes = new ArrayList<Route>(graph.routes)
    }

    public void execute(Person source) {
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

    public Integer getBestRoutesHard(Person target) {
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
            getBestRoutesHard(it) > (getBestRoutesHard(origin) + getRouteHard(origin, it))
        }.forEach { Person target ->
                smallestHards.put(target, getBestRoutesHard(origin) + getRouteHard(origin, target))
                predecessors.put(target, origin)
                unSettledPeople.add(target)
        }

    }

    private int getRouteHard(Person origin, Person target) {
        return routes.find { it.source == origin && it.destination == target}?.hard

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
                if (getBestRoutesHard(person) < getBestRoutesHard(minimum)) {
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
