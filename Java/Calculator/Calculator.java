import java.math.*;
import java.util.*;

/**
 * Calculator类通过使用栈和逆波兰表达式，计算出四则运算式的结果。
 * 算式中的操作数可以是任意精度的有符号十进制数。
 * 目前支持如下运算符：
 *     加+, 减-, 乘*, 除/, 取余%, 括号(), 形如"(-3)"的负数, 形如"(+3)"的正数
 *
 */
public class Calculator{
    private String infixStr;        //用户输入的未做任何处理的中缀表达式字符串
    private ArrayList<Element> infixExp = new ArrayList<Element>();      //中缀表达式
    private ArrayList<Element> postfixExp = new ArrayList<Element>();    //后缀表达式
    private int scale = 10;         //标度，默认10,即如果小数位数超过10位，则只保留10位

    /**
     * Constructor
     * 使用默认标度10,即如果小数位数超过10位，则只保留10位。
     * 
     * @param infixStr 要计算的四则运算式子的字符串表示
     * 
     */
    public Calculator(String infixStr){
        this.infixStr = infixStr;
    }
    /**
     * Constructor
     * @param infixStr 要计算的四则运算式子的字符串表示
     * @param scale 标度，默认10,即如果小数位数超过10位，则只保留10位
     */
    public Calculator(String infixStr,int scale){
        this.infixStr = infixStr;
        this.scale = scale;
    }

    /**
     * 获得初始化Caculator实例时的四则运算式
     * @return 初始化Caculator实例时的四则运算式
     */
    public String getInfixStr(){  
        return infixStr;
    }

    /**
     * 对String类型的四则运算式进行预处理：
     *   1.去掉空格
     *   2.负号前加0,并用括号包起来,如-2变成(0-2)
     *   3.正号前加0,并用括号包起来,如+2变成(0+2)
     * @param exp 要预处理的四则运算式
     * @return 预处理后的四则运算式
     */
    private String preProcess(String exp){  
        StringBuffer sb = new StringBuffer(exp);
        System.out.println("预处理前  ： "+sb);
        //1.去掉空格
        for(int i=0; i<sb.length(); i++){
            char c = sb.charAt(i);
            if( c==' ' )
                sb.deleteCharAt(i);
        }

        //2.把形似(-3)变成(0-3),把形似(+3)变成(0+3)
        boolean lastIsLeftBracket = false;
        for(int i=0; i<sb.length(); i++){  
            char c = sb.charAt(i);
            if( lastIsLeftBracket && ( c=='-' || c=='+' ) ){
                sb.insert(i,'0');
                lastIsLeftBracket = false;
                continue;
            }
            if( c=='(' )
                lastIsLeftBracket = true;
            else
                lastIsLeftBracket = false;
        }
        System.out.println("预处理后  ： "+sb);

        return sb.toString();
    }
    
    /**
     * 获得中缀表达式
     * @return 中缀表达式
     */
    public ArrayList<Element> getInfixExp(){  
        if( infixExp.size() != 0 )
            return infixExp;
        else
            return getInfixExp(infixStr);
    }
    /**
     * 获得中缀表达式
     * @param infixStr 要转换为中缀表达式的四则运算式
     * @return 中缀表达式
     */
    private ArrayList<Element> getInfixExp(String infixStr){  
        infixStr = preProcess(infixStr);     //预处理
        
        int infixStrLength = infixStr.length();
        int curr = 0;
        while( curr < infixStrLength ){
            char c = infixStr.charAt(curr);

            if( c=='+' || c=='-' || c=='*'
                             || c=='/' || c=='%' || c=='(' || c==')' ){     //如果是计算符号
                Element e = new Element(c);
                infixExp.add( e );
            }else if( Character.isDigit(c) ){                     //如果是数字
                StringBuffer sb = new StringBuffer();
                sb.append(c);

                char t;
                while( curr+1 < infixStrLength &&
                        ( Character.isDigit(t=infixStr.charAt(curr+1)) || t=='.' ) ){    //如果参与计算的number不止1位
                    sb.append(t);
                    curr++;
                }

                Element e = new Element( new BigDecimal(sb.toString()) );
                infixExp.add(e);
            }
            curr++;
        }

        return infixExp;
    }

    /**
     * 获得后缀表达式
     * @return 后缀表达式
     */
    public ArrayList<Element> getPostfixExp(){
        if( postfixExp.size() != 0 )
            return postfixExp;
        else{
            if( infixExp.size() == 0 )    //如果尚未解析出中缀表达式
                getInfixExp();
            return getPostfixExp( infixExp );
        }
    }
    
