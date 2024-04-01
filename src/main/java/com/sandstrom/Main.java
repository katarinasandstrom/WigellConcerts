package com.sandstrom;

import com.sandstrom.CustomClasses.CustomTextField;
import com.sandstrom.CustomClasses.CustomVBox;
import com.sandstrom.CustomClasses.StylesNotCoveredByCSS;
import com.sandstrom.Entities.Address;
import com.sandstrom.Entities.Concert;
import com.sandstrom.Entities.Customer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.sandstrom.ButtonMethods.*;



public class Main extends Application {

    private TextArea textAreaShowSoldTickets = new TextArea();

    String email;
    boolean isFirstClick = true;
    boolean isFirstClick2 = true;
    boolean isFirstClick3 = true;
    private  boolean isInitialized = false;

    Scene sceneFirst, sceneRegisterUser, sceneLogin, sceneCustomerMenu, sceneEditCustomerData,
            sceneRegConcert, sceneAdminMenu, sceneShowSoldTickets;
    Button btnRegisterUser, btnChoseLogin, btnRegister, btnBack1, btnBack2, btnLogin, btnBuyTicket,
             btnChangeDetails, btnLogOut, btnBack3,btnBack4, btnEdit, btnDeleteCustomer,
            btnRegisterConcert, btnBack5,btnGoToRegConcert ,btnGoToSeeConcerts, btnBack6, btnBack7;

    Label labelWelcome, labelRegisterNewCustomer, labelEditNewCustomer, labelErrorLogin,
    labelAreYouSure ,labelLogin, labelRegisterConcert, labelAdminMenu, labelShowSold,
            labelErrorRegConcert, labelDuplicateCustomer,labelCustomerPage, labelConfirmation;

    CustomVBox vBoxStart, vBoxRegister1, vBoxRegister2, vBoxRegister3, vBoxLogin, vBoxCustomerMenu, vBoxEdit1,
            vBoxEdit2, vBoxEdit3, vBoxInserConcert1, vBoxInsertConcert2, vBoxRegisterConcert,
            vBoxShowSoldTickets,  vBoxAdminMenu;


    BorderPane borderPaneStart, borderPaneRegisterPage, borderPaneLogin, borderPaneCustomerMenu,borderPaneEditPage,
            borderPaneRegisterConcert, borderPaneAdminMenu,    borderPaneShowSoldTickets;

    CustomTextField textFieldFirstName, textFieldLastName, textFieldDateOfBirth, textFieldPhoneNr,
            textFieldStreetAddress, textFieldStreetNr, textFieldZipNr, textFieldCity, textFieldEmail,
            textFieldPassWord, textFieldInsertUserName, textFieldEditFirstName,
            textFieldEditLastName, textFieldEditDateOfBirth, textFieldEditPhoneNr, textFieldEditStreetAddress,
            textFieldEditStreetNr, textFieldEditZipNr, textFieldEditCity, textFieldEditEmail, textFieldEditPassWord,
            textFieldInsertArtist, textFieldInsertTicketPrice, textFieldInsertAge, textFieldInsertConcertDate;

    PasswordField passwordFieldInsertPassWord;
    HBox hBoxRegister, hBoxBackAndLogOut, hBoxEdit, hBoxRegisterConcert;
    StackPane stackPaneFirstPage, stackPaneRegisterPage, stackPaneEditPage,stackPaneLogin, stackPaneRegConcert,
            stackPaneCustomer,  stackPaneAdminMenu, stackPaneShowSoldTickets;
    TableView<Concert> tableViewConcerts;
    TableColumn<Concert, String> columnArtist;
    TableColumn<Concert, String> columnArena;
    TableColumn<Concert, String> columnDate;
    TableColumn<Concert, String> columnPrice;
    TableColumn<Concert, String> columnAge;

    ObservableList<Concert> concertList = FXCollections.observableArrayList();


    public static Concert selectedConcert;


