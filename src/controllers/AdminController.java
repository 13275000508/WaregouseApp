package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import Entity.Cargo;
import Entity.User;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.AdminModel;


public class AdminController implements Initializable{
	@FXML
	private TextField txUser;
	@FXML
	private TextField txPassword;
	@FXML
	private TextField txAdmin;
	@FXML
	private TextField txname;
	@FXML
	private TextField txorderNumber;
	@FXML
	private TextField txcolor;
	@FXML
	private TextField txweight;
	@FXML
	private TextField txname1;
	@FXML
	private TextField txorderNumber1;
	@FXML
	private TextField txcolor1;
	@FXML
	private TextField txweight1;
	@FXML
	private TextField optname;
	@FXML
	private Label updatenotify;
	@FXML
	private Label deletenotify;
	@FXML
	private Label usernotify;
	@FXML
	private Pane pane1;
	@FXML
	private Pane pane2;
	@FXML
	private Pane pane3;
	@FXML
	private Pane pane4;
	@FXML
	private TableView<Cargo> inventory1;
	@FXML
	private TableColumn<Cargo, String> orderNumber;
	@FXML
	private TableColumn<Cargo, String> name1;
	@FXML
	private TableColumn<Cargo, String> color1;
	@FXML
	private TableColumn<Cargo, Double> weight1;
	@FXML
	private TableColumn<Cargo, Integer> optid1;
	// Declare DB objects
		Cargo cargo=new Cargo();
		AdminModel am;
        User user=new User();
	public void initialize(URL location, ResourceBundle resources) {
		orderNumber.setCellValueFactory(new PropertyValueFactory<Cargo, String>("orderNumber"));
		name1.setCellValueFactory(new PropertyValueFactory<Cargo, String>("name"));
		color1.setCellValueFactory(new PropertyValueFactory<Cargo, String>("color"));
		weight1.setCellValueFactory(new PropertyValueFactory<Cargo, Double>("weight"));
		optid1.setCellValueFactory(new PropertyValueFactory<Cargo, Integer>("userid"));

		// auto adjust width of columns depending on their content
		inventory1.setColumnResizePolicy((param) -> true);
		Platform.runLater(() -> customResize(inventory1));

		inventory1.setVisible(false); // set invisible initially
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
    
	public void searchInventorybyopt() throws IOException {
		cargo.setName(this.optname.getText());
		List<Cargo> cargoes = am.getInventorybyopt(cargo);
		inventory1.getItems().setAll(cargoes); // load table data from ClientModel List
		inventory1.setVisible(true); // set tableview to visible if not
		

	}

	
	public AdminController() {

		 
		am = new AdminModel();
	}

	public void viewAccounts() {
		pane4.setVisible(false);
		pane3.setVisible(false);
		pane2.setVisible(false);
		pane1.setVisible(true);
	}

	public void updateRec() {

		pane4.setVisible(false);
		pane3.setVisible(false);
		pane2.setVisible(true);
		pane1.setVisible(false);

	}

	public void deleteRec() {
		pane4.setVisible(false);
		pane3.setVisible(true);
		pane2.setVisible(false);
		pane1.setVisible(false);
	}

	public void addBankRec() {

		pane4.setVisible(true);
		pane3.setVisible(false);
		pane2.setVisible(false);
		pane1.setVisible(false);

	}

	public void addUser() {
		String admin=this.txAdmin.getText();
		if("Y".equals(admin))
			user.setAdmin("1");
		else {
			user.setAdmin("0");
		}
		
		user.setPassword(this.txPassword.getText());
		user.setName(this.txUser.getText());
		int addcount = am.addUser(user); 
		 if(addcount>0) {
			 usernotify.setText("User Added");
			 usernotify.setVisible(true);
			 
			 txUser.setText("");
			 txPassword.setText("");
			 txAdmin.setText("");
			
		 }

		
	}
	public void submitUpdate() throws IOException {
		cargo.setOrderNumber(this.txorderNumber.getText());
		cargo.setName(this.txname.getText());
		cargo.setColor(this.txcolor.getText());
		cargo.setWeight(Double.parseDouble(this.txweight.getText()));
		int updatecount = am.updateCargo(cargo); 
		 if(updatecount>0) {
			 updatenotify.setText("Update Success");
			 updatenotify.setVisible(true);
			 //clear text
			 txorderNumber.setText("");
			 txname.setText("");
			 txcolor.setText("");
			 txweight.setText("");
			 	
		 }

	}

	public void submitDelete() throws IOException {
		cargo.setOrderNumber(this.txorderNumber1.getText());
		cargo.setName(this.txname1.getText());
		cargo.setColor(this.txcolor1.getText());
		cargo.setWeight(Double.parseDouble(this.txweight1.getText()));
		
		int deletecount = am.deleteCargo(cargo); 
		 if(deletecount>0) {
			 deletenotify.setText("Delete Success");
			 deletenotify.setVisible(true);
			 txorderNumber1.setText("");
			 txname1.setText("");
			 txcolor1.setText("");
			 txweight1.setText("");
			 
			
		 }

	}
	
	
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


}
