#ifndef OPTIONSPARSER_H_INCLUDED
#define OPTIONSPARSER_H_INCLUDED

struct OptionList{
    char * inputFile;
    int hasInOption;

    char * finOption;
    int hasfinOption;

    char * outputFile;
    int hasOutOption;

    int numberBitsUsed;

    char * channels;
    char * pattern_used;

    char * magic_number;
    int magicNumberSize;

    int hasCompressOption;
    int compressLevel;
    int hasShowOption;
    int erreur;
}; //Toutes les options possibles en entrée

int isInFileExisting(char * filename);

int isCorrectBitsUsedOption(int bitsUsed);

int isCorrectFinOption(char * Fin);

struct  OptionList parseOption(char* argv[]);

#endif // OPTIONSPARSER_H_INCLUDED
