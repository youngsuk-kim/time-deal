package com.bread.timedeal.study;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

class Practice {

  @Test
  void consumer() {
    Consumer<String> myStringConsumer = System.out::println;
    List<Integer> integerInputs = Arrays.asList(4, 2, 3);

    Consumer<Integer> myIntegerProcessor = x -> System.out.println("Proccessing integer " + x);

    process(integerInputs, myIntegerProcessor);

    Consumer<Integer> myDifferentIntegerProccessor = x ->
        System.out.println("Processing integer in different way " + x);

    process(integerInputs, myDifferentIntegerProccessor);
  }

  @Test
  void biConsumer() {
    BiConsumer<Integer, Double> myDoubleProccessor = (Integer index, Double input) -> {
      System.out.println("Processing " + input + " at index " + index);
    };

    List<Double> inputs = Arrays.asList(1.1, 2.2, 3.3);
    process(inputs, myDoubleProccessor);
  }

  public static <T> void process(List<T> inputs, BiConsumer<Integer, T> prccessor) {
    for (int i = 0; i < inputs.size(); i++) {
      prccessor.accept(i, inputs.get(i));
    }
  }

  public static <T> void process(List<T> inputs, Consumer<T> processor) {
    for (T input : inputs) {
      processor.accept(input);
    }
  }

}
