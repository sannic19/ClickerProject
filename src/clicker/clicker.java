package clicker;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

import javafx.geometry.Point2D;

// Keyboard events
public class clicker extends Application 
{
    public static void main(String[] args) 
    {
        launch(args);
    }
    public class Circle
    {
        private Point2D center;
        private double  radius;

        public Circle(double x, double y, double r)
        {
            setCenter(x,y);
            setRadius(r);
        }
        
        public void setCenter(double x, double y)
        {
            center = new Point2D(x,y);
        }
        
        public void setRadius(double r)
        {  
            radius = r;  
        }
        
        public double getX()
        {
            return center.getX();
        }
        
        public double getY()
        {
            return center.getY();
        }
        
        public double getRadius()
        {
            return radius;
        }
        
        public boolean containsPoint(double x, double y)
        {
            return (center.distance(x,y) < radius);
        }
    }
    
    public class IntValue
    {
        public int value;
        
        public IntValue(int i)
        {
            value = i;
        }
    }

    @Override
    public void start(Stage theStage) 
    {
        theStage.setTitle( "Click Me!" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 500, 500 );

        root.getChildren().add( canvas );
        
        Circle targetData = new Circle(280,280,150);
        IntValue points = new IntValue(0);

        theScene.setOnMouseClicked(
            new EventHandler<MouseEvent>()
            {
            	GraphicsContext gc = canvas.getGraphicsContext2D();

                public void handle(MouseEvent e)
                {
                    if ( targetData.containsPoint( e.getX(), e.getY() ) )
                    {


           
                            points.value++;
		                    int  x=280;
		                    int  y=280;
		                    targetData.setCenter(x, y);
                       
 
                    
                    }
                    }  
                }
            );
            
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);
        Image bullseye = new Image( "bullseye.png", 250, 250, false, false );

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // Clear the canvas
                gc.setFill( new Color(0.85, 0.85, 1.0, 1.0) );
                gc.fillRect(0,0, 512,512);

                gc.drawImage( bullseye, 
                    targetData.getX() - targetData.getRadius(),
                    targetData.getY() - targetData.getRadius() );

                gc.setFill( Color.BLUE );

                String pointsText = "Clicks: " + points.value;
                gc.fillText( pointsText, 360, 36 );
                gc.strokeText( pointsText, 360, 36 );
            }
        }.start();


        theStage.show();
    }
}