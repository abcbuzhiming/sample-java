package com.youming.jmh.samples.counter;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Java并发计数器探秘 https://mp.weixin.qq.com/s/we3wCaArfPF5WzYYkB1zoA
 * 各种计数器实现测试，分别测试AtomicLong的Jdk1.7，Jdk1.8版本，JCTools 中的 ConcurrentAutoTable，LongAdder在jdk1.7和jdk1.8版本这5个的性能
 * 1.8 的 LongAdder性能最优秀。而AtomicLong可以满足每次递增之后都精准的返回其递增值，
 * 而 LongAdder 并不具备这样的特性。LongAdder 为了性能而丧失了一部分功能
 * */
@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class CounterBenchmark {
    private Counter counter;

    @Param// This will default to running through all the counter types
    CounterFactory.CounterType counterType;

    @Setup
    public void buildMeCounterHearty() {
        counter = CounterFactory.build(counterType);
    }

    @Benchmark
    @Group("rw")
    @GroupThreads(20)
    public void inc() {
        counter.inc();
    }

    @Benchmark
    @Group("rw")
    @GroupThreads(1)
    public long get() {
        return counter.get();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CounterBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
