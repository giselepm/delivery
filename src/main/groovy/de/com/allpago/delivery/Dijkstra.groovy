package de.com.allpago.delivery

class Dijkstra {

    private final List<Person> people
    private final List<Route> routes
    private Set<Person> settledPeople
    private Set<Person> unSettledPeople
    private Map<Person, Person> predecessors
    private Map<Person, Integer> hard

    Dijkstra(Graph graph) {
        this.people = new ArrayList<Person>(graph.people)
        this.routes = new ArrayList<Route>(graph.routes)
    }

    public void execute(Person source) {
        settledPeople = new HashSet<Person>()
        unSettledPeople = new HashSet<Person>()
        hard = new HashMap<Person, Integer>()
        predecessors = new HashMap<Person, Person>()

        hard.put(source, 0)
        unSettledPeople.add(source)

        while (unSettledPeople.size() > 0) {
            Person person = getMinimum(unSettledPeople)
            settledPeople.add(person)
            unSettledPeople.remove(person)
            findMinimalHard(person)
        }
    }

    private void findMinimalHard(Person node) {
        List<Person> adjacentNodes = getNeighbors(node)
        adjacentNodes.forEach { Person target ->
            if (getSmallestHard(target) > (getSmallestHard(node) + getHard(node, target))) {

                hard.put(target, getSmallestHard(node) + getHard(node, target))
                predecessors.put(target, node)
                unSettledPeople.add(target)
            }
        }

    }

    private int getHard(Person node, Person target) {
        for (Route route : routes) {
            if (route.source == node && route.destination == target) {
                return route.hard
            }
        }

        throw new RuntimeException("Should not happen")
    }

    private List<Person> getNeighbors(Person node) {
        List<Person> neighbors = new ArrayList<Person>()
        routes.forEach { Route route ->
            if (route.source == node && !isSettled(route.destination)) {
                neighbors.add(route.destination)
            }
        }
        return neighbors
    }

    private Person getMinimum(Set<Person> people) {
        Person minimum = null
        people.forEach { Person person ->
            if (!minimum) {
                minimum = person
            } else {
                if (getSmallestHard(person) < getSmallestHard(minimum)) {
                    minimum = person
                }
            }
        }
        return minimum
    }

    private boolean isSettled(Person Person) {
        return settledPeople.contains(Person)
    }

    private int getSmallestHard(Person destination) {
        Integer d = hard.get(destination)
        if (d == null) {
            return Integer.MAX_VALUE;
        }

        return d;
    }

    /*
     * This method returns the best route from the source to the selected target and NULL if no path exists
     */
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

}
