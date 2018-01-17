import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by MThomas on 17.01.2018.
 */
class TableEditView : View("My View") {
    val myView2: MyView2 by inject()

    val controller: CustomerController by inject()
    var tableViewEditModel: TableViewEditModel<Customer> by singleAssign()
    override val root = borderpane {
        top = buttonbar {
            button("COMMIT").setOnAction {
                tableViewEditModel.commit()
                log.info {"Button pressed"}
            }
            button("ROLLBACK").setOnAction {
                tableViewEditModel.rollback()
            }
        }
        center = tableview<Customer> {
            items = controller.customers
            isEditable = true
            column("ID",Customer::idProperty)
            column("FIRST NAME", Customer::firstNameProperty).makeEditable().remainingWidth()
            column("LAST NAME", Customer::lastNameProperty).makeEditable().contentWidth(padding = 50.0)
            enableCellEditing() //enables easier cell navigation/editing
            enableDirtyTracking() //flags cells that are dirty
            tableViewEditModel = editModel

            columnResizePolicy = SmartResize.POLICY
        }
    }
}
class CustomerController : Controller() {
    val customers = listOf(
            Customer(1, "Marley", "John"),
            Customer(2, "Schmidt", "Ally"),
            Customer(3, "Johnson", "Eric")
    ).observable()
}
class Customer(id: Int, lastName: String, firstName: String) {
    val lastNameProperty = SimpleStringProperty(this, "lastName", lastName)
    var lastName by lastNameProperty
    val firstNameProperty = SimpleStringProperty(this, "firstName", firstName)
    var firstName by firstNameProperty
    val idProperty = SimpleIntegerProperty(this, "id", id)
    var id by idProperty
}
