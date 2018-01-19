package tornadofxtutorial

import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.BorderPane
import tornadofx.*

class TutorialModel : View("tornadofxtutorial.Person Editor") {
    override val root = BorderPane()
    init {
        with(root) {
            center {
                root += PersonEditor()
            }
            left {
                root += PersonList()
            }
        }
    }
}

class Person(name: String? = null, title: String? = null) {
    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty
    val titleProperty = SimpleStringProperty(this, "title", title)
    var title by titleProperty
}

class PersonList : View("tornadofxtutorial.Person List") {
    val persons = listOf(Person("John", "Manager"), Person("Jay", "Worker bee")).observable()
    val model : PersonModel by inject()

    override val root = tableview(persons) {
        column("Name", Person::nameProperty)
        column("Title", Person::titleProperty)
// Update the person inside the view model on selection change
        bindSelected(model)

    }
}

class PersonEditor : View("tornadofxtutorial.Person Editor") {
    val model : PersonModel by inject()

    override val root = form {
        fieldset("Edit person") {
            field("Name") {
                textfield(model.name).required()
            }
            field("Title") {
                textfield(model.title) {
                    validator {
                        if (it.isNullOrBlank()) error("The name field is required") else null
                    }
                }
            }
            buttonbar {
                button("Save") {
                    enableWhen(model.dirty and model.valid)
                    action {
                        save()
                    }
                }
                button("Reset").action {
                    model.rollback()
                }.also { enableWhen(model.dirty) }
            }
            model.validate(decorateErrors = false)
        }

    }
    private fun save() {
// Flush changes from the text fields into the model
        model.commit()
// The edited person is contained in the model
        val person = model.item
// A real application would persist the person here
        println("Saving ${person.name} / ${person.title}")
    }
}
class PersonModel : ItemViewModel<Person>() {
    val name = bind(Person::nameProperty)
    val title = bind(Person::titleProperty)

    override fun onCommit(commits: List<Commit>) {
        commits.findChanged(name)?.let {
            println("name changed from ${it.second} to ${it.first}")
        }
        commits.findChanged(title)?.let {
            println("title changed from ${it.second} to ${it.first}")
        }
    }

    private fun <T> List<Commit>.findChanged(ref: Property<T>): Pair<T, T>? {
        val commit = find { it.property == ref && it.changed}
        return commit?.let { (it.newValue as T) to (it.oldValue as T) }
    }

}
