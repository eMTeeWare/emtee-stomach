import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*
import java.time.LocalDate
import java.time.Period
import tornadofx.getValue
import tornadofx.setValue

/**
 * Created by MThomas on 17.01.2018.
 */

class TutorialListView : View("My View") {
    val persons = listOf(
            BirthdayPerson("John Marlow", LocalDate.of(1982,11,2)),
            BirthdayPerson("Samantha James", LocalDate.of(1973,2,4))
    ).observable()
    override val root = listview(persons) {
        cellFragment(PersonCellFragment::class)
    }
}
class PersonCellFragment : ListCellFragment<BirthdayPerson>() {

    val person = BirthdayPersonModel().bindTo(this)
    override val root = form {
        fieldset {
            field("Name") {
                label(person.name)
            }
            field("Birthday") {
                label(person.birthday)
            }
            label(stringBinding(person.age) { "$value years old" }) {
                alignment = Pos.CENTER_RIGHT
                style {
                    fontSize = 22.px
                    fontWeight = FontWeight.BOLD
                }
            }
        }
    }
}

class BirthdayPerson(name : String, birthday : LocalDate) {
    val birthdayProperty = SimpleObjectProperty<LocalDate>()
    var birthday by birthdayProperty

    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val age: Int get() = Period.between(birthday, LocalDate.now()).years
}

class BirthdayPersonModel : ItemViewModel<BirthdayPerson>() {
    val name = bind(BirthdayPerson::nameProperty)
    val birthday = bind(BirthdayPerson::birthdayProperty)
    val age = bind(BirthdayPerson::age)

    override fun onCommit(commits: List<Commit>) {
        commits.findChanged(name)?.let {
            println("name changed from ${it.second} to ${it.first}")
        }
        commits.findChanged(birthday)?.let {
            println("title changed from ${it.second} to ${it.first}")
        }
    }

    private fun <T> List<Commit>.findChanged(ref: Property<T>): Pair<T, T>? {
        val commit = find { it.property == ref && it.changed}
        return commit?.let { (it.newValue as T) to (it.oldValue as T) }
    }

}
