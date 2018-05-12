package VectorLibraryPeformanceTest;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import info.yeppp.Core;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static java.lang.Math.sqrt;

/**
 * Created by luke on 7/05/16.
 */
public class VectorLibraryPerformanceTest {
    static final int size = 10000000;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(VectorLibraryPerformanceTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

//
//    public static void runTests()
//    {
//        long startTime, stopTime, elapsedTime;
//
//        testPythagoras(); //spool up
//
//        System.out.println("Testing Apache Commons Vector3D Performance");
//
//        startTime = System.currentTimeMillis();
//        testApacheCommons();
//        stopTime = System.currentTimeMillis();
//        elapsedTime = stopTime - startTime;
//
//        System.out.println("Elapsed Time: " + elapsedTime);
//
//        System.out.println("Testing Pythagoras d.Vector Performance");
//
//        startTime = System.currentTimeMillis();
//        testPythagoras();
//        stopTime = System.currentTimeMillis();
//        elapsedTime = stopTime - startTime;
//
//        System.out.println("Elapsed Time: " + elapsedTime);
//
//        System.out.println("Testing Yeppp! Library Performance");
//
//        startTime = System.currentTimeMillis();
//        testYepp();
//        stopTime = System.currentTimeMillis();
//        elapsedTime = stopTime - startTime;
//
//        System.out.println("Elapsed Time: " + elapsedTime);
//
//        System.out.println("Testing OpenCL Performance");
//
//        startTime = System.currentTimeMillis();
//        testOpenCL();
//        stopTime = System.currentTimeMillis();
//        elapsedTime = stopTime - startTime;
//
//        System.out.println("Elapsed Time: " + elapsedTime);
//    }
//
//    public static void testOpenCL()
//    {
//        VectorLibraryPerformanceTest.SharedLibraryLoader.load();
//        // The OpenCL kernel
//        final String source =
//        "kernel void sum(global const float *a, global const float *b, int const size) { "
//                + "  unsigned int xid = get_global_id(0); "
//                + " if(xid < size) {"
//                + "   answer[xid] = a[xid] + b[xid];"
//                + " }"
//                + ""
//                + "}";
//
//
//
//
//    }
//
//
//
    @Benchmark
    public static void testApacheCommons()
    {
        Vector3D vectors[] = new Vector3D[size];
        for (int i=0; i < size; i++)
        {
            vectors[i] = new Vector3D(i, i, i);
        }

        //adding
        for (Vector3D vector: vectors)
        {
            vector.add(vector);
        }

        //subtracting
        for (Vector3D vector: vectors)
        {
            vector.subtract(vector);
        }

        //dot product
        for (Vector3D vector: vectors) {
            vector.dotProduct(vector);
        }

        //length
        for (Vector3D vector: vectors) {
            sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY() + vector.getZ()*vector.getZ());
        }


        //multiply scalar
        for (Vector3D vector: vectors) {
            vector.scalarMultiply(2.0);
        }

        //cross product
        for (Vector3D vector: vectors) {
            vector.crossProduct(vector);
        }

        //equality
        for (Vector3D vector: vectors) {
            vector.equals(vector);
        }
    }

    @Benchmark
    public static void testPythagoras()
    {
        pythagoras.d.Vector3 vectors[] = new pythagoras.d.Vector3[size];
        for (int i=0; i < size; i++)
        {
            vectors[i] = new pythagoras.d.Vector3(i, i, i);
        }

        //adding
        for (pythagoras.d.Vector3 vector: vectors)
        {
            vector.add(vector);
        }

        //subtracting
        for (pythagoras.d.Vector3 vector: vectors)
        {
            vector.subtract(vector);
        }

        //dot product
        for (pythagoras.d.Vector3 vector: vectors) {
            vector.dot(vector);
        }

        //length
        for (pythagoras.d.Vector3 vector: vectors) {
            vector.length();
        }


        //multiply scalar
        for (pythagoras.d.Vector3 vector: vectors) {
            vector.mult(2.0);
        }

        //cross product
        for (pythagoras.d.Vector3 vector: vectors) {
            vector.cross(vector);
        }

        //equality
        for (pythagoras.d.Vector3 vector: vectors) {
            vector.equals(vector);
        }
    }

    @Benchmark
    public static void testYepp()
    {
        double[][] vectors = new double[size][3];
        double[] sumArray = new double[3];

        for (int i=0; i < size; i++)
        {
            vectors[i][0] = i;
            vectors[i][1] = i;
            vectors[i][2] = i;
        }

        //adding
        for (double[] vector: vectors)
        {
            Core.Add_V64fV64f_V64f(vector, 0, vector, 0, sumArray, 0, 3);
        }

        //subtracting
        for (double[] vector: vectors)
        {
            Core.Subtract_V64fV64f_V64f(vector, 0, vector, 0, sumArray, 0, 3);
        }

        //dotproduct
        for (double[] vector: vectors)
        {
            Core.DotProduct_V64fV64f_S64f(vector, 0, vector, 0, 3);
        }


        //length
        for (double[] vector: vectors)
        {
            //not completely supported in yeppp. using java sqrt
            double length = sqrt(Core.SumSquares_V64f_S64f(vector, 0, 3));
//            System.out.println("x:" + vector[0] + " y:"  + vector[1] + " z:" + vector[2]);
//            System.out.println("length:" + length);
        }

        //scalar multiply
        for (double[] vector: vectors)
        {
            Core.Multiply_V64fS64f_V64f(vector, 0, 2.0, sumArray, 0, 3);
//            System.out.println("x:" + vector[0] + " y:"  + vector[1] + " z:" + vector[2]);
//            System.out.println("x:" + sumArray[0] + " y:"  + sumArray[1] + " z:" + sumArray[2]);
        }

        //cross product
        for (double[] vector: vectors)
        {
            double[] u = vector;
            double[] v = vector;
            //not supported in yeppp, doing manually in java instead
            sumArray[0] = u[1]*v[2] - u[2]*v[1];
            sumArray[1] = u[2]*v[0] - u[0]*v[2];
            sumArray[2] = u[0]*v[1] - u[2]*v[0];
        }


        //equality
        for (double[] vector: vectors)
        {
            boolean equal = false;
            if(vector[0] == vector[0])
            {
                if(vector[1] == vector[1])
                {
                    if(vector[2] == vector[2])
                    {
                        equal = true;
                    }
                }
            }
        }
    }
}
