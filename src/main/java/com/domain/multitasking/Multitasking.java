package com.domain.multitasking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

//temp code
@Component
public class Multitasking {

	private static final int LIST_SIZE = 10000000;
	private static final int STRING_LENGTH = 10; // Length of each random string
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final Random RANDOM = new Random();
	private List<String> finalList = new ArrayList<>();

//	@Async("processExecutor")
	private static List<String> generateRandomStringList() {
		return IntStream.range(0, LIST_SIZE).parallel() // Improves performance for large lists
				.mapToObj(i -> generateRandomString(STRING_LENGTH)).collect(Collectors.toList());
	}

//	@Async("processExecutor")
	private static String generateRandomString(int length) {
		return RANDOM.ints(length, 0, CHARACTERS.length()).mapToObj(CHARACTERS::charAt).map(String::valueOf)
				.collect(Collectors.joining());
	}

	public static void writeListToFile(List<String> list, String filePath) {
		File file = new File(filePath);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (String line : list) {
				writer.write(line);
				writer.newLine(); // Move to the next line
			}
		} catch (IOException e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

	@Async("processExecutor")
	public void generateReport1() {
		System.out.println("Executing generateReport() on Thread: " + Thread.currentThread().getName());
		String desktopPath = "C:\\temp\\finalList.txt";
		List<String> list1 = new ArrayList<>(generateRandomStringList());

		int numThreads = 20;
		int chunkSize = list1.size() / numThreads;
		for (int i = 0; i < numThreads; i++) {
			int start = i * chunkSize;
			int end = (i == numThreads - 1) ? list1.size() : (i + 1) * chunkSize;

			System.out.println("Start:-" + start + "::::::::::::: End:-" + end);

			List<String> sublist = list1.subList(start, end);
			for (String l : sublist) {
				tempProcess1(l);
			}

			writeListToFile(finalList, desktopPath);
		}

		System.out.println("finalList:===========>>>>>>>>>>" + finalList.size());

	}

	@Async("processExecutor")
	public void tempProcess1(String string) {
		if (string.length() > 6 && string.startsWith("a")) {
			finalList.add(string);
		}
	}

	@Async("processExecutor")
	public void tempProcess(List<String> sublist) throws InterruptedException, ExecutionException {
		int numThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		List<Future<List<String>>> futures = new ArrayList<>();
		Callable<List<String>> task = () -> sublist.parallelStream().filter(a -> a.length() > 6 && a.startsWith("a"))
				.collect(Collectors.toList());

		futures.add(executor.submit(task));

		List<String> finalList = new ArrayList<>();
		for (Future<List<String>> future : futures) {
			finalList.addAll(future.get()); // Merging results
		}

		executor.shutdown(); // Shut down executor after task completion
		System.out.println("Filtered List Size: " + finalList.size());

	}

	public void generateReport() throws InterruptedException, ExecutionException {
		System.out.println("Executing generateReport() on Thread: " + Thread.currentThread().getName());
		String desktopPath = System.getProperty("user.home") + "/Desktop/finalList.txt";
		List<String> list1 = new ArrayList<>(generateRandomStringList());

		int numThreads = Runtime.getRuntime().availableProcessors();
//		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		int chunkSize = list1.size() / numThreads;
//		List<Future<List<String>>> futures = new ArrayList<>();

		for (int i = 0; i < numThreads; i++) {
			int start = i * chunkSize;
			int end = (i == numThreads - 1) ? list1.size() : (i + 1) * chunkSize;

			List<String> sublist = list1.subList(start, end);
			tempProcess(sublist);
//			Callable<List<String>> task = () -> sublist.parallelStream().filter(a -> a.length() > 5)
//					.collect(Collectors.toList());
//
//			futures.add(executor.submit(task));
		}

//		List<String> finalList = new ArrayList<>();
//		for (Future<List<String>> future : futures) {
//			finalList.addAll(future.get()); // Merging results
//		}
//
//		executor.shutdown(); // Shut down executor after task completion
//		System.out.println("Filtered List Size: " + finalList.size());

	}

}
