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

### Example
Running the jar like so:
```sh
java -jar LCSCDSB.jar STM32F103C8
```
Results in an output something like this. It also opens the first pdf file it found with your default pdf program.
```
[4/8/2023 17:11:19] Initializing LCSC Datasheet Browser
[4/8/2023 17:11:19] [INFO]: Indexing...
[4/8/2023 17:11:19] [SUCCESS]: Indexed all zip files in 38ms. Found 219970 datasheets with total file size of 194.9 GiB
[4/8/2023 17:11:19] [INFO]: Searching for 'STM32F103C8' using mode containing string...
[4/8/2023 17:11:19] [DEBUG]: datasheets_third_quarter/STMicroelectronics-STM32F103C8T6_C8734.pdf
[4/8/2023 17:11:19] [DEBUG]: datasheets_second_quarter/STMicroelectronics-STM32F103C8T6TR_C2969777.pdf
[4/8/2023 17:11:19] [INFO]: Found 2 results in 71ms.
[4/8/2023 17:11:19] [INFO]: Exporting only one result to pdf file.
[4/8/2023 17:11:19] [SUCCESS]: Finished. Datasheet location: /home/crayne/lcscapi/lcscsheet/LCSCDSB/STMicroelectronics-STM32F103C8T6TR_C2969777.pdf
[4/8/2023 17:11:19] [INFO]: Opening datasheet...
[4/8/2023 17:11:19] [INFO]: Press enter to exit.
```
You can also use LCSC component numbers, for example like this:
```sh
java -jar LCSCDSB.jar l C2969777

[4/8/2023 17:14:07] Initializing LCSC Datasheet Browser
[4/8/2023 17:14:07] [INFO]: Indexing...
[4/8/2023 17:14:07] [SUCCESS]: Indexed all zip files in 39ms. Found 219970 datasheets with total file size of 194.9 GiB
[4/8/2023 17:14:07] [INFO]: Searching for 'C2969777' using mode matching lcsc part number...
[4/8/2023 17:14:08] [DEBUG]: datasheets_second_quarter/STMicroelectronics-STM32F103C8T6TR_C2969777.pdf
[4/8/2023 17:14:08] [INFO]: Found 1 results in 231ms.
[4/8/2023 17:14:08] [INFO]: Exporting only one result to pdf file.
[4/8/2023 17:14:08] [SUCCESS]: Finished. Datasheet location: /home/crayne/lcscapi/lcscsheet/LCSCDSB/STMicroelectronics-STM32F103C8T6TR_C2969777.pdf
[4/8/2023 17:14:08] [INFO]: Opening datasheet...
[4/8/2023 17:14:08] [INFO]: Press enter to exit.
```
