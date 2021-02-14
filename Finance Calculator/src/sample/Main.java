package sample;

//______Project Finance Calculator


//______Scenes for Finance Calculator

import com.mongodb.*;
import com.sun.org.apache.xpath.internal.objects.XObject;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static sample.Controller.*;


public class Main extends Application {
    Stage stage;
    Scene scene1, scene2 ,scene3 ,scene4 ,scene5;
    //Creating array to collect keyboard input and selected textfiled
    TextField[] txtFldArray = new TextField[1];

    //Creating mongoClient to path the database
    MongoClient client = new MongoClient("localhost",27017);

    //Creating the database
    DB database = client.getDB("database");

    //Creating database to add data from non PMT finance calculator
    DBCollection finance1 = database.getCollection("finance1");
    //Creating database to add data from PMT finance calculator
    DBCollection finance2 = database.getCollection("finance2");
    //Creating database to add data from non mortgage calculator
    DBCollection mortgage = database.getCollection("mortgage");
    //Creating database to add data from loan calculator
    DBCollection loan = database.getCollection("loan");
    

    DBObject recode =  null;
    DBObject recode2 = null;
    DBObject recode3 = null;
    DBObject recode4 = null;


    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        //Adding Finanace 1 to borderpane
        BorderPane layout1 = new BorderPane();
        layout1.setTop(addNavigationBar());
        layout1.setCenter(addFinancialCal1());
        scene1 = new Scene(layout1,575,672);
        layout1.setStyle("-fx-background-color:#ffffb3;-fx-font-family:Arial");

        //Adding Finanace 2 to borderpane
        BorderPane layout2 = new BorderPane();
        layout2.setTop(addNavigationBar());
        layout2.setCenter(addFinancialCal2());
        scene2 = new Scene(layout2,575,672);
        layout2.setStyle("-fx-background-color:#ffffb3;-fx-font-family:Arial");

        //Adding Mortgage to borderpane
        BorderPane layout3 = new BorderPane();
        layout3.setTop(addNavigationBar());
        layout3.setCenter(addMortgageCal());
        scene3 = new Scene(layout3,575,672);
        layout3.setStyle("-fx-background-color:#ffffb3;-fx-font-family:Arial");

        //Adding Loan to borderpane
        BorderPane layout4 = new BorderPane();
        layout4.setTop(addNavigationBar());
        layout4.setCenter(addLoanCal());
        scene4 = new Scene(layout4,575,672);
        layout4.setStyle("-fx-background-color:#ffffb3;-fx-font-family:Arial");

        //Adding Help to borderpane
        BorderPane layout5 = new BorderPane();
        layout5.setTop(addNavigationBar());
        layout5.setCenter(help());
        scene5 = new Scene(layout5,575,672);
        layout5.setStyle("-fx-background-color:#ffffb3;-fx-font-family:Arial");

