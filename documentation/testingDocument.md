# Compression testing document

The barely standing cardhouse collection of algorithms coded with blood and tears shall be called project in this context.

## test data

To fit into the overall feeling of the project following test data sets were selected.
1. Book of Job - to describe the suffering and agony [source](https://www.jewishvirtuallibrary.org/iyov-job-full-text)
2. Moby Dick by Herman Melville - to describe the unattainable goal [source](https://www.gutenberg.org/ebooks/2701)

Additionally subset of Moby Dick (chapters 1-9) were used to test smaller files and for further performance testing, files consisting of multiple moby dicks in row were used.


## Testing method

All tests were run from console using command similar to: 
* 'time ./gradlew run --args="-lz -c chapters_orig.txt" '* 

This means that os is used to time the operations and no part of program or java running is excluded.

All sizes are measured from actual files on filesystem.
Following information was captured:
- Original size of file 
- Huffman compressed size 
- LZW compressed size 
- Huffman time used to compress 
- Huffman time to used to extract
- LZW time used to compress 
- LZW time to used to extract 

In order to furhter test the performance i created double, triple, quadruple and quintuple Moby Dick files. 
This was done using simple unix command for example:
'cat mbdic_orig.txt mbdic_orig.txt > doubleDick.txt' 

File correctness was tested with [cmp](https://en.wikipedia.org/wiki/Cmp_(Unix)) which tells you if there are differences in byte level on the files being compared.

## Testing results

### Performance

|File            | File size | File size huffman + space saved(%) | File size LZW + space saved(%)   | Time to compress hf | Time to compress LZW | Time to extract hf | Time to extract LZW |
|----------------|-----------:|:-----------------------------------:|:--------------------------------:|:-------------------:|:--------------------:|:------------------:|:-------------------:|
|MBdick 1-9 chrs |102132B     | 57421B		44%                 | 50486B         51%	       | 1.833s		     | 1.513s	          | 1.426s             | 1.479s              |
|Book of Job     |100790B     | 57727B   	43%                 | 47118B         53%               | 1.394s              | 1.547s		  | 1.428s             | 1.621s              |
|Moby Dick       |1253916B    | 718432B 	43%	            | 676172B        46%	       | 1.722s		     | 1.823s		  | 1.389s	       | 1.521s		     |

Testing performance with the larger files had only effect on times spent, the ratios between the original and compressed remained the same. 
#### Compression times
![compression times](./CompressionTimes.jpeg)

#### Extraction times
![Extraction times](./ExtractionTimes.jpeg)

### File integrity / correctness
For each file used, i also ran i comparison using cmp with flag '-b'. There were no differences between original and the packed and then extracted file in any of the cases.
For example checking the LZW files using three consecutive moby dicks in one file:

'cmp -b tripledick.txt tripledicklz.txt' 

Would result in no changes found. ie. nothing displayed. This is due to cmp being silent if no differences are found.

### Findings

LZW is clearly better algorithm. It is visible from the test results, that the type of data has significant impact on how good the compression rate is. More repetition in data, the lower width of coding works. In my implementation all dictionary entries are coded with 12 bits of data giving dictionary size of 4095 entries. As side note this shows how large vocabulary Herman Meville had while writing the Moby Dick.
Performance wise LZW suffers from Javas handling of Strings in my implementation. While Huffman gets around this by just using single characters, LZW uses whole words in dictionary.
In summary, setting the max size for dictionary and coding length to optimal will help in getting the results rigtht. Additionally implementation could be better..
# Automated unit tests

Automated unit tests have been written and are executed by gradle. Unfortunately my lacking skills with gradle and the cursed java do not allow me to exclude the UI from the jacoco report.
## Jacoco report
![jacoco](./jacoco.jpeg)


