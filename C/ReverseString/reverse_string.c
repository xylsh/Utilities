#include <stdio.h>
#include <string.h>

/* 反转字符串，字符串必须以'\0'结尾 */
void reverse(char *str){
    int i,k,length;
    length = strlen(str);
    k = length/2;
    char c;
    for(i=0; i<k; i++){   // 也可以i<length-i-1
        c = str[i];
        str[i] = str[length-i-1];
        str[length-i-1] = c;
    }
}

int main(){ 

    char str1[] = "abcde";
    char str2[] = "abcdef";
    reverse(str1);
    reverse(str2);

    printf("%s\n%s\n",str1,str2);
    
    return 0;
}
