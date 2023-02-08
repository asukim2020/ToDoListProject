package com.asusoft.todolistproject

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Test

class RxKotlinUnitTest {

    @Test
    fun justTest() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .subscribe(System.out::println)
    }

    @Test
    fun disposableTest() {
        val source = Observable.just("RED", "GREEN", "YELLOW")
        val disposable = source.subscribe(
            { onNext: String ->
                println("onNext() : value : $onNext")
                println("onNext() : value : $onNext")
            },
            { error: Throwable -> System.err.println("onError() : err : " + error.message) },
        ) { println("onComplete()") }

        println("isDisposed() : " + disposable.isDisposed)
    }

    @Test
    fun createTest() {
        val source = Observable.create { emitter: ObservableEmitter<Int?> ->
            emitter.onNext(100)
            emitter.onNext(200)
            emitter.onNext(300)
            emitter.onComplete()
        }
        source.subscribe { x: Int? -> println(x) }
    }

    @Test
    fun fromArrayTest() {
        val arr = arrayOf(100, 200, 300)
        val source = Observable.fromArray(*arr)
        source.subscribe { x: Int? -> println(x) }
    }

}