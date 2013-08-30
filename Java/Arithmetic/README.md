# Arithmetic.java

由于十进制数的二进制表示可能不够精确，浮点数或是双精度浮点数无法精确表示的情况并不少见。浮点数值不能用十进制来精确表示的原因要归咎于CPU表示浮点数的方法。这样的话就可能会牺牲一些精度，有些浮点数运算也会引入误差。

Java中，float和double只能用来做科学计算或者是工程计算，在商业计算中我们要用java.math.BigDecimal。

Arithmetic类封装了使用BigDecimal进行浮点数加减乘除的运算。

## How to use

``` java

    double d1 = 2.4;
    double d2 = 2.9;
    double d3 = Arithmetic.add(d1, d2);

```

## Bugs

Please report any bugs feature requests to [the Github issue tracker](https://github.com/xylsh/Utilities/issues)

