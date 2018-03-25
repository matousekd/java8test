package cz.matousekd.java8test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFutureExample completableFutureExample = new CompletableFutureExample();
        completableFutureExample.doIt();
    }

    public void doIt() throws ExecutionException, InterruptedException {
        Supplier<String> supplier = () -> new String("a message");
        Consumer<? super String> consumer = s -> System.out.println(s);
        Function<? super String, ? extends String> function = s -> s += " another message";
        Function<Integer, Integer> function1 = i -> i + 1;
        Function<Throwable, ? extends String> throwableFunction = throwable -> (throwable.getMessage());
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(supplier).thenApply(function).thenAccept(consumer);
        //comment to fail
        future.get();
        future.completeExceptionally(new RuntimeException("fail"));
        Function<Throwable, ? extends Void> throwableFunction1 = (Function<Throwable, Void>) throwable -> {
            System.out.println("failed");
            return null;
        };
        future.exceptionally(throwableFunction1);
        future.get();

    }
}
