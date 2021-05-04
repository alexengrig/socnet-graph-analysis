package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Value
@Builder
public class ClusterRecordParameters {

    @Singular("parameter")
    Map<String, Double> map;

    public boolean like(ClusterRecordParameters other) {
        if (map.size() != other.map.size()) {
            return false;
        }
        for (String key : map.keySet()) {
            if (!other.map.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    public Set<String> names() {
        return map.keySet();
    }

    public double get(String name) {
        return map.get(name);
    }

    public void forEach(BiConsumer<String, Double> consumer) {
        map.forEach(consumer);
    }

    public void update(String name, Function<Double, Double> updater) {
        map.compute(name, (ignore, value) -> updater.apply(value));
    }

    public void add(String name, double value) {
        map.put(name, value);
    }
}