    @Override
    public void start(Stage stage) throws IOException {
        // Förbered data
        boolean isInitialized = false;
        String firstName2 = null;
        String lastName2 = null;
        LocalDate dateOfBirth2 = null;
        String phoneNr2 = null;
        String streetAddress2 = null;
        int streetNumber2 = 0;
        String zipNr2 = null;
        String city2 = null;
        String email2 = null;
        String password2 = null;



        StylesNotCoveredByCSS applyStyle = new StylesNotCoveredByCSS();

        // Sökvägen till bildfilen
        String imagePath = "C:\\Users\\annak\\IdeaProjects\\TESTJAVLA\\koncert1.jpeg";

        // Skapar en ImageView och laddar in bilden
        ImageView imageViewConcertPic1 = new ImageView(new Image("file:" + imagePath));
        imageViewConcertPic1.setFitWidth(885);
        imageViewConcertPic1.setFitHeight(413);

        imageViewConcertPic1.setPreserveRatio(true);



        labelWelcome = new Label("WIGELL CONCERTS");
        applyStyle.headerLabel(labelWelcome);

        btnRegisterUser = new Button("Registrera konto");
        btnRegisterUser.setOnAction(e -> stage.setScene(sceneRegisterUser));

        btnChoseLogin = new Button("Logga in");
        btnChoseLogin.setOnAction(e -> {
            stage.setScene(sceneLogin);
        });

        vBoxStart = new CustomVBox();
        vBoxStart.getChildren().addAll(imageViewConcertPic1, btnRegisterUser, btnChoseLogin);
        vBoxStart.getStyleClass().add("vBox");

        VBox vBoxLabelWelcome = new VBox();
        vBoxLabelWelcome.setAlignment(Pos.BASELINE_CENTER);
        vBoxLabelWelcome.getChildren().add(labelWelcome);

        borderPaneStart = new BorderPane();
        borderPaneStart.setCenter(vBoxStart);

        stackPaneFirstPage = new StackPane();
        stackPaneFirstPage.setStyle("-fx-background-color: #110502;");
        stackPaneFirstPage.getChildren().addAll(vBoxLabelWelcome, imageViewConcertPic1, borderPaneStart);

        sceneFirst = new Scene(stackPaneFirstPage, 600, 700);
        sceneFirst.getStylesheets().add("concert.css");

        stage.setScene(sceneFirst);
        stage.show();


        //REGISTRERA ANVÄNDARE

        labelRegisterNewCustomer = new Label("REGISTRERA KUNDKONTO");
        applyStyle.headerLabel(labelRegisterNewCustomer);

        textFieldFirstName = new CustomTextField();
        textFieldFirstName.setPromptText("Förnamn");

        textFieldLastName = new CustomTextField();
        textFieldLastName.setPromptText("Efternamn");

        textFieldDateOfBirth = new CustomTextField();
        textFieldDateOfBirth.setPromptText("Födelsedatum");

        textFieldPhoneNr = new CustomTextField();
        textFieldPhoneNr.setPromptText("Telefonnummer");

        textFieldStreetAddress = new CustomTextField();
        textFieldStreetAddress.setPromptText("Gatuadress");

        textFieldStreetNr = new CustomTextField();
        textFieldStreetNr.setPromptText("Gatunr");

        textFieldZipNr = new CustomTextField();
        textFieldZipNr.setPromptText("Postnr");

        textFieldCity = new CustomTextField();
        textFieldCity.setPromptText("Postadress");

        textFieldEmail = new CustomTextField();
        textFieldEmail.setPromptText("Mailadress");

        textFieldPassWord = new CustomTextField();
        textFieldPassWord.setPromptText("Välj lösenord");

        labelDuplicateCustomer = new Label(" ");
        applyStyle.errorLabel(labelDuplicateCustomer);

        btnRegister = new Button("Registrera");
        btnRegister.setOnAction(e -> { // HÄR ÄR KODEN FÖR ATT SPARA TILL DATABASEN
           String firstName = textFieldFirstName.getText();
           String lastName = textFieldLastName.getText();


            LocalDate localDateOfBirth = null;
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateOfBirthString = textFieldDateOfBirth.getText();
                localDateOfBirth = LocalDate.parse(dateOfBirthString, dateFormatter);
            } catch (DateTimeParseException ex) {
                ex.printStackTrace();
            }

            String phoneNr = textFieldPhoneNr.getText();
            String streetAddress = textFieldStreetAddress.getText();
            int streetNumber = Integer.parseInt(textFieldStreetNr.getText());
            String zipNr = textFieldZipNr.getText();
            String city = textFieldCity.getText();
            email = textFieldEmail.getText();
            String passWord = textFieldPassWord.getText();


            java.sql.Date sqlDateOfBirth = java.sql.Date.valueOf(localDateOfBirth);

            ButtonMethods.registerNewCustomer(labelDuplicateCustomer,firstName, lastName, sqlDateOfBirth,
                    phoneNr, streetAddress, streetNumber, zipNr, city, email, passWord);
        });


