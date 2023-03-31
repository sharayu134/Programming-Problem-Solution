import java.util.*;

public class BasicCal3 {

	public static void main(String[] args) {
        // cal("42");
        // cal("0.006");
        //  cal("0.006+7");
        //  cal("1-0.006*3");
        cal("3/0.02");
        //  cal("1 * 1");
        //  cal(" 6-4 / 2 ");
        //  cal("6+(7*9)+(3+4)");
        //  cal("2*(5+5*2)/3+(6/2+8)");
        //  cal("(2+6* 3+5- (3*14/7+2)*5)+3");  
    }
    public static void cal(String s){
        s = s.replaceAll(" ","");
        while(s.contains("(")){
            s = solveBrackets(s);
        }
        Deque<String> st = new ArrayDeque();
        int i =0;
        
        String [] ar= s.split("");
        while(i<ar.length){
            MyClass mC =getDeque(st,ar,i);
            i=mC.getIndex();
            i++;
        }
        String temp = calculateVal(st);
        System.out.println("The ans is "+temp);
    }

    public static String solveBrackets(String s){
        String [] ar= s.split("");
        int i= 0;
        String num = "";
        String num0 = "";
        String temp = "";
        Deque<String> st = new ArrayDeque();
        Deque<String> st1 = new ArrayDeque();
        int idx = getOpenBracIdx(s);
        i=idx+1;
        while(ar[i]!=")"){
            if(ar[i].equals(")")){
                break;
            }
            MyClass mC =getDeque(st,ar,i);
            st = mC.getSt();
            i=mC.getIndex();
            i++;
        }
        temp = calculateVal(st);
        s=s.substring(0,idx)+temp+s.substring(i+1,s.length());
        return s;
    }

    public static int getOpenBracIdx(String s){
        int idx =-1;
        int i=0;
        while(i<s.length()){
            if(s.charAt(i)=='(')
                idx = i;  
            i++;        
        }
        return idx;
    }

    public static String calculateVal(Deque<String> stk){
        String st = "";
        String st1    ="";
        float res = 0;
        while(!stk.isEmpty()){
            st=stk.removeFirst();
            if(stk.size()==0){
                return st;
            }
            if(!stk.isEmpty()){
                st1=stk.removeFirst();
            }
            if(st1.equals("+")){
                if(!stk.isEmpty())
                res = Float.parseFloat(st)+Float.parseFloat(stk.removeFirst());
                stk.add(res+"");
            }
            if(st1.equals("-")){
                if(!stk.isEmpty())
                res = (Float.parseFloat(st)-Float.parseFloat(stk.removeFirst()));
                stk.add(res+"");
            }
        }
        return res+"";
    }

    public static MyClass getDeque(Deque<String>st, String[]ar, int i){
        String num ="";
        String num0 ="";
        switch(ar[i]){
                case "+" :
                    st.add(ar[i]);
                    break;
                case "-" :
                    st.add(ar[i]);
                    break;
                 case "*" :
                    num = st.removeLast();
                    i++;
                    while(i<ar.length){
                        if("+-(*/)".contains(ar[i])){
                            i--;
                            break;
                        }
                        num0=num0+ar[i];
                        i++;
                    }
                    st.add(Float.parseFloat(num)*Float.parseFloat(num0)+"");
                    break;
                case "/" :
                    num = st.removeLast();
                    i++;
                    while(i<ar.length ){
                        if("+-(*/)".contains(ar[i])){
                            i--;
                            break;
                        }
                        num0=num0+ar[i];
                        i++;
                    }
                    st.add((Float.parseFloat(num)) / (Float.parseFloat(num0))+"");
                    break;
                default  :{
                    boolean isNeg = false;
                    if(st.size()==1){
                        String a = st.removeLast();
                        if(a.equals("-")){
                            isNeg =true;
                        }else{
                            st.add(a);
                        }
                    }
                    num = "";
                    while(i<ar.length){
                        if("+-(*/)".contains(ar[i])){
                            i--;
                            break;
                        }
                        num=num+ar[i];
                        i++;
                    }if(isNeg){
                        num="-"+num;
                    }
                    st.add(num);
                }
            }
        MyClass mC =new MyClass(st,i);
        return mC;
    }
}

class MyClass{
    Deque<String> st;
    int index;
    MyClass(Deque<String> st,int index){
        this.st=st;
        this.index = index;
    }
    public Deque<String> getSt(){
        return this.st;
    }
    public void setSt(Deque<String>st){
        this.st=st;
    }
    public int getIndex(){
        return this.index;
    }
    public void setIndex(int index){
        this.index = index;
    }    
}