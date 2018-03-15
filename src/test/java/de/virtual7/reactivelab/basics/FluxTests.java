package de.virtual7.reactivelab.basics;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/**
 * Created by mihai.dobrescu
 */
public class FluxTests {

    @Test
    public void testCreateFluxJust() {
        Flux.just("abc").subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxFromList() {
        Flux.just(asList("a", "b", "c")).subscribe(System.out::println);
        Flux.fromIterable(asList("a", "b", "c")).subscribe(System.out::println);
    }

    @Test
    public void testFluxCountElements() {
        Flux<String> stringFlux = Flux.fromIterable(asList("a", "b", "c"));
        stringFlux.count().subscribe(System.out::println);
        stringFlux.subscribe(System.out::println);
    }

    @Test
    public void testFluxRange() {
        Flux.range(5, 8).subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxUsingGenerate() throws InterruptedException {
        Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) sink.complete();
                    return state + 1;
                }).subscribe(System.out::println);

        Flux.generate(sink -> sink.next("5"))
                .take(2)
                .subscribe(System.out::println);

    }

    @Test
    public void testCreateFluxUsingInterval() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(1);

        Flux.interval(Duration.ofSeconds(1))
                .take(2)
                .doOnComplete(cdl::countDown)
                .subscribe(value -> System.out.println(value));

        cdl.await();
    }

    @Test
    public void testCreateFluxFromStream() {
        Flux.fromStream(Stream.of(5, 3, 3)).subscribe(System.out::println);
    }

    @Test
    public void testZipThem() throws InterruptedException {
        Flux.fromStream(Stream.of(5, 3, 3))
                .zipWith(Flux.interval(Duration.ofSeconds(1)))
                .subscribe(System.out::println);
        Thread.sleep(5000);
    }

    @Test
    public void testSwitchIfEmpty() {
        Flux.empty().switchIfEmpty(Flux.fromIterable(asList("a", "b"))).subscribe(System.out::println);
    }

    @Test
    public void testHandleAndSkipNulls() {
        Flux.just("a", "b", null, "c");

        //TODO: create a Flux using just and for each value call a method which could return a null
        //use handle() to filter out the nulls
    }

    @Test
    public void testFluxOnErrorResume() throws InterruptedException {
        //TODO: generate a Flux using range and then throw an exception for one of the elements. Use onErrorResume as fallback
    }

    private Integer returnNullOrFive() {
        return Math.random() < 0.5 ? null : Integer.valueOf(5);
    }

}
