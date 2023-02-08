package com.asusoft.todolistproject

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import org.junit.Test

class RxKotlinUnitTest {

    @Test
    fun just() {
        // Hello
        Observable.just("Hello", "RxJava3!!").subscribe(System.out ::println)

        // Default
        Observable.just(1, 2, 3, 4, 5, 6).subscribe(System.out::println)

        // Color
        val source = Observable.just("RED", "GREEN", "YELLOW")
        val dispose = source.subscribe(
            { result -> println("onNext() : value : $result") },
            { err -> println("onError() : err : " + err.message) },
            { println("onComplete()") }
        )
        println("isDisposed() : " + dispose.isDisposed)
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

    @Test
    fun fromArray() {
        val arr = listOf<Int>(100, 200, 300)
        val source = Observable.fromArray(arr)
        source.subscribe(System.out::println)

        val list = ArrayList<String>()
        list.add("Jerry")
        list.add("William")
        list.add("Bob")

        val observable = Observable.fromIterable(list)
        observable.subscribe(System.out::println)
    }

    // flatMap 은 데이터 가공시 Observable 반환해야 한다.
    @Test
    fun flatMapTest() {
        val balls = arrayOf("1", "2", "3", "5")
        val source = Observable.fromArray(*balls).flatMap{ ball: String -> Observable.just("$ball◆") }
        source.subscribe { x: String? -> println(x) }
    }

    @Test
    fun flatMapTest2() {
        val list = ArrayList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)

        val observable = Observable.fromIterable(list)
        val flatMap = observable.flatMap { x -> Observable.just(x) }
        flatMap.subscribe { s -> println(s) }
    }
}