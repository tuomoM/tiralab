# User manual for compression

The compression program here is built for education purposes.

## Installation

Either download the released zip file, or clone the repository and build the program using gradle.
If built the relevat zip file can be found under compression-> build -> distributions 

Extract the zip file.
## Use

In the extracted compression folder go to folder bin.
Copy the files you want to compress or extrac to that folder.

the compression can be run using following syntax using terminal in the bin folder:

' ./compression -hf -c fileToBeCompressed.txt '

Would compress the file using huffman compression. A new file named ' fileToBeCompressed.hf ' would be created.

Similarily if you wish to compress the same file using LZW the command would be:

' ./compression -lz -c fileToBeCompressed.txt '
And the system would name the new file as  ' fileToBeCompressed.lz '

To exract you need to change the second parameter from ' -c ' to ' -e '.
Note that system does not check the file extension or the file. Giving huffman extraction a LZW compressed file will result in short dump.

The system will automatically name the files with just the extension * hf/lz * for compressed files and * .txt * for extracted files.
It is possible to change that on both occasions, compression and extraction using a fourth flag. For example
' ./compression -lz -e fileToBeCompressed.lz extracted.myOwnfile '
the system would use LZW algorithm to extract the file and the resulting file would be named:  ' fileToBeCompressedextracted.myOwnfile '

Additionally you can get the help from the program using flag ' --help ' for example
'./compression --help' would discplay the help 
