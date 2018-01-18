import tornadofx.*

/**
 * Created by MThomas on 18.01.2018.
 */
class MainView : View("My View") {
    override val root = borderpane {
        top {
            MainMenuBar()
        }
        left {
            FoodConsumptionView()
        }
        center {
            ConsumptionGraph()
        }
    }
}
