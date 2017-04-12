package de.com.allpago.delivery.domain

class Graph {
    List<Person> people
    List<Route> routes

    Graph(List<Person> people, List<Route> routes) {
        this.people = people
        this.routes = routes
    }
}
