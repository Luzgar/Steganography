#ifndef HUFFMANDECOMPRESSION_H_INCLUDED
#define HUFFMANDECOMPRESSION_H_INCLUDED



struct Dictionnary {
    unsigned char * letter;
    char ** letterCode;
    unsigned char * sizeOfCode;
    int nbOfSymbol;
    int lastPadding;
    int maxSize;
};


char * getCode(int * decimalCode, int nbBitsCode, int nbByte);
void resetBuffer(char * buffer);
int isSameBinary(char * firstBinary, int sizeOfFirst, char * secondBinary, int sizeOfSecond);
int getPositionOfCodeInDictionnary(char * buffer, struct Dictionnary * dictionnary, int sizeBuffer);
struct Dictionnary * fillDictionnary(struct HiddenFileByte * tabByte);
char * getTreeShape(struct Dictionnary * dictionnary, struct HiddenFileByte * tabByte);
struct Dictionnary * fillDictionnaryWithEncodingTree(struct HiddenFileByte * tabByte);
char * getBinaryOfFile(struct HiddenFileByte * tabByte, int sizeOfBinaryTab, int positionToStart);
int decompressMessage(struct HiddenFileByte * finalByteTab, struct Dictionnary * dictionnary, int sizeOfBinaryTab, char * binaryOfFile);
int defaultDecompression(struct HiddenFileByte * tabByte, struct HiddenFileByte * finalByteTab, int hasShowOption);
int huffmanTreeEncodingDecompression(struct HiddenFileByte * tabByte, struct HiddenFileByte * finalByteTab, int hasShowOption);
int huffmanTreeSymbolsOptimization(struct HiddenFileByte * tabByte, struct HiddenFileByte * finalByteTab, int hasShowOption);
struct HiddenFileByte decompress(struct HiddenFileByte * tabByte, int hasShowOption, int levelOfCompression);


#endif // HUFFMANDECOMPRESSION_H_INCLUDED
