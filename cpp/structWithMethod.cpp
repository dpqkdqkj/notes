#include <cstdio>


struct Cat {
private:
    char *name;
public:
    void setName(char *a_name) { name = a_name; }
    void sound() { printf("%s does Meow :3!\n", name); }
};


int main()
{
    struct Cat cat;
    //cat.name = (char*)"Funtik";
    cat.setName((char*)"Funtik");
    cat.sound();
    return 0;
}

