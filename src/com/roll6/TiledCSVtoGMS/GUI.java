package com.roll6.TiledCSVtoGMS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Josh
 */
public class GUI extends Application {
    
    public Stage window;
    
    public GUI() {
        
    }
    
    public void startGUI() {
        launch();
    }

    @Override
    public void start(Stage window) throws Exception {
        GridPane grid = new GridPane();
        
        window.setTitle("Tiled CSV To GMX Tile List");
        window.setResizable(false);
        
        Image icon = new Image(Main.class.getResourceAsStream("/images/favicon.png"));
        window.getIcons().add(icon);
        
        TextField tileWidth = new TextField("TileWidth");
        grid.add(tileWidth, 0, 0, 1, 1);
        GridPane.setMargin(tileWidth, new Insets(10,0,0,10));
        TextField tileHeight = new TextField("TileHeight");
        grid.add(tileHeight, 1, 0, 1, 1);
        GridPane.setMargin(tileHeight, new Insets(10,0,0,10));
        TextField tilesetName = new TextField("TilesetName");
        grid.add(tilesetName, 2, 0, 1, 1);
        GridPane.setMargin(tilesetName, new Insets(10,0,0,10));
        TextField tilesetColumns = new TextField("TilesetColumns");
        grid.add(tilesetColumns, 0, 1, 1, 1);
        GridPane.setMargin(tilesetColumns, new Insets(10,0,0,10));
        TextField tilesetRowHeight = new TextField("TilesetRowHeight");
        grid.add(tilesetRowHeight, 1, 1, 1, 1);
        GridPane.setMargin(tilesetRowHeight, new Insets(10,0,0,10));
        TextField heightOffset = new TextField("HeightOffset");
        grid.add(heightOffset, 2, 1, 1, 1);
        GridPane.setMargin(heightOffset, new Insets(10,0,0,10));
        TextField fileString = new TextField("FileToConvert");
        grid.add(fileString, 0, 2, 1, 1);
        GridPane.setMargin(fileString, new Insets(30,0,0,10));
        Button button = new Button("Convert");
        button.setOnAction(e -> {
            String[] args = new String[] {tileWidth.getText(), tileHeight.getText(), tilesetName.getText(),
            tilesetColumns.getText(), tilesetRowHeight.getText(), heightOffset.getText(), fileString.getText()};
            try {
                run(args);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.add(button, 2, 2, 1, 1);
        GridPane.setMargin(button, new Insets(30,0,0,100));
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);
        
        Scene scene = new Scene(grid);
        
        window.setScene(scene);
        
        this.window = window;
        this.window.show();
    }
    
    public void run(String[] args) throws IOException {
        int tileWidth = Integer.parseInt(args[0]);
        int tileHeight = Integer.parseInt(args[1]);
        String tilesetName = args[2];
        int tilesetColumns = Integer.parseInt(args[3]);
        int tilesetRowHeight = Integer.parseInt(args[4]);
        String fileString = args[6];
        int heightOffset = Integer.parseInt(args[5]);
        File file = new File(fileString);
        InputStream is = new FileInputStream(file);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        StringBuilder output = new StringBuilder();
        while (line != null) {
            sb.append(line).append("\r\n");
            line = buf.readLine();
        }
        String fileAsString = sb.toString();
        List<String> rows = new ArrayList();
        for (String s : fileAsString.split("\\r?\\n")) {
            rows.add(s);
        }
        System.out.println("Contents : ");
        boolean toggle = false;
        int id = 1;
        for (int y = 0; y < rows.size(); y++) {
            String row = (String) rows.get(y);
            List<Integer> column = new ArrayList();
            int x = 0;
            for (String contents : row.split(",")) {
                column.add(Integer.valueOf(Integer.parseInt(contents)));
                if (Integer.parseInt(contents) != -1) {
                    if (toggle) {
                        output.append("    <tile bgName=\"" + tilesetName + "\" x=\"" + (x * tileWidth + tileWidth / 2) + "\" y=\"" + (y * (tileHeight / 2) + heightOffset) + "\"");
                        System.out.print("x=\"" + (x * tileWidth + tileWidth / 2) + "\" y=\"" + y * (tileHeight / 2) + "\"\n");
                    } else {
                        output.append("    <tile bgName=\"Tileset\" x=\"" + x * tileWidth + "\" y=\"" + (y * (tileHeight / 2) + heightOffset) + "\"");
                        System.out.print("x=\"" + x * tileWidth + "\" y=\"" + y * (tileHeight / 2) + "\"\n");
                    }
                    output.append(" w=\"" + tileWidth + "\" h=\"" + tilesetRowHeight + "\" ");
                    int yOffset = Integer.parseInt(contents) / tilesetColumns * tilesetRowHeight;
                    int xOffset = (Integer.parseInt(contents) - tilesetColumns * (yOffset / tilesetRowHeight)) * tileWidth;
                    output.append("xo=\"" + xOffset + "\" yo=\"" + yOffset + "\" id=\"" + id + "\" depth=\"" + -y + "\" scaleX=\"1\" scaleY=\"1\" />\r\n");
                    System.out.println(yOffset + " " + xOffset + " " + contents);
                    id++;
                }
                x++;
            }
            if (toggle) {
                toggle = false;
            } else {
                toggle = true;
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"));
        writer.write(output.toString());
        writer.close();
    }
    
}
