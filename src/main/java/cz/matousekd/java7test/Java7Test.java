package cz.matousekd.java7test;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * @author David.Matousek
 *
 */
public class Java7Test {

    public static void main(String[] args) throws InterruptedException {

        int bigNumber = 1_000_000;
        int binaryLiteral = 0b01110101010;

        Java7Test java8Test = new Java7Test();
        java8Test.diamondOperator();
        java8Test.stringSwitch();
        java8Test.resourceManagement();
        java8Test.multiCatchBlock();
        java8Test.newFilesApi();
        java8Test.forkJoin();
        java8Test.forkJoinTask();


    }

    void diamondOperator() {
        final Map<String, Object> map = new HashMap<>();
    }

    void stringSwitch() {
        String str = "S2";
        switch (str) {
            case "S1":
                System.out.println("case 1 " + str);
                break;
            case "S2":
                System.out.println("case 2 " + str);
                break;
            default:
                break;
        }

    }

    void multiCatchBlock() {

        try {
            if (true) {
                throw new ParseException("A message", 1);
            } else {
                throw new EOFException();
            }
        } catch (ParseException | EOFException e) {
            System.out.println(e.getMessage());
        }
    }


    void resourceManagement() {
        ClassLoader classLoader = getClass().getClassLoader();
        try (FileInputStream fis = new FileInputStream(new File(classLoader.getResource("aFile.txt").getFile()));
                DataInputStream dis = new DataInputStream(fis)) {
            while (dis.available() > 0) {
                //crashes on some modified utf bullshit
                String value = dis.readUTF();
                System.out.println(value);
            }

        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }

    }

    void newFilesApi() {
        Path path = Paths.get("C:/dev");
        System.out.println("Number of Nodes:" + path.getNameCount());
        System.out.println("File Name:" + path.getFileName());
        System.out.println("File Root:" + path.getRoot());
        System.out.println("File Parent:" + path.getParent());
        System.out.println("Is directory? " + Files.isDirectory(path));
        try {
            DirectoryStream<Path> drs = Files.newDirectoryStream(path);
            Iterator<Path> iter = drs.iterator();
            while (iter.hasNext()) {
                Path p = iter.next();
                System.out.println("Dir node: " + p.getFileName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    void forkJoin() throws InterruptedException {


        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("Forkjoin parallelism:" + pool.getParallelism());
        System.out.println("Forkjoin active threads:" + pool.getActiveThreadCount());
        System.out.println("Forkjoin async:" + pool.getAsyncMode());

        pool.invoke(new MyProblemAction(0));
        //longer the limit, more tasks are created
        pool.awaitTermination(10L, TimeUnit.MILLISECONDS);
        pool.shutdown();


    }

    void forkJoinTask() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("Forkjoin parallelism:" + pool.getParallelism());
        System.out.println("Forkjoin active threads:" + pool.getActiveThreadCount());
        System.out.println("Forkjoin async:" + pool.getAsyncMode());

        pool.execute(new MyProblemTask(0));
        pool.awaitTermination(100L, TimeUnit.MILLISECONDS);

    }

    public class MyProblemAction extends RecursiveAction {

        int number;

        public MyProblemAction() {

        }

        public MyProblemAction(int number) {
            this.number = number;
        }


        @Override
        protected void compute() {

            MyProblemAction task = new MyProblemAction(number + 1);
            if (number < 10) {
                task.fork();
            }
            System.out.println("Long operation finished! number:" + number);


        }
    }

    public class MyProblemTask extends RecursiveTask {
        Integer number;

        public MyProblemTask() {

        }

        public MyProblemTask(int number) {
            this.number = number;
        }

        @Override
        protected Object compute() {
            number++;
            System.out.println("Number is now:" + number);
            if (number < 5) {
                MyProblemTask task = new MyProblemTask(number);
                task.fork();
                Integer result = (Integer) task.join();
                return number + result;
            }
            return number;
        }
    }

}
