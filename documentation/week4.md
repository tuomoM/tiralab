# Weekly progress report W4

*****************
The walk through the valley of tears and darkness continued with random blinkers of light. I firstly managed to get some sort of compression working, but it did mangle the data badly. I got some words ok, but most as gibberish or badly deformed.
During the week i changed the approach to save the huffman tree instead of occurences. This seemed straight forward but turned out to be absolute horror as the writing and reading to file had to be redesigned and rewritten.

The current version is unable to read the compressed data. This could be due to reading or perhaps the writing of the data goes terribly wrong in the first place. I dont know how to test this using automatade test, neither are my debugging skills in a shape required by the task. This means i have riddled the code with * System.out.println * commands to ensure i get some feedback. This is has so far yealded zero progress.
I assume it will take some 20 more hours to get this debugged and working. 

I did write some javadoc comments to be in line with the requirements, but certainly that is stil falling short. Additionally i do know know how one could test the file interfacing using Junit tests. 

This week i spent about 30 hours working with the project. Most if not all of those hours were spent with the cursed file reading and writing.

Testing overall has not progressed much as the problems faced with IO have caused me to concentrate on those buring issues.
********
During next week i am hoping to crawl up from this hole and finally get to LZ compression. I am also hoping that the obstacles i am hoping to overtake are going to help me in LZ part aswell. 