        btnBack1 = new Button("Tillbaka");
        btnBack1.setOnAction(e -> {
            stage.setScene(sceneFirst);
            textFieldFirstName.clear();
            textFieldLastName.clear();
            textFieldDateOfBirth.clear();
            textFieldPhoneNr.clear();
            textFieldStreetAddress.clear();
            textFieldStreetNr.clear();
            textFieldZipNr.clear();
            textFieldCity.clear();
            textFieldEmail.clear();
            textFieldPassWord.clear();

        }); //Tillbaka till förra sidan.
        applyStyle.backButton(btnBack1);

        vBoxRegister1 = new CustomVBox();
        vBoxRegister1.setSpacing(20);
        vBoxRegister1.getChildren().addAll(textFieldFirstName,
                textFieldDateOfBirth, textFieldStreetAddress, textFieldZipNr, textFieldEmail);

        vBoxRegister2 = new CustomVBox();
        vBoxRegister2.setSpacing(20);
        vBoxRegister2.getChildren().addAll(textFieldLastName, textFieldPhoneNr, textFieldStreetNr,
                textFieldCity, textFieldPassWord);

        hBoxRegister = new HBox();
        hBoxRegister.setSpacing(20);
        hBoxRegister.getChildren().addAll(vBoxRegister1, vBoxRegister2);
        hBoxRegister.setPadding(new Insets(50));
        hBoxRegister.setAlignment(Pos.CENTER);

        vBoxRegister3 = new CustomVBox();
        vBoxRegister3.getChildren().addAll(labelRegisterNewCustomer, hBoxRegister, btnRegister,
                labelDuplicateCustomer);
        vBoxRegister3.setAlignment(Pos.CENTER);

        borderPaneRegisterPage = new BorderPane();
        borderPaneRegisterPage.setCenter(vBoxRegister3);
        borderPaneRegisterPage.setBottom(btnBack1);

        stackPaneRegisterPage = new StackPane();
        stackPaneRegisterPage.setStyle("-fx-background-color: #110502;");
        stackPaneRegisterPage.getChildren().addAll(borderPaneRegisterPage);


        sceneRegisterUser = new Scene(stackPaneRegisterPage, 600, 700);
        sceneRegisterUser.getStylesheets().add("concert.css");


        // LOGINSIDAN

        labelLogin = new Label("LOGGA IN");
        applyStyle.headerLabel(labelLogin);

        textFieldInsertUserName = new CustomTextField();
        textFieldInsertUserName.setPromptText("Användarnamn");


        passwordFieldInsertPassWord =new PasswordField();
        passwordFieldInsertPassWord.setPromptText("Lösenord");
        passwordFieldInsertPassWord.setMinSize(200, 40);
        passwordFieldInsertPassWord.setMaxSize(200, 40);

        labelErrorLogin = new Label();
        labelErrorLogin.setText(" ");
        applyStyle.errorLabel(labelErrorLogin);


