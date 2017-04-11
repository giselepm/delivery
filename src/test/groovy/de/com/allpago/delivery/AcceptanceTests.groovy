package de.com.allpago.delivery

import com.opencsv.CSVReader
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class AcceptanceTests extends Specification {

    def "testing scenarios in file #scenario.fileName"() {
        given:
        Graph graph = scenario.graph

        and:
        Dijkstra dijkstra = new Dijkstra(graph)
        dijkstra.execute(Person.getAndAddPerson("ME", graph.people))

        and:
        List testCases = scenario.testCases

        expect:
        testCases.forEach { Map testCase ->
            assert Shipping.calculateShippingCost(dijkstra.getBestRoutesHard(testCase.person), testCase.package) == testCase.cost
        }

        where:
        scenario << getCSVFiles().collect { loadScenario(it) }

    }


    private List<File> getCSVFiles() {
        File directory = new File("csvs")

        return directory.listFiles().findAll { it.isFile() && it.getName().endsWith('.csv') }

    }

    private Map loadScenario(File csv) {

        CSVReader reader = new CSVReader(new FileReader(csv))
        List<String> lines = reader.readAll()
        Graph graph = generateGraph(lines.findAll { it[0] != "@" })

        return [fileName : csv.name,
                graph    : graph,
                testCases: generateTestCases(lines.findAll { it[0] == "@" }, graph.people)]
    }

    private Graph generateGraph(List csvRouteLines) {
        List<Person> people = []
        List<Route> routes = []

        csvRouteLines.forEach { line ->
            List routeParts = (List) line
            Person origin = Person.getAndAddPerson(routeParts.remove(0), people)

            routeParts.forEach {
                String it ->
                    routes.add(new Route(origin, Person.getAndAddPerson(it.split(':').first(), people), it.split(':').last().toInteger()))
            }
        }

        return new Graph(people, routes)

    }

    private List generateTestCases(List csvLines, List people) {
        List testCases = []

        csvLines.forEach { line ->
            List testCaseParts = (List) line

            List dimensions = testCaseParts.get(2).split('x')
            Package aPackage = new Package((dimensions.get(3) as Integer) / 1000, dimensions.get(0) as BigDecimal,
                    dimensions.get(1) as BigDecimal, dimensions.get(2) as BigDecimal)

            String costAsString = testCaseParts.get(3)
            BigDecimal cost = (costAsString == "~" ? Double.MAX_VALUE : costAsString) as BigDecimal

            testCases.add(
                    [person : Person.getAndAddPerson(testCaseParts.get(1), people),
                     package: aPackage,
                     cost   : cost])

        }

        return testCases

    }

}