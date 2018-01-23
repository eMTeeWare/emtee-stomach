package viewmodel

import model.DailyConsumptionData
import tornadofx.*

class DailyConsumptionDataModel : ItemViewModel<DailyConsumptionData>() {
    val mainDish = bind(DailyConsumptionData::mainDishProperty)
    val consumption = bind(DailyConsumptionData::consumptionProperty)
    val date = bind(DailyConsumptionData::dateProperty)
}
