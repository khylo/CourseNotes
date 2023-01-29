public class UniqueCode{
    public static void main(String[] args) {
        UniqueCode app = new UniqueCode(100);
        app.run()       ;
    }

    int range;

    UniqueCode(int range){
        this.range=range;
    }

    public void run(){
        for(int i=0;i<range;i++){
            StringBuilder ans = new StringBuilder("");
            boolean fb=false;
            if(i%3==0){
                fb=true;
                ans.append("fizz");
            }
            if(i%5==0){
                fb=true;
                ans.append("buzz");
            }
            if(fb)
                System.out.print(ans.toString());
            else
                System.out.print(i);

            System.out.print(" ");
        }

    }


}