# Isometric TMX To GMX

[Download](https://drive.google.com/file/d/0B_bRpsgUligWVHV6N1lIRG81clU/view?usp=sharing)

## Info:
Converts the layers in a TMX Isometric Staggered Y file into usable tiles for Gamemaker, currently it outputs XML entries into an output.txt file that can be pasted into your .room.gmx file.
It has two options that you can set as custom layer variables in Tiled, two booleans called RandomisedHeight and DepthCorrection, which slightly raise and lower the layer's tiles randomly to look more natural, and assign the layer's tiles a depth of -(y * (tileHeight)/2) to fix depth before runtime, respectively.

RandomisedHeight: | Effect:
--- | ---
![alt text](http://i.imgur.com/F5jAGJd.png?2) | ![alt text](http://i.imgur.com/c8ZeuaG.png?1)  
  
DepthCorrection: | Effect:
--- | ---
![alt text](http://i.imgur.com/QY888KY.png?1) | ![alt text](http://i.imgur.com/otKJkmP.png?1)![alt text](http://i.imgur.com/a98a5R0.png?1)
