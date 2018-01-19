package ui

import javafx.scene.layout.BorderPane
import tornadofx.*

/**
 * Created by MThomas on 18.01.2018.
 */
class MainView : View("My View") {
    override val root = BorderPane()
    init {
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
