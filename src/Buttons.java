import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;

public class Buttons extends Application{

    private WebView webView = new WebView();
    private Label status;

    @Override
    public void start(Stage primaryStage) {

        GridPane pane = new GridPane();
        pane.setVgap(5);
        pane.setHgap(5);
        pane.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-insets: 5;" +
                "-fx-border-color: green;"
        );
        //Menu buttons
        MenuButton menuButton = new MenuButton("Home");
        MenuItem link1 = new MenuItem("Links");
        link1.setOnAction(event -> load("http://www.google.com"));
        MenuItem link2 = new MenuItem("About");
        link2.setOnAction(event -> load("http://www.youtube.com"));
        MenuItem link3 = new MenuItem("youtube");
        link3.setOnAction(event -> load("http://www.twitter.com"));
        MenuItem link4 = new MenuItem("twitter");
        link4.setOnAction(event -> load("www.yahoo.com"));
        menuButton.getItems().addAll(link1, link2, link3, link4);
        Label menuBtn = new Label("MenuButton:");
        pane.add(menuBtn, 0, 0);
        pane.add(menuButton, 1, 0);

        //ToggleButton
        ToggleButton yes = new ToggleButton("Yes");
        ToggleButton no = new ToggleButton("No");
        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Old Value: " + oldValue + "\n" +
                    "New Value: " + newValue
            );
        });
        group.getToggles().addAll(yes, no);
        pane.add(new Label("ToggleButton: "), 0, 1);
        pane.add(yes, 1, 1);
        pane.add(no, 2, 1);

        //SplitMenuButton
        SplitMenuButton menuButton1 = new SplitMenuButton();
        menuButton1.setText("Home");
        menuButton1.setOnAction(event -> System.out.println(menuButton1));
        MenuItem item = new MenuItem("Likes");
        item.setOnAction(event -> System.out.println(item));
        MenuItem item1 = new MenuItem("Comments");
        item1.setOnAction(event -> System.out.println(item1));
        MenuItem item2 = new MenuItem("Reviews");
        item2.setOnAction(event -> System.out.println(item2));
        MenuItem item3 = new MenuItem("Subscribers");
        item3.setOnAction(event -> System.out.println(item3));
        menuButton1.getItems().addAll(item, item1, item2, item3);
        pane.add(new Label("SplitMenuItem:"), 0, 2);
        pane.add(menuButton1, 1, 2);

        //ChoiceBox
        ChoiceBox<Person> people = new ChoiceBox<>();
        StringConverter<Person> converter = new StringConverter<Person>() {
            @Override
            public String toString(Person object) {
                return object.getName();
            }

            @Override
            public Person fromString(String string) {
                return new Person(new SimpleIntegerProperty(0), new SimpleStringProperty(string), new SimpleObjectProperty<>(LocalDate.of(1900, 2, 2)));
            }
        };
        people.setConverter(converter);
        people.getItems().add(new Person(new SimpleIntegerProperty(25),
                new SimpleStringProperty("Mudi Lukman"),
                new SimpleObjectProperty(LocalDate.of(1997, 07, 14))));
        people.getItems().add(new Person(new SimpleIntegerProperty(25),
                new SimpleStringProperty("Control"),
                new SimpleObjectProperty(LocalDate.of(1993, 9, 1))));
        people.getItems().add(new Person(new SimpleIntegerProperty(25),
                new SimpleStringProperty("Mudison"),
                new SimpleObjectProperty(LocalDate.of(1999, 3, 11))));
        pane.add(new Label("ChoiceBox"), 0, 3);
        people.getSelectionModel().selectFirst();
        pane.add(people, 1, 3);

        Image icon = new Image("resources/images/icon.jpg");
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(200);
        iconView.setFitWidth(200);
        pane.add(iconView, 1, 4);
        Button clear = new Button("Clear Clipboard");
        clear.setOnAction(event -> Clipboard.getSystemClipboard().clear());
        pane.add(clear, 1, 5);

        Label label = new Label("API_KEY: Gtyc765HFHfvdfhmb7t57567yun");
        Button copy = new Button("Copy");
        copy.setOnAction(event -> copyKey(label));

        pane.addRow(6, label, copy);

        status = new Label();
        updateLabel();
        //status.textProperty().bind(value.concat();
        pane.add(status, 1, 20);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Buttons");
        primaryStage.show();
    }

    private void copyKey(Label label) {
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(label.getText());
        Clipboard.getSystemClipboard().setContent(clipboardContent);
    }

    private void updateLabel() {

        Thread thread = new Thread(()->{
            while (true){
                Platform.runLater(()->{
                    if(Clipboard.getSystemClipboard() != null){
                        status.textProperty().bind(new SimpleStringProperty("Clipboard: ")
                                .concat(Clipboard.getSystemClipboard().getString()));
                    }
                });
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    Platform.runLater(()-> status.setText(e.toString()));
                }
            }
        });

        thread.start();

    }

    private void load(String url) {

        webView.getEngine().load(url);

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
