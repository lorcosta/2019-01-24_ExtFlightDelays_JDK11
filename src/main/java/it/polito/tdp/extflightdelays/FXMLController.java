package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Adiacenza;
import it.polito.tdp.extflightdelays.model.Model;
import it.polito.tdp.extflightdelays.model.StatoTuristi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<String> cmbBoxStati;

    @FXML
    private Button btnVisualizzaVelivoli;

    @FXML
    private TextField txtT;

    @FXML
    private TextField txtG;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	model.creaGrafo();
    	Integer vertici=model.getNumVertici(), archi=model.getNumArchi();
    	if(vertici==0 || archi.equals(0)) {
    		this.txtResult.appendText("ATTENZIONE! Qualcosa e' andato storto nella creazione del grafo.\n");
    		return;
    	}
    	this.txtResult.appendText("GRAFO CREATO!\n #VERTICI: "+vertici+" e #ARCHI: "+archi+"\n");
    	this.cmbBoxStati.getItems().addAll(model.getStati());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	String TString=this.txtT.getText(),GString=this.txtG.getText();
    	Integer turisti=null,giorni=null;
    	try {
    		turisti=Integer.parseInt(TString);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore inserito nel campo 'T' non e' un numero intero");
    		throw new NumberFormatException();
    	}
    	try {
    		giorni=Integer.parseInt(GString);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! Il valore inserito nel campo 'T' non e' un numero intero");
    		throw new NumberFormatException();
    	}
    	String statoSelezionato=this.cmbBoxStati.getValue();
    	if(statoSelezionato==null) {
    		this.txtResult.appendText("ATTENZIONE! E' necessario selezionare uno stato per simulare.\n");
    		return;
    	}
    	List<StatoTuristi> statoTuristi=model.simula(turisti,giorni,statoSelezionato);
    	for(StatoTuristi s:statoTuristi) {
    		this.txtResult.appendText(s.getStato()+" turisti:"+s.getTuristi()+"\n");
    	}
    }

    @FXML
    void doVisualizzaVelivoli(ActionEvent event) {
    	this.txtResult.clear();
    	String stato=this.cmbBoxStati.getValue();
    	if(stato==null) {
    		this.txtResult.appendText("ATTENZIONE! Valore selezionato errato!\n");
    		return;
    	}
    	List<Adiacenza> velivoli=model.visualizzaVelivoli(stato);
    	if(velivoli.size()<=0) {
    		this.txtResult.appendText("Errore nella ricerca dei velivoli.\n");
    		return;
    	}
    	this.txtResult.appendText("Gli stati collegati (e il relativo numero di velivoli sono:\n"); 
    	for(Adiacenza a: velivoli) {
    		this.txtResult.appendText(a.getA2()+"-->"+a.getPeso()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxStati != null : "fx:id=\"cmbBoxStati\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnVisualizzaVelivoli != null : "fx:id=\"btnVisualizzaVelivoli\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtT != null : "fx:id=\"txtT\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtG != null : "fx:id=\"txtG\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