    /**
     * 获得后缀表达式
     * @param infixExp 要转换为后缀表达式的中缀表达式
     * @return 后缀表达式
     */
    private ArrayList<Element> getPostfixExp(ArrayList<Element> infixExp){  
        Stack<Element> stack = new Stack<Element>();
        int infixExpLength = infixExp.size();
        for(int i=0; i<infixExpLength; i++){     //遍历中缀表达式
            Element e = infixExp.get(i);

            if( e.isNumber() ){         //如果当前元素是数字
                postfixExp.add(e);
            }else{                      //如果当前元素是符号
                if( stack.empty() || e.getSign() == '(' ){     //如果栈为空或当前元素为左括号
                    stack.push( e );
                }else if( e.getSign() == ')' ){                //如果当前元素为右括号(Note1)
                    Element top = stack.peek();
                    while( top.getSign() != '(' ){  
                        postfixExp.add( stack.pop() );
                        top = stack.peek();
                    }
                    stack.pop();     //pop '('
                }else{                                         //如果当前元素为'+','-','*','/'
                    while( !stack.empty() && 
                            shouldPop(stack.peek(),e) ){

                        postfixExp.add( stack.pop() );
                    }
                    stack.push( e );
                }
            }
        }
        //stack中剩余元素加入后缀表达式
        while( !stack.empty() )
            postfixExp.add( stack.pop() );

        return postfixExp;
    }

    /**
     * 这是getPostfixExp()的辅助方法，用于判断是否应该pop当前栈顶元素 并 添加到后缀表达式尾部.
     * 
     * 这个方法的实现原理是通过在纸上画图，分析总结得到的：
     * 1.如果栈顶元素为'(',则不弹出，直接push下一个元素入栈.
     * 2.如果栈顶元素为'+'，'-'，
     *     (1).如果当前元素为'+'或'-'，则栈顶元素出栈并添加到后缀表达式尾部,然后当前元素入栈。
     *     (2).如果当前元素为'*'或'/'或'%'，则栈顶元素不出栈,然后当前元素入栈。
     * 3.如果栈顶元素为'*'，'/'，'%'
     *     (1).如果当前元素为'+'或'-'，则栈顶元素出栈并添加到后缀表达式尾部,然后当前元素入栈。
     *     (2).如果当前元素为'*'或'/'或'%'，则栈顶元素出栈并添加到后缀表达式尾部,然后当前元素入栈。
     *
     * (情况2和3可以总结为：[栈顶元素是'+','-' && 当前元素为'*','/','%'] 则栈顶元素不出栈，直接当前元素入栈；
     *                   其余情况，栈顶元素都要出栈并添加到后缀表达式尾部,然后当前元素入栈)
     * (不用考虑当前元素为右括号的情况，因为在调用本方法前就已经处理了这种情况。见"Note1")
     * 
     * @param stackTopEle 栈顶元素
     * @param currEle 当前元素
     * @return 如果应该pop当前栈顶元素则返回true，否则返回false
     */
    private boolean shouldPop(Element stackTopEle,Element currEle){
        if( stackTopEle.getSign() == '(' ){
            return false;
        }else if( ( stackTopEle.getSign()=='+' || stackTopEle.getSign()=='-' ) &&
                  ( currEle.getSign()=='*' || currEle.getSign()=='/' || currEle.getSign()=='%' ) ){  
            return false;
        }else{  
            return true;
        }
    }

    /**
     * 计算四则运算式的运算结果
     * @return 四则运算式的运算结果
     */
    public BigDecimal calculate(){
        
        if( postfixExp.size() == 0 )    //如果尚未转换得到后缀表达式
            getPostfixExp();

        Stack<Element> stack = new Stack<Element>();
        int postfixExpLength = postfixExp.size();
        for(int i=0; i<postfixExpLength; i++){      //遍历后缀表达式每一个元素
            Element e = postfixExp.get(i);
            if( e.isNumber() ){  
                stack.push( e );
            }else{
                Element e1 = stack.pop();
                Element e2 = stack.pop();
                Element e3 = new Element();
                switch( e.getSign() ){
                    case '+':  e3.setNumber( e2.getNumber().add(e1.getNumber()) );
                               break;
                    case '-':  e3.setNumber( e2.getNumber().subtract(e1.getNumber()) );   //注意是谁减谁
                               break;
                    case '*':  e3.setNumber( e2.getNumber().multiply(e1.getNumber()) );
                               break;
                    case '/':  e3.setNumber( e2.getNumber().divide(e1.getNumber(),scale,RoundingMode.HALF_UP) );   //注意是谁除谁
                               break;
                    case '%':  e3.setNumber( e2.getNumber().remainder(e1.getNumber()) );
                               break;
                }
                stack.push(e3);         //计算结果入栈
            }
        }

        BigDecimal calResult = stack.pop().getNumber();     //栈中的最后一个元素就是计算结果

        return format( calResult );      //返回处理过格式的计算结果
    }

    /**
     * 修改一下BigDecimal.toPlainString()字符串的格式：
     *    1.如果bd是小数且尾部有0，则去掉bd尾部的0
     * @param bd 要修改格式的BigDecimal实例
     * @return 修改格式过后的BigDecimal实例
     */
    private BigDecimal format(BigDecimal bd){
        StringBuffer sb = new StringBuffer( bd.toPlainString() );
        
        System.out.println("格式化前  ： "+sb);

        //判断是否是小数
        boolean isDecimal = false;
        int length = sb.length();
        for(int i=length-1; i>=0; i--){  
            if( sb.charAt(i) == '.' ){
                isDecimal = true;
                break;
            }
        }

        if( isDecimal ){     //如果是小数，则去掉尾部的0
            int i;
            for(i = sb.length() - 1; i>=0; i--){
                char c = sb.charAt(i);
                if( c == '0' )
                    sb.deleteCharAt(i);
                else
                    break;
            }
            if( sb.charAt(i) == '.' )
                sb.deleteCharAt(i);
        }

        System.out.println("格式化后  ： "+sb);

        return new BigDecimal(sb.toString());
    }

}

class Test{  
    public static void main(String[] args){
        
        Calculator cal = new Calculator("556+24%(3+2)");
        System.out.println( "原始算式  ： "+ cal.getInfixStr() );
        System.out.println( "中缀表达式： "+ cal.getInfixExp() );
        System.out.println( "后缀表达式： "+ cal.getPostfixExp() );
        System.out.println( "计算结果  ： "+ cal.calculate() );

    }
}

/**
 * Element类是Calculator类需要的类，
 * Caculator类用Element的实例来表示中缀表达式和后缀表达式中的元素，这个元素可以是一个操作数，也可以是一个运算符号.
 * 例如：中缀表达式[556, +, 24, %, (, 3, +, 2, )]中，556，+，24等等都是一个Element实例.
 *
 */
class Element{  
    private boolean isNumber;    //该Element实例是否表示操作数
    private boolean isSign;      //该Element实例是否表示运算符号
    private BigDecimal number;    //该Element实例所表示的操作数
    private char sign;            //该Element实例所表示的运算符号

    /**
     * Constructor
     * 构造一个默认为表示操作数的Element实例，其值为0
     */
    public Element(){ 
        this(new BigDecimal("0"));
    }
    
    /**
     * Constructor
     * 构造一个表示操作数的Element实例，其值为number
     * @param number 该Element实例所要表示的操作数
     */
    public Element(BigDecimal number){
        isNumber = true;
        isSign = false;
        this.number = number;
    }
    
    /**
     * Constructor
     * 构造一个表示运算符号的Element实例，其值为sign
     * @param sign 该Element实例所要表示的运算符号
     */
    public Element(char sign){  
        isNumber = false;
        isSign = true;
        this.sign = sign;
    }
    
    /**
     * 该Element实例是否表示操作数
     * @return 该Element实例表示操作数则返回true,否则返回false
     */
    public boolean isNumber(){  
        return isNumber;
    }
    
    /**
     * 该Element实例是否表示运算符号
     * @return 该Element实例表示运算符则返回true,否则返回false
     */
    public boolean isSign(){  
        return isSign;
    }
    
    /**
     * 获得该Element实例所表示的数
     * @return 该Element实例所表示的数
     */
    public BigDecimal getNumber(){  
        return number;
    }
    
    /**
     * 设置该Element实例所表示的数。
     * 如果该Element实例在调用本方法之前是表示运算符号的，
     * 在调用该方法后，将不再表示运算符号，而变成表示数的Element实例。
     * @param num 该Element实例所要表示的数
     */
    public void setNumber(BigDecimal num){
        this.number = num;
        isNumber = true;
        isSign = false;
    }
    
    /**
     * 获得该Element实例所表示的运算符号
     * @return 该Element实例所表示的运算符号
     */
    public char getSign(){  
        return sign;
    }
    /**
     * 设置该Element实例所表示的运算符号。
     * 如果该Element实例在调用本方法之前是表示操作数的，
     * 在调用该方法后，将不再表示操作数，而变成表示运算符号的Element实例。
     * @param num 该Element实例所要表示的运算符号
     */
    public void setSign(char sig){
        this.sign = sig;
        isNumber = false;
        isSign = true;
    }
    
    /**
     * 返回该Element实例所表示的数或符号的字符串形式。
     */
    public String toString(){  
        if( isNumber() )
            return number.toString();
        else
            return String.valueOf( sign );
    }
}

