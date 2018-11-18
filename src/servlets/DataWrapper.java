package servlets;

import java.util.LinkedList;
import java.util.List;

public class DataWrapper<E> {
    public String error = "";
    public List<E> data = new LinkedList<>();
}