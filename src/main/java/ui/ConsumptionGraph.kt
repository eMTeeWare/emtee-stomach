package ui

import javafx.scene.Parent
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import tornadofx.*

class ConsumptionGraph : View() {
    override val root = linechart(messages["diagramtitle"], CategoryAxis(), NumberAxis()) {

    }

}