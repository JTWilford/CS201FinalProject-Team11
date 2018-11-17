package servlets;

import java.util.LinkedList;
import java.util.List;

public class DataWrapper<E> {
    String error = "";
    List<E> data = new LinkedList<>();
}