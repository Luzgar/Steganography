COMMAND = gcc
FLAGS = -Wall -std=c99 -w

reveal.exe : main.o mathlib.o optionsParser.o tools.o image.o pattern.o output.o huffmanDecompression.o binaryTree.o
	$(COMMAND) $(FLAGS) -o Reveal.exe main.o mathlib.o optionsParser.o tools.o image.o pattern.o output.o huffmanDecompression.o binaryTree.o -lfreeimage

main.o : main.c 
	$(COMMAND) $(FLAGS) -c main.c -lfreeimage

mathlib.o : mathlib.c 
	$(COMMAND) $(FLAGS) -c mathlib.c

optionParser.o : optionsParser.c 
	$(COMMAND) $(FLAGS) -c optionsParser.c

tools.o : tools.c
	$(COMMAND) $(FLAGS) -c tools.c

image.o : image.c
	$(COMMAND) $(FLAGS) -c image.c
	
pattern.o : pattern.c
	$(COMMAND) $(FLAGS) -c pattern.c

output.o : output.c
	$(COMMAND) $(FLAGS) -c output.c

huffmanDecompression.o : huffmanDecompression.c
	$(COMMAND) $(FLAGS) -c huffmanDecompression.c

binaryTree.o : binaryTree.c
	$(COMMAND) $(FLAGS) -c binaryTree.c
clean:
	rm *.o *.exe
