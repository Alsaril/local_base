import Printer.printBarcode
import Printer.printCheck


fun main(args: Array<String>) {

    printBarcode("1322413423")

    val positions = ArrayList<Position>()
    positions.add(Position("afra", "aerfaerf", "23.00", "123.00", "12323.00"))
    positions.add(Position("afra", "aerfaerf", "23.00", "123.00", "12323.00"))
    positions.add(Position("afra", "aerfaerf", "23.00", "123.00", "12323.00"))
    printCheck(12, "123123123", "34.22.8093", 512, "34:72", positions, "123.00", "123.00", "0.00", "Человек")
}



