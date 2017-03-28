package com.roll6.TiledCSVtoGMS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

        window.setTitle("TMX(Iso Offset Y) To GMX");
        //window.setWidth(400);
        window.setResizable(false);

        Image icon = new Image(Main.class.getResourceAsStream("/images/favicon.png"));
        window.getIcons().add(icon);

        TextField fileString = new TextField("untitled.tmx");
        grid.add(fileString, 0, 0, 1, 1);
        GridPane.setMargin(fileString, new Insets(10, 0, 0, 10));
        Button button = new Button("Convert");
        button.setOnAction(e -> {
            try {
                run(fileString.getText());
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        grid.add(button, 2, 0, 1, 1);
        GridPane.setMargin(button, new Insets(10, 0, 0, 100));
        GridPane.setFillWidth(button, true);
        GridPane.setFillHeight(button, true);

        Scene scene = new Scene(grid);

        window.setScene(scene);

        this.window = window;
        this.window.show();
    }

    public void run(String file) throws IOException, ParserConfigurationException, SAXException {
        int tileWidth;
        int tileHeight;
        String[] tilesetNames;
        int tilesetColumns;
        int tilesetRowHeight;
        File fXmlFile = new File(file);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        Element root = doc.getDocumentElement();
        tileWidth = Integer.parseInt(root.getAttribute("tilewidth"));
        tileHeight = Integer.parseInt(root.getAttribute("tileheight"));
        NodeList tilesetsN = doc.getElementsByTagName("tileset");
        List<Tileset> tilesets = new ArrayList<>();
        for (int i = 1; i <= tilesetsN.getLength(); i++) {
            Element e = (Element) tilesetsN.item(i - 1);
            Tileset t = new Tileset(e.getAttribute("name"), Integer.parseInt(e.getAttribute("tilewidth")),
                    Integer.parseInt(e.getAttribute("tileheight")), Integer.parseInt(e.getAttribute("tilecount")),
                    Integer.parseInt(e.getAttribute("columns")));
            tilesets.add(t);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"));

        NodeList layers = root.getElementsByTagName("layer");
        int id = 1;
        for (int i = 1; i <= layers.getLength(); i++) {
            boolean rHeightValue = false;
            StringBuilder output = new StringBuilder();

            //System.out.println(layer);
            Element layer = (Element) layers.item(i - 1);
            Node data = layer.getElementsByTagName("data").item(0);
            Element properties = (Element) layer.getElementsByTagName("properties").item(0);
            Element rHeight = null;
            if(properties != null) {
                rHeight = (Element) properties.getElementsByTagName("property").item(0);
            }
            if (rHeight != null && rHeight.getAttribute("name").toLowerCase().equals("randomisedheight")) {
                rHeightValue = Boolean.parseBoolean(rHeight.getAttribute("value"));
            }

            int heightOffset = 0;
            if (layer.getAttribute("offsety") != "") {
                heightOffset = Integer.parseInt(layer.getAttribute("offsety"));
            }

            List<String> rows = new ArrayList();
            for (String s : data.getTextContent().split("\\r?\\n")) {
                rows.add(s);
            }

            boolean toggle = false;
            for (int y = 0; y < rows.size(); y++) {
                String row = (String) rows.get(y);
                List<Integer> column = new ArrayList();
                int x = 0;
                for (String contents : row.split(",")) {
                    if (contents.equals("")) {
                        continue;
                    }

                    column.add(Integer.valueOf(Integer.parseInt(contents)));
                    Tileset ts = null;
                    for (Tileset ti : tilesets) {
                        if (ts == null || (ti.tilecount > ts.tilecount && ti.tilecount < Integer.parseInt(contents))) {
                            ts = ti;
                        }
                    }
                    int depth = 1000000 + heightOffset;
                    if (Integer.parseInt(contents) != 0) {
                        int yPos = (y * (tileHeight / 2) + heightOffset);
                        if(rHeightValue) {
                            Random r = new Random();
                            yPos += ((r.nextDouble()*8)-16);
                        }
                        if (toggle) {
                            output.append("    <tile bgName=\"" + ts.name + "\" x=\"" + (x * tileWidth + tileWidth / 2) + "\" y=\"" + yPos + "\"");
                        } else {
                            output.append("    <tile bgName=\"" + ts.name + "\" x=\"" + x * tileWidth + "\" y=\"" + yPos + "\"");
                        }
                        output.append(" w=\"" + ts.tilewidth + "\" h=\"" + ts.tileheight + "\" ");
                        int yOffset = (Integer.parseInt(contents) - 1) / ts.columns * ts.tileheight;
                        int xOffset = ((Integer.parseInt(contents) - 1) - ts.columns * (yOffset / ts.tileheight)) * ts.tilewidth;
                        output.append("xo=\"" + xOffset + "\" yo=\"" + yOffset + "\" id=\"" + id + "\" depth=\""+ depth +"\" scaleX=\"1\" scaleY=\"1\" />\r\n");
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
            writer.write(output.toString());
        }
        writer.close();
    }

    class Tileset {

        String name;
        int tilewidth;
        int tileheight;
        int tilecount;
        int columns;

        Tileset(String name, int tilewidth, int tileheight, int tilecount, int columns) {
            this.name = name;
            this.columns = columns;
            this.tilecount = tilecount;
            this.tileheight = tileheight;
            this.tilewidth = tilewidth;
        }
    }
}
