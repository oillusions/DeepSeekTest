package cn.oillusions.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TextFiller {
    protected static final List<Provider> textProviders = new ArrayList<>();

    public static void register(String key, Supplier<String> supplier) {
        textProviders.add(new Provider("[?%s]".formatted(key), supplier));
    }

    public static String filler(String input) {
        for (Provider provider : textProviders) {
            input = input.replace(provider.key, provider.supplier.get());
        }

        return input;
    }

    protected record Provider(String key, Supplier<String> supplier) {
    }
}