        btnLogin = new Button("Logga in");
        btnLogin.setOnAction(e -> {
            email = textFieldInsertUserName.getText();
            String password = passwordFieldInsertPassWord.getText();

            if (ButtonMethods.login(email, password)) {
                if (ButtonMethods.isAdmin(email)) {
                    stage.setScene(sceneAdminMenu); // Skicka admin till scenen sceneAdmin
                } else {
                    stage.setScene(sceneCustomerMenu); // Skicka kund till scenen sceneCustomerMenu
                }
            } else {
                // Inloggning misslyckades
                labelErrorLogin.setText("Inloggning misslyckades. Försök igen.");
            }
            passwordFieldInsertPassWord.clear();
            textFieldInsertUserName.clear();
        });
        btnLogin.setMinSize(200, 30);
        btnLogin.setMaxSize(200, 30);

        btnBack2 = new Button("Tillbaka");
        btnBack2.setOnAction(e -> stage.setScene(sceneFirst)); //Tillbaka till förra sidan.
        applyStyle.backButton(btnBack2);

        vBoxLogin = new CustomVBox();
        vBoxLogin.getChildren().addAll(labelLogin,textFieldInsertUserName, passwordFieldInsertPassWord, btnLogin,labelErrorLogin);
        vBoxLogin.setSpacing(10);
        vBoxLogin.setAlignment(Pos.CENTER);

        borderPaneLogin = new BorderPane();
        borderPaneLogin.setCenter(vBoxLogin);
        borderPaneLogin.setBottom(btnBack2);

        stackPaneLogin = new StackPane();
        stackPaneLogin.setStyle("-fx-background-color: #110502;");
        stackPaneLogin.getChildren().addAll(borderPaneLogin);

        sceneLogin = new Scene(stackPaneLogin, 600, 700);
        sceneLogin.getStylesheets().add("concert.css");



        //KUNDMENYN
        labelCustomerPage = new Label("AKTUELLA KONSERTER");
        applyStyle.headerLabel(labelCustomerPage);

        labelConfirmation = new Label(" ");
        applyStyle.errorLabel(labelConfirmation);

        btnBuyTicket = new Button("Köp biljett");
        btnBuyTicket.setOnAction(e -> {
            if (isFirstClick3){
                btnBuyTicket.setText("Bekräfta köp");
                isFirstClick3 = false;
                btnBuyTicket.setStyle("-fx-background-color: #67040c; -fx-text-fill: #efb5ca");
            }
            //Köp biljett som lagras på email för kontot som är inloggat
            else {
                buyTicket(email);
                btnBuyTicket.setStyle("-fx-background-color:#efb5ca ; -fx-text-fill:  #67040c");
                btnBuyTicket.setText("Köp biljett");
                labelConfirmation.setText("Bokningsbekräftelse skickat till " + email + ".");
                isFirstClick3 = true;
            }
        });

        btnChangeDetails = new Button("Ändra uppgifter");
        btnChangeDetails.setOnAction(e -> stage.setScene(sceneEditCustomerData));

        btnLogOut = new Button("Logga ut");
        applyStyle.backButton(btnLogOut);
        btnLogOut.setOnAction(e -> stage.setScene(sceneFirst)); // till förstasidan

        btnBack3 = new Button("Tillbaka");
        btnBack3.setOnAction(e -> {stage.setScene(sceneLogin);//Tillbaka till förra sidan.
            labelConfirmation.setText(" ");});
        applyStyle.backButton(btnBack3);

        tableViewConcerts = new TableView<>();
        tableViewConcerts.setMinSize(400, 300);
        tableViewConcerts.setMaxSize(400, 300);

        SessionFactory sessionFactory1 = new Configuration().configure().buildSessionFactory();
        Session session1 = sessionFactory1.openSession();
        session1.beginTransaction();

        tableViewConcerts.getItems().clear();
        tableViewConcerts.setStyle("-fx-background-color: #67040C;");

        loadConcertsFromDatabase(concertList);

