package Algorithm;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private static TrieNode root;

    public Trie() {
        root = new TrieNode(' ');
    }

    public void insert(String word) {
        TrieNode current = root;
        TrieNode pre;
        for (int i = 0; i < word.length(); i++) {
            pre = current;
            TrieNode child = current.getChild(word.charAt(i));
            if (child != null) {
                current = child;
                child.parent = pre;
            } else {
                current.children.add(new TrieNode(word.charAt(i)));
                current = current.getChild(word.charAt(i));
                current.parent = pre;
            }
        }
        current.isEnd = true;
    }

    public static List<String> autoComplete(String prefix) {
        TrieNode lastNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            lastNode = lastNode.getChild(prefix.charAt(i));
            if (lastNode == null)
                return new ArrayList<String>();
        }
        return lastNode.getWords();
    }
}

