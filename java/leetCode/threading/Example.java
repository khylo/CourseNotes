package threading;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Recomendations
 * simple parallel data processing :- parallelStream   (uses forkJoinpool under hood)
 *      Note issues with java lambdas.. Can't throw checked exceptions.. (can wrap as runtime)
 *        variable need to be final or effectively final..
 * complex task orchestration    ExecutorService
 * ForkJoinPool recursive divide and conquer#
 * Asyn workflow (e.g. waiting on slow proceses etc) completableFuture for async programming (maybe also waiting on slow tasks)
 *
 *
 * ParallelStrem / forkJoinPool commonPool)
 *  ideal for CPU bound / (e.g. filtering/ mapping // reduce)
 * ExecutorService for complex task orchestraton
 */
public class Example {

    List<String> data = List.of("A","B","C","D","E","F","G","H");
    public static void main(String[] args) throws Exception{
        Example ex = new Example();
        ex.forkJoin();
    }

    public void forkJoin() throws InterruptedException{
        System.out.println("ForkJoin ParallelStream started");
        int cores = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(cores);
        forkJoinPool.submit(() ->{
            data.parallelStream().map(item-> {
                long time = (long)(Math.random()*1000);
                System.out.println(Thread.currentThread().getName() +" processing "+item+" after "+time);
                try {
                    Thread.currentThread().sleep(time);
                }catch(InterruptedException e){
                    System.err.println("Fork Join Thread interuppted "+e.getMessage());
                    e.printStackTrace();
                }
                return item.toLowerCase();
            }).forEach(System.out::println);
        }).join();

        System.out.println("ForkJoin ParallelStream method complete");
    }

    public void executor() throws InterruptedException{
        System.out.println("Executor ParallelStream started");
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(cores);
        for(String item: data){

            executorService.submit(()->{
                long time = (long)(Math.random()*1000);
                System.out.println(Thread.currentThread().getName() +" processing "+item+" after "+time);
                try {
                    Thread.currentThread().sleep(time);
                }catch(InterruptedException e){
                    System.err.println("Fork Join Thread interuppted "+e.getMessage());
                    e.printStackTrace();
                }
                return item.toLowerCase();
        });
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);

        )


        System.out.println("Executor ParallelStream method complete");
}
