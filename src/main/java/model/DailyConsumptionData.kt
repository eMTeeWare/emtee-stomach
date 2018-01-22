package model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.time.LocalDate
import tornadofx.getValue
import tornadofx.setValue

class DailyConsumptionData(date: LocalDate, consumedCalories: Int, mainDish: String) {
    val mainDishProperty = SimpleStringProperty(this,"mainDish", mainDish)
    var mainDish by mainDishProperty
    val consumptionProperty = SimpleIntegerProperty(this, "consumption", consumedCalories)
    var consumption by consumptionProperty
    val dateProperty = SimpleObjectProperty<LocalDate>(this, "date", date)
    var date by dateProperty
}

