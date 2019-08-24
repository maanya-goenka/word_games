/*
WordGameHelper.java
Authors: Katherine McFerrin, Maanya Goenka
18.03.2019
*/
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;


public class WordGameHelper {
  //linked list implementation of a queue class
  public static class Queue{

    public class Node{
      String key = "";
      Node next = null;
      public Node(){
        this.key = "";
        this.next = null;
      }
      public Node(String key){
        this.key = key;
        this.next = null;
      }
      public void setNext(Node next){
        this.next = next;
      }
    }

    Node head = null;
    Node tail = null;

    public Queue(){
      this.head = null;
      this.tail = null;
    }

    //adds to the end of a linked list queue
    public void enqueue(String newKey){
      Node newNode = new Node(newKey);
      if(this.head == null){
        this.head = newNode;
      }
      else{
        Node currentNode = this.head;
        while(currentNode.next != null){
          currentNode = currentNode.next;
        }
        currentNode.next = newNode;
      }
    }

    //removes from the front of a linked list queue
    public String dequeue(){
      if(this.head == null){
        return null;
      }
      else{
        Node temp = new Node();
        temp = this.head;
        this.head = this.head.next;
        return temp.key;
      }
    }

    //returns the number of nodes in a linked list queue
    public int size(){
      if(this.head ==null){
        return 0;
      }
      else{
        Node currentNode = this.head;
        int count = 1;
        while(currentNode.next != null){
          currentNode = currentNode.next;
          count++;
        }
        return count;
      }
    }

    //returns true if linked list queue is empty, else returns false
    public boolean isEmpty(){
      if(this.head== null){
        return true;
      }
      else{
        return false;
      }
    }
  }

  //
  public static void solutionLadder(HashSet<String> setOfWords, String startWord, String endWord){
    HashMap<String, ArrayList<String>> wordMap = new HashMap<String, ArrayList<String>>();
    String output = "";
    //puts the word and its neighbors into a HashMap where key is the word and value is an ArrayList of its neighbors
    for(String word1 : setOfWords){
      ArrayList<String> neighbors = new ArrayList<String>();
      for(String word2 : setOfWords){
        boolean answer1= isANeighbor(word1, word2);
        if(answer1 == true){
          neighbors.add(word2);
        }
        wordMap.put(word1, neighbors);
      }
    }
    //will store the solution words in order: end-to-start in the queue after the hasLadder() method is called
    Queue backwardsLadder =  new Queue();
    //stores the boolean value returned by the hasLadder() method
    boolean answer2 = hasLadder(setOfWords, startWord, endWord, wordMap, backwardsLadder);
    //if the word ladder has a solution, prints its solution
    if(answer2 == true){
      while(!backwardsLadder.isEmpty()){
        if(backwardsLadder.size()>1)
        {
          output = " - " + backwardsLadder.dequeue() + output;
        }
        else{
          output = backwardsLadder.dequeue() + output;
        }
      }
      System.out.println(output);
    }
    //prints message if hasLadder() method returns false
    else{
      System.out.println("No word ladder found for the given words");
    }
  }


  //returns true if there is an existing word ladder path in the dictionary, else returns false
  public static boolean hasLadder(HashSet<String> setOfWords, String startWord, String endWord, HashMap<String, ArrayList<String>> wordMap, Queue backwardsLadder){
    //path stores all visited words from setOfWords
    Queue path = new Queue();
    path.enqueue(startWord);
    //endMap will store potential word links such that keys are one of the neighbors of the value
    HashMap<String, String> endMap = new HashMap<String, String>();
    endMap.put(startWord, null);
    while(path.size()!=0){
      //stores the top word in the path queue and we will then loop through its neighbors
      String currentWord = path.dequeue();
      //looping through all potential neighbors of the currentWord
      for(String word : wordMap.get(currentWord)){
        //if word is endWord, starts building the word ladder backwards
        if(word.equals(endWord)){
          path.enqueue(endWord);
          endMap.put(endWord, currentWord);

          while(endMap.get(endWord) != null){
            backwardsLadder.enqueue(endWord);
            endWord = endMap.get(endWord);
          }
          //adds the startWord, we have now finished building the backwards ladder
          backwardsLadder.enqueue(startWord);
          return true;
        }
        //ensures that keys are unique in the endMap
        if(!endMap.keySet().contains(word)){
          path.enqueue(word);
          endMap.put(word, currentWord);
        }
      }
    }
    //if there are no neighbors that link the startWord and the endWord, the ladder cannot be created
    return false;
  }

  //checks if two words are neighbors: the words are one character difference away
  public static boolean isANeighbor(String word1, String word2){
    if(word1.length()==word2.length())
    {
      int similar = 0;
      //comparing every letter of the first word to that of the second word and counting the letters in common
      for(int i=0; i<word1.length(); i++){
        if(word1.charAt(i)==word2.charAt(i)){
          similar++;
        }
      }
      if(similar == word1.length()-1){
        return true;
      }
      else{
        return false;
      }
    }
    else{
      return false;
    }
  }

