import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DragAndDropImage extends Application{

    private ImageView originalView;
    private ImageView copyImage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane pane = getUI();

        addDagNDropHandlers();

        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Drag n Drop Image");
        primaryStage.show();
    }

    private void addDagNDropHandlers() {
        originalView.setOnDragDetected(this::dragDetected);
        copyImage.setOnDragOver(this::dragOver);
        copyImage.setOnDragDropped(this::dragDropped);
        originalView.setOnDragDone(this::dragDone);
    }

    private void dragDetected(MouseEvent event){

        Dragboard dragboard = originalView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        ClipboardContent content = new ClipboardContent();
        content.putImage(originalView.getImage());
        dragboard.setContent(content);

        event.consume();

    }

    private void dragOver(DragEvent event){

        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasImage() || dragboard.hasUrl() || dragboard.hasFiles()){
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();

    }

    private void dragDropped(DragEvent event){
        Dragboard dragboard = event.getDragboard();

        if(dragboard.hasImage()){
            copyImage.setImage(dragboard.getImage());
            event.setDropCompleted(true);
        }
        else if(dragboard.hasUrl()){
            copyImage.setImage(new Image(dragboard.getUrl()));
            event.setDropCompleted(true);
        }
        else if(dragboard.hasFiles()){
            String mimeType = "";
            for(File file : dragboard.getFiles()){
                try {
                    mimeType = Files.probeContentType(file.toPath());
                    if(mimeType != null && mimeType.startsWith("image/")){
                        copyImage.setImage(new Image(file.toURI().toURL().toExternalForm()));
                    }
                }catch (IOException e){
                    System.out.println(e);
                    event.setDropCompleted(false);
                }
            }
        }
        else {
            event.setDropCompleted(false);
            event.consume();
        }
    }

    private void dragDone(DragEvent event){
        if(event.getTransferMode() == TransferMode.MOVE){
            originalView.setImage(null);
        }
        event.consume();
    }

    public GridPane getUI() {

        GridPane ui = new GridPane();
        ui.setAlignment(Pos.CENTER);

        Label title = new Label("Drag And Drop Image Here");

        originalView = new ImageView(new Image("resources/images/icon.jpg"));
        originalView.setFitWidth(100);
        originalView.setFitHeight(100);

        copyImage = new ImageView(new Image("resources/images/test.jpg"));
        copyImage.setFitHeight(100);
        copyImage.setFitWidth(100);
        Label arrow = new Label(" -> ");
        Label originalCaption = new Label("Original");
        originalCaption.setAlignment(Pos.CENTER);
        Label copyCaption = new Label("Copy");
        Button clear = new Button("Reset");
        clear.setOnAction(event -> {
            copyImage.setImage(new Image("resources/images/test.jpg"));
            originalView.setImage(new Image("resources/images/icon.jpg"));
        });

        HBox imagesSection = new HBox(5);
        imagesSection.setAlignment(Pos.CENTER);
        VBox leftRegion = new VBox(3, originalView, originalCaption);
        leftRegion.setAlignment(Pos.CENTER);
        VBox rightRegion = new VBox(3, copyImage, copyCaption);
        rightRegion.setAlignment(Pos.CENTER);
        imagesSection.getChildren().addAll(leftRegion, arrow, rightRegion);

        ui.addRow(0, title);
        GridPane.setConstraints(title, 0, 0, 3, 1);
        ui.add(imagesSection, 0, 1, 3, 1);
        ui.add(clear, 2, 3, 2, 1);
        GridPane.setHalignment(clear, HPos.RIGHT);

        return ui;
    }
}
