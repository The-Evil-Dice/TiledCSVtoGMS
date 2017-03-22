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

Example:
```
<tile bgName="Tileset" x="0" y="0" w="80" h="70" xo="0" yo="0" id="1" depth="0" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="80" y="0" w="80" h="70" xo="0" yo="70" id="2" depth="0" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="40" y="20" w="80" h="70" xo="0" yo="0" id="3" depth="-1" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="120" y="20" w="80" h="70" xo="0" yo="70" id="4" depth="-1" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="0" y="40" w="80" h="70" xo="0" yo="0" id="5" depth="-2" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="80" y="40" w="80" h="70" xo="0" yo="70" id="6" depth="-2" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="40" y="60" w="80" h="70" xo="0" yo="0" id="7" depth="-3" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="120" y="60" w="80" h="70" xo="0" yo="70" id="8" depth="-3" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="0" y="80" w="80" h="70" xo="0" yo="0" id="9" depth="-4" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="80" y="80" w="80" h="70" xo="0" yo="70" id="10" depth="-4" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="40" y="100" w="80" h="70" xo="0" yo="0" id="11" depth="-5" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="120" y="100" w="80" h="70" xo="0" yo="70" id="12" depth="-5" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="0" y="120" w="80" h="70" xo="0" yo="0" id="13" depth="-6" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="80" y="120" w="80" h="70" xo="0" yo="70" id="14" depth="-6" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="40" y="140" w="80" h="70" xo="0" yo="0" id="15" depth="-7" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="120" y="140" w="80" h="70" xo="0" yo="70" id="16" depth="-7" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="0" y="160" w="80" h="70" xo="0" yo="0" id="17" depth="-8" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="80" y="160" w="80" h="70" xo="0" yo="70" id="18" depth="-8" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="40" y="180" w="80" h="70" xo="0" yo="0" id="19" depth="-9" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="120" y="180" w="80" h="70" xo="960" yo="0" id="20" depth="-9" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="0" y="200" w="80" h="70" xo="0" yo="0" id="21" depth="-10" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="80" y="200" w="80" h="70" xo="0" yo="70" id="22" depth="-10" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="40" y="220" w="80" h="70" xo="0" yo="0" id="23" depth="-11" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="120" y="220" w="80" h="70" xo="320" yo="140" id="24" depth="-11" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="0" y="240" w="80" h="70" xo="0" yo="0" id="25" depth="-12" scaleX="1" scaleY="1" />
<tile bgName="Tileset" x="80" y="240" w="80" h="70" xo="0" yo="70" id="26" depth="-12" scaleX="1" scaleY="1" />
```  
    
    Also, I know the code is incredibly sloppy, and no I have no intentions of cleaning it up...
