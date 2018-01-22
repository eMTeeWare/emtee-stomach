package ui

import tornadofx.*

class MainView : View() {
    val leftView : FoodConsumptionView by inject()
    val topView : MainMenuBar by inject()
    val centerView : ConsumptionGraph by inject()
    override val root = borderpane {

    }
    init {
        with(root) {
            top = topView.root
            center = centerView.root
            left = leftView.root
        }
        // this will not work in the View constructor
        title = messages["appname"]
    }
}
