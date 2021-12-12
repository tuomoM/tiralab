# Implementation document
-----
##Components:
### Main program
Main program has just the user interface. It provides the possiblity to compress and extract using both Huffman and LZW methods.
The program can be run with this format:
 ./gradlew run --args="-lz -c chapters.txt" *
here there are 3 arguments for the code.
*1st arguments can be -lz for LZW or -hf for Huffman.
*2nd argument is either -c for compress or -e for extract
*3rd argument is the file name you want to compress or extract.
there is additional 4th argument that you can use to specify the target file extension. default in compression is lz for LZW and hf for Huffman and .txt for extracted file.

### fileservice
This class provides the tools for writing and reading bits from and to disk. It has internal buffering using one byte. The main methods are write/read boolean (bit) and write / read byte.

### Huffman 
This class uses the fileservice to write and read. the functions here allow compression and decompression of files using huffman.

### LZ
this incorrectly named class works has two methods one for compression and one for extraction. This is rather simple class

### TST2
this is my second attempt of creating the Ternary Search Tree. This is the hearth of the LZW compression.

## Functions and the relevant performance figures
### Huffman
### compression
Huffman coding utilizes the java priorityQueue in my implementation and that runs in **O(n log n)** time. The keys are stored once and there is no repetition, thus the space requirement is n, where n is the string lenght.
### extraction
Huffman encoding traverses the tree for each character in the string, this has time requirement of **O(log n)** for each character, thus the time requirement is **O(n log n)** for the extraction.

### LZW
#### compression
Compression uses the Ternary Search Trie as its core and that has worst case time requirement of **O(n+k)** and average time requirement of **O(n log n)**. Im assuming i get about average performance :D... thus i expect the compression to have  **O(n log n)** time requirement. Since the TST is very efficient space wise, i assume the space requirement ot be close to **O(n)**
#### extraction
Extraction is significantly simpler as the indexing of the keys is always predictable (LZW is cool), the extraction uses up to 4095 key words that can be accessed in time of **O(n)**. Thus the time requirement of the extraction should be **O(n)**


