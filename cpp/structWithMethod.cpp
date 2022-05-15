#include <cstdio>


struct Cat {
    char *name;
    void sound() { printf("%s does Meow :3!\n", name); }
};


int main()
{
    struct Cat cat;
    cat.name = (char*)"Funtik";
    cat.sound();
    return 0;
}