        columnArena = new TableColumn<>("Arena");
        columnArena.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArena().getArenaName()));
        columnArtist = new TableColumn<>("Artist");
        columnArtist.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        columnDate = new TableColumn<>("Datum");
        columnDate.setCellValueFactory(new PropertyValueFactory<>("concertDate"));
        columnPrice = new TableColumn<>("Pris");
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        columnAge = new TableColumn<>("Åldersgräns");
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        tableViewConcerts.getColumns().addAll(columnArena, columnArtist, columnDate, columnPrice, columnAge);
        tableViewConcerts.setItems(concertList);
        tableViewConcerts.refresh();

        tableViewConcerts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Spara den valda konserten för att komma åt senare
                selectedConcert = newSelection;
            }
        });


        vBoxCustomerMenu = new CustomVBox();
        vBoxCustomerMenu.getChildren().addAll(labelCustomerPage,tableViewConcerts,labelConfirmation, btnBuyTicket, btnChangeDetails);
        vBoxCustomerMenu.setSpacing(10);
        vBoxCustomerMenu.setPadding(new Insets(20));
        vBoxCustomerMenu.setAlignment(Pos.TOP_CENTER);

        hBoxBackAndLogOut = new HBox();
        hBoxBackAndLogOut.getChildren().addAll(btnBack3, btnLogOut);
        hBoxBackAndLogOut.setAlignment(Pos.CENTER_LEFT);

        borderPaneCustomerMenu = new BorderPane();
        borderPaneCustomerMenu.setCenter(vBoxCustomerMenu);
        borderPaneCustomerMenu.setBottom(hBoxBackAndLogOut);

        stackPaneCustomer = new StackPane(); //NY RAD
        stackPaneCustomer.getChildren().add(borderPaneCustomerMenu); //NY RAD
        stackPaneCustomer.setStyle("-fx-background-color: #110502;");//NY RAD

        sceneCustomerMenu = new Scene(stackPaneCustomer, 600, 700);//NY RAD
        sceneCustomerMenu.getStylesheets().add("concert.css");



        // REDIGERA KUNDDATA

        labelEditNewCustomer = new Label("REDIGERA KUNDKONTO");
        applyStyle.headerLabel(labelEditNewCustomer );

        textFieldEditFirstName = new CustomTextField();
        textFieldEditFirstName.setPromptText("Förnamn");

        textFieldEditLastName = new CustomTextField();
        textFieldEditLastName.setPromptText("Efternamn");

        textFieldEditDateOfBirth = new CustomTextField();
        textFieldEditDateOfBirth.setPromptText("Födelsedatum");

        textFieldEditPhoneNr = new CustomTextField();
        textFieldEditPhoneNr.setPromptText("Telefonnummer");

        textFieldEditStreetAddress = new CustomTextField();
        textFieldEditStreetAddress.setPromptText("Gatuadress");

        textFieldEditStreetNr = new CustomTextField();
        textFieldEditStreetNr.setPromptText("Gatunr");

        textFieldEditZipNr = new CustomTextField();
        textFieldEditZipNr.setPromptText("Postnr");

        textFieldEditCity = new CustomTextField();
        textFieldEditCity.setPromptText("Postadress");

        textFieldEditEmail = new CustomTextField();
        textFieldEditEmail.setPromptText("Mailadress");

        textFieldEditPassWord = new CustomTextField();
        textFieldEditPassWord.setPromptText("Välj lösenord");

        btnEdit = new Button("Se dina uppgifter");

        btnEdit.setOnAction(event -> {
            if (isFirstClick) {
                // Första knapptrycket
                btnEdit.setText("Spara dina uppgifter");
                btnEdit.setStyle("-fx-background-color: #67040c; -fx-text-fill: #efb5ca");
                Transaction transaction = null;
                SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
                try (Session session = sessionFactory.openSession()) {
                    transaction = session.beginTransaction();

                    // Hämta kunden baserat på e-postadressen
                    Query query = session.createQuery("FROM Customer WHERE email = :email");
                    query.setParameter("email", email);
                    Customer customer = (Customer) query.uniqueResult();

                    if (customer != null) {
                        // Fyll i textfälten med kundens data
                        textFieldEditFirstName.setText(customer.getFirstName());
                        System.out.println(customer.getFirstName());
                        textFieldEditLastName.setText(customer.getLastName());
                        // Konvertera födelsedatum till sträng och fyll i textfältet
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String dateOfBirthString = dateFormat.format(customer.getDateOfBirth());
                        textFieldEditDateOfBirth.setText(dateOfBirthString);
                        textFieldEditPhoneNr.setText(customer.getPhoneNr());
                        textFieldEditStreetAddress.setText(customer.getAddress().getStreet());
                        textFieldEditStreetNr.setText(String.valueOf(customer.getAddress().getStreetNumber()));
                        textFieldEditZipNr.setText(customer.getAddress().getZip());
                        textFieldEditCity.setText(customer.getAddress().getCity());
                        textFieldEditEmail.setText(customer.getEmail());
                        textFieldEditPassWord.setText(customer.getPassWord());
                    } else {
                        // Kund inte hittad i databasen
                        System.out.println("Kund inte hittad i databasen.");
                    }

                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                }
                isFirstClick = false;
            } else {
                // Andra knapptrycket
                SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();

                try {
                    Query query = session.createQuery("FROM Customer WHERE email = :email");
                    query.setParameter("email", email);
                    Customer customer = (Customer) query.uniqueResult();
                    if (customer != null) {
                        Address address = customer.getAddress();
                        customer.setFirstName(textFieldEditFirstName.getText());
                        customer.setLastName(textFieldEditLastName.getText());
                        textFieldEditDateOfBirth.getText();
                        customer.setPhoneNr(textFieldEditPhoneNr.getText());
                        address.setStreet(textFieldEditStreetAddress.getText());
                        address.setStreetNumber(Integer.parseInt(textFieldEditStreetNr.getText()));
                        address.setZip(textFieldEditZipNr.getText());
                        address.setCity(textFieldEditCity.getText());
                        customer.setEmail(textFieldEditEmail.getText());
                        customer.setPassWord(textFieldEditPassWord.getText());
                        customer.setAddress(address);
                        session.update(customer);
                        transaction.commit();
                    } else {

                    }
                } catch (Exception e) {
                    transaction.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
                btnEdit.setStyle("-fx-background-color: #EFB5CA; -fx-text-fill: #67040C");
                isFirstClick=true;
            }
        });
        labelAreYouSure = new Label (" ");
        applyStyle.errorLabel(labelAreYouSure);

        btnDeleteCustomer = new Button("Avsluta konto");
        btnDeleteCustomer.setOnAction(e -> {

            if (isFirstClick2) {
                // Första knapptrycket
                labelAreYouSure.setText("Är du säker på att du vill avsluta kontot?");
                btnDeleteCustomer.setStyle("-fx-background-color: #67040c; -fx-text-fill: #efb5ca");
                isFirstClick2 = false;
            }
            else {
                //andra knapptrycket
                    ButtonMethods.deleteCustomer(email);
                    stage.setScene(sceneFirst);
                    isFirstClick2 = true;
        }});

        btnBack4 = new Button("Tillbaka");
        btnBack4.setOnAction(e -> stage.setScene(sceneCustomerMenu)); //Tillbaka till förra sidan.
        applyStyle.backButton(btnBack4);

        vBoxEdit1 = new CustomVBox();
        vBoxEdit1.getChildren().addAll(textFieldEditFirstName,
                textFieldEditDateOfBirth, textFieldEditStreetAddress, textFieldEditZipNr, textFieldEditEmail, btnEdit);

        vBoxEdit2 = new CustomVBox();
        vBoxEdit2.getChildren().addAll(textFieldEditLastName, textFieldEditPhoneNr, textFieldEditStreetNr,
                    textFieldEditCity, textFieldEditPassWord, btnDeleteCustomer);

        hBoxEdit = new HBox();
        hBoxEdit.setSpacing(20);
        hBoxEdit.getChildren().addAll(vBoxEdit1, vBoxEdit2);
        hBoxEdit.setPadding(new Insets(50));
        hBoxEdit.setAlignment(Pos.CENTER);

        vBoxEdit3 = new CustomVBox();
        vBoxEdit3.getChildren().addAll(labelEditNewCustomer, hBoxEdit, labelAreYouSure);

        borderPaneEditPage = new BorderPane();
        borderPaneEditPage.setCenter(vBoxEdit3);
        borderPaneEditPage.setBottom(btnBack4);

        stackPaneEditPage = new StackPane();
        stackPaneEditPage.getChildren().add(borderPaneEditPage);
        stackPaneEditPage.setStyle("-fx-background-color: #110502;");

        sceneEditCustomerData = new Scene(stackPaneEditPage, 600, 700);
        sceneEditCustomerData.getStylesheets().add("concert.css");

            // ADMINMENY

        labelAdminMenu = new Label("ADMIN");

        applyStyle.headerLabel(labelAdminMenu);

        btnGoToSeeConcerts = new Button("Se sålda biljetter");
        btnGoToSeeConcerts.setOnAction(e-> stage.setScene(sceneShowSoldTickets));

        btnGoToRegConcert = new Button("Registrera konserter");
        btnGoToRegConcert.setOnAction(e-> stage.setScene(sceneRegConcert));

        btnBack6 = new Button("Tillbaka");
        btnBack6.setOnAction(e-> stage.setScene(sceneFirst));
        applyStyle.backButton(btnBack6);

        vBoxAdminMenu = new CustomVBox();
        vBoxAdminMenu.getChildren().addAll(labelAdminMenu, btnGoToSeeConcerts, btnGoToRegConcert);

        borderPaneAdminMenu = new BorderPane();
        borderPaneAdminMenu.setCenter(vBoxAdminMenu);
        borderPaneAdminMenu.setBottom(btnBack6);

        stackPaneAdminMenu = new StackPane(borderPaneAdminMenu);
        stackPaneAdminMenu.setStyle("-fx-background-color: #110502;");

        sceneAdminMenu = new Scene(stackPaneAdminMenu, 600, 700);
        sceneAdminMenu.getStylesheets().add("concert.css");


            //REGISTRERA KONSERTER

        labelRegisterConcert = new Label("REGISTRERA KONSERT");
        applyStyle.headerLabel(labelRegisterConcert);

        textFieldInsertArtist = new CustomTextField();
        textFieldInsertArtist.setPromptText("Artist");

        textFieldInsertTicketPrice = new CustomTextField();
        textFieldInsertTicketPrice.setPromptText("Biljettpris");

        textFieldInsertAge = new CustomTextField();
        textFieldInsertAge.setPromptText("Åldersgräns");

        textFieldInsertConcertDate = new CustomTextField();
        textFieldInsertConcertDate.setPromptText("Datum");

        CustomTextField textFieldInsertArenaName = new CustomTextField();//Byt till dropdown sen
        textFieldInsertArenaName.setPromptText("Arena name");

        labelErrorRegConcert = new Label();
        applyStyle.errorLabel(labelErrorRegConcert);

        btnRegisterConcert = new Button("Registrera konsert");
        btnRegisterConcert.setOnAction(e -> {
            String artistName = textFieldInsertArtist.getText();
            String concertDate = textFieldInsertConcertDate.getText();
            String ticketPriceString = textFieldInsertTicketPrice.getText();
            int ticketPrice = 0;
            try {
                ticketPrice = Integer.parseInt(ticketPriceString);
            } catch (NumberFormatException g) {
                g.printStackTrace();
            }
            String ageString = textFieldInsertAge.getText();
            int age = 0;
            try {
                age = Integer.parseInt(ageString);
            } catch (NumberFormatException f) {
                f.printStackTrace();
            }
            String arenaName = textFieldInsertArenaName.getText();

            ButtonMethods.registerNewConcert(labelErrorRegConcert ,concertList,artistName, concertDate, ticketPrice,
                    age, arenaName);
            });


        btnBack5 = new Button("Tillbaka");
        applyStyle.backButton(btnBack5);
        btnBack5.setOnAction(e -> {stage.setScene(sceneAdminMenu);//Tillbaka till förra sidan.
                labelErrorRegConcert.setText(" ");
            textFieldInsertArtist.clear();
            textFieldInsertTicketPrice.clear();
            textFieldInsertAge.clear();
            textFieldInsertConcertDate.clear();
            textFieldInsertArenaName.clear();
            });

        vBoxInserConcert1 = new CustomVBox();
        vBoxInserConcert1.getChildren().addAll(textFieldInsertArtist, textFieldInsertTicketPrice, textFieldInsertConcertDate);
        vBoxInserConcert1.setAlignment(Pos.CENTER);
        vBoxInserConcert1.setSpacing(20);

        vBoxInsertConcert2 = new CustomVBox();
        vBoxInsertConcert2.getChildren().addAll(textFieldInsertAge, textFieldInsertArenaName, btnRegisterConcert);
        vBoxInsertConcert2.setAlignment(Pos.CENTER);
        vBoxInsertConcert2.setSpacing(20);

        hBoxRegisterConcert = new HBox();
        hBoxRegisterConcert.getChildren().addAll(vBoxInserConcert1, vBoxInsertConcert2);
        hBoxRegisterConcert.setSpacing(20);
        hBoxRegisterConcert.setAlignment(Pos.CENTER);

        vBoxRegisterConcert = new CustomVBox();
        vBoxRegisterConcert.getChildren().addAll(labelRegisterConcert, hBoxRegisterConcert, labelErrorRegConcert);

        borderPaneRegisterConcert = new BorderPane();
        borderPaneRegisterConcert.setCenter(vBoxRegisterConcert);
        borderPaneRegisterConcert.setBottom(btnBack5);

        stackPaneRegConcert = new StackPane();
        stackPaneRegConcert.getChildren().addAll(borderPaneRegisterConcert);
        stackPaneRegConcert.setStyle("-fx-background-color: #110502;");

        sceneRegConcert = new Scene(stackPaneRegConcert, 600, 700);
        sceneRegConcert.getStylesheets().add("concert.css");


            //Visa sålda

        labelShowSold = new Label("SÅLDA BILJETTER");
        applyStyle.headerLabel(labelShowSold);
        textAreaShowSoldTickets = new TextArea();
        textAreaShowSoldTickets.setEditable(false);
        textAreaShowSoldTickets.setMinSize(400,400);
        textAreaShowSoldTickets.setMaxSize(400, 400);
        applyStyle.textArea(textAreaShowSoldTickets);

        ButtonMethods.showConcertCustomer(textAreaShowSoldTickets);

        btnBack7 = new Button("Tillbaka");
        btnBack7.setOnAction(e -> stage.setScene(sceneAdminMenu)); //Tillbaka till förra sidan.
        applyStyle.backButton(btnBack7);

        vBoxShowSoldTickets = new CustomVBox();
        vBoxShowSoldTickets.getChildren().addAll(labelShowSold , textAreaShowSoldTickets);
        vBoxShowSoldTickets.setAlignment(Pos.CENTER);

        borderPaneShowSoldTickets= new BorderPane();
        borderPaneShowSoldTickets.setCenter(vBoxShowSoldTickets);
        borderPaneShowSoldTickets.setBottom(btnBack7);

        stackPaneShowSoldTickets = new StackPane();
        stackPaneShowSoldTickets.getChildren().add(borderPaneShowSoldTickets);
        stackPaneShowSoldTickets.setStyle("-fx-background-color: #110502;");

        sceneShowSoldTickets = new Scene(stackPaneShowSoldTickets, 600, 700);
        sceneShowSoldTickets.getStylesheets().add("concert.css");
    }



    public static void main(String[] args) {
        launch();
    }
   }

