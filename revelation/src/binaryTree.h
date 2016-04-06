
struct node {
    int is_leaf;
    struct node *left;
    struct node *right;
    char value;
};

typedef struct node * node;


struct codeList
{
    char ** codeTab;
    int indexOfNextCode;
    char * sizeOfCode;
    int tabSize;
};

char * construct_tree(char * shape, node n);
void display_tree(node n) ;
void fillLetterCode(node n, struct Dictionnary * dictionnary);
void getLeafPathRecurs(node n, char path[], int pathLen, struct codeList * codelist);
char * pathToCode(char path[], int len);