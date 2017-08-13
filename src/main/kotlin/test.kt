import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.print.PrinterJob
import javafx.scene.Scene
import javafx.scene.web.WebView
import javafx.stage.Stage
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors


fun main(args: Array<String>) {
    val positions = ArrayList<Position>()
    positions.add(Position("afra", "aerfaerf", "23.00", "123.00", "12323.00"))
    positions.add(Position("afra", "aerfaerf", "23.00", "123.00", "12323.00"))
    positions.add(Position("afra", "aerfaerf", "23.00", "123.00", "12323.00"))
    print(12, "123123123", "34.22.8093", 512, "34:72", positions, "123.00", "123.00", "0.00", "Человек")
}

val HEADER_TEMPLATE = "templates/header.html"
val ROW_TEMPLATE = "templates/row.html"
val FOOTER_TEMPLATE = "templates/footer.html"

val NAME = "Человек-продавец"

fun print(cashbox: Int,
          inn: String,
          date: String,
          number: Int,
          time: String,
          positions: List<Position>,
          sum: String,
          cash: String,
          odd: String,
          operator: String) {
    val headerTemplate = String(Files.readAllBytes(Paths.get(HEADER_TEMPLATE)))
    val rowTemplate = String(Files.readAllBytes(Paths.get(ROW_TEMPLATE)))
    val footerTemplate = String(Files.readAllBytes(Paths.get(FOOTER_TEMPLATE)))

    val headerLength = NAME.length

    val headerBuilder = StringBuilder()
    for (i in 0..headerLength + 2) {
        headerBuilder.append('*')
    }

    val asteriskHeader = headerBuilder.toString()

    val header = headerTemplate.format(asteriskHeader, "*$NAME*", asteriskHeader, cashbox, inn, date, number, time)

    val rows: String = positions
            .map { (vendor, name, price, count, sum) -> rowTemplate.format(vendor, name, price, count, sum) }
            .stream()
            .collect(Collectors.joining(""))

    val footer = footerTemplate.format(sum, cash, odd, operator)

    val html = "$header$rows$footer"

    JFXPanel()
    Platform.runLater({
        val stage = Stage()
        val wv = WebView()
        wv.engine.loadContent(html)
        stage.scene = Scene(wv)
        stage.setOnShown {
            val pj = PrinterJob.createPrinterJob()
            val b = pj.printPage(wv)
            if (b) {
                pj.endJob()
            }
            Platform.runLater({ stage.close() })
        }
        stage.show()
    })
}

data class Position(
        val vendor: String,
        val name: String,
        val price: String,
        val count: String,
        val sum: String
)