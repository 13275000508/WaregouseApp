package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.Cargo;
import models.ClientModel;
 
public class ClientController implements Initializable {
	
	static int userid;
	Cargo cargo=new Cargo();
	ClientModel cm;

	/***** TABLEVIEW intel *********************************************************************/
	@FXML
	private TextField txname;
	@FXML
	private TextField txorderNumber;
	@FXML
	private TextField txcolor;
	@FXML
	private TextField txweight;
	@FXML
	private TextField txinventory;
	@FXML
	private Label lbnotice;
	@FXML
	private TableView<Cargo> inventory;
	@FXML
	private TableColumn<Cargo, String> name;
	@FXML
	private TableColumn<Cargo, String> color;
	@FXML
	private TableColumn<Cargo, Double> weight;
	
	public void initialize(URL location, ResourceBundle resources) {
		name.setCellValueFactory(new PropertyValueFactory<Cargo, String>("name"));
		color.setCellValueFactory(new PropertyValueFactory<Cargo, String>("color"));
		weight.setCellValueFactory(new PropertyValueFactory<Cargo, Double>("weight"));

		// auto adjust width of columns depending on their content
		inventory.setColumnResizePolicy((param) -> true);
		Platform.runLater(() -> customResize(inventory));

		inventory.setVisible(false); // set invisible initially
	}

    public void customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
    }
    
	public void searchInventory() throws IOException {
		cargo.setOrderNumber(this.txinventory.getText());

		inventory.getItems().setAll(cm.getInventory(cargo)); // load table data from ClientModel List
		inventory.setVisible(true); // set tableview to visible if not
		
	

	}
	
	public void inbound() throws IOException {
		cargo.setOrderNumber(this.txorderNumber.getText());
		cargo.setName(this.txname.getText());
		cargo.setColor(this.txcolor.getText());
		cargo.setWeight(Double.parseDouble(this.txweight.getText()));
		cargo.setUserid(userid);

		 int addcount = cm.addInbound(cargo); 
		 if(addcount>0) {
			 lbnotice.setText("Cargo Inbound Successfully");
			 lbnotice.setVisible(true); // set tableview to visible if not
		 }
		
		

	}
	public void outbound() throws IOException {
		cargo.setOrderNumber(this.txorderNumber.getText());
		cargo.setName(this.txname.getText());
		cargo.setColor(this.txcolor.getText());
		cargo.setWeight(Double.parseDouble(this.txweight.getText()));
		cargo.setUserid(userid);

		 int outboundcount = cm.outbound(cargo); // load table data from ClientModel List
		 if(outboundcount>0) {
			 lbnotice.setText("Cargo Outbound Successfully");
			 lbnotice.setVisible(true); // set tableview to visible if not
				 }
		
	

	}

	/***** End TABLEVIEW intel *********************************************************************/

	public void logout() {
		// System.exit(0);
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/styles.css").toExternalForm());
			Main.stage.setScene(scene);
			Main.stage.setTitle("Login");
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

	
	public static void setUserid(int user_id) {
		userid = user_id;
		System.out.println("Welcome id " + userid);
	}

	public ClientController() {

		
		  Alert alert = new Alert(AlertType.INFORMATION);
		  alert.setTitle("Operator View");
	
		  alert.setContentText("Welcome !"); alert.showAndWait();
		 
		cm = new ClientModel();

	}

}
