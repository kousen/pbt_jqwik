package jqwik.samples;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.statistics.Histogram;
import net.jqwik.api.statistics.NumberRangeHistogram;
import net.jqwik.api.statistics.Statistics;
import net.jqwik.api.statistics.StatisticsReport;

// froom https://github.com/jlink/jqwik/blob/1.6.5/documentation/src/test/java/net/jqwik/docs/statistics/HistogramExamples.java
class HistogramExampleTests {

    @Property(generation = GenerationMode.RANDOMIZED)
    @StatisticsReport(format = Histogram.class)
    void integers(@ForAll("gaussians") int aNumber) {
        Statistics.collect(aNumber);
    }

    @Provide
    Arbitrary<Integer> gaussians() {
        return Arbitraries
                .integers()
                .between(0, 20)
                .shrinkTowards(10)
                .withDistribution(RandomDistribution.gaussian());
    }

    @Property(generation = GenerationMode.RANDOMIZED)
    @StatisticsReport(format = NumberRangeHistogram.class)
    void integersInRanges(@ForAll @IntRange(min = -1000, max = 1000) int aNumber) {
        Statistics.collect(aNumber);
    }

}