# lcscdsb
Quick and dirty implementation of an lcsc electronics datasheet browser, using zipped pdf files

### Usage
Firstly, you will need to obtain a lot of datasheets as pdf files and then simply zip them. I would provide them here if they werent so large, however the ones I have downloaded are about 200GiB in total.
Create a file called "datasheet.txt" in the same directory as the LCSCDSB.jar file, then write all file paths to your datasheet zip files, followed by newlines, e.g.:
```sh
/home/crayne/lcscdsb/datasheets1.zip
/home/crayne/lcscdsb/datasheets2.zip
/home/crayne/lcscdsb/datasheets3.zip
/home/crayne/lcscdsb/datasheets4.zip
```
In my setup, these are 4 separate zip files, containing about ~50k datasheets each.
To search through these datasheets, you provide a mode and your search. Alternatively, you can provide a search only.
```sh
java -jar LCSCDSB.jar <mode> '<search>'
```
For more help, provide no arguments:
```sh
java -jar LCSCDSB.jar
