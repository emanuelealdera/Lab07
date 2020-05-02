package it.polito.tdp.poweroutages;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FXMLController {
	
	
	private Model model;
	
	public void setModel (Model model) {
		this.model = model;
		for (Nerc n : model.getNercList()) {
			boxNerc.getItems().add(n.getValue());
		}
	}

    @FXML
    private ImageView img;

    @FXML
    private ComboBox<String> boxNerc;

    @FXML
    private TextField txtMaxYears;

    @FXML
    private TextField txtMaxHours;

    @FXML
    private Button btnWCS;

    @FXML
    private TextArea txtResult;

    @FXML
    void worstCaseScenario(ActionEvent event) {
    	
    	txtResult.clear();
    	int maxAnni = 0;
    	int maxOre = 0;
    	String nerc = boxNerc.getValue();
    	try {
    		maxAnni = Integer.parseInt(txtMaxYears.getText());
    		maxOre = Integer.parseInt(txtMaxHours.getText());
    	} catch (NumberFormatException e) {
    		txtResult.setText("Inserire valori numerici");
    		e.printStackTrace();
    	}
    	
    	if (maxAnni!=0 && maxOre != 0) {
    		txtResult.setText(model.worstCaseScenario(nerc, maxAnni, maxOre));
    	}
    }

}
