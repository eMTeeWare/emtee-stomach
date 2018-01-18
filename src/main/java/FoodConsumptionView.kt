import tornadofx.*
import javafx.scene.control.TableView

/**
 * Created by MThomas on 18.01.2018.
 */
class FoodConsumptionView : View("My View") {
    override val root = tableview<DailyConsumptionData> {

    }

}
