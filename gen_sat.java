import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;


public class gen_sat {
    public static void generate_problem() throws IOException{
        FileOutputStream outputStream = new FileOutputStream("./sat.txt");
        Integer[] vertlist = new Integer[100];
        //fill vertlist
        for(int i = 0; i<100; i++){
            vertlist[i] = i+1;
        }
        ArrayList<Integer> a = new ArrayList<Integer>(Arrays.asList(vertlist));
        Random ran = new Random();
        for(int z = 0; z<100; z++){

        for(int i = 0; i<428; i++){
            Collections.shuffle(a);
            String l = "";
            for(int j = 0; j<3; j++){
                int genNot = ran.nextInt(11);
                if(genNot <5){
                    l += Integer.toString(a.get(j)) + " ";
                    }
                else{
                    l+= "!" + Integer.toString(a.get(j)) + " ";
                }
            }
        byte[] strToBytes = l.getBytes();
        outputStream.write(strToBytes);
        byte[] nl = "\n".getBytes();
        outputStream.write(nl);
        }
        byte[] b = "BREAK\n".getBytes();
        outputStream.write(b);
    }        
        outputStream.close();
    }
    public static void main(String args[]) throws IOException{
        generate_problem();
    }
}
