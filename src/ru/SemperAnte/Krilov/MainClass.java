package ru.SemperAnte.Krilov;

import gauss.LinearSystem;
import gauss.MainGausSolution;
import gauss.MyEquation;
import ru.SemperAnte.Krilov.Utils.FindSolution;
import ru.SemperAnte.Krilov.Utils.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainClass
{
    public static Matrix matrix;
    public static List<List<Double>> vectors = new ArrayList<>();
    private static LinearSystem<Double, MyEquation> sysOfVectors;
    public static int size;
    private static MainGausSolution mgs = new MainGausSolution();

    public static void main(String[] args)
    {
        readMatrix();
        matrix.out();
        System.out.println("=============================");
        createVectors();
        outVectors();
        System.out.println("=============================");
        createSystem(true);
        mgs.printSystem(sysOfVectors);
        checkSystem();
        createSystem(false);
        mgs.printSystem(sysOfVectors);
        Double[] coof = mgs.findSolution(sysOfVectors, false);
        mgs.printVector(coof);
        FindSolution FS = new FindSolution(coof);
    }

    private static void createVectors()
    {
        List<Double> vec = new ArrayList<>();
        vec.add(1.0);
        for (int i = 1; i < matrix.size(); ++i)
            vec.add(1.0);
        vectors.add(vec);
        for (int i = 0; i < matrix.size(); ++i)
            vectors.add(matrix.mul(vectors.get(vectors.size() - 1)));

    }

    private static void checkSystem()
    {
        mgs.makeTriangle(sysOfVectors);
        mgs.printSystem(sysOfVectors);
        double mul = 1;
        for (int i = 0; i < matrix.size(); ++i)
            mul *= sysOfVectors.itemAt(i, i);

        if (mul == 0)
        {
            System.out.println("Один из векторов до n+1 линейно-связный. Метод Крылова не имеет смысла для такой системы векторов.");
            System.exit(0);
        }
    }


    private static void createSystem(boolean forCheck)
    {
        sysOfVectors = new LinearSystem<>();

        for (int i = 0; i < matrix.size(); ++i)
        {
            MyEquation myEq = new MyEquation();
            for (int j = 0; j < matrix.size(); ++j)
                myEq.addElement(vectors.get(j).get(i));

            if (forCheck)
                myEq.addElement(0.0);
            else
                myEq.addElement(vectors.get(vectors.size() - 1).get(i));
            sysOfVectors.push(myEq);
        }
    }

    private static void readMatrix()
    {
        Random rand = new Random();
        try
        {

            float[][] res = null;
            Scanner sc = new Scanner(new File("src/input.txt"));
            List<Double> curr = new ArrayList<>();
            for (int i = 0; sc.hasNextLine(); ++i)
            {
                String line = sc.nextLine();
                String[] numbers = line.split(" ");
                size = numbers.length;
                if (res == null)
                    res = new float[size][size];

                for (int j = 0; j < size; ++j)
                    res[i][j] = Integer.valueOf(numbers[j]);
            }
            matrix = new Matrix(res);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static void outVectors()
    {
        for (List<Double> l : vectors)
        {
            for (double d : l)
                System.out.print(d + "\t");
            System.out.println("");
        }
    }

}
