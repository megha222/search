package com.test;

import java.util.*;

public class CurrencyConversion {

    public static void main(String[] args) {

        CurrencyConversion solution = new CurrencyConversion();
        List<CurrencyExchangeInput> inputList = new ArrayList();
        /*inputList.add(new CurrencyExchangeInput("BTC", "USD", 990, 1000));
        inputList.add(new CurrencyExchangeInput("BTC", "EUR", 1150, 1200));
        inputList.add(new CurrencyExchangeInput("ETH", "USD", 180, 200));
        inputList.add(new CurrencyExchangeInput("ETH", "EUR", 210, 220));*/



        solution.initializeGraph(inputList);
        System.out.println(solution.getBestConversionExchangeRate("USD", "EUR"));
    }

    private final Map<String, Map<String, Double>> graph;

    public CurrencyConversion() {
        graph = new HashMap<String, Map<String, Double>>();
    }

    /// This method initializes the graph with the given conversion list inputs
    public void initializeGraph(List<CurrencyExchangeInput> currencyInput) {
        for (CurrencyExchangeInput input: currencyInput) {
            String from = input.getFromCurrency();
            String to = input.getToCurrency();
            if (!graph.containsKey(from)) {
                graph.put(from, new HashMap<String, Double>());
            }
            if (!graph.containsKey(to)) {
                graph.put(to, new HashMap<String, Double>());
            }
            graph.get(from).put(to, input.getBid());
            graph.get(to).put(from, 1/input.getAsk());
        }
    }

    public double getBestConversionExchangeRate(String fromCurrency, String toCurrency) {
        Set<String> visited = new HashSet();

        //Check if we have seen the currencies before
        if (!graph.containsKey(fromCurrency) || !graph.containsKey(toCurrency))
            return -1;

        Map<String, Double> children =  graph.get(fromCurrency);
        double rate = -1;
        for(String currCurrency : children.keySet()) {
            double currentRate = depthFirstSearch(currCurrency, toCurrency, visited, children.get(currCurrency));
            rate = Math.max(rate, currentRate);
        }
        return rate;
    }

    /// This method recurses to find the best rate to convery given currCurrency to given toCurrency.
    /// The visted data set is used to avoid revisiting the same child since there could be cycles in the graph.
    private double depthFirstSearch(String currCurrency, String toCurrency, Set<String> visited, double amount) {

        // Check to see if we have reached the target node
        if(currCurrency.equals(toCurrency))
            return amount;

        // Recurse on every child t find the best conversion from currCurrency to toCurrency.
        Map<String, Double> children =  graph.get(currCurrency);
        double rate = -1;
        for(String child : children.keySet()) {

            // Add the node we are recursing on so we do not revisit it again
            visited.add(currCurrency);

            // If a child is already seen in the recursion, do not enter the recursion again
            if(!visited.contains(child)) {
                double currentRate = depthFirstSearch(child, toCurrency, visited, amount * children.get(child));
                rate = Math.max(rate, currentRate);
            }

            // Remove the visited node from the set since the recursion is now complete.
            visited.remove(currCurrency);
        }
        return rate;
    }
}

