package org.gmjm.reactive.reactor;

import lombok.Value;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

@CommonsLog
public class ReactorExampleTests {

    @Test
    public void fluxSimple() {
        Flux
            .just("greater", "mke", 1, "meetup")
            .takeUntil(item -> NumberUtils.isCreatable(item.toString()))
            .subscribe(log::info);

        Flux
            .just(4, "greater", "mke", 1, "meetup")
            .takeUntil(item -> NumberUtils.isCreatable(item.toString()))
            .subscribe(log::info);
    }

    @Test
    public void monoSimple() {
        Mono
            .just("hello")
            .subscribe(log::info);
    }

    @Test
    public void appendMonoToFlux() {
        Flux<String> resultFlux = Mono
            .just("hello")
            .concatWith(Mono.just("world!"));

        resultFlux.subscribe(log::info);
    }

    @Test
    public void reduce() {
        Flux
            .just("hello", "world!")
            .reduce("", (val, acc) -> val.concat(acc).concat(" "))
            .subscribe(log::info);
    }

    @Test
    public void flatMapReverseWithReduce() {
        Flux
            .just("hello world!")
            .flatMap(str -> Flux.fromArray(str.split("")))
            .reduce("", (acc, val) -> val.concat(acc))
            .subscribe(log::info);
    }

    @Test
    public void firstWithDelay() {
        String result = Mono
            .first(
                Mono.just("first!").delayElement(Duration.ofMillis(500)),
                Mono.just("no you're not!").delayElement(Duration.ofMillis(50))
            )
            // Made this synchronous on purpose, obviously you won't do this in the "real world"
            .block(Duration.ofSeconds(1));

        assertEquals("no you're not!", result);
    }

    @Test
    public void singleFluxData_MultipleSubscribers() {
        Flux<String> data = Flux
            .just("Java", "C#");

        Mono<String> javaFilter = data
            .filter(item -> item.toLowerCase().equals("java"))
            .reduce("", (acc, val) -> val + " rocks!");

        Mono<String> nonJavaFilter = data
            .filter(item -> !item.toLowerCase().equals("java"))
            .reduce("", (acc, val) -> val + " is not java!");

        javaFilter.subscribe(log::info);
        nonJavaFilter.subscribe(log::info);
    }

    @Test
    public void circuitBreakerFallback() {
        CatRepository successfulCatRepository = new SuccessfulCatRepository();
        CatRepository timedOutCatRepository = new TimedOutCatRepository();
        CatRepository cachedCatRepository = new CachedCatRepository();

        int value = successfulCatRepository.findAll()
            .timeout(Duration.ofMillis(100))
            .onErrorResume(err -> cachedCatRepository.findAll())
            .reduce(0, (acc, cat) -> acc += cat.age)
            .block(Duration.ofMillis(200));

        assertEquals(17, value);

        value = timedOutCatRepository.findAll()
            .timeout(Duration.ofMillis(100))
            .onErrorResume(err -> cachedCatRepository.findAll())
            .reduce(0, (acc, cat) -> acc += cat.age)
            .block(Duration.ofMillis(200));

        assertEquals(11, value);
    }

    private interface CatRepository {
        Flux<Cat> findAll();
    }

    @Value
    private static class Cat {
        private final String name;
        private final int age;
    }

    private static class SuccessfulCatRepository implements CatRepository {

        @Override
        public Flux<Cat> findAll() {
            return Flux.just(
                new Cat("Boomer", 7),
                new Cat("Phoebe", 10)
            );
        }
    }

    private static class TimedOutCatRepository implements CatRepository {

        @Override
        public Flux<Cat> findAll() {
            return Flux
                .just(new Cat("Shouldn't happen", 25))
                .delayElements(Duration.ofHours(1));
        }
    }

    private static class CachedCatRepository implements CatRepository {

        @Override
        public Flux<Cat> findAll() {
            return Flux.just(
                new Cat("Chloe", 11)
            );
        }
    }

}
