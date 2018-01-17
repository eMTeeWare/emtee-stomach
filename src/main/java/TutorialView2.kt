import com.sun.org.apache.bcel.internal.Repository.addClass
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.control.TreeItem
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.StageStyle
import javafx.util.Duration
import tornadofx.*
import java.time.LocalDate
import java.time.Period
import kotlin.concurrent.thread

class TutorialView2 : View() {
    override val root = borderpane {
        top = menubar {
            menu(messages["file"]) {
                menu("Connect") {
                    item("Facebook")
                    item("Twitter")
                }
                separator()
                item("Save","Shortcut+S").action{
                    println("Saving!")
                }
                item("Quit")
            }
            menu("Edit") {
                item("Copy")
                item("Paste")
            }
        }

        bottom = label("BOTTOM") {
            useMaxWidth = true
            style {
                backgroundColor += Color.BLUE
            }
        }
        left = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab("Screen 1", VBox()) {
                squeezebox {
                    fold("Customer Editor", expanded = true) {
                        form {
                            fieldset("Customer Details") {
                                field("Name") { textfield() }
                                field("Password") { textfield() }
                            }
                        }
                    }

                    fold(title = messages["animationHeader"], expanded = true) {
                        vbox {
                            val rectangle = rectangle(width = 60.0,height = 40.0) {
                            padding = Insets(20.0)
                            }
                            timeline {
                                keyframe(5.seconds) {
                                    keyvalue(rectangle.rotateProperty(),90.0)
                                    keyvalue(rectangle.arcWidthProperty(),60.0)
                                    keyvalue(rectangle.arcHeightProperty(),60.0)
                                }
                                isAutoReverse = true
                                cycleCount = 3
                            }

                        }
                        fold("Some other editor", expanded = false) {
                            piechart("Desktop/Laptop OS Market Share") {
                                data("Windows", 77.62)
                                data("OS X", 9.52)
                                data("Other", 3.06)
                                data("Linux", 1.55)
                                data("Chrome OS", 0.55)
                            }
                        }

                    }
                }



            }
            tab("Screen 2", HBox()) {
                tableview<MyView2.Person> {
                    items = listOf(
                            MyView2.Person(1, "Samantha Stuart", LocalDate.of(1981, 12, 4)),
                            MyView2.Person(2, "Tom Marks", LocalDate.of(2001, 1, 23)),
                            MyView2.Person(3, "Stuart Gills", LocalDate.of(1989, 5, 23)),
                            MyView2.Person(3, "Nicole Williams", LocalDate.of(1998, 8, 11))
                    ).observable()
                    column("ID", MyView2.Person::id)
                    column("Name", MyView2.Person::name)
                    column("Birthday", MyView2.Person::birthday)
                    column("Age", MyView2.Person::age)

                    contextmenu {
                        item("Send Email").action {
                            selectedItem?.apply { println("Sending Email to $name") }
                        }
                        item("Change Status").action {
                            selectedItem?.apply { println("Changing Status for $name") }
                        }
                    }

                }
            }
        }
        right = form {
            fieldset("Personal Info") {
                field("First Name") {
                    textfield()
                }
                field("Last Name") {
                    textfield()
                }
                field("Birthday") {
                    datepicker()
                }
            }
            fieldset("Contact") {
                field("Phone") {
                    textfield()
                }
                field("Email") {
                    textfield()
                }
            }
            button("Commit") {
                action { println("Wrote to database!")}
            }
        }

        center =
            vbox {
                button("Button 1") {
                    action {
                        replaceWith(TutorialView::class)
                    }
                    vboxConstraints {
                        marginBottom = 20.0
                        vGrow = Priority.ALWAYS
                    }
                }

                button("Button 2") {
                    action {
                        println("Button 2 Pressed")
                    }
                    vgrow = Priority.ALWAYS // what should this do?
                }

                flowpane {
                    for (i in 1..100) {
                        button(i.toString()) {
                            setOnAction { println("You pressed button $i") }
                        }
                    }
                }


            }

        }
    }


