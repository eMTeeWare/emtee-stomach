package ui

import javafx.scene.Parent
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import tornadofx.*

/**
 * Created by MThomas on 18.01.2018.
 */
class ConsumptionGraph : View() {
    override val root = linechart(messages["diagramtitle"], CategoryAxis(), NumberAxis()) {

    }

}