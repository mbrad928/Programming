/**
 * Created by max on 4/29/15.
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;

public class BWT {
    public static void main(String[] args){
        Scanner sc = null;
        File file = new File(args[0]);
        try{
            sc = new Scanner(file);
        } catch (IOException e){
            e.printStackTrace();
        }

        //given one line
        String lastCol = sc.nextLine();
        char[] sorted = lastCol.toCharArray();
        Arrays.sort(sorted); //sorts it with $ first
        String sortedStr = new String(sorted);;

        //going to create a hash which stores an array of the beginning and end indices of each letter in sortedStr
        //to make it easier to find out which letter is being referred to
        Hashtable<Character, int[]> chars = new Hashtable<>(4);
        for(int i = 1; i < sortedStr.length(); i++){
            int idx = 2;
            //make sure that there is an A
            int[] aIdx = new int[2];
            if(sortedStr.charAt(1) == 'A'){
                aIdx[0] = 1;
            }
            while(idx < sortedStr.length() && sortedStr.charAt(idx) == 'A'){
                idx++;
            }
            aIdx[1] = idx-1;
            chars.put('A',aIdx);

            int[] cIdx = new int[2];
            if(sortedStr.charAt(idx) == 'C'){
                cIdx[0] = idx;
                idx++;
            }
            while(idx < sortedStr.length() && sortedStr.charAt(idx) == 'C'){
                idx++;
            }
            cIdx[1] = idx-1;
            chars.put('C',cIdx);

            int[] gIdx = new int[2];
            if(sortedStr.charAt(idx) == 'G'){
                gIdx[0] = idx;
                idx++;
            }
            while(idx < sortedStr.length() && sortedStr.charAt(idx) == 'G'){
                idx++;
            }
            gIdx[1] = idx-1;
            chars.put('G',gIdx);

            int[] tIdx = new int[2];
            if(sortedStr.charAt(idx) == 'T'){
                tIdx[0] = idx;
                idx++;
            }
            while(idx < sortedStr.length() && sortedStr.charAt(idx) == 'T'){
                idx++;
            }
            tIdx[1] = idx-1;
            chars.put('T',tIdx);

        }

        String output = "$";
        //go through sorted array char by char and find that letter in lastCol, and then find which letter in sorted
        //is at that index
        int count = 0;
        for(int i = 0; i < sortedStr.length()-1; i++){
            char c = output.charAt(i);

            if(c == 'A') {
                int tempCount = 0;
                int idx = 0;
                while(tempCount != count){
                    if(lastCol.charAt(idx) == 'A'){
                        tempCount++;
                    }
                    idx++;
                }
                idx-=1;
                //now we have index
                char a = sortedStr.charAt(idx);
                output += a;
                int start = chars.get(a)[0];
                count = idx-start+1;
            } else if(c== 'C') {
                int tempCount = 0;
                int idx = 0;
                while(tempCount != count){
                    if(lastCol.charAt(idx) == 'C'){
                        tempCount++;
                    }
                    idx++;
                }
                idx-=1;
                //now we have index
                char a = sortedStr.charAt(idx);
                output += a;
                int start = chars.get(a)[0];
                count = idx-start+1;
            } else if(c == 'G') {
                int tempCount = 0;
                int idx = 0;
                while(tempCount != count){
                    if(lastCol.charAt(idx) == 'G'){
                        tempCount++;
                    }
                    idx++;
                }
                idx-=1;
                //now we have index
                char a = sortedStr.charAt(idx);
                output += a;
                int start = chars.get(a)[0];
                count = idx-start+1;
            } else if(c == 'T') {
                int tempCount = 0;
                int idx = 0;
                while(tempCount != count){
                    if(lastCol.charAt(idx) == 'T'){
                        tempCount++;
                    }
                    idx++;
                }
                //now we have index
                idx-=1;
                char a = sortedStr.charAt(idx);
                output += a;
                int start = chars.get(a)[0];
                count = idx-start+1;
            } else { //$
                int idx = lastCol.indexOf('$');
                char a = sortedStr.charAt(idx);
                int start = chars.get(a)[0];
                count = idx-start+1;//signifies which number of the letter it is ex: 3rd T, 2nd C
                output += a;

            }
        }

        String out = output.substring(1,output.length());
        out+='$';
        System.out.println(out);
    }
}
