import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DragAndDropText extends Application {

    private TextField sourceTxt;
    private TextField targetTxt;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        sourceTxt = new TextField("Text Here");
        targetTxt = new TextField("Drag Here");

        scene = new Scene(makeUI(), 200, 200);
        
        addDragAndDropListeners();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Drag And Drop Text");
        primaryStage.show();
    }

    private void addDragAndDropListeners() {

        sourceTxt.setOnDragDetected(this::dragDetected);
        sourceTxt.getScene().setOnDragDetected(this::dragDetected);
        targetTxt.setOnDragOver(this::dragOver);
        targetTxt.setOnDragDropped(this::dragDropped);
        sourceTxt.setOnDragDone(this::dragDone);

    }

    private void dragDetected(MouseEvent event){
        String text = "";
        if(event.getSource() instanceof TextField){
            text = sourceTxt.getText();
        }

        if(text == null || text.trim().equals("")){
            event.consume();
            return;
        }

        Dragboard dragboard = sourceTxt.startDragAndDrop(TransferMode.COPY_OR_MOVE);
        Image icon = ((Node) event.getSource()).snapshot(null, null);
        dragboard.setDragView(icon);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(text);
        dragboard.setContent(clipboardContent);

        event.consume();
    }

    private void dragOver(DragEvent event){

        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasString()){
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();

    }

    private void dragDropped(DragEvent event){

        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasString()){
            targetTxt.setText(dragboard.getString());
            event.setDropCompleted(true);
        }
        else{
            event.setDropCompleted(false);
        }

        event.consume();

    }

    private void dragDone(DragEvent event){
        if(event.getTransferMode() == TransferMode.MOVE){
            sourceTxt.setText("");
        }

        event.consume();
    }

    private Parent makeUI() {

        HBox top = new HBox(5, new Label("Source Text"), sourceTxt);
        HBox bottom = new HBox(5, new Label("Target Text"), targetTxt);
        Pane pane = new VBox(20, top, bottom);
        pane.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-insets: 5;" +
                "-fx-border-color: green;");

        return pane;

    }
}
