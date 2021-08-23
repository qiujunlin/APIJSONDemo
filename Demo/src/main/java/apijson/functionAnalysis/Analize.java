package apijson.functionAnalysis;

import edu.princeton.cs.algs4.Stack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Analize {
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\test.txt");//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
            System.out.println(s);
        }
        bReader.close();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            s = scanner.nextLine();
            try {
                System.out.println(new Analize().get(s,0));
            }catch (Exception e){
                System.out.println("@column中函数不符合规则，请检查是否正确，减少不必要的空格");
               e.printStackTrace();
            }

        }
      //  System.out.println(new analize().getnext(4,s));
    }
    public String get(String s,int depth) throws  Exception{
        // raw
        if("".equals(Functions.RAW_MAP.get(s)))  return  s;
        Stack<String> stack=new Stack<>();
        int len =s.length();
        StringBuilder res =new StringBuilder();
        int i =0;
        while (i<s.length()){
             char c =s.charAt(i);
             //一段一段解析  是遇见非 ' ' ,几种情况： 1、字段 2. 字符串  3. 是函数  。情况1 ，2 直接截取，情况3 ，判断最外层函数的括号位置，递归的解析括号内部的函数。
             if(c!=' '){
                 //是冒号
                 if(c==',') {
                     res.append(',');
                     i++;
                 }else {
                     //是字符
                     StringBuilder stringBuilder = new StringBuilder();
                     int j = i;
                     //是字符串开始
                     if (c == '\'') {
                         j=i+1;
                         while (j < len && s.charAt(j) != '\'') {
                             stringBuilder.append(s.charAt(j));
                             j++;
                         }
                         res.append(s.substring(i, j + 1));
                         i=j+1;
                     } else {
                         //不是字符串开始
                         //不是 1：函数 2:字段
                         //思路一
                         int endfun = -1;
                         int right =0;
                         //  moment : m ,comment : c
                         while (j < len && s.charAt(j) != ' '&&s.charAt(j)!=','&&s.charAt(j)!=':') {
                             //是函数
                             if (s.charAt(j) == '(') {
                                 String fun = stringBuilder.toString();

                                 //判断fun
                                 if (fun.isEmpty() == false) {
                                     if (Functions.SQL_FUNCTION_MAP == null || Functions.SQL_FUNCTION_MAP.isEmpty()) {
                                         if (StringUtil.isName(fun) == false) {
                                             throw new IllegalArgumentException("字符 " + fun + " 不合法！"
                                                     + "预编译模式下 @column:\"column0,column1:alias;function0(arg0,arg1,...);function1(...):alias...\""
                                                     + " 中 function 必须符合小写英文单词的 SQL 函数名格式！");
                                         }
                                     }
                                     else if (Functions.SQL_FUNCTION_MAP.containsKey(fun) == false) {
                                         throw new IllegalArgumentException("字符 " + fun + " 不合法！"
                                                 + "预编译模式下 @column:\"column0,column1:alias;function0(arg0,arg1,...);function1(...):alias...\""
                                                 + " 中 function 必须符合小写英文单词的 SQL 函数名格式！且必须是后端允许调用的 SQL 函数!");
                                     }
                                 }
                                 // 思路1 寻找结尾 空格 （错误）
                                 //思路2 根据括号寻找
                                 right = getnext(j + 1, s);
                                 if (right == -1) {
                                     throw  new RuntimeException("函数括号不匹配");
                                 } else {

                                     stringBuilder.append("(");
                                     // 递归调用
                                     stringBuilder.append(get(s.substring(j + 1, right),depth+1));
                                     stringBuilder.append(")");
                                     endfun =1;
                                 }
                                 break;
                             } else{
                                 stringBuilder.append(s.charAt(j));
                                 j++;
                             }
                         }
                         // j ： 循环结束的下标 可能是（结尾/遇见空格/遇见”：“）  right： 函数结束的下标
                         if(j==len) endfun =4;
                         else  if(s.charAt(j)==' '){
                             endfun = 3;
                         }else if(s.charAt(j)==',') {
                             endfun = 2;
                         }
                             //  1 ，解析函数结束 2.遇见“，结束  3. 遇见空格结束 4. 到了结尾结束. -1: 字段遇见”：“结束
                             if (endfun == 1) {
                                 if ((right + 1) < len && s.charAt(right + 1) == ':') {
                                     // 获取别名的结束下标 可能是s的结尾或者是遇见空格冒号
                                     int k = StringUtil.getAlians(right + 1, s);
                                     // 获取别名 并加上 "`"
                                     String alians = StringUtil.getQuoteColumn(s.substring(right+2, k), "`");
                                     res.append(stringBuilder.append(" AS ").append(alians));
                                     i = k;
                                 } else {
                                     res.append(stringBuilder);
                                     i = right + 1;
                                 }
                             } else if (endfun == 2||endfun==3) {
                                 //字段在 rawmap中
                                 if("".equals(Functions.RAW_MAP.get(stringBuilder.toString()))) {
                                     res.append(stringBuilder.toString());
                                     i=j;
                                 }else {
                                     // 判断是否是数字
                                     if(StringUtil.isNumer(stringBuilder.toString())){
                                         res.append(stringBuilder.toString());
                                     }else {
                                         res.append(StringUtil.getQuoteColumn(stringBuilder.toString(), "`"));
                                     }
                                     i = j;
//                                 if (s.charAt(j) == ':') {
//                                     int k = StringUtil.getAlians(j + 1, s);
//                                     String alians = StringUtil.getQuoteColumn(s.substring(j + 1, k), "`");
//                                     res.append(stringBuilder.append(" AS ").append(alians));
//                                     i = k;
//                                 } else {

                                 }
                                 //}
                             } else if (endfun == 4) {
                                 stringBuilder.insert(0, "`");
                                 stringBuilder.append("`");
                                 res.append(stringBuilder);
                                    i=j;
                             } else if (endfun == -1) {

                                 if (s.charAt(j) == ':') {
                                     int k = StringUtil.getAlians(j + 1, s);
                                     String alians = StringUtil.getQuoteColumn(s.substring(j + 1, k), "`");
                                     stringBuilder.insert(0, "`");
                                     stringBuilder.append("`");
                                     res.append(stringBuilder.append(" AS ").append(alians));
                                     i = k;
                                 } else {
                                     stringBuilder.insert(0, "`");
                                     stringBuilder.append("`");
                                     res.append(stringBuilder);
                                     i = j;
                                 }
                            }
                     }
                 }
             }else{
                 int j =i;
                 while (j<len&&s.charAt(j)==' ') j++;
                 //空格浓缩
                 if(j<len) res.append(' ');
                 i=j;
             }
        }
        return res.toString();
    }

    private int getnext(int j, String s) {
        Stack<Integer> stack = new Stack<>() ;
        int index=j;
        boolean isString =false;
        while (index<s.length()){
            if(s.charAt(index)=='\''){
                isString=!isString;
            } else if(s.charAt(index)=='('&&!isString){
                stack.push(index);
            }else if(s.charAt(index)==')'&&!isString){
                if(!stack.isEmpty()&&s.charAt(stack.peek())=='('){
                    stack.pop();
                }else{
                    return index;
                }
            }
            index++;
        }
        return -1;
    }

}
