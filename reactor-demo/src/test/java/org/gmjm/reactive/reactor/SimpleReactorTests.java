package org.gmjm.reactive.reactor;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CommonsLog
public class SimpleReactorTests {

    @Test
    public void flux_Simple() {
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
    public void mono_Simple() {
        Mono
            .just("hello")
            .subscribe(log::info);
    }

    @Test
    public void combine_Mono_FluxResult() {
        Flux<String> resultFlux = Mono
            .just("hello")
            .concatWith(Mono.just("world!"));

        resultFlux.subscribe(log::info);
    }

    @Test
    public void reduce_Flux() {
        Flux
            .just("hello", "world!")
            .reduce("", (val, acc) -> val.concat(acc).concat(" "))
            .subscribe(log::info);
    }

    @Test
    public void reverse_String() {
        Flux
            .just("hello world!")
            .flatMap(str -> Flux.fromArray(str.split("")))
            .reduce("", (val, acc) -> acc.concat(val))
            .subscribe(log::info);
    }

}
