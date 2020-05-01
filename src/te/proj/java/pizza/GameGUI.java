package te.proj.java.pizza;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class GameGUI extends Application {

    /**
     * Screen Width.
     */
    public static final int WIDTH = 1200;
    /**
     * Screen height.
     */
    public static final int HEIGTH = 800;
    /**
     * Padding.
     */
    public static final int PADDING = 20;

    /**
     * Size for second screen.
     */
    public static final double DECREASE = 0.8;

    /**
     * Small java image.
     */
    public static final int SMALL = 300;
    public static final int SMALL2 = 600;

    /**
     * Normal java image.
     */
    public static final int NORM = 375;
    public static final int NORM2 = 675;

    /**
     * Constant 10.
     */
    public static final int TEN = 10;
    /**
     * Constant 30.
     */
    public static final int THIRTY = 30;

    /**
     * Constant 100.
     */
    public static final int HUNDRED = 100;
    /**
     * Constant 50.
     */
    public static final int FIFTY = 50;
    /**
     * Size 580.
     */
    public static final int SIZE580 = 580;
    /**
     * Size 380.
     */
    public static final int SIZE380 = 380;
    /**
     * Size 760.
     */
    public static final int SIZE760 = 760;
    /**
     * Starts the application.
     * @param args Javafx nodes.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ClickerGame game = new ClickerGame();
        primaryStage.setTitle("Pizza Clicker");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        StackPane backPanel = new StackPane();
        backPanel.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        backPanel.setStyle("-fx-background-color: transparent");
        HBox split = new HBox();
        split.setAlignment(Pos.CENTER);
        split.setStyle("-fx-background-color: rgba(1,1,1,0);");

        Button exit = exit(primaryStage);

        Label pizza = new Label("0" + "\n" + "PIZZAS");
        pizza.setStyle("-fx-font-size: 50 px;" + "-fx-font-weight: bold;"
                + "-fx-background-color: rgba(1,1,1,0);" + "-fx-text-fill: ghostwhite;"
                + "-fx-font-family: Comic Sans;" + "-fx-text-alignment: center;");

        javafx.scene.control.Button buyCursor = new Button("Buy Cursor");
        mouseEffects(buyCursor);

        javafx.scene.control.Button buyClicker = new Button("Buy Clicker");
        mouseEffects(buyClicker);

        Image image = background();
        ImageView imageView2 = javaClick(game, buyClicker, buyCursor, pizza);
        ImagePattern pattern = new ImagePattern(image);

        StackPane leftCanvas = new StackPane();
        leftCanvas.setPrefSize(SIZE760, SIZE580);
        leftCanvas.setStyle("-fx-background-color: rgba(1,1,1,0);");

        leftCanvas.getChildren().addAll(imageView2);
        leftCanvas.setAlignment(Pos.CENTER);

        VBox leftSplit = new VBox();
        leftSplit.setAlignment(Pos.CENTER);
        leftSplit.getChildren().addAll(pizza, leftCanvas);
        VBox rightSplit = new VBox();
        rightSplit.setAlignment(Pos.CENTER);

        VBox up = new VBox();
        up.setStyle("-fx-background-color: rgba(192,192,192,0.3);");
        up.setPrefSize(SIZE580, SIZE380);
        up.setPadding(new Insets(THIRTY, TEN, THIRTY, TEN));
        up.setAlignment(Pos.CENTER_RIGHT);

        Label price = new Label("Price:");
        price.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;");

        Label cursorInf = new Label(String.format(
                "%8s\t%4d\t\t%5d", "Cursors:", game.getCursorCount(), game.getCursorPrice()));
        cursorInf.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;"
                + "-fx-pref-width: 580;");

        Label clickerInf = new Label(String.format(
                "%8s\t%4d\t\t%5d", "Clickers:", game.getClickerCount(), game.getClickerPrice()));
        clickerInf.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: rgba(33,33,33,0.91);" + "-fx-font-family: Impact;"
                + "-fx-pref-width: 580;");

        up.getChildren().addAll(price, cursorInf, clickerInf);

        VBox down = new VBox();
        down.setStyle("-fx-background-color: rgba(192,192,192,0.3);");
        down.setPadding(new Insets(THIRTY, TEN, THIRTY, TEN));
        down.setSpacing(TEN);
        down.setPrefSize(SIZE580, SIZE380);
        down.setAlignment(Pos.TOP_CENTER);

        Timeline clickerAction = clickerAction(game, buyClicker, buyCursor, pizza, imageView2);

        buyCursor.setOnMouseClicked(event -> {
            game.buyCursor();
            pizza.setText(game.getpizza() + "\n" + "PIZZAS");
            cursorInf.setText(String.format(
                    "%8s\t%4d\t\t%5d", "Cursors:", game.getCursorCount(), game.getCursorPrice()));
            if (!game.canBuyCursor()) {
                buyCursor.setVisible(false);
            }
            if (!game.canBuyClicker()) {
                buyClicker.setVisible(false);
            }
        });

        buyClicker.setOnMouseClicked(event -> {
            game.buyClicker();
            clickerAction.stop();
            clickerAction.setRate((1.0 / game.getClickerInterval()));
            clickerAction.play();
            pizza.setText(game.getpizza() + "\n" + "PIZZAS");
            clickerInf.setText(String.format(
                    "%8s\t%4d\t\t%5d", "Clickers:", game.getClickerCount(), game.getClickerPrice()));
            if (!game.canBuyClicker()) {
                buyClicker.setVisible(false);
            }
            if (!game.canBuyCursor()) {
                buyCursor.setVisible(false);
            }
        });

        Button pause = pause(pattern);
        Button menu = menu(pattern);

        down.getChildren().addAll(buyCursor, buyClicker);

        rightSplit.getChildren().addAll(up, down);
        split.getChildren().addAll(leftSplit, rightSplit);
        backPanel.getChildren().addAll(split, exit, pause, menu);

        primaryStage.setScene(new Scene(backPanel, WIDTH, HEIGTH, pattern));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Mouse hovering button changes.
     * @param button button that will change.
     */
    private void mouseEffects(Button button) {
        button.managedProperty().bind(button.visibleProperty());
        button.setVisible(false);
        button.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                + "-fx-font-size: 30 px;" + "-fx-text-fill: ghostwhite;"
                + "-fx-font-family: Comic Sans;" + "-fx-font-weight: bold;");
        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: red;"
                    + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                    + "-fx-font-size: 30 px;" + "-fx-text-fill: ghostwhite;"
                    + "-fx-font-family: Comic Sans;" + "-fx-font-weight: bold;");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 10;" + "-fx-pref-width: 580;"
                    + "-fx-font-size: 30 px;" + "-fx-text-fill: ghostwhite;"
                    + "-fx-font-family: Comic Sans;" + "-fx-font-weight: bold;");
        });
    }

    /**
     * Creates a background image.
     * @return image.
     */
    private Image background() {
        return image("te/proj/java/pizza/black.jpg");
    }

  
    private ImageView javaClick(ClickerGame game, Button buyClicker, Button buyCursor, Label pizza) {
        Image image2 = image("te/proj/java/pizza/pizza_icon.png");
        ImageView imageView2 = new ImageView(image2);

        imageView2.setFitHeight(NORM);
        imageView2.setFitWidth(NORM2);
        imageView2.setOnMouseClicked(event -> {
            game.click();
            if (game.canBuyClicker()) {
                buyClicker.setVisible(true);
            }
            if (game.canBuyCursor()) {
                buyCursor.setVisible(true);
            }
            pizza.setText(game.getpizza() + "\n" + "PIZZAS");
            Timeline timeline2 = new Timeline(new KeyFrame(
                    Duration.millis(HUNDRED),
                    ae -> {
                        imageView2.setFitHeight(NORM);
                        imageView2.setFitWidth(NORM2);
                    }));
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(FIFTY),
                    ae -> {
                        imageView2.setFitHeight(SMALL);
                        imageView2.setFitWidth(SMALL2);
                    }));
            timeline.play();
            timeline2.play();
        });
        return imageView2;
    }

    /**
     * Creates an image object.
     *
     * @param pathname location of the image.
     * @return image object
     */
    private Image image(String pathname) {
        javafx.scene.image.Image image = new Image(getClass().getClassLoader().getResource(pathname).toString());
        return image;
    }

    private Timeline clickerAction(
            ClickerGame game, Button buyClicker, Button buyCursor, Label pizza, ImageView imageView2) {
        Timeline clickerAction = new Timeline(new KeyFrame(
                Duration.millis(1),
                ae -> {
                    if (game.getClickerCount() != 0) {
                        game.clickerAction();
                        if (game.canBuyClicker()) {
                            buyClicker.setVisible(true);
                        }
                        if (game.canBuyCursor()) {
                            buyCursor.setVisible(true);
                        }
                        pizza.setText(game.getpizza() + "\n" + "PIZZAS");

                        Timeline timeline2 = new Timeline(new KeyFrame(
                                Duration.millis(HUNDRED),
                                e -> {
                                    imageView2.setFitHeight(NORM);
                                    imageView2.setFitWidth(NORM2);
                                }));
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.millis(FIFTY),
                                e -> {
                                    imageView2.setFitHeight(SMALL);
                                    imageView2.setFitWidth(SMALL2);
                                }));
                        timeline.play();
                        timeline2.play();
                    }
                }));
        clickerAction.setCycleCount(Timeline.INDEFINITE);
        clickerAction.play();
        return clickerAction;
    }

    /**
     * Creates a new window.
     * @param pattern background
     */
    private void pauseStage(ImagePattern pattern) {
        Stage pausestage = new Stage();
        pausestage.initStyle(StageStyle.UNDECORATED);
        StackPane pausescreen = new StackPane();
        pausescreen.setStyle("-fx-background-color: rgba(33,33,33,0.77)");
        pausescreen.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        pausestage.setTitle("PAUSE");
        pausestage.setResizable(false);

        Label pauselabel = new Label("PAUSE");
        pauselabel.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: ghostwhite;" + "-fx-font-family: Comic Sans;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(pauselabel, Pos.CENTER);

        Button close = new Button("CLOSE");
        close.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: white;"
                + "-fx-font-family: Comic Sans;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        close.setOnMouseEntered(eventclose -> {
            close.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: red;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: white;" + "-fx-font-family: Comic Sans;");
        });
        close.setOnMouseExited(eventclose -> {
            close.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: white;" + "-fx-font-family: Comic Sans;");
        });
        close.setOnMouseClicked(eventclose -> pausestage.close());

        pausescreen.getChildren().addAll(close, pauselabel);
        pausestage.setScene(new Scene(pausescreen, WIDTH * DECREASE, HEIGTH * DECREASE, pattern));
        pausestage.show();
    }

    private void menuStage(ImagePattern pattern) {
        Stage menustage = new Stage();
        menustage.initStyle(StageStyle.UNDECORATED);
        StackPane menuscreen = new StackPane();
        menuscreen.setStyle("-fx-background-color: rgba(33,33,33,0.77)");
        menuscreen.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        menustage.setTitle("MENU");
        menustage.setResizable(false);

        Label menulabel = new Label("PIZZA CLICKER - CS 3443");
        menulabel.setStyle("-fx-font-size: 45 px;" + "-fx-background-color: rgba(1,1,1,0);"
                + "-fx-text-fill: ghostwhite;" + "-fx-font-family: Comic Sans;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(menulabel, Pos.CENTER);
       
        Button close = new Button("CLOSE");
        close.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: white;"
                + "-fx-font-family: Comic Sans;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(close, Pos.BOTTOM_LEFT);
        close.setOnMouseEntered(eventclose -> {
            close.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: red;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: white;" + "-fx-font-family: Comic Sans;");
        });
        close.setOnMouseExited(eventclose -> {
            close.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: white;" + "-fx-font-family: Comic Sans;");
        });
        close.setOnMouseClicked(eventclose -> menustage.close());

        menuscreen.getChildren().addAll(close, menulabel);
        menustage.setScene(new Scene(menuscreen, WIDTH * DECREASE, HEIGTH * DECREASE, pattern));
        menustage.show();
    }    
    
    
    /**
     * Creates a button that closes the application.
     * @param primaryStage application primary stage.
     * @return close button.
     */
    private Button exit(Stage primaryStage) {
        Button exit = new Button("EXIT");
        exit.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;"
                + "-fx-font-family: Comic Sans;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(exit, Pos.TOP_LEFT);
        exit.setOnMouseEntered(event -> {
            exit.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: red;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;" + "-fx-font-family: Comic Sans;");
        });
        exit.setOnMouseExited(event -> {
            exit.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;" + "-fx-font-family: Comic Sans;");
        });
        exit.setOnMouseClicked(event -> primaryStage.close());
        return exit;
    }

    /**
     * Button that opens a new window.
     * @param pattern background
     * @return pause button.
     */
    private Button pause(ImagePattern pattern) {
        Button pause = new Button("PAUSE");
        pause.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;"
                + "-fx-font-family: Comic Sans;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(pause, Pos.BOTTOM_LEFT);
       pause.setOnMouseEntered(event -> {
            pause.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: red;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;" + "-fx-font-family: Comic Sans;");
        });
        pause.setOnMouseExited(event -> {
            pause.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;" + "-fx-font-family: Comic Sans;");
        });
        pause.setOnMouseClicked(event -> pauseStage(pattern));
        return pause;
    }
    private Button menu(ImagePattern pattern) {
        Button menu = new Button("MENU");
        menu.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;"
                + "-fx-font-family: Comic Sans;" + "-fx-text-alignment: center;");
        StackPane.setAlignment(menu, Pos.BOTTOM_CENTER);
       menu.setOnMouseEntered(event -> {
            menu.setStyle("-fx-background-color: rgba(33,33,33,1);" + "-fx-border-color: red;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;" + "-fx-font-family: Comic Sans;");
        });
        menu.setOnMouseExited(event -> {
            menu.setStyle("-fx-background-color: rgba(33,33,33,0.91);" + "-fx-border-color: ghostwhite;"
                    + "-fx-border-width: 5;" + "-fx-pref-width: 150;"
                    + "-fx-font-size: 25 px;" + "-fx-text-fill: ghostwhite;" + "-fx-font-family: Comic Sans;");
        });
        menu.setOnMouseClicked(event -> menuStage(pattern));
        return menu;
    }
}