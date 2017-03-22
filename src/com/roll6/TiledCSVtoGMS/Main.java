package com.roll6.TiledCSVtoGMS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main
{
  public static void main(String[] args)
    throws FileNotFoundException, IOException
  {
    int tileWidth = Integer.parseInt(args[0]);
    int tileHeight = Integer.parseInt(args[1]);
    int tilesetColumns = Integer.parseInt(args[2]);
    int tilesetRowHeight = Integer.parseInt(args[3]);
    String fileString = args[5];
    int heightOffset = Integer.parseInt(args[4]);
    File file = new File(fileString);
    InputStream is = new FileInputStream(file);
    BufferedReader buf = new BufferedReader(new InputStreamReader(is));
    String line = buf.readLine();
    StringBuilder sb = new StringBuilder();
    StringBuilder output = new StringBuilder();
    while (line != null)
    {
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
    for (int y = 0; y < rows.size(); y++)
    {
      String row = (String)rows.get(y);
      List<Integer> column = new ArrayList();
      int x = 0;
      for (String contents : row.split(","))
      {
        column.add(Integer.valueOf(Integer.parseInt(contents)));
        if (Integer.parseInt(contents) != -1)
        {
          if (toggle)
          {
            output.append("    <tile bgName=\"Tileset\" x=\"" + (x * tileWidth + tileWidth / 2) + "\" y=\"" + (y * (tileHeight / 2) + heightOffset) + "\"");
            System.out.print("x=\"" + (x * tileWidth + tileWidth / 2) + "\" y=\"" + y * (tileHeight / 2) + "\"\n");
          }
          else
          {
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
