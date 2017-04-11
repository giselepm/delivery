package de.com.allpago.delivery

import spock.lang.Specification

class DijkstraSpec extends Specification {

    static List<Person> people
    static List<Route> routes
    static Person me
    static Person stefan
    static Person amir
    static Person martin
    static Person adam
    static Person philipp
    static Person diana


    void setup() {
        me = new Person("me")
        stefan = new Person("Stefan")
        amir = new Person("Amir")
        martin = new Person("Martin")
        adam = new Person("Adam")
        philipp = new Person("Philipp")
        diana = new Person("Diana")

        people = [me, stefan, amir, martin, adam, philipp, diana]

        routes = [new Route(me, stefan, 100),
                  new Route(me, amir, 1042),
                  new Route(me, martin, 595),
                  new Route(me, adam, 10),
                  new Route(me, philipp, 128),
                  new Route(stefan, amir, 850),
                  new Route(stefan, adam, 85),
                  new Route(adam, philipp, 7),
                  new Route(adam, martin, 400),
                  new Route(diana, amir, 57),
                  new Route(diana, martin, 3)]

    }

    def "When there is no routes from me, there is no path to any person"() {
        given:
        Graph graph = new Graph(people, [])

        and:
        Dijkstra dijkstra = new Dijkstra(graph)
        dijkstra.execute(me)

        and:
        Person destination = people.get(new Random().nextInt(people.size() - 2) +1) // To get a random destination person that's not me

        when:
        LinkedList<Person> path = dijkstra.getBestRoute(destination)

        then:
        !path

    }

    def "When there is only one route from me to Philipp, that's the best route"() {
        given:

        List<Route> routes = [new Route(me, philipp, 100)]

        and:
        Graph graph = new Graph(people, routes)

        and:
        Dijkstra dijkstra = new Dijkstra(graph)
        dijkstra.execute(me)

        when:
        LinkedList<Person> path = dijkstra.getBestRoute(philipp)

        then:
        path == [me, philipp]
    }

    def "When there is two routes from me to Philipp, the smallest route should be chosen"() {
        given:
        Graph graph = new Graph(people, routes)

        and:
        Dijkstra dijkstra = new Dijkstra(graph)
        dijkstra.execute(me)

        when:
        LinkedList<Person> path = dijkstra.getBestRoute(philipp)

        then:
        path == [me, adam, philipp]
    }

    def "When there is two routes from me to Amir, the smallest route should be chosen"() {
        given:
        Graph graph = new Graph(people, routes)

        and:
        Dijkstra dijkstra = new Dijkstra(graph)
        dijkstra.execute(me)

        when:
        LinkedList<Person> path = dijkstra.getBestRoute(amir)

        then:
        path == [me, stefan, amir]
    }

    def "When the best route from me to Phillip is through adam and the package width=26cm, length=10cm, height=11cm and weight = 0.4Kg, the shipping cost should be 4.12 EUR"() {
        given:
        Graph graph = new Graph(people, routes)

        and:
        Dijkstra dijkstra = new Dijkstra(graph)
        dijkstra.execute(me)

        expect:
        Shipping.calculateShippingCost(dijkstra.getBestRoutesHard(philipp), new Package(0.4, 26, 10, 11)) == 4.12
    }

}
