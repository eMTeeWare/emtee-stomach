import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.StageStyle
import tornadofx.*
import kotlin.concurrent.thread

class MainView: View() {
    private val controller: MyController by inject()

    var firstNameField: TextField by singleAssign()
    var lastNameField: TextField by singleAssign()

    override val root = vbox {
        button("Press Me") {
            textFill = Color.RED
            action {
                find(MyFragment::class).openModal(stageStyle = StageStyle.UTILITY)
            }
        }

        button("Go to MyView2") {
            action {
                replaceWith(MyView2::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.RIGHT))
            }
        }

        hbox {
            label("First Name")
            firstNameField = textfield()
        }
        hbox {
            label("Last Name")
            lastNameField = textfield()
        }

        label("Input")

        val inputField = textfield("input")

        button("Commit") {
            useMaxWidth = true
            action {
                controller.writeToDb(inputField.text)
                controller.writeToDb("${firstNameField.text} ${lastNameField.text}")
                inputField.clear()
            }
        }

        label("My Items")
        listview(controller.values)

        textflow {
            text("Tornado") {
                fill = Color.PURPLE
                font = Font(20.0)
            }
            text("FX") {
                fill = Color.ORANGE
                font = Font(28.0)
                tooltip("Just for fun!")
            }
        }

        progressbar {
            thread {
                for (i in 1..100) {
                    Platform.runLater { progress = i.toDouble() / 100.0 }
                    Thread.sleep(100)
                }
            }
        }
    }

}



class MyController: Controller() {
    fun writeToDb(inputValue: String) {
        values.add(inputValue)
        println("Writing $inputValue to database!")
    }
    val values = FXCollections.observableArrayList("Alpha","Beta","Gamma","Delta")
}

class MyFragment: Fragment() {
    override val root = label("This is a popup")
}

class MyView2: View() {
    override val root = vbox {
        button("Go to MyView1") {
            action {
                replaceWith(MainView::class, ViewTransition.Slide(1.5.seconds, ViewTransition.Direction.UP))
            }
        }
    }
}