# Calculator.java

Calculator类通过使用栈和逆波兰表达式，计算出四则运算式的结果。  
算式中的操作数可以是任意精度的有符号十进制数。  
目前支持如下运算符：  
    加+, 减-, 乘*, 除/, 取余%, 括号(), 形如"(-3)"的负数, 形如"(+3)"的正数

## How to use

``` java

    Calculator cal = new Calculator("556+24%(3+2)");
    System.out.println( "原始算式  ： "+ cal.getInfixStr() );
    System.out.println( "中缀表达式： "+ cal.getInfixExp() );
    System.out.println( "后缀表达式： "+ cal.getPostfixExp() );
    System.out.println( "计算结果  ： "+ cal.calculate() );

```