class TutorialView : View() {
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
        hbox {
            button("Go to MyView2") {
                action {
                    replaceWith(MyView2::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.RIGHT))
                }
                style {
                    fontWeight = FontWeight.EXTRA_BOLD
                    borderColor += box(
                            top = Color.RED,
                            right = Color.DARKGREEN,
                            left = Color.ORANGE,
                            bottom = Color.PURPLE
                    )
                    rotate = 45.deg
                }

            }
            button("Go to MyView3") {
                action{
                    replaceWith(MyView3::class)
                }
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
        children.style {
            backgroundColor += Color.LIGHTGREEN
            textFill = Color.PINK
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


    class Person(id: Int, name: String, birthday: LocalDate) {
        var id by property(id)
        fun idProperty() = getProperty(Person::id)
        var name by property(name)
        fun nameProperty() = getProperty(Person::name)
        var birthday by property(birthday)
        fun birthdayProperty() = getProperty(Person::birthday)

        val age: Int get() = Period.between(birthday, LocalDate.now()).years
    }


    private val persons = listOf(
            Person(1,"Samantha Stuart", LocalDate.of(1981,12,4)),
            Person(2,"Tom Marks",LocalDate.of(2001,1,23)),
            Person(3,"Stuart Gills",LocalDate.of(1989,5,23)),
            Person(3,"Nicole Williams",LocalDate.of(1998,8,11))
    ).observable()




    override val root = vbox {

        button("Go to MyView1") {
            action {
                replaceWith(TutorialView::class, ViewTransition.Slide(1.5.seconds, ViewTransition.Direction.UP))
            }
        }

        tableview(persons) {
            column("ID",Person::id).makeEditable()
            column("Name", Person::name).makeEditable()
            column("Birthday", Person::birthday).makeEditable()
            // how to make this update when birthday is changed?
            column("Age",Person::age).cellFormat {
                text = it.toString()
                style {
                    if(it < 22) {
                        backgroundColor += c("#8b0000")
                        textFill = Color.WHITE
                    } else {
                        backgroundColor += Color.WHITE
                        textFill = Color.BLACK
                    }
                }
            }
        }

        tableview(regions) {
            column("ID",Region::id)
            column("Name", Region::name)
            column("Country", Region::country)
            rowExpander(expandOnDoubleClick = true) {
                paddingLeft = expanderColumn.width
                tableview(it.branches) {
                    column("ID",Branch::id)
                    column("Facility Code",Branch::facilityCode)
                    column("City",Branch::city)
                    column("State/Province",Branch::stateProvince)
                }
            }
        }

    }
}



class Branch(val id: Int, val facilityCode: String, val city: String, val stateProvince
: String)
class Region(val id: Int, val name: String, val country: String, val branches: ObservableList<Branch>)

val regions = listOf(
        Region(1,"Pacific Northwest", "USA",listOf(
                Branch(1,"D","Seattle","WA"),
                Branch(2,"W","Portland","OR")
        ).observable()),
        Region(2,"Alberta", "Canada",listOf(
                Branch(3,"W","Calgary","AB")
        ).observable()),
        Region(3,"Midwest", "USA", listOf(
                Branch(4,"D","Chicago","IL"),
                Branch(5,"D","Frankfort","KY"),
                Branch(6, "W","Indianapolis", "IN")
        ).observable())
).observable()

class MyView3 : View() {
    data class Person(val name: String, val department: String)
    val persons = listOf(
            Person("Mary Hanes","Marketing"),
            Person("Steve Folley","Customer Service"),
            Person("John Ramsy","IT Help Desk"),
            Person("Erlick Foyes","Customer Service"),
            Person("Erin James","Marketing"),
            Person("Jacob Mays","IT Help Desk"),
            Person("Larry Cable","Customer Service")
    )
    val departments = persons
            .map { it.department }
            .distinct().map { Person(it, "") }

    override val root = vbox {
        this += DangerButton()
        button("To View 1") {
            addClass(MyStyle.tackyButton)
            action {
                replaceWith(TutorialView::class, ViewTransition.Explode(1.5.seconds, point(2,2)))
            }
        }

        val numbers = (1..10).toList()
        datagrid(numbers) {
            cellHeight = 75.0
            cellWidth = 75.0
            multiSelect = true
            cellCache {
                stackpane {
                    circle(radius = 25.0) {
                        fill = Color.FORESTGREEN
                    }
                    label(it.toString())
                }
            }
        }
        treeview<Person> {
            // Create root item
            root = TreeItem(Person("Departments", ""))
            // Make sure the text in each TreeItem is the name of the Person
            cellFormat { text = it.name }
            // Generate items. Children of the root item will contain departments
            populate { parent ->
                if (parent == root) departments else persons.filter { it.department == parent.
                        value.name }
            }
        }

    }


}
class MyStyle: Stylesheet() {

    companion object {
        val redStyle by cssclass()
        val tackyButton by cssclass()

        private val topColor = Color.RED
        private val rightColor = Color.DARKGREEN
        private val leftColor = c("#FFA500")
        private val bottomColor = c("#800080")
    }

    init {
        val redAllTheThings = mixin {
            backgroundInsets += box(5.px)
            borderColor += box(Color.RED)
            textFill = Color.RED
        }

        redStyle {
            +redAllTheThings
        }

        s(textInput, label) {
            +redAllTheThings
            fontWeight = FontWeight.BOLD
        }

        passwordField {
            +redAllTheThings
            backgroundColor += Color.YELLOW
        }

        tackyButton {
            rotate = 10.deg
            borderColor += box(topColor,rightColor,bottomColor,leftColor)
            fontFamily = "Comic Sans MS"
            fontSize = 20.px
        }
        button {
            rotate = 33.deg
            borderColor += box(topColor, topColor, topColor, topColor)
            fontSize = 33.px
            and(hover) {
                backgroundColor += Color.TURQUOISE
            }
        }
    }
}

class DangerButton : Button("Danger!") {
    init {
        addClass(DangerButtonStyles.dangerButton)
    }
    override fun getUserAgentStylesheet() = DangerButtonStyles().base64URL.toExternalForm()
}

class DangerButtonStyles : Stylesheet() {
    companion object {
        val dangerButton by cssclass()
    }

    init {
        dangerButton {
            backgroundInsets += box(0.px)
            fontWeight = FontWeight.BOLD
            fontSize = 20.px
            padding = box(10.px)
        }
    }
}