import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.print.PrinterJob
import javafx.scene.Scene
import javafx.scene.web.WebView
import javafx.stage.Stage
import org.krysalis.barcode4j.impl.code128.Code128Bean
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider
import java.awt.Graphics2D
import java.awt.print.Printable
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object Printer {
    fun printBarcode(barcode: String) {
        val pj = java.awt.print.PrinterJob.getPrinterJob()
        pj.setPrintable({
            graphics, page, pageIndex ->
            run {
                if (pageIndex > 0) {
                    Printable.NO_SUCH_PAGE
                } else {
                    val bean = Code128Bean()
                    bean.doQuietZone(false);
                    val g2d = graphics as Graphics2D
                    g2d.translate(300, 100)
                    g2d.scale(5.0, 5.0)

                    val canvas = Java2DCanvasProvider(g2d, 0)
                    bean.generateBarcode(canvas, barcode)

                    Printable.PAGE_EXISTS
                }
            }
        })
        pj.print()
    }


    val HEADER_TEMPLATE = "templates/header.html"
    val ROW_TEMPLATE = "templates/row.html"
    val FOOTER_TEMPLATE = "templates/footer.html"

    val NAME = "Человек-продавец"

    fun printCheck(cashbox: Int,
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
}

data class Position(
        val vendor: String,
        val name: String,
        val price: String,
        val count: String,
        val sum: String
)