        stage.setScene(scene1);
        stage.setTitle("Calculator");
        stage.show();

    }


    //Adding navigation bar
    private HBox addNavigationBar() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 15, 15, 15));
        hbox.setSpacing(1);   // Gap between nodes
        hbox.setStyle("-fx-background-color: #01233f;");

        Image imgLogo = new Image("sample/logo.png");   //adding gui logo
        ImageView logo = new ImageView(imgLogo);
        logo.setFitWidth(170);
        logo.setFitHeight(37);


        //Adding buttons to navigation bar
        DropShadow shadow = new DropShadow();
        Button btnFC1 = new Button("Finance 1");
        btnFC1.setPrefSize(80, 22);
        btnFC1.setStyle("-fx-background-color: #ebf0fa");

        btnFC1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnFC1.setEffect(shadow); });
        btnFC1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnFC1.setEffect(null);});

        Button btnFC2 = new Button("Finance 2");
        btnFC2.setPrefSize(80, 22);
        btnFC2.setStyle("-fx-background-color: #ebf0fa");

        btnFC2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnFC2.setEffect(shadow); });
        btnFC2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnFC2.setEffect(null);});

        Button btnMC = new Button("Mortgage");
        btnMC.setPrefSize(80, 22);
        btnMC.setStyle("-fx-background-color: #ebf0fa");
        btnMC.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnMC.setEffect(shadow); });
        btnMC.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnMC.setEffect(null);});

        Button btnLC = new Button("Loan");
        btnLC.setPrefSize(80, 22);
        btnLC.setStyle("-fx-background-color: #ebf0fa");
        btnLC.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnLC.setEffect(shadow); });
        btnLC.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnLC.setEffect(null);});

        Button btnHelp = new Button("Help");
        btnHelp.setPrefSize(50, 22);
        btnHelp.setStyle("-fx-background-color: #ebf0fa");
        btnHelp.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnHelp.setEffect(shadow); });
        btnHelp.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnHelp.setEffect(null);});

        //Calling for scenes by navigation buttons
        btnFC1.setOnAction(e -> stage.setScene(scene1));
        btnFC2.setOnAction(e -> stage.setScene(scene2));
        btnMC.setOnAction(e -> stage.setScene(scene3));
        btnLC.setOnAction(e -> stage.setScene(scene4));
        btnHelp.setOnAction(e -> stage.setScene(scene5));

        hbox.getChildren().addAll(logo,btnFC1,btnFC2,btnMC,btnLC,btnHelp);
        return hbox;
    }


    //Adding pane to Finance 1 calculator
    private GridPane addFinancialCal1() {
        GridPane grid1 = new GridPane();
        grid1.setHgap(10);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(10, 20, 60, 20));

        Text heading = new Text("Finance Calculator");
        heading.setFont(Font.font("null", FontWeight.BOLD, 20));
        heading.setFill(Color.web("#01233f"));

        GridPane grid2 =addKeyboard();

        Text txtFV = new Text("Future Value");
        Text txtSP = new Text("Start Principal");
        Text txtIR = new Text("Interest Rate");
        Text txtNP = new Text("Number of periods    ");

        //______Calling for array to add data to textfield from keyboard
        TextField txtFldFV = new TextField();
        txtFldFV.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldFV;});

        TextField txtFldSP = new TextField();
        txtFldSP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldSP;});

        TextField txtFldIR = new TextField();
        txtFldIR.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldIR;});

        TextField txtFldNP = new TextField();
        txtFldNP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldNP;});



        Button btnCal = new Button("Calculate");
        btnCal.setPrefSize(150, 20);
        btnCal.setStyle("-fx-background-color:#e6e6e6");

        DropShadow shadow = new DropShadow();
        btnCal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnCal.setEffect(shadow); });
        btnCal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnCal.setEffect(null);});


        Text txtHeading2 = new Text("Results");
        txtHeading2.setFont(Font.font("null", FontWeight.BOLD, 15));
        Text txtResults1 = new Text();
        Text txtResults2 = new Text();
        Text txtResults3 = new Text();
        Text txtResults4 = new Text();
        txtResults1.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults2.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults3.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults4.setFill(Color.web("rgb(0, 61, 89)"));


        //____search for last data entries
        DBCursor select = finance1.find();
        while (select.hasNext()){
            recode = select.next();
        }

        try{
            if(txtFldFV.getText().isEmpty()){
                txtFldSP.setText(recode.get("Start Principal").toString());
                txtFldIR.setText(recode.get("Interest Rate").toString());
                txtFldNP.setText(recode.get("Number of Payments").toString());

            }


        }catch(NullPointerException e){

        }

        try{
            if (txtFldSP.getText().isEmpty()) {
                txtFldFV.setText(recode.get("Future value").toString());
                txtFldIR.setText(recode.get("Interest Rate").toString());
                txtFldNP.setText(recode.get("Number of Payments").toString());
            }
        }catch(NullPointerException e)
            {
        }

        try{
            if (txtFldIR.getText().isEmpty()) {
                txtFldFV.setText(recode.get("Future value").toString());
                txtFldSP.setText(recode.get("Start Principal").toString());
                txtFldNP.setText(recode.get("Number of Payments").toString());
            }
        }catch(NullPointerException e)
        {
        }

        try{
            if (txtFldNP.getText().isEmpty()) {
                txtFldFV.setText(recode.get("Future value").toString());
                txtFldIR.setText(recode.get("Interest Rate").toString());
                txtFldSP.setText(recode.get("Start Principal").toString());
            }
        }catch(NullPointerException e)
        {
        }

        //Calling each calculation according to the empty textfield
        btnCal.setOnAction((ActionEvent e)-> {
            if(txtFldFV.getText().isEmpty()) {
                try {
                    BasicDBObjectBuilder finance1Recodes = new BasicDBObjectBuilder().start();
                    double SP=Double.parseDouble(txtFldSP.getText());
                    double IR=Double.parseDouble(txtFldIR.getText());
                    double NP=Double.parseDouble(txtFldNP.getText());

                    txtResults1.setText("Future Value: "+finanFV(SP,IR,NP));
                    txtFldFV.setText(finanFV(SP,IR,NP)+"");
                    txtResults2.setText("Total Principal: "+SP);
                    double totInterest = finanFV(SP,IR,NP)-SP;   //calculating total interest value
                    txtResults3.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);
                    GridPane grid3 =addChart(totInterest,SP);
                    grid1.getChildren().indexOf(grid3);
                    grid1.add(grid3,2,8,8,10);

                    finance1Recodes.append("Start Principal",txtFldSP.getText());
                    finance1Recodes.append("Interest Rate",txtFldIR.getText());
                    finance1Recodes.append("Number of Payments",txtFldNP.getText());
                    WriteResult data = finance1.insert(finance1Recodes.get());


                }catch (NumberFormatException e2){
                    checkingInputs();

                }


            }
            else if(txtFldSP.getText().isEmpty()){
                try {
                    BasicDBObjectBuilder finance1Recodes = new BasicDBObjectBuilder().start();
                    double FV=Double.parseDouble(txtFldFV.getText());
                    double IR=Double.parseDouble(txtFldIR.getText());
                    double NP=Double.parseDouble(txtFldNP.getText());
                    txtResults1.setText("Start Principal: "+finanSP(FV,IR,NP));
                    txtFldSP.setText(finanSP(FV,IR,NP)+"");
                    double totInterest = FV-finanSP(FV,IR,NP);   //calculating total interest value
                    txtResults2.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);
                    GridPane grid3 =addChart(totInterest,finanSP(FV,IR,NP));
                    grid1.add(grid3,2,8,8,10);

                    finance1Recodes.append("Future value",txtFldFV.getText());
                    finance1Recodes.append("Interest Rate",txtFldIR.getText());
                    finance1Recodes.append("Number of Payments",txtFldNP.getText());
                    WriteResult data = finance1.insert(finance1Recodes.get());

                }catch (NumberFormatException e2){
                    checkingInputs();

                }

            }
            else if(txtFldIR.getText().isEmpty()){
                try {
                    BasicDBObjectBuilder finance1Recodes = new BasicDBObjectBuilder().start();
                    double FV=Double.parseDouble(txtFldFV.getText());
                    double SP=Double.parseDouble(txtFldSP.getText());
                    double NP=Double.parseDouble(txtFldNP.getText());

                    txtResults1.setText("Interest Rate: "+finanIR(FV,SP,NP));
                    txtFldIR.setText(finanIR(FV,SP,NP)+"");
                    txtResults2.setText("Total Principal: "+SP);
                    double totInterest = FV-SP;   //calculating total interest value
                    txtResults3.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);
                    GridPane grid3 =addChart(totInterest,SP);
                    grid1.add(grid3,2,8,8,10);

                    finance1Recodes.append("Future value",txtFldFV.getText());
                    finance1Recodes.append("Start Principal",txtFldSP.getText());
                    finance1Recodes.append("Number of Payments",txtFldNP.getText());

                    WriteResult data = finance1.insert(finance1Recodes.get());


                }catch (NumberFormatException e2){
                    checkingInputs();
                }

            }
            else if(txtFldNP.getText().isEmpty()){
                try {
                    BasicDBObjectBuilder finance1Recodes = new BasicDBObjectBuilder().start();
                    double FV=Double.parseDouble(txtFldFV.getText());
                    double SP=Double.parseDouble(txtFldSP.getText());
                    double IR=Double.parseDouble(txtFldIR.getText());

                    txtResults1.setText("Number of Payments: "+finanNP(FV,SP,IR));
                    txtFldNP.setText(finanNP(FV,SP,IR)+"");
                    txtResults2.setText("Total Principal: "+SP);
                    double totInterest = FV-SP;   //calculating total interest value
                    txtResults3.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                    GridPane grid3 =addChart(totInterest,SP);
                    grid1.add(grid3,2,8,8,10);

                    finance1Recodes.append("Future value",txtFldFV.getText());
                    finance1Recodes.append("Start Principal",txtFldSP.getText());
                    finance1Recodes.append("Interest Rate",txtFldIR.getText());
                    WriteResult data = finance1.insert(finance1Recodes.get());

                }catch (NumberFormatException e2){
                    checkingInputs();

                }


            }
            //_____Adding clear button
            Button btnClr = new Button("Clear");
            btnClr.setPrefSize(50, 20);
            btnClr.setStyle("-fx-background-color:#e6e6e6");
            btnClr.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e3)->{
                btnClr.setEffect(shadow); });
            btnClr.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e3)-> {
                btnClr.setEffect(null);});
            grid1.add(btnClr,0,15,2,1);
            btnClr.setOnAction((ActionEvent e2)->{
                txtFldFV.clear();
                txtFldSP.clear();
                txtFldNP.clear();
                txtFldIR.clear();
                txtResults1.setText(null);
                txtResults2.setText(null);
                txtResults3.setText(null);
                txtResults4.setText(null);
                grid1.getChildren().remove(16);
                grid1.getChildren().remove(btnClr);


            });

        });


        grid1.add(heading,0,1,2,1);

        grid1.add(txtFV, 0, 3);
        grid1.add(txtSP, 0, 4);
        grid1.add(txtIR, 0, 5);
        grid1.add(txtNP, 0, 6);

        grid1.add(txtFldFV,1,3);
        grid1.add(txtFldSP,1,4);
        grid1.add(txtFldIR,1,5);
        grid1.add(txtFldNP,1,6);

        grid1.add(btnCal,0,8,2,1);
        grid1.add(txtHeading2,0,10,2,1);
        grid1.add(txtResults1,0,11,4,1);
        grid1.add(txtResults2,0,12,4,1);
        grid1.add(txtResults3,0,13,4,1);
        grid1.add(txtResults4,0,14,4,1);

        grid1.add(grid2,2,3,8,8);



        return grid1;

    }

    //Adding Keyboard
    private GridPane addKeyboard() {
        GridPane grid2 = new GridPane();
        grid2.setHgap(1);
        grid2.setVgap(1);
        grid2.setPadding(new Insets(0, 20, 0, 70));

        //___________Creating all buttons in keyboard, adding css to buttons, and cdding button to array according to the user selection
        Button btn1 = new Button("1");
        btn1.setPrefSize(40, 35);
        btn1.setStyle("-fx-background-color:#e6e6e6");;
        btn1.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"1");});
        Button btn2 = new Button("2");
        btn2.setPrefSize(40, 35);
        btn2.setStyle("-fx-background-color:#e6e6e6");
        btn2.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"2");});
        Button btn3 = new Button("3");
        btn3.setPrefSize(40, 35);
        btn3.setStyle("-fx-background-color:#e6e6e6");
        btn3.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"3");});
        Button btn4 = new Button("4");
        btn4.setPrefSize(40, 35);
        btn4.setStyle("-fx-background-color:#e6e6e6");
        btn4.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"4");});
        Button btn5 = new Button("5");
        btn5.setPrefSize(40, 35);
        btn5.setStyle("-fx-background-color:#e6e6e6");
        btn5.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"5");});
        Button btn6 = new Button("6");
        btn6.setPrefSize(40, 35);
        btn6.setStyle("-fx-background-color:#e6e6e6");
        btn6.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"6");});
        Button btn7 = new Button("7");
        btn7.setPrefSize(40, 35);
        btn7.setStyle("-fx-background-color:#e6e6e6");
        btn7.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"7");});
        Button btn8 = new Button("8");
        btn8.setPrefSize(40, 35);
        btn8.setStyle("-fx-background-color:#e6e6e6");
        btn8.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"8");});
        Button btn9 = new Button("9");
        btn9.setPrefSize(40, 35);
        btn9.setStyle("-fx-background-color:#e6e6e6");
        btn9.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"9");});
        Button btn0 = new Button("0");
        btn0.setPrefSize(40, 35);
        btn0.setStyle("-fx-background-color:#e6e6e6");
        btn0.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText()+"0");});
        Button btnDecimal = new Button(".");
        btnDecimal.setPrefSize(40, 35);
        btnDecimal.setStyle("-fx-background-color:#e6e6e6");
        btnDecimal.setOnAction(event -> {
            boolean noDecimalPoint = true;
            String Checktext = txtFldArray[0].getText();
            for (int i = 0; i < Checktext.length(); i++) {
                if (Checktext.charAt(i) == '.') {
                    noDecimalPoint = false;
                }
            }
            if (noDecimalPoint) {
                txtFldArray[0].setText(txtFldArray[0].getText() + ".");
            }});


        Button btnClr = new Button("c");
        btnClr.setPrefSize(40, 35);
        btnClr.setStyle("-fx-background-color:#e6e6e6");
        btnClr.setOnAction(event -> {txtFldArray[0].setText(txtFldArray[0].getText(0,txtFldArray[0].getLength()-1));});

        DropShadow shadow = new DropShadow();

        btn1.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn1.setEffect(shadow); });
        btn1.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn1.setEffect(null);});
        btn2.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn2.setEffect(shadow); });
        btn2.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn2.setEffect(null);});
        btn3.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn3.setEffect(shadow); });
        btn3.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn3.setEffect(null);});
        btn4.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn4.setEffect(shadow); });
        btn4.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn4.setEffect(null);});
        btn5.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn5.setEffect(shadow); });
        btn5.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn5.setEffect(null);});
        btn6.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn6.setEffect(shadow); });
        btn6.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn6.setEffect(null);});
        btn7.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn7.setEffect(shadow); });
        btn7.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn7.setEffect(null);});
        btn8.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn8.setEffect(shadow); });
        btn8.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn8.setEffect(null);});
        btn9.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn9.setEffect(shadow); });
        btn9.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn9.setEffect(null);});
        btn0.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btn0.setEffect(shadow); });
        btn0.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btn0.setEffect(null);});
        btnClr.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnClr.setEffect(shadow); });
        btnClr.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnClr.setEffect(null);});
        btnDecimal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnDecimal.setEffect(shadow); });
        btnDecimal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnDecimal.setEffect(null);});



        grid2.add(btn1, 1, 3);
        grid2.add(btn2, 2, 3);
        grid2.add(btn3, 3, 3);
        grid2.add(btn4, 1, 4);
        grid2.add(btn5, 2, 4);
        grid2.add(btn6, 3, 4);
        grid2.add(btn7, 1, 5);
        grid2.add(btn8, 2, 5);
        grid2.add(btn9, 3, 5);
        grid2.add(btn0, 4, 3);

        grid2.add(btnDecimal, 4, 4);
        grid2.add(btnClr, 4, 5);

        return grid2;

    }

    //Adding chart
    private GridPane addChart(double IR , double pricipalAmount) {
        GridPane grid3 = new GridPane();
        //giving values to pie chart
       ObservableList<PieChart.Data> chart2 = FXCollections.observableArrayList(
                new PieChart.Data("Interest Rate",IR),
                new PieChart.Data("Principal Amount",pricipalAmount));

        PieChart balanceChart = new PieChart(chart2);
        balanceChart.setTitle("Breakdown");
        balanceChart.setMaxSize(300,300);
        grid3.add(balanceChart, 1, 1);


        return grid3;

    }


    //Adding pane to Financial Calculator 2
    private GridPane addFinancialCal2() {
        GridPane grid4 = new GridPane();
        grid4.setHgap(10);
        grid4.setVgap(10);
        grid4.setPadding(new Insets(10, 20, 60, 20));

        Text heading = new Text("Finance Calculator");
        heading.setFont(Font.font("null", FontWeight.BOLD, 20));
        heading.setFill(Color.web("#01233f"));

        Text txtFV = new Text("Future Value");
        Text txtSP = new Text("Start Principal");
        Text txtIR = new Text("Interest Rate");
        Text txtNP = new Text("Number of periods    ");
        Text txtPMT= new Text("Annuity Payment");

        TextField txtFldFV = new TextField();
        txtFldFV.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldFV;});
        TextField txtFldSP = new TextField();
        txtFldSP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldSP;});
        TextField txtFldIR = new TextField();
        txtFldIR.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldIR;});
        TextField txtFldNP = new TextField();
        txtFldNP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldNP;});
        TextField txtFldPMT = new TextField();
        txtFldPMT.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldPMT;});

        final ToggleGroup groupRB = new ToggleGroup();

        Text txtPMT1 = new Text("PMT made at the,");
        RadioButton rbtn1 = new RadioButton("beginning of each compound period.");
        rbtn1.setToggleGroup(groupRB);
        RadioButton rbtn2 = new RadioButton("end of each compound period.");
        rbtn2.setToggleGroup(groupRB);
        rbtn2.setSelected(true);

        Button btnCal = new Button("Calculate");
        btnCal.setPrefSize(150, 20);
        btnCal.setStyle("-fx-background-color:#e6e6e6");
        DropShadow shadow = new DropShadow();
        btnCal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnCal.setEffect(shadow); });
        btnCal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnCal.setEffect(null);});

        Text txtheading2 = new Text("Results");
        txtheading2.setFont(Font.font("null", FontWeight.BOLD, 15));
        Text txtResults1 = new Text();
        Text txtResults2 = new Text();
        Text txtResults3 = new Text();
        Text txtResults4 = new Text();
        txtResults1.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults2.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults3.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults4.setFill(Color.web("rgb(0, 61, 89)"));

        //Adding keyboard to grid
        GridPane keyboard =addKeyboard();

        //____checking for past data
        DBCursor select = finance2.find();
        while (select.hasNext()){
            recode2 = select.next();
        }

        try{
            if(txtFldFV.getText().isEmpty()){
                txtFldSP.setText(recode2.get("Start Principal").toString());
                txtFldIR.setText(recode2.get("Interest Rate").toString());
                txtFldNP.setText(recode2.get("Number of Payments").toString());
                txtFldPMT.setText(recode2.get("Monthly payment").toString());


            }
        }catch(NullPointerException e){

        }

        try{
            if(txtFldSP.getText().isEmpty()){
                txtFldFV.setText(recode2.get("Future Value").toString());
                txtFldIR.setText(recode2.get("Interest Rate").toString());
                txtFldNP.setText(recode2.get("Number of Payments").toString());
                txtFldPMT.setText(recode2.get("Monthly payment").toString());


            }
        }catch(NullPointerException e){

        }

        try{
            if(txtFldNP.getText().isEmpty()){
                txtFldSP.setText(recode2.get("Start Principal").toString());
                txtFldIR.setText(recode2.get("Interest Rate").toString());
                txtFldFV.setText(recode2.get("Future Value").toString());
                txtFldPMT.setText(recode2.get("Monthly payment").toString());

            }
        }catch(NullPointerException e){

        }

        try{
            if(txtFldPMT.getText().isEmpty()){
                txtFldSP.setText(recode2.get("Start Principal").toString());
                txtFldIR.setText(recode2.get("Interest Rate").toString());
                txtFldNP.setText(recode2.get("Number of Payments").toString());
                txtFldFV.setText(recode2.get("Future Value").toString());


            }
        }catch(NullPointerException e){

        }



        //_______Doing all the calculations format exceptions under Finance Calculator2
        btnCal.setOnAction((ActionEvent e)->
                    {
                        if (rbtn2.isSelected()) {
                            if (txtFldFV.getText().isEmpty()) {
                                try {
                                    BasicDBObjectBuilder finance2Recodes = new BasicDBObjectBuilder().start();
                                    double SP=Double.parseDouble(txtFldSP.getText());
                                    double IR=Double.parseDouble(txtFldIR.getText());
                                    double NP=Double.parseDouble(txtFldNP.getText());
                                    double PMT=Double.parseDouble(txtFldPMT.getText());
                                    txtResults1.setText("Future Value: " +finanFV2(SP, IR, NP, PMT));
                                    txtFldFV.setText(finanFV2(SP, IR, NP, PMT) + "");
                                    txtResults2.setText("Present Value: " +finanPresentValue(finanFV3(SP, IR, NP, PMT),IR,NP));
                                    double totPrincipalValue = SP + NP*PMT;     //calculating total principal value
                                    txtResults3.setText("Total Principal: "+Math.round(totPrincipalValue*100.0)/100.0);
                                    double totInterest = finanFV2(SP, IR, NP, PMT)-totPrincipalValue;   //calculating total interest value
                                    txtResults4.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                                    GridPane grid3 =addChart(totInterest,SP);
                                    grid4.add(grid3,2,10,8,10);

                                    finance2Recodes.append("Start Principal",txtFldSP.getText());
                                    finance2Recodes.append("Interest Rate",txtFldIR.getText());
                                    finance2Recodes.append("Number of Payments",txtFldNP.getText());
                                    finance2Recodes.append("Monthly payment",txtFldPMT.getText());
                                    WriteResult data = finance2.insert(finance2Recodes.get());

                                }catch (NumberFormatException e2){
                                    checkingInputs();
                                }

                            }
                           else if (txtFldSP.getText().isEmpty()) {
                                try {
                                    BasicDBObjectBuilder finance2Recodes = new BasicDBObjectBuilder().start();
                                    double FV=Double.parseDouble(txtFldFV.getText());
                                    double IR=Double.parseDouble(txtFldIR.getText());
                                    double NP=Double.parseDouble(txtFldNP.getText());
                                    double PMT=Double.parseDouble(txtFldPMT.getText());
                                    txtResults1.setText("Start Principal: "+finanSP2(FV, IR, NP, PMT) );
                                    txtFldSP.setText(finanSP2(FV, IR, NP, PMT) + "");
                                    txtResults2.setText("Present Value: " +finanPresentValue(FV,IR,NP));
                                    double totPrincipalValue = finanSP2(FV, IR, NP, PMT) + NP*PMT;     //calculating total principal value
                                    txtResults3.setText("Total Principal: "+totPrincipalValue);
                                    double totInterest = FV-totPrincipalValue;   //calculating total interest value
                                    txtResults4.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                                    GridPane grid3 =addChart(totInterest,finanSP2(FV, IR, NP, PMT) );
                                    grid4.add(grid3,2,10,8,10);

                                    finance2Recodes.append("Future Value",txtFldFV.getText());
                                    finance2Recodes.append("Interest Rate",txtFldIR.getText());
                                    finance2Recodes.append("Number of Payments",txtFldNP.getText());
                                    finance2Recodes.append("Monthly payment",txtFldPMT.getText());
                                    WriteResult data = finance2.insert(finance2Recodes.get());

                                }catch (NumberFormatException e2){
                                    checkingInputs();
                                }


                            }
                            else if (txtFldNP.getText().isEmpty()) {
                                try {
                                    BasicDBObjectBuilder finance2Recodes = new BasicDBObjectBuilder().start();
                                    double FV=Double.parseDouble(txtFldFV.getText());
                                    double SP=Double.parseDouble(txtFldSP.getText());
                                    double IR=Double.parseDouble(txtFldIR.getText());
                                    double PMT=Double.parseDouble(txtFldPMT.getText());
                                    txtResults1.setText("Number of periods: "+finanNP2(FV,SP, IR, PMT) );
                                    txtFldNP.setText(finanNP2(FV,SP, IR, PMT) + "");
                                    txtResults2.setText("Present Value: " +finanPresentValue(FV,IR,finanNP2(FV,SP, IR, PMT)));
                                    double totPrincipalValue = SP + finanNP2(FV,SP, IR, PMT)*PMT;     //calculating total principal value
                                    txtResults3.setText("Total Principal: "+totPrincipalValue);
                                    double totInterest = FV-totPrincipalValue;   //calculating total interest value
                                    txtResults4.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                                    GridPane grid3 =addChart(totInterest,SP);
                                    grid4.add(grid3,2,10,8,10);
                                    finance2Recodes.append("Start Principal",txtFldSP.getText());
                                    finance2Recodes.append("Interest Rate",txtFldIR.getText());
                                    finance2Recodes.append("Future Value",txtFldFV.getText());
                                    finance2Recodes.append("Monthly payment",txtFldPMT.getText());
                                    WriteResult data = finance2.insert(finance2Recodes.get());


                                }catch (NumberFormatException e2){
                                    checkingInputs();
                                }

                            }
                            else if (txtFldPMT.getText().isEmpty()) {
                                try {
                                    BasicDBObjectBuilder finance2Recodes = new BasicDBObjectBuilder().start();
                                    double FV=Double.parseDouble(txtFldFV.getText());
                                    double SP=Double.parseDouble(txtFldSP.getText());
                                    double IR=Double.parseDouble(txtFldIR.getText());
                                    double NP=Double.parseDouble(txtFldNP.getText());
                                    txtResults1.setText("Monthly Payment: "+finanPMT2(FV,SP, IR, NP) + "");
                                    txtFldPMT.setText(finanPMT2(FV,SP, IR, NP) + "");
                                    txtResults2.setText("Present Value: " +finanPresentValue(FV,IR,NP));
                                    double totPrincipalValue = SP + NP*finanPMT2(FV,SP, IR, NP);     //calculating total principal value
                                    txtResults3.setText("Total Principal: "+totPrincipalValue);
                                    double totInterest = FV-totPrincipalValue;   //calculating total interest value
                                    txtResults4.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                                    GridPane grid3 =addChart(totInterest,SP);
                                    grid4.add(grid3,2,10,8,10);

                                    finance2Recodes.append("Future Value",txtFldFV.getText());                                    finance2Recodes.append("Start Principal",txtFldSP.getText());
                                    finance2Recodes.append("Interest Rate",txtFldIR.getText());
                                    finance2Recodes.append("Number of Payments",txtFldPMT.getText());
                                    finance2Recodes.append("Start Principal",txtFldSP.getText());
                                    WriteResult data = finance2.insert(finance2Recodes.get());


                                }catch (NumberFormatException e2){
                                    checkingInputs();
                                }

                            }
                                Button btnClr = new Button("Clear");
                                btnClr.setPrefSize(50, 20);
                                btnClr.setStyle("-fx-background-color:#e6e6e6");
                                btnClr.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e3)->{
                                btnClr.setEffect(shadow); });
                                btnClr.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e3)-> {
                                btnClr.setEffect(null);});
                                grid4.add(btnClr,0,18,4,1);
                                btnClr.setOnAction((ActionEvent e2)->{
                                txtFldFV.clear();
                                txtFldSP.clear();
                                txtFldNP.clear();
                                txtFldIR.clear();
                                txtFldPMT.clear();
                                txtResults1.setText(null);
                                txtResults2.setText(null);
                                txtResults3.setText(null);
                                txtResults4.setText(null);
                                grid4.getChildren().remove(21);
                                grid4.getChildren().remove(btnClr);});




                        }  else if (rbtn1.isSelected())   {

                            if (txtFldFV.getText().isEmpty()) {
                                try {
                                    BasicDBObjectBuilder finance2Recodes = new BasicDBObjectBuilder().start();
                                    double SP=Double.parseDouble(txtFldSP.getText());
                                    double IR=Double.parseDouble(txtFldIR.getText());
                                    double NP=Double.parseDouble(txtFldNP.getText());
                                    double PMT=Double.parseDouble(txtFldPMT.getText());
                                    txtResults1.setText("Future Value: "+finanFV3(SP, IR, NP, PMT) );
                                    txtFldFV.setText(finanFV3(SP, IR, NP, PMT) + "");
                                    txtResults2.setText("Present Value: " +finanPresentValue(finanFV3(SP, IR, NP, PMT),IR,NP));
                                    double totPrincipalValue = SP + NP*PMT;     //calculating total principal value
                                    txtResults3.setText("Total Principal: "+totPrincipalValue);
                                    double totInterest = finanFV3(SP, IR, NP, PMT)-totPrincipalValue;   //calculating total interest value
                                    txtResults4.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                                    finance2Recodes.append("Start Principal",txtFldSP.getText());
                                    finance2Recodes.append("Interest Rate",txtFldIR.getText());
                                    finance2Recodes.append("Number of Payments",txtFldNP.getText());
                                    finance2Recodes.append("Monthly payment",txtFldPMT.getText());
                                    WriteResult data = finance2.insert(finance2Recodes.get());

                                    GridPane grid3 =addChart(totInterest,SP);
                                    grid4.add(grid3,2,10,8,10);

                                }catch (NumberFormatException e2){
                                    checkingInputs();
                                }

                            }
                            else if (txtFldSP.getText().isEmpty()) {
                                try {
                                    BasicDBObjectBuilder finance2Recodes = new BasicDBObjectBuilder().start();
                                    double FV=Double.parseDouble(txtFldFV.getText());
                                    double IR=Double.parseDouble(txtFldIR.getText());
                                    double NP=Double.parseDouble(txtFldNP.getText());
                                    double PMT=Double.parseDouble(txtFldPMT.getText());
                                    txtResults1.setText("Start Principal: "+finanSP3(FV, IR, NP, PMT));
                                    txtFldSP.setText(finanSP3(FV, IR, NP, PMT) + "");
                                    txtResults2.setText("Present Value: " +finanPresentValue(FV,IR,NP));
                                    double totPrincipalValue = finanSP3(FV, IR, NP, PMT) + NP*PMT;     //calculating total principal value
                                    txtResults3.setText("Total Principal: "+totPrincipalValue);
                                    double totInterest = FV-totPrincipalValue;   //calculating total interest value
                                    txtResults4.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                                    GridPane grid3 =addChart(totInterest,finanSP3(FV, IR, NP, PMT));
                                    grid4.add(grid3,2,10,8,10);

                                    finance2Recodes.append("Future Value",txtFldFV.getText());
                                    finance2Recodes.append("Interest Rate",txtFldIR.getText());
                                    finance2Recodes.append("Number of Payments",txtFldNP.getText());
                                    finance2Recodes.append("Monthly payment",txtFldPMT.getText());
                                    WriteResult data = finance2.insert(finance2Recodes.get());


                                }catch (NumberFormatException e2){
                                    checkingInputs();
                                }


                            }
                            else if (txtFldNP.getText().isEmpty()) {
                                try {
                                    BasicDBObjectBuilder finance2Recodes = new BasicDBObjectBuilder().start();
                                    double FV=Double.parseDouble(txtFldFV.getText());
                                    double SP=Double.parseDouble(txtFldSP.getText());
                                    double IR=Double.parseDouble(txtFldIR.getText());
                                    double PMT=Double.parseDouble(txtFldPMT.getText());
                                    txtResults1.setText("Number of Paymnets: "+finanNP3(FV,SP, IR, PMT) + "");
                                    txtFldNP.setText(finanNP3(FV,SP, IR, PMT) + "");
                                    txtResults2.setText("Present Value: " +finanPresentValue(FV,IR,finanNP3(FV,SP, IR, PMT)));
                                    double totPrincipalValue = SP + finanNP3(FV,SP, IR, PMT)*PMT;     //calculating total principal value
                                    txtResults3.setText("Total Principal: "+totPrincipalValue);
                                    double totInterest = FV-totPrincipalValue;   //calculating total interest value
                                    txtResults4.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                                    finance2Recodes.append("Start Principal",txtFldSP.getText());
                                    finance2Recodes.append("Interest Rate",txtFldIR.getText());
                                    finance2Recodes.append("Future Value",txtFldFV.getText());
                                    finance2Recodes.append("Monthly payment",txtFldPMT.getText());
                                    WriteResult data = finance2.insert(finance2Recodes.get());
                                    GridPane grid3 =addChart(totInterest,SP);
                                    grid4.add(grid3,2,10,8,10);

                                }catch (NumberFormatException e2){
                                    checkingInputs();
                                }

                            }
                            else if (txtFldPMT.getText().isEmpty()) {
                                try {
                                    BasicDBObjectBuilder finance2Recodes = new BasicDBObjectBuilder().start();
                                    double FV=Double.parseDouble(txtFldFV.getText());
                                    double SP=Double.parseDouble(txtFldSP.getText());
                                    double IR=Double.parseDouble(txtFldIR.getText());
                                    double NP=Double.parseDouble(txtFldNP.getText());
                                    txtResults1.setText("Monthly Payment: "+finanPMT3(FV,SP,IR, NP) + "");
                                    txtFldPMT.setText(finanPMT3(FV,SP,IR, NP) + "");
                                    txtResults2.setText("Present Value: " +finanPresentValue(FV,IR,NP));
                                    double totPrincipalValue = SP + NP*finanPMT3(FV,SP,IR, NP);     //calculating total principal value
                                    txtResults3.setText("Total Principal: "+totPrincipalValue);
                                    double totInterest = FV-totPrincipalValue;   //calculating total interest value
                                    txtResults4.setText("Total interest: "+Math.round(totInterest*100.0)/100.0);

                                    finance2Recodes.append("Future Value",txtFldFV.getText());                                    finance2Recodes.append("Start Principal",txtFldSP.getText());
                                    finance2Recodes.append("Interest Rate",txtFldIR.getText());
                                    finance2Recodes.append("Number of Payments",txtFldPMT.getText());
                                    finance2Recodes.append("Start Principal",txtFldSP.getText());
                                    WriteResult data = finance2.insert(finance2Recodes.get());

                                    GridPane grid3 =addChart(totInterest,SP);
                                    grid4.add(grid3,2,10,8,10);

                                }catch (NumberFormatException e2){
                                    checkingInputs();
                                }

                            }
                            //Addig clear button
                                Button btnClr = new Button("Clear");
                                btnCal.setPrefSize(150, 20);
                                grid4.add(btnClr,0,18,4,1);
                                btnClr.setOnAction((ActionEvent e2)->{
                                txtFldFV.clear();
                                txtFldSP.clear();
                                txtFldNP.clear();
                                txtFldIR.clear();
                                txtFldPMT.clear();
                                txtResults1.setText(null);
                                txtResults2.setText(null);
                                txtResults3.setText(null);
                                txtResults4.setText(null);
                                grid4.getChildren().remove(21);
                                grid4.getChildren().remove(btnClr);});

                        }


                    });


        grid4.add(heading,0,1,2,1);
        grid4.add(txtFV, 0, 3);
        grid4.add(txtSP, 0, 4);
        grid4.add(txtIR, 0, 5);
        grid4.add(txtNP, 0, 6);
        grid4.add(txtPMT,0,7);
        grid4.add(txtPMT1,0,8,2,1);

        grid4.add(rbtn1,0,9,2,1);
        grid4.add(rbtn2,0,10,2,1);

        grid4.add(txtFldFV,1,3);
        grid4.add(txtFldSP,1,4);
        grid4.add(txtFldIR,1,5);
        grid4.add(txtFldNP,1,6);
        grid4.add(txtFldPMT,1,7);
        grid4.add(btnCal,0,12,2,1);
        grid4.add(txtheading2,0,13,2,1);
        grid4.add(txtResults1,0,14,4,1);
        grid4.add(txtResults2,0,15,4,1);
        grid4.add(txtResults3,0,16,4,1);
        grid4.add(txtResults4,0,17,4,1);

        grid4.add(keyboard,2,3,8,8);


        return grid4;

    }

    //Adding pane to Loan Calculator
    private GridPane addLoanCal() {
        GridPane grid5 = new GridPane();
        grid5.setHgap(20);
        grid5.setVgap(10);
        grid5.setPadding(new Insets(10, 20, 60, 20));

        Text heading = new Text("Loan Calculator");
        heading.setFont(Font.font("null", FontWeight.BOLD, 20));
        heading.setFill(Color.web("#01233f"));


        Text txtAP = new Text("Auto Price");
        Text txtMP = new Text("Monthly Pay");
        Text txtLT = new Text("Loan Term");
        Text txtIR = new Text("Interest Rate");
        Text txtDP= new Text("Down Payment");
        Text txtST = new Text("Sales Tax Rate");
        Text txtOT= new Text("Other Fees");

        //____Asking for data to textfields through array from keyboard
        TextField txtFldAP = new TextField();
        txtFldAP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldAP;});
        TextField txtFldMP = new TextField();
        txtFldMP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldMP;});
        TextField txtFldLT = new TextField();
        txtFldLT.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldLT;});
        TextField txtFldIR = new TextField();
        txtFldIR.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldIR;});
        TextField txtFldDP = new TextField();
        txtFldDP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldDP;});
        TextField txtFldST = new TextField();
        txtFldST.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldST;});
        TextField txtFldOT = new TextField();
        txtFldOT.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldOT;});

        Button btnCal = new Button("Calculate");
        btnCal.setPrefSize(70, 20);
        btnCal.setStyle("-fx-background-color:#e6e6e6");

        DropShadow shadow = new DropShadow();
        btnCal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnCal.setEffect(shadow); });
        btnCal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnCal.setEffect(null);});

        Text txtheading2 = new Text("Results");
        txtheading2.setFont(Font.font("null", FontWeight.BOLD, 15));
        Text txtResults1 = new Text();
        Text txtResults2 = new Text();
        Text txtResults3 = new Text();
        Text txtResults4 = new Text();
        Text txtResults5 = new Text();
        Text txtResults6 = new Text();
        Text txtResults7 = new Text();
        txtResults1.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults2.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults3.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults4.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults5.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults6.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults7.setFill(Color.web("rgb(0, 61, 89)"));

        //Adding keyboard
        GridPane keyboard =addKeyboard();

        //Checking for past data
        DBCursor select = loan.find();
        while (select.hasNext()){
            recode4 = select.next();
        }

        try{
            if(txtFldAP.getText().isEmpty()){
                txtFldMP.setText(recode4.get("Monthly Pay").toString());
                txtFldLT.setText(recode4.get("Loan Term").toString());
                txtFldIR.setText(recode4.get("Interest Rate").toString());
                txtFldDP.setText(recode4.get("Down Payment").toString());
                txtFldST.setText(recode4.get("Sales Tax").toString());
                txtFldOT.setText(recode4.get("Other Fees").toString());

            }
        }catch(NullPointerException e){

        }
        try{
            if(txtFldMP.getText().isEmpty()){
                txtFldAP.setText(recode4.get("Auto Price").toString());
                txtFldLT.setText(recode4.get("Loan Term").toString());
                txtFldIR.setText(recode4.get("Interest Rate").toString());
                txtFldDP.setText(recode4.get("Down Payment").toString());
                txtFldST.setText(recode4.get("Sales Tax").toString());
                txtFldOT.setText(recode4.get("Other Fees").toString());

            }
        }catch(NullPointerException e){

        }


       btnCal.setOnAction((ActionEvent e)-> {
            if(txtFldAP.getText().isEmpty()) {
                try {
                    BasicDBObjectBuilder loanRecodes = new BasicDBObjectBuilder().start();
                    double MP=Double.parseDouble(txtFldMP.getText());
                    double LT=Double.parseDouble(txtFldLT.getText());
                    double IR=Double.parseDouble(txtFldIR.getText());
                    double DP=Double.parseDouble(txtFldDP.getText());
                    double ST=Double.parseDouble(txtFldST.getText());
                    double OT=Double.parseDouble(txtFldOT.getText());

                    txtResults1.setText("Property price: " +loanAPPlusDP(MP,LT,IR,DP));
                    txtFldAP.setText(loanAPPlusDP(MP,LT,IR,DP)+"");
                    txtResults2.setText("Total Loan Amount: "+loanAP(MP,LT,IR));
                    double salesTax = loanAPPlusDP(MP,LT,IR,DP)*(ST/100);       //calculating sales tax
                    txtResults3.setText("Sales Tax: "+Math.round((salesTax*100.0))/100.0);
                    double upfrontPay = loanAPPlusDP(MP,LT,IR,DP)*(ST/100)+DP+OT; //calculating upfront payment
                    txtResults4.setText("Upfront Payment: "+Math.round((upfrontPay*100.0))/100.0);
                    double loanPay = LT*MP;         //calculating loan payment
                    txtResults5.setText("Total of "+LT+ " Loan Payments: "+Math.round(loanPay*100.0)/100.0);
                    double totalInterest = (LT*MP)-loanAP(MP,LT,IR);        //calculating total interest
                    txtResults6.setText("Total Interest: "+Math.round(totalInterest*100.0)/100.0);
                    double totalCost = loanAPPlusDP(MP,LT,IR,DP)*(ST/100)+DP+OT+(LT*MP);    //calculating total cost
                    txtResults7.setText("Total Cost: "+Math.round(totalCost*100.0)/100.0);



                    GridPane grid3 =addChart(totalInterest,loanAP(MP,LT,IR));
                    grid5.add(grid3,2,10,8,14);

                    loanRecodes.append("Monthly Pay",txtFldMP.getText());
                    loanRecodes.append("Loan Term",txtFldLT.getText());
                    loanRecodes.append("Interest Rate",txtFldIR.getText());
                    loanRecodes.append("Down Payment",txtFldDP.getText());
                    loanRecodes.append("Sales Tax",txtFldST.getText());
                    loanRecodes.append("Other Fees",txtFldOT.getText());
                    WriteResult data3 = loan.insert(loanRecodes.get());


                }catch (NumberFormatException e2){
                    checkingInputs();
                }

            }
            else if(txtFldMP.getText().isEmpty()){
                try {
                    BasicDBObjectBuilder loanRecodes = new BasicDBObjectBuilder().start();
                    double AP=Double.parseDouble(txtFldAP.getText());
                    double LT=Double.parseDouble(txtFldLT.getText());
                    double IR=Double.parseDouble(txtFldIR.getText());
                    double DP=Double.parseDouble(txtFldDP.getText());
                    double ST=Double.parseDouble(txtFldST.getText());
                    double OT=Double.parseDouble(txtFldOT.getText());

                    txtResults1.setText("Monthly Pay: "+loanMP(AP,LT,IR,DP));
                    txtFldMP.setText(loanMP(AP,LT,IR,DP)+"");
                    txtResults2.setText("Total Loan Amount: "+loanMPTotLoan(AP,DP));
                    double salesTax = AP*(ST/100);           //calculating sales tax
                    txtResults3.setText("Sales Tax: "+Math.round((salesTax*100.0))/100.0);
                    double upfrontPay = AP*(ST/100)+DP+OT;      //calculating upfront payment
                    txtResults4.setText("Upfront Payment: "+Math.round((upfrontPay*100.0))/100.0);
                    double loanPay = LT*loanMP(AP,LT,IR,DP);         //calculating total loan payment
                    txtResults5.setText("Total of "+LT+ " Loan Payments: "+Math.round(loanPay*100.0)/100.0);
                    double totalInterest = (LT*loanMP(AP,LT,IR,DP))-loanMPTotLoan(AP,DP);        //calculating total interest
                    txtResults6.setText("Total Interest: "+Math.round(totalInterest*100.0)/100.0);
                    double totalCost = loanMPTotLoan(AP,DP)*(ST/100)+DP+OT+(LT*loanMP(AP,LT,IR,DP));    //calculating total cost
                    txtResults7.setText("Total Cost: "+Math.round(totalCost*100.0)/100.0);

                    GridPane grid3 =addChart(totalInterest,loanMPTotLoan(AP,DP));
                    grid5.add(grid3,2,10,8,14);

                    loanRecodes.append("Auto Price",txtFldAP.getText());
                    loanRecodes.append("Loan Term",txtFldLT.getText());
                    loanRecodes.append("Interest Rate",txtFldIR.getText());
                    loanRecodes.append("Down Payment",txtFldDP.getText());
                    loanRecodes.append("Sales Tax",txtFldST.getText());
                    loanRecodes.append("Other Fees",txtFldOT.getText());
                    WriteResult data3 = loan.insert(loanRecodes.get());

                }catch (NumberFormatException e2){
                    checkingInputs();
                }


            }   //Adding clear button
                Button btnClr = new Button("Clear");
                btnClr.setPrefSize(50, 20);
                btnClr.setStyle("-fx-background-color:#e6e6e6");
                btnClr.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e3)->{
                btnClr.setEffect(shadow); });
                btnClr.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e3)-> {
               btnClr.setEffect(null);});
               grid5.add(btnClr,1,12,4,1);
               btnClr.setOnAction((ActionEvent e2)->{
               txtFldAP.clear();
               txtFldMP.clear();
               txtFldLT.clear();
               txtFldDP.clear();
               txtFldIR.clear();
               txtFldST.clear();
               txtFldOT.clear();
               txtResults1.setText(null);
               txtResults2.setText(null);
               txtResults3.setText(null);
               txtResults4.setText(null);
               txtResults5.setText(null);
               txtResults6.setText(null);
               txtResults7.setText(null);
               grid5.getChildren().remove(25);
               grid5.getChildren().remove(btnClr);});

        });



        grid5.add(heading,0,1,2,2);
        grid5.add(txtAP, 0, 3);
        grid5.add(txtMP, 0, 4);
        grid5.add(txtLT, 0, 5);
        grid5.add(txtIR, 0, 6);
        grid5.add(txtDP,0,7);
        grid5.add(txtST,0,8);
        grid5.add(txtOT,0,9);


        grid5.add(txtFldAP,1,3);
        grid5.add(txtFldMP,1,4);
        grid5.add(txtFldLT,1,5);
        grid5.add(txtFldIR,1,6);
        grid5.add(txtFldDP,1,7);
        grid5.add(txtFldST,1,8);
        grid5.add(txtFldOT,1,9);


        grid5.add(btnCal,0,12,1,1);
        grid5.add(txtheading2,0,13,2,1);
        grid5.add(txtResults1,0,14,4,1);
        grid5.add(txtResults2,0,15,4,1);
        grid5.add(txtResults3,0,16,4,1);
        grid5.add(txtResults4,0,17,4,1);
        grid5.add(txtResults5,0,18,4,1);
        grid5.add(txtResults6,0,19,4,1);
        grid5.add(txtResults7,0,20,4,1);

        grid5.add(keyboard,2,3,8,8);

        return grid5;

    }

    //Adding pane to Mortgage Calculator
    private GridPane addMortgageCal() {
        GridPane grid6 = new GridPane();
        grid6.setHgap(10);
        grid6.setVgap(10);
        grid6.setPadding(new Insets(10, 20, 60, 20));

        Text heading = new Text("Mortgage Calculator");
        heading.setFont(Font.font("null", FontWeight.BOLD, 20));
        heading.setFill(Color.web("#01233f"));

        Text txtPP = new Text("Property Price");
        Text txtDP = new Text("Down Payment");
        Text txtLT = new Text("Loan Term(years)");
        Text txtIR = new Text("Interest Rate");


        //Adding data to textfields through array from keyboard
        TextField txtFldPP = new TextField();
        txtFldPP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldPP;});
        TextField txtFldDP = new TextField();
        txtFldDP.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldDP;});
        TextField txtFldLT = new TextField();
        txtFldLT.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldLT;});
        TextField txtFldIR = new TextField();
        txtFldIR.setOnMouseClicked(e ->{ txtFldArray[0]=txtFldIR;});

        Button btnCal = new Button("Calculate");
        btnCal.setPrefSize(150, 20);
        btnCal.setStyle("-fx-background-color:#e6e6e6");
        DropShadow shadow = new DropShadow();
        btnCal.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e)->{
            btnCal.setEffect(shadow); });
        btnCal.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e)-> {
            btnCal.setEffect(null);});

        Text txtheading2 = new Text("Results");
        txtheading2.setFont(Font.font("null", FontWeight.BOLD, 15));
        Text txtResults1 = new Text();
        Text txtResults2 = new Text();
        Text txtResults3 = new Text();
        Text txtResults4 = new Text();
        Text txtResults5 = new Text();
        Text txtResults6 = new Text();
        Text txtResults7 = new Text();
        txtResults1.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults2.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults3.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults4.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults5.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults6.setFill(Color.web("rgb(0, 61, 89)"));
        txtResults7.setFill(Color.web("rgb(0, 61, 89)"));
        //Adding keyboard
        GridPane keyboard =addKeyboard();
        //checking for past data
        DBCursor select = mortgage.find();
        while (select.hasNext()){
            recode3 = select.next();
        }

        try{
            txtFldPP.setText(recode3.get("Property Price").toString());
            txtFldIR.setText(recode3.get("Interest Rate").toString());
            txtFldDP.setText(recode3.get("Down Payment").toString());
            txtFldLT.setText(recode3.get("Loan Term").toString());

        }catch(NullPointerException e){

        }
        //Doing all the calculations and format exceptions to Mortgage calculator
       btnCal.setOnAction((ActionEvent e)-> {
           try {
               BasicDBObjectBuilder mortgageRecodes = new BasicDBObjectBuilder().start();
               double PP=Double.parseDouble(txtFldPP.getText());
               double DP=Double.parseDouble(txtFldDP.getText());
               double LT=Double.parseDouble(txtFldLT.getText());
               double IR=Double.parseDouble(txtFldIR.getText());
               txtResults1.setText("Monthly Payment: "+mortgageMP(PP,DP,LT,IR)+"");
               txtResults2.setText("Property Price : "+PP);
               double loanAmount = PP-DP;   //Calculating loan amount
               txtResults3.setText("Loan Amount : "+Math.round((loanAmount*100.0))/100.0);
               txtResults4.setText("Down Payment: "+DP);
               double totalMortPayments = mortgageMP(PP,DP,LT,IR)*(LT*12);      //calculating total amount for mortgage payments
               txtResults5.setText("Total of "+(LT*12)+ " Mortgage Payments: "+Math.round(totalMortPayments*100.0)/100.0);
               double mortgateInterest= totalMortPayments-loanAmount;  //calculating total interest
               txtResults6.setText("Total Interest: "+Math.round(mortgateInterest*100.0)/100.0);

               GridPane grid2 =addChart(mortgateInterest,loanAmount);
               grid6.add(grid2,2,8,7,10);

               mortgageRecodes.append("Property Price",txtFldPP.getText());
               mortgageRecodes.append("Down Payment",txtFldDP.getText());
               mortgageRecodes.append("Loan Term",txtFldLT.getText());
               mortgageRecodes.append("Interest Rate",txtFldIR.getText());
               WriteResult data3 = mortgage.insert(mortgageRecodes.get());



           }catch (NumberFormatException e2){
               checkingInputs();
           }
            //Adding clear button
           Button btnClr = new Button("Clear");
           btnClr.setPrefSize(50, 20);
           btnClr.setStyle("-fx-background-color:#e6e6e6");
           btnClr.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e3)->{
               btnClr.setEffect(shadow); });
           btnClr.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e3)-> {
               btnClr.setEffect(null);});
           grid6.add(btnClr,0,17,4,1);
           btnClr.setOnAction((ActionEvent e2)->{
               txtFldPP.clear();
               txtFldLT.clear();
               txtFldDP.clear();
               txtFldIR.clear();
               txtResults1.setText(null);
               txtResults2.setText(null);
               txtResults3.setText(null);
               txtResults4.setText(null);
               txtResults5.setText(null);
               txtResults6.setText(null);
               txtResults7.setText(null);
               grid6.getChildren().remove(19);
               grid6.getChildren().remove(btnClr);});

       });


        grid6.add(heading,0,1,3,1);
        grid6.add(txtPP, 0, 3);
        grid6.add(txtDP, 0, 4);
        grid6.add(txtLT, 0, 5);
        grid6.add(txtIR, 0, 6);

        grid6.add(btnCal,0,8,2,1);

        grid6.add(txtFldPP,1,3);
        grid6.add(txtFldDP,1,4);
        grid6.add(txtFldLT,1,5);
        grid6.add(txtFldIR,1,6);

        grid6.add(txtheading2,0,9,2,1);
        grid6.add(txtResults1,0,10,4,1);
        grid6.add(txtResults2,0,11,4,1);
        grid6.add(txtResults3,0,12,4,1);
        grid6.add(txtResults4,0,13,4,1);
        grid6.add(txtResults5,0,14,4,1);
        grid6.add(txtResults6,0,15,4,1);
        grid6.add(txtResults7,0,16,4,1);
        grid6.add(keyboard,2,3,8,8);


        return grid6;

    }

    //Prompt the error to wrong data inputs
    private Object checkingInputs(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Data need to be numeric.");
        alert.show();
        return alert;
    }

   // Adding pane to help view
    private GridPane help(){
        GridPane grid6 = new GridPane();
        grid6.setHgap(10);
        grid6.setVgap(7);
        grid6.setPadding(new Insets(20, 20, 20, 20));
        Text line1 = new Text("This application provides a mean for users to perform calculations related to 3 different financial\n"+
                "scenarios.This includes mortgage calculations,loan calculations, financial calculations with and\n"+"without monthly payments can be calculated.");

        Text line2 = new Text("In order to obtain the desired output, keep the relevant text field empty and click the \"Calculate\" button.\n"+"Then in the results layout you will be given information according to your requirements.\n" +
                "BY using the option \"Clear\" button, the text field can be emptied and all your prior work is cleared\n"+ "providing space for another calculation.\n");
        Text line3 = new Text("1.Finance Calculator");
        Text line4 = new Text("a.Finance 1");
        Text line5 = new Text("This finance 1 calculator allows you to get extended information about start principal, future value,\n"+"interest rate and number of periods regarding the transaction you are concerned of.");

        Text line6 = new Text("b.Finance 2");
        Text line7 = new Text("This calculator can be employed to calculate records of an extra income which was made in a monthly\n"+ "basis.Furthermore this provides additional information about start principal,future value,number of\n"+"periods and annuity payment like figures of the certain transaction.");
        Text line8 = new Text("2.Mortgage Calculator");
        Text line9 = new Text("This calculator lets you estimate the monthly payment of a mortgaged property and further information\n"+ "about how your payment changes.Enter the every value in the relevant text field and click \"Calculate\"\n"+"button to proceed.");
        Text line10 = new Text("3.Loan Calculator");
        Text line11 = new Text("when loan price and other details regarding the loan is given this calculator will figure its auto price\n" +"and the monthly pay like figures in detail.In order to calculate the auto price and the monthly payment\n"+"keep the relevant text field empty and the click the \"Calculate\" button.");

        Text line12 = new Text("Keyboard");
        Text line13 = new Text("keyboard can be used to enter numerical figures into textfields.First the relevant textfield should be\n"+"selected.");
        Text line14 = new Text("Breakdown Graph");
        Text line15 = new Text("this can be used to get access to additional data about principal amount and interest rate related to\n"+"the transaction made.");

        line3.setFont(Font.font("null", FontWeight.BOLD, 15));
        line4.setFont(Font.font("null", FontWeight.BOLD, 15));
        line6.setFont(Font.font("null", FontWeight.BOLD, 15));
        line8.setFont(Font.font("null", FontWeight.BOLD, 15));
        line10.setFont(Font.font("null", FontWeight.BOLD, 15));
        line12.setFont(Font.font("null", FontWeight.BOLD, 15));
        line14.setFont(Font.font("null", FontWeight.BOLD, 15));

        grid6.add(line1,0,0);
        grid6.add(line2,0,1);
        grid6.add(line3,0,2);
        grid6.add(line4,0,3);
        grid6.add(line5,0,4);
        grid6.add(line6,0,5);
        grid6.add(line7,0,6);
        grid6.add(line8,0,7);
        grid6.add(line9,0,8);
        grid6.add(line10,0,9);
        grid6.add(line11,0,10);
        grid6.add(line12,0,11);
        grid6.add(line13,0,12);
        grid6.add(line14,0,13);
        grid6.add(line15,0,14);

        return  grid6;

    }

    public static void main(String[] args) {
        launch(args);
    }
}