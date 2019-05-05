package com.ashan.healthguider;

import java.io.Serializable;
import java.util.ArrayList;

class Node implements Serializable
{
    String symptom_id;
    ArrayList<ArrayList<String>> data;
    Node left, right;

    public Node(ArrayList<ArrayList<String>> item, String symptom_id)
    {
        data = item;
        this.symptom_id = symptom_id;
        left = right = null;
    }
}

class BinaryTree
{
    Node root;

    BinaryTree(ArrayList<ArrayList<String>> key, String symptom)
    {
        root = new Node(key,symptom);
    }

    BinaryTree()
    {
        root = null;
    }
}
