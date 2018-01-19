package ui

import model.DailyConsumptionData
import tornadofx.*
import java.time.LocalDate
import java.time.Month

class FoodConsumptionView : View() {
    override val root = tableview<DailyConsumptionData> {
        items = listOf(
                DailyConsumptionData(LocalDate.of(2018,Month.JANUARY, 1), 2000, "Nudeln mit Soße"),
                DailyConsumptionData(LocalDate.of(2018,Month.JANUARY, 2), 1800, "Rehrücken")
        ).observable()

        column(messages["dateheader"], DailyConsumptionData::date)
        column(messages["caloryheader"], DailyConsumptionData::consumption)
        column(messages["maindishheader"], DailyConsumptionData::mainDish)

    }

}
