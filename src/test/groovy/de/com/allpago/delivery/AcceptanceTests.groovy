package de.com.allpago.delivery

import com.opencsv.CSVReader
import de.com.allpago.delivery.domain.Graph
import de.com.allpago.delivery.domain.Package
import de.com.allpago.delivery.domain.Person
import de.com.allpago.delivery.domain.Route
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class AcceptanceTests extends Specification {

    static String fileDirectory = "csvs"
    static String csvFileExtension = ".csv"
    static String originName = "ME"
    static String testCaseIdentifier = "@"
    static String personHardSeparator = ":"
    static String packageMeasuresSeparator = "x"
    static String infinity = "~"

    def "testing scenarios in file #scenario.fileName"() {
        given:
        Graph graph = scenario.graph

        and:
        BestRouteCalculator dijkstra = new BestRouteCalculator(graph)
        dijkstra.calculateBestRoutes(Person.getAndAddPerson(originName, graph.people))

        and:
        List testCases = scenario.testCases

        expect:
        testCases.forEach { Map testCase ->
            assert ShippingCalculator.calculateShippingCost(dijkstra.getBestRouteHard(testCase.person), testCase.package) == testCase.cost
        }

        where:
        scenario << getCSVFiles().collect { loadScenario(it) }

    }


    private List<File> getCSVFiles() {
        File directory = new File(fileDirectory)

        return directory.listFiles().findAll { it.isFile() && it.getName().endsWith(csvFileExtension) }

    }

    private Map loadScenario(File csv) {

        CSVReader reader = new CSVReader(new FileReader(csv))
        List<String> lines = reader.readAll()
        Graph graph = generateGraph(lines.findAll { it[0] != testCaseIdentifier })

        return [fileName : csv.name,
                graph    : graph,
                testCases: generateTestCases(lines.findAll { it[0] == testCaseIdentifier }, graph.people)]
    }

    private Graph generateGraph(List csvRouteLines) {
        List<Person> people = []
        List<Route> routes = []

        csvRouteLines.forEach { line ->
            List routeParts = (List) line
            Person origin = Person.getAndAddPerson(routeParts.remove(0), people)

            routeParts.forEach {
                String it ->
                    routes.add(new Route(origin,
                            Person.getAndAddPerson(it.split(personHardSeparator).first(), people),
                            it.split(personHardSeparator).last().toInteger()))
            }
        }

        return new Graph(people, routes)

    }

    private List generateTestCases(List csvLines, List people) {
        List testCases = []

        csvLines.forEach { line ->
            List testCaseParts = (List) line

            List dimensions = testCaseParts.get(2).split(packageMeasuresSeparator)
            Package aPackage = new Package((dimensions.get(3) as Integer) / 1000, dimensions.get(0) as BigDecimal,
                    dimensions.get(1) as BigDecimal, dimensions.get(2) as BigDecimal)

            String costAsString = testCaseParts.get(3)
            BigDecimal cost = (costAsString == infinity ? Double.MAX_VALUE : costAsString) as BigDecimal

            testCases.add(
                    [person : Person.getAndAddPerson(testCaseParts.get(1), people),
                     package: aPackage,
                     cost   : cost])
        }

        return testCases

    }

}