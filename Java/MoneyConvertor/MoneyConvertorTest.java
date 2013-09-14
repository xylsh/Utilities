import junit.framework.TestCase;

public class MoneyConvertorTest extends TestCase {

    private MoneyConvertor mc = null;
    private String[] legalTestData = { "0.0","0.",".0","0.06","0.006","0.600","0.60",
            "0.6","0.060","5","50","500","5000","50000","500000","5000000",
            "50000000","500000000","5000000000","50000000000","500000000000",
            "123456789012","123456789012.0","123456789012.00","123456789012.000",
            "395830573928.235","395830573928.230","395830573928.205","395830573928.045",
            "395830573928.007","395830573928.070","395830573928.700"};
    private String[] illegalTestData = {"","0.1234","1234567890123",
            "123456789012.1234"};
    
    protected void tearDown(){
        mc = null;
    }
    
    public void testIsLegal(){
        boolean expected;
        boolean result;
        
        expected = true;
        for(int i=0; i<legalTestData.length; i++){
            result = MoneyConvertor.isLegal(legalTestData[i]);
            assertEquals("test "+legalTestData[i]+": ", expected, result);
        }
        
        expected = false;
        for(int i=0; i<illegalTestData.length; i++){
            result = MoneyConvertor.isLegal(illegalTestData[i]);
            assertEquals("test "+illegalTestData[i]+": ", expected, result);
        }
        
    }
    
    public void testConvert(){
        String[] expected = {"零元零角整","零元整","零元零角整","陆分整","陆厘整",
                "陆角整","陆角整","陆角整","陆分整","伍元整","伍拾元整","伍佰元整",
                "伍仟元整","伍万元整","伍拾万元整","伍佰万元整","伍仟万元整","伍亿元整",
                "伍拾亿元整","伍佰亿元整","伍仟亿元整","壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元整",
                "壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元整",
                "壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元整",
                "壹仟贰佰叁拾肆亿伍仟陆佰柒拾捌万玖仟零壹拾贰元整",
                "叁仟玖佰伍拾捌亿叁仟零伍拾柒万叁仟玖佰贰拾捌元贰角叁分伍厘整",
                "叁仟玖佰伍拾捌亿叁仟零伍拾柒万叁仟玖佰贰拾捌元贰角叁分整",
                "叁仟玖佰伍拾捌亿叁仟零伍拾柒万叁仟玖佰贰拾捌元贰角零伍厘整",
                "叁仟玖佰伍拾捌亿叁仟零伍拾柒万叁仟玖佰贰拾捌元零肆分伍厘整",
                "叁仟玖佰伍拾捌亿叁仟零伍拾柒万叁仟玖佰贰拾捌元零柒厘整",
                "叁仟玖佰伍拾捌亿叁仟零伍拾柒万叁仟玖佰贰拾捌元零柒分整",
                "叁仟玖佰伍拾捌亿叁仟零伍拾柒万叁仟玖佰贰拾捌元柒角整a"};
        String result;
        
        for(int i=0; i<legalTestData.length; i++){
            mc = new MoneyConvertor(legalTestData[i]);
            result = mc.convert();
            assertEquals("Test data "+legalTestData[i]+": ", expected[i], result);
        }
        
    }
}
