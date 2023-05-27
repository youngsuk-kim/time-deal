package com.bread.timedeal.study;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
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

  @Test
  void predicate() {
    Predicate<Integer> isPositive = x -> x > 0;
    List<Integer> inputs = Arrays.asList(10, -5, 4, -2, 0);
    System.out.println("Positive number: " + filter(inputs, isPositive));
    System.out.println("Non-positive number: " + filter(inputs, isPositive.negate()));
  }

  public static <T> List<T> filter(List<T> inputs, Predicate<T> condition) {
    List<T> output = new ArrayList<>();
    for (T input : inputs) {
      if (condition.test(input)) {
        output.add(input);
      }
    }

    return output;
  }

}
