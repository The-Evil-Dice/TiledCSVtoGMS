# TiledCSVtoGMS

Just a simple command line java tool with arguments to convert tile layers output to csv by Tiled into Gamemaker Studio 1.4's Isometric grid.

[Download](../blob/master/TiledCSVtoGMS.jar)

## Arguments:
- TileWidth
- TileHeight
- TilesetColumns
- TilesetRowHeight
- HeightOffset
- InputFile

## Notes
I haven't tested with multiple tilesets in tiled, but if multiple do work, their width will need to be dividable by the TilesetColumns argument.
The height offset is just because I cannot get that information from Tiled itself, it doesn't output that into the CSV.

I desperately needed this tool and nothing else seemed to work, so I just made it for myself, it'll never have a UI, but it really isn't difficult to use, I'll give an example below.

Make sure you have the file you want to convert in the same folder as the jar, for simplicity's sake. For example, if your isometric grid was 80x40, and your general largest tile height was 80x70, and your tileset had rows of 13 textures, and you had exported your layer to Layer2.csv and in Tiled it was 31 pixels higher than your Layer1...

Open CMD, use CD commands until you locate the folder with the jar, then use

***java -jar TiledCSVToGMSTileList.jar 80 40 13 70 31 Layer2.csv***

That'll make an output.txt file containing all the tiles in that layer for you to simply paste into your room.gmx
