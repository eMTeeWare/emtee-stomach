package ui

import javafx.scene.layout.BorderPane
import tornadofx.*

class MainView : View() {
    override val root = BorderPane()
    init {
        // this will not work in the View constructor
        title = messages["appname"]
        with(root) {
            top {
                root += MainMenuBar()
            }
            left {
                root += FoodConsumptionView()
            }
            center {
                root += ConsumptionGraph()
            }
        }
    }
}
