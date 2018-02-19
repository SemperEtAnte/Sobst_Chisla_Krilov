package ru.SemperAnte.Krilov.Utils;

import java.util.ArrayList;
import java.util.List;
public class Matrix
{
    private float[][] elements;

    public Matrix(float[][] el)
    {
        this.elements = el;
    }
    public int size()
    {
        return elements.length;
    }
    public Matrix(short n)
    {
        elements = new float[n][n];
    }

    public List<Double> mul(List<Double> vec)
    {
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < elements.length; ++i)
        {
            double sum = 0;
            for (int j = 0; j < elements.length; ++j)
                sum += vec.get(j) * elements[i][j];
            res.add(sum);
        }
        return res;
    }

    public void out()
    {
        for (int i = 0; i < elements.length; ++i)
        {
            for (int j = 0; j < elements.length; ++j)
                System.out.print(elements[i][j] + "\t");
            System.out.println("");
        }
    }

}
