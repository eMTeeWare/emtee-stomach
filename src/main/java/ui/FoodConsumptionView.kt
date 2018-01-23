package ui

import controller.DailyConsumptionController
import model.DailyConsumptionData
import tornadofx.*

class FoodConsumptionView : View() {
    val controller : DailyConsumptionController by inject()
    override val root = tableview<DailyConsumptionData> {

        items = controller.dailyConsumes

        column(messages["dateheader"], DailyConsumptionData::dateProperty)
        column(messages["caloryheader"], DailyConsumptionData::consumptionProperty)
        column(messages["maindishheader"], DailyConsumptionData::mainDishProperty)

    }

}
