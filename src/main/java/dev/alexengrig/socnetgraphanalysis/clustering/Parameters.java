package dev.alexengrig.socnetgraphanalysis.clustering;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Data
@Builder
public class Parameters {

    @Singular("parameter")
    private final Map<String, Double> map;

    public boolean like(Parameters other) {
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
