package ui

import model.DailyConsumptionData
import tornadofx.*

class FoodConsumptionView : View() {
    override val root = tableview<DailyConsumptionData> {

    }

}
