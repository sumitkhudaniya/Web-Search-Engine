package com.websearchengine.org;
public class BoyerMoore {

    static int noOfCharacters = 10000;
    static int max (int a, int b) { 
    	if(a > b) return a;
    	return b;
    } 
    static void badCharHeuristic(char []str, int size, int badchar[]) { 
     int i;
     for (i = 0; i < noOfCharacters; i++) 
          badchar[i] = -1; 
     for (i = 0; i < size; i++) 
          badchar[(int) str[i]] = i;
    } 
    
    
    static int search( char textArray[],  char patternArray[]) { 
     int patternLength = patternArray.length; 
     int textLength = textArray.length; 
     int count = 0;
     int badCharArray[] = new int[noOfCharacters]; 
 
     badCharHeuristic(patternArray, patternLength, badCharArray); 
 
     int size = 0; 
     while(size <= (textLength - patternLength)) { 
         int j = patternLength - 1;
         while(j >= 0 && patternArray[j] == textArray[size+j]) j--; 
         if (j < 0) { 
             System.out.println("The word occurs at position " + size); 
             count++;
             size += (size + patternLength < textLength)? patternLength - badCharArray[textArray[size + patternLength]] : 1;
         } 
         else size += max(1, j - badCharArray[textArray[size+j]]);
     } 
     return count;
    } 
}