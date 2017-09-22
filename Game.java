import util.*;
import java.util.Scanner;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Game {

  public static void main(String[] args) {
    Trie dictionary  = new Trie();
    System.out.println("Ghost Game");
    fillDictionary(dictionary);
    System.out.println("loading...");
    Scanner input = new Scanner(System.in);
    String word = "";
    Node lastCharNode = dictionary.root();
    boolean isComputerTurn = false;
    while(!isEndGame(word, dictionary)) {
      if(!isComputerTurn) {
        System.out.println("Please enter a letter.");
        char c = input.next(".").charAt(0);
        word = word + c;
      } else {
        System.out.println("#* The computer is picking a letter.");
        char move = 'a';
        int max = Integer.MIN_VALUE;
        List<Character> potentialMoves = new ArrayList<Character>();
        for(Map.Entry<Character, Node> e: dictionary.traversePrefix(word).links().entrySet()) {
          int val = getStateValue(e.getValue(), word, true);
          if(val > max) {
            max = val;
            potentialMoves.clear();
            potentialMoves.add(e.getKey());
          } else if (val == max) {
            potentialMoves.add(e.getKey());
          } else {
            continue;
          }
        }
        move = potentialMoves.get((new Random()).nextInt(potentialMoves.size()));
        System.out.println("#* The computer picked: " + move);
        word = word + move;
      }
      isComputerTurn = !isComputerTurn;
      System.out.println("The word is now: '" + word + "'");
    }
    if(!isComputerTurn) {
      System.out.println("YOU WON!!! :D");
    } else {
      System.out.println("The computer won!!! :(");
    }
    System.out.println("The final word was: '" + word + "'");
  }


  private static void fillDictionary(Trie t){
    final String WORD_LIST = "WORD_LIST.txt";
    try (Stream<String> lines = Files.lines(Paths.get(WORD_LIST), Charset.defaultCharset())) {
      lines.forEachOrdered(line -> t.insert(line));
    } catch (Exception e) {
      System.out.println("Error while loading. Please try opening running program again");
    }
  }

  private static int getWordValue(String word) {
    boolean b = (word.length() % 2) == 1;
    boolean c = (word.length() > 3);
    int val = 0;
    if(word.length() > 3) {
      if(word.length() % 2 == 1) {
        val += 50;
      } else {
        val += word.length();
      }
    }
    return val;
  }

  private static int getStateValue(Node state, String word, boolean computerAgent) {
    if(state.isTerminalNode()) {
      return getWordValue(word);
    }
    if(!computerAgent) {
      return maxValue(state,word, !computerAgent); 
    } else {
      return minValue(state, word, !computerAgent); 
    }
  }

  private static int maxValue(Node state, String word, boolean computerAgent) {
    int val = Integer.MIN_VALUE;
    for(Map.Entry<Character, Node> e: state.links().entrySet()) {
      Node successor = e.getValue();
      val = Math.max(val, getStateValue(successor, word + e.getKey(), computerAgent)); 
    }
    return val;
  }
  private static int minValue(Node state, String word, boolean computerAgent) {
    int val = Integer.MAX_VALUE;
    for(Map.Entry<Character, Node> e: state.links().entrySet()) {
      Node successor = e.getValue();
      val = Math.min(val, getStateValue(successor, word + e.getKey(), computerAgent)); 
    }
    return val;
  }

  private static boolean isEndGame(String word, Trie t) {
    if(!t.find(word,false) && !t.find(word, true)) {
      return true;
    } else if(word.length() < 4) {
      return false;
    } else if(t.find(word,true)){
      return true;
    } else {
      return false;
    }
  }
}

