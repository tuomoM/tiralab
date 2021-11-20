# Week 3 weekly report

------
Most of this weeks work was around reading and writing to file. To reduce the pain and misery i spent some time working on Ternary Search Trie aka TST. My current understanding is that TST is the main component in LZW compression.
Unfortunately my testing and documenation did not progress  much further this week and i certainly need to work on them next week.
I am positive that i will walk through this valley of tears and get the huffman coding with files to work during next week and that would allow me to concentrate on LZW which sounds much more fun than endless bit operations that i need to google at every turn.

my current implementation of huffman assumes that the occurences of characters fits in int and in 4 bytes. I assume that in real life this is not enough. If i should fix that, i would want to get around of saving the occurences to file and instead figure out a way to save the huffman tree.

-----
Current situation is that i actually have just coded the file interface and the binary operations needed, but not tested it or even performed smoke test. I dont know how to create automatic tests for file interface related tasks.
I also have all the file reading in the main class which is currently a mess. 

------
I spent about 20 hours this week.
