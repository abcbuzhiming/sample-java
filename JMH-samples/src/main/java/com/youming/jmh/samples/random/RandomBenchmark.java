package com.youming.jmh.samples.random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 测试随机数的两种生成模式谁更快，Random和Jdk 1.7后提供的ThreadLocalRandom
 * 参考文章：揭秘Java高效随机数生成器 https://mp.weixin.qq.com/s/5A9VBI2r0QBOFRD6l_3w3w
 * Random使用的CAS: 可以看见在next(int bits)方法中，对AtomicLong进行CAS操作，如果失败则会对其进行循环重试。
 * 多个线程都进行CAS的时候必定只有一个成功其他的继续会循环做CAS操作，当并发线程越多的时候，其性能肯定越低
 * */
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 5)
@Measurement(iterations = 3, time = 5)
@Threads(10)
@Fork(1)
@State(Scope.Benchmark)
public class RandomBenchmark {

    Random random = new Random();

    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    @Benchmark
    public int random() {
        return random.nextInt();
    }

    @Benchmark
    public int threadLocalRandom() {
        return threadLocalRandom.nextInt();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RandomBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

}
