# Week 7
----
as the terrifying file working is behind my back and the memory slowly fading new terrors are rising in the horizon making a small developer tremble.
----
This week i managed to get almost all parts working. There is still a strange case in packing the whole Moby Dick which was expected, Moby Dick being a whale and all. The issue comes up in LZW extraction as there is a case somewhere in the book where it seems that i get a reference to a dictionary entry that has not yet been created. This reference to future is impossible to handle and obviosly results into *null pointer exception*. I still need to debug that during tomorrow (*sunday*).
Additionally it became cler that my poor code was not able to handle large files and in special the binary files cause issues. Interestingly Huffman seem to surf trough with no significan problems. In order to deal with the overwhelming masses of data running my poor engine to its limits, i had to change the way i read the files. In particular string appending from file had to be replaced with somewhat better stringbuilder functionality.
I managed to get some of the automated testing in place, but the LZW remains largely untested. I hope to get that in place tomorrow aswell.

------
Next week is the demo and i am confident that some sort of program is in place for that and i can manage it throug. This ofcourse is largely due to me trusting myself to be able to demo entirely non working code aswell. Going through tribulations of life in general prepares one to hard situations.

-----
This week i used roughly 16 hours on this program, with most of the time used on debugging the lzw.
