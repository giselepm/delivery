package de.com.allpago.delivery

class Route {
    private Person source
    private Person destination
    private Integer hard

    Route(Person source, Person destination, int hard) {
        this.source = source
        this.destination = destination
        this.hard = hard
    }
}
