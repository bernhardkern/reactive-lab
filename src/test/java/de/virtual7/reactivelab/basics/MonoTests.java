package de.virtual7.reactivelab.basics;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mihai.dobrescu
 */
public class MonoTests {

    @Test
    public void testCreateVoidMono() {
        Mono<Object> emptyMono = Mono.empty();
        emptyMono.log().subscribe(System.out::println);
    }

    @Test
    public void testCreateScalarMono() {
        Mono<String> mono = Mono.just("hello");
        mono.log().subscribe(System.out::println);
    }

    @Test
    public void testFlatMapMono() {
        Mono<String> mono = Mono.just("Hello");
        mono.map(string -> string + ", World").subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxFromMono() {
        List<String> strings = Arrays.asList("" +
                "a", "b", "c");
        Mono<List<String>> stringsMono = Mono.just(strings);
        Flux<String> stringFlux = stringsMono.flatMapMany(Flux::fromIterable);
        stringFlux.log().subscribe(System.out::println);
    }

    @Test
    public void testMergeMonos() {
        Flux<String> mergedMono = Mono.just("Hello").mergeWith(Mono.just(", World"));
        mergedMono.subscribe(System.out::println);
    }

    @Test
    public void testZipMonos() {
        Mono<Tuple2<String, String>> zippedMono = Mono.just("bla").zipWith(Mono.just("blubb"));
        Mono<Tuple2<Tuple2<String, String>, String>> superZippedMono = zippedMono.zipWith(Mono.just("def"));
        superZippedMono.subscribe(t -> System.out.println(t.getT1().getT1()));
    }

    @Test
    public void testBlockingMono() {
        String blocked = Mono.just("value").block(Duration.ZERO);
        System.out.println(blocked);
    }


}
