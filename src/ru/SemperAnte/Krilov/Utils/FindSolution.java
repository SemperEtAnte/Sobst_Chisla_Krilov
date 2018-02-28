package ru.SemperAnte.Krilov.Utils;

import com.sun.tools.javac.Main;
import ru.SemperAnte.Krilov.MainClass;

import java.util.ArrayList;
import java.util.List;

public class FindSolution
{
    private Double[] coofs;
    private Double[][] β;
    private double a;
    private double b;
    private int size;

    public double getA()
    {
        return a;
    }

    public double getB()
    {
        return b;
    }

    public FindSolution(Double[] arr)
    {
        size = arr.length + 1;
        coofs = new Double[size];
        coofs[0] = 1D;
        for (int i = 1; i < size; ++i)
            coofs[i] = arr[i - 1];
        findLine();


        Double[] ƛ = find();
        for (int i = 0; i < ƛ.length; ++i)
            System.out.println("ƛ" + i + " = " + ƛ[i]);
        System.out.println("============= FIND β ===============");
        β = new Double[size - 1][size - 1];

        for (int i = 0; i < MainClass.size; ++i)
        {
            β[0][i] = 1.0;
            System.out.print(β[0][i] + "\t");
        }
        System.out.println("");
        for (int j = 1; j <= MainClass.size - 1; ++j)
        {
            for (int i = 0; i < MainClass.size; ++i)
            {
                β[j][i] = ƛ[i] * β[j - 1][i] - coofs[j];
                System.out.print(β[j][i] + "\t");
            }
            System.out.println("");
        }

        System.out.println("===================================");
        List<List<Double>> x = new ArrayList<>();
        for (int i = 0; i < MainClass.size; ++i)
        {
            x.add(MainClass.vectors.get(MainClass.size - 1));
            for (int j = MainClass.size - 2; j >= 0; --j)
            {
                int raz = MainClass.size - 1 - j;
                System.out.println("[ " + raz + " , " + j + " ]");
                x.set(i, sum(x.get(i), mul(β[MainClass.size - 1 - j][i], MainClass.vectors.get(j))));
            }
        }
        System.out.println("============= x ================");
        for (List<Double> s : x)
        {
            for (Double d : s)
            {
                System.out.print(d + "\t");
            }
            System.out.println("");
        }

        System.out.println("=================check=================");
        for (int i = 0; i < MainClass.size; ++i)
        {
            System.out.println(MainClass.matrix.mul(x.get(i)) + " = " + mul(ƛ[i], x.get(i)));
        }
    }

    private List<Double> mul(double f, List<Double> list)
    {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i)
            result.add(list.get(i) * f);
        return result;
    }

    private List<Double> sum(List<Double> a, List<Double> b)
    {
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < a.size(); ++i)
            res.add(a.get(i) + b.get(i));
        return res;
    }

    private void findLine()
    {
        double amax = Math.abs(coofs[1]);

        System.out.println(coofs[1]);
        for (int i = 2; i < size; ++i)
        {
            System.out.println(coofs[i]);
            if (Math.abs(coofs[i]) > amax)
                amax = Math.abs(coofs[i]);
        }
        b = 1 + amax;
        a = -b;

    }

    private double coof(double ƛ)
    {
        double res = Math.pow(ƛ, size - 1);
        for (int i = 1; i < size; ++i)
            res -= coofs[i] * Math.pow(ƛ, size - i - 1);
        return res;
    }

    public Double[] find()
    {
        Double[] res = new Double[size - 1];
        int index = 0;
        for (double left = a, right = a + 0.5; right <= b; right += 0.5)
        {
            double cl = coof(left);
            double cr = coof(right);
            if (cl == 0)
            {
                res[index++] = left;
                left = right += 0.5;
            }
            else if (cr == 0)
            {
                res[index++] = right;
                left = right += 0.5;
            }
            else if (cl * cr < 0)
            {
                res[index++] = method_chord(left, right);
                left = right += 0.5;
            }
        }
        return res;
    }

    private double method_chord(double a, double b)
    {
        double x_next = 0;
        double tmp;
        do
        {
            tmp = x_next;
            x_next = b - coof(b) * (a - b) / (coof(a) - coof(b));
            a = b;
            b = tmp;
        } while (Math.abs(x_next - b) > 1e-20);
        return x_next;
    }
}