  //creates and returns an ArrayList of anagrams for an input word
  public static ArrayList<String> anagrams(HashMap<String, String> mapOfWords, String inputWord, String sortedInputWord){
    ArrayList<String> collectionOfAnagrams=new ArrayList<String>();
    //loops through all the keys in mapOfWords
    for(String key: mapOfWords.keySet()){
      if(key.length()== inputWord.length()){
        //compares the sorted version of the word to the sorted inputWord
        if(mapOfWords.get(key).compareTo(sortedInputWord)==0 && !key.equals(inputWord)){
          collectionOfAnagrams.add(key);

        }

      }
    }
    return collectionOfAnagrams;
  }

  //method to sort the letters of the word in alphabetical order and return the sorted word
  public static String sortWord(String word){
    String sortedWord = "";
    char[] a=new char[word.length()];
    for(int k=0; k<word.length(); k++){
      char ch = word.charAt(k);
      a[k]=ch;
    }
    for(int i=0; i<(a.length-1); i++){
      for(int j=i+1;j<a.length; j++){
        if(a[j]<a[i]){
          char temp = a[j];
          a[j] = a[i];
          a[i] = temp;
        }
      }
    }
    for(int k=0; k<a.length; k++){
      sortedWord = sortedWord+a[k];
    }
    return sortedWord;
  }

  public static void main(String args[]){
    //takes in a text file through a scanner and prints an exception if that file isn't found
    String filePath = "dictionary.txt";
    File inputFile = new File(filePath);
    Scanner scanner = null;
    try{
      scanner = new Scanner(inputFile);
    }
    catch(FileNotFoundException e){
      System.err.println(e);
      System.exit(1);
    }

    //word ladder implementation
    if(args.length==3 && args[0].equals("ladder")){
      //this HashSet will only contain words from the dictionary with the same length as the start word
      HashSet<String> setOfWords= new HashSet<String>();
      String startWord = args[1].toUpperCase();
      String endWord = args[2].toUpperCase();

      //prints an error if startWord and endWord are not of the same length
      if(startWord.length()!= endWord.length() ){
        System.err.println("Start word and end word must be the same length");
        System.exit(1);
      }

      //prints an error if startWord and endWord are the same
      if(startWord.equals(endWord)){
        System.err.println("Start word and end word must be different");
        System.exit(1);
      }

      //loads words with the same length as the start word into the HashSet setOfWords
      String word = "";
      while(scanner.hasNextLine()){
        word = scanner.nextLine();
        if(word.length()==startWord.length()){
          setOfWords.add(word);
        }
      }

      //prints error message if either start word or end word or both are not present in the HashSet
      if(!setOfWords.contains(startWord) || !setOfWords.contains(endWord)){
        System.err.println("Either start word or end word or both are not present in the dictionary");
        System.exit(1);
      }

      //calls method solutionLadder()
      solutionLadder(setOfWords, startWord, endWord);
    }

    //prints error message and exits program if command line is incorrect
    else if(args[0].equals("ladder")){
      System.err.println("Usage: java CommandLine [ladder] [start word] [end word]");
      System.exit(1);
    }

    //anagrams implementation
    else if(args.length == 2 && args[0].equals("anagram")){
      HashMap<String, String> mapOfWords = new HashMap<String, String>();
      String inputWord = args[1].toUpperCase();
      String sortedInputWord = sortWord(inputWord);
      String word = "";
      String sortedWord = "";
      while(scanner.hasNextLine()){
        word = scanner.nextLine();
        //adds only words of the same length to the HashMap mapOfWords
        if(word.length()==inputWord.length()){
          sortedWord = sortWord(word);
          //stores the word as the key and its sorted version as value
          mapOfWords.put(word, sortedWord);
        }
      }

      ArrayList<String> collectionOfAnagrams = new ArrayList<String>();
      collectionOfAnagrams = anagrams(mapOfWords, inputWord, sortedInputWord);
      if(collectionOfAnagrams.size()==0){
        System.out.println("There are no anagrams for " +inputWord+ " in the dictionary.");
      }
      //prints out the anagrams of the inputWord
      else{
        System.out.print("The anagrams of the word " +inputWord+ " are:");
        for(int i=0; i<collectionOfAnagrams.size(); i++){
          System.out.print(" "+collectionOfAnagrams.get(i));
        }
        System.out.println();
      }
    }

    //prints error message and exits program if command line is incorrect
    else if(args[0].equals("anagram")){
      System.err.println("Usage: java CommandLine [anagram] [word to anagram]");
      System.exit(1);
    }

    //prints error message and exits program if command line is incorrect
    else{
      System.err.println("Invalid command line!");
      System.exit(1);
    }
  }
}
