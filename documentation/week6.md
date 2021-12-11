# Weekly report for week 6
------
Seems that sun will raise after all and the terrors of the night will disappear into holes they came from.
This week i finally managed to get the fileservice class working and thus the Huffman coding started working.

The huffman is packing the chapters file im using to approximately half of the size of the origninal file.
The work on lzw is progressing very well and small files are packing right.
I had some trouble with my TST, but that started working and now only problem i have is that the test data seems to contain a character that is not in my original dictionary. I still need to figure that out.

Additionally i still have the problem of getting the testing in place. The structure of the program doesnt really support unit testing as i have not separated functions properly. Especially the lz coding is so straight forward that apart of TST i have very little to test.

However the working huffman compression and the almost working lzw compression makes me quite happy.

--------------
this week i spent approximately 20 hours on this project
-------------
next week i will complete the lzw and figure out what i will do about the testing coverage and javadoc.
