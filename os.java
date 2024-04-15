package OperatingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class os {

	private ArrayList<Integer> ReadyQueue;
	private ArrayList<ArrayList<String>> Memory;
	private ArrayList<ArrayList<Integer>> PCB;
	private int[] clockcycle;

	public os(String Prog1, String Prog2, String Prog3) {
		ReadyQueue = new ArrayList<>();

		Memory = new ArrayList<>();
		PCB = new ArrayList<>();
		clockcycle = new int[3];
		clockcycle[0] = 0;
		clockcycle[1] = 0;
		clockcycle[2] = 0;

		try {
			File file = new File(Prog1);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<String> variables = new ArrayList<>();
			ArrayList<Integer> PCBHELPER = new ArrayList<>();
			ArrayList<String> lines = new ArrayList<>();
			PCBHELPER.add(0);
			PCBHELPER.add(0);
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				clockcycle[0] = clockcycle[0] + 1;
				String[] linehelper = line.split(" ");
				lines.add(line);

				if (linehelper[0].equals("assign")
						&& !variables.contains(linehelper[1])) {
					variables.add(linehelper[1]);

				}

			}
			PCBHELPER.add(variables.size() + clockcycle[0]);
			this.PCB.add(PCBHELPER);
			this.Memory.add(lines);

			bufferedReader.close(); // Close the BufferedReader when done
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			File file = new File(Prog2);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<String> variables = new ArrayList<>();
			ArrayList<Integer> PCBHELPER = new ArrayList<>();
			ArrayList<String> lines = new ArrayList<>();
			PCBHELPER.add(1);
			PCBHELPER.add(0);
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				clockcycle[1] = clockcycle[1] + 1;
				String[] linehelper = line.split(" ");
				lines.add(line);
				if (linehelper[0].equals("assign")
						&& !variables.contains(linehelper[1])) {
					variables.add(linehelper[1]);

				}
			}
			PCBHELPER.add(variables.size() + clockcycle[1]);
			this.PCB.add(PCBHELPER);
			this.Memory.add(lines);

			bufferedReader.close(); // Close the BufferedReader when done
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			File file = new File(Prog3);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<String> variables = new ArrayList<>();
			ArrayList<Integer> PCBHELPER = new ArrayList<>();
			PCBHELPER.add(2);
			PCBHELPER.add(0);
			ArrayList<String> lines = new ArrayList<>();
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				clockcycle[2] = clockcycle[2] + 1;
				String[] linehelper = line.split(" ");
				lines.add(line);
				if (linehelper[0].equals("assign")
						&& !variables.contains(linehelper[1])) {
					variables.add(linehelper[1]);

				}
			}
			PCBHELPER.add(variables.size() + clockcycle[2]);
			this.PCB.add(PCBHELPER);
			this.Memory.add(lines);
			bufferedReader.close(); // Close the BufferedReader when done
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(this.PCB);
		System.out.println(this.Memory);

		// this.roundRobin();
		this.shortestJobFirst();

	}

	public void roundRobin() { // add ids in the ready queue
		ReadyQueue.add(0);
		ReadyQueue.add(1);
		ReadyQueue.add(2);
		while (!ReadyQueue.isEmpty()) {
			int programId = ReadyQueue.get(0);
			boolean done = false;
			for (int i = 0; i < 2; i++) {

				int counter = this.PCB.get(programId).get(1); // get program
																// counter for
																// this id
				String instruction = this.Memory.get(programId).get(counter); // get
																				// the
																				// instruction
																				// that
																				// should
																				// be
																				// executing

				String[] instructionArray = instruction.split(" ");
				System.out.println(instruction);

				switch (instructionArray[0]) {

				case "assign":
					if (instructionArray[2].equals("input")) {
						// takes input
						Scanner scanner = new Scanner(System.in);
						String input = scanner.nextLine();

						if (this.Memory.get(programId).contains(
								instructionArray[1])) { // updates the existing
														// variable
							int inputindex = this.Memory.get(programId)
									.indexOf(instructionArray[1]);
							this.Memory.get(programId).remove(inputindex + 1);
							this.Memory.get(programId).add(inputindex + 1,
									input);
						} else // adds the new variable and assign it's input
						{
							this.Memory.get(programId).add(instructionArray[1]);
							this.Memory.get(programId).add(input);
						}

					}

					else if (instructionArray[2].equals("readFile")) {
						String filePath = Memory.get(programId).get(
								Memory.get(programId).indexOf(
										instructionArray[3]) + 1);
						File file = new File(filePath);
						FileReader fileReader;
						try {
							fileReader = new FileReader(file);
							BufferedReader bufferedReader = new BufferedReader(
									fileReader);
							String s = bufferedReader.readLine();
							// int index = this.Memory.get(programId).indexOf(
							// instructionArray[1]) + 1;
							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
															// existing
															// variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										s);
							} else // adds the new variable and assign it's
									// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(s);
							}

							// this.Memory.get(programId).set(index, s); // put
							// the
							// string
							// s in
							// the
							// index

						} catch (Exception e) {

							e.printStackTrace();
						}

					}

					else {
						if (instructionArray[2].equals("add")) {

							String a = instructionArray[3];
							String b = instructionArray[4];
							int IndexA = this.Memory.get(programId).indexOf(a);
							int IndexB = this.Memory.get(programId).indexOf(b);

							int A = Integer.parseInt(this.Memory.get(programId)
									.get(IndexA + 1));
							int B = Integer.parseInt(this.Memory.get(programId)
									.get(IndexB + 1));

							int result = A + B;

							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
															// existing variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										String.valueOf(result));
							} else // adds the new variable and assign it's
									// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(
										String.valueOf(result));
							}

						}

						else if (instructionArray[2].equals("multiply")) {
							String a = instructionArray[3];
							String b = instructionArray[4];
							int IndexA = this.Memory.get(programId).indexOf(a);
							int IndexB = this.Memory.get(programId).indexOf(b);

							int A = Integer.parseInt(this.Memory.get(programId)
									.get(IndexA + 1));
							int B = Integer.parseInt(this.Memory.get(programId)
									.get(IndexB + 1));

							int result = A * B;

							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
															// existing variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										String.valueOf(result));
							} else // adds the new variable and assign it's
									// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(
										String.valueOf(result));
							}

						}

						else if (instructionArray[2].equals("divide")) {
							String a = instructionArray[3];
							String b = instructionArray[4];
							int IndexA = this.Memory.get(programId).indexOf(a);
							int IndexB = this.Memory.get(programId).indexOf(b);

							int A = Integer.parseInt(this.Memory.get(programId)
									.get(IndexA + 1));
							int B = Integer.parseInt(this.Memory.get(programId)
									.get(IndexB + 1));

							int result = A / B;

							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
															// existing variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										String.valueOf(result));
							} else // adds the new variable and assign it's
									// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(
										String.valueOf(result));
							}

						} else {
							String a = instructionArray[3];
							String b = instructionArray[4];
							int IndexA = this.Memory.get(programId).indexOf(a);
							int IndexB = this.Memory.get(programId).indexOf(b);

							int A = Integer.parseInt(this.Memory.get(programId)
									.get(IndexA + 1));
							int B = Integer.parseInt(this.Memory.get(programId)
									.get(IndexB + 1));

							int result = A - B;

							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
															// existing variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										String.valueOf(result));
							} else // adds the new variable and assign it's
									// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(
										String.valueOf(result));
							}

						}

					}

					break;

				case "print":
					// System.out.println("ping");
					// System.out.println(Memory);
					int index = this.Memory.get(programId).indexOf(
							instructionArray[1]);
					System.out.println(this.Memory.get(programId)
							.get(index + 1));

					break;
				case "writeFile":
					String a = instructionArray[1];
					String b = instructionArray[2]; // / filePath
					int IndexA = this.Memory.get(programId).indexOf(a);
					int IndexB = this.Memory.get(programId).indexOf(b);

					String A = this.Memory.get(programId).get(IndexA + 1);
					String B = this.Memory.get(programId).get(IndexB + 1); // /filePath

					try (FileWriter writer = new FileWriter(B)) {
						// Write the string to the file
						writer.write(A);

						// System.out
						// .println("String has been written to the file successfully.");
					} catch (IOException e) {
						// Handle IO exception, e.g., permission issues
						e.printStackTrace();
					}

				}

				// update the program counter
				this.PCB.get(programId).remove(1);
				this.PCB.get(programId).add(1, counter + 1);
				if (this.PCB.get(programId).get(1) == this.clockcycle[programId]) {
					ReadyQueue.remove(0);
					done = true;
					break;
				}

			}
			// System.out.println(done);
			if (!done) // if the process didn't finish the instructions will
						// remove it from the beginning the array and add it to
						// last
			{
				ReadyQueue.add(ReadyQueue.remove(0));
			}

		}
		System.out.println(Memory);

	}

	public void shortestJobFirst() {

		int[] tmpclockcycle = new int[clockcycle.length];
		for (int i = 0; i < tmpclockcycle.length; i++)
			tmpclockcycle[i] = clockcycle[i];

		// int max =
		// Arrays.stream(tmpclockcycle).max().orElse(Integer.MIN_VALUE);
		for (int i = 0; i < tmpclockcycle.length; i++) {
			int min = tmpclockcycle[0];
			int minindex = 0;
			for (int j = 0; j < tmpclockcycle.length; j++) {
				if (tmpclockcycle[j] < min) {
					min = tmpclockcycle[j];
					minindex = j;

				}

			}
			ReadyQueue.add(minindex);
			tmpclockcycle[minindex] = Integer.MAX_VALUE;

			// tmpclockcycle[minindex] = tmpclockcycle[minindex] * max;
			// ReadyQueue.add(minindex);

		}

		while (!ReadyQueue.isEmpty()) {
			int programId = ReadyQueue.remove(0);
			for (int i = 0; i < clockcycle[programId]; i++) {
				int counter = this.PCB.get(programId).get(1); // get program
				// counter for
				// this id
				String instruction = this.Memory.get(programId).get(counter); // get
				// the
				// instruction
				// that
				// should
				// be
				// executing

				String[] instructionArray = instruction.split(" ");
				System.out.println(instruction);

				switch (instructionArray[0]) {

				case "assign":
					if (instructionArray[2].equals("input")) {
						// takes input
						Scanner scanner = new Scanner(System.in);
						String input = scanner.nextLine();

						if (this.Memory.get(programId).contains(
								instructionArray[1])) { // updates the
							// existing
							// variable
							int inputindex = this.Memory.get(programId)
									.indexOf(instructionArray[1]);
							this.Memory.get(programId).remove(inputindex + 1);
							this.Memory.get(programId).add(inputindex + 1,
									input);
						} else // adds the new variable and assign it's input
						{
							this.Memory.get(programId).add(instructionArray[1]);
							this.Memory.get(programId).add(input);
						}

					}

					else if (instructionArray[2].equals("readFile")) {
						String filePath = Memory.get(programId).get(
								Memory.get(programId).indexOf(
										instructionArray[3]) + 1);
						File file = new File(filePath);
						FileReader fileReader;
						try {
							fileReader = new FileReader(file);
							BufferedReader bufferedReader = new BufferedReader(
									fileReader);
							String s = bufferedReader.readLine();
							// int index = this.Memory.get(programId).indexOf(
							// instructionArray[1]) + 1;
							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
								// existing
								// variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										s);
							} else // adds the new variable and assign it's
							// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(s);
							}

							// this.Memory.get(programId).set(index, s); // put
							// the
							// string
							// s in
							// the
							// index

						} catch (Exception e) {

							e.printStackTrace();
						}

					}

					else {
						if (instructionArray[2].equals("add")) {

							String a = instructionArray[3];
							String b = instructionArray[4];
							int IndexA = this.Memory.get(programId).indexOf(a);
							int IndexB = this.Memory.get(programId).indexOf(b);

							int A = Integer.parseInt(this.Memory.get(programId)
									.get(IndexA + 1));
							int B = Integer.parseInt(this.Memory.get(programId)
									.get(IndexB + 1));

							int result = A + B;

							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
								// existing variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										String.valueOf(result));
							} else // adds the new variable and assign it's
							// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(
										String.valueOf(result));
							}

						}

						else if (instructionArray[2].equals("multiply")) {
							String a = instructionArray[3];
							String b = instructionArray[4];
							int IndexA = this.Memory.get(programId).indexOf(a);
							int IndexB = this.Memory.get(programId).indexOf(b);

							int A = Integer.parseInt(this.Memory.get(programId)
									.get(IndexA + 1));
							int B = Integer.parseInt(this.Memory.get(programId)
									.get(IndexB + 1));

							int result = A * B;

							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
								// existing variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										String.valueOf(result));
							} else // adds the new variable and assign it's
							// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(
										String.valueOf(result));
							}

						}

						else if (instructionArray[2].equals("divide")) {
							String a = instructionArray[3];
							String b = instructionArray[4];
							int IndexA = this.Memory.get(programId).indexOf(a);
							int IndexB = this.Memory.get(programId).indexOf(b);

							int A = Integer.parseInt(this.Memory.get(programId)
									.get(IndexA + 1));
							int B = Integer.parseInt(this.Memory.get(programId)
									.get(IndexB + 1));

							int result = A / B;

							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
								// existing variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										String.valueOf(result));
							} else // adds the new variable and assign it's
							// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(
										String.valueOf(result));
							}

						} else {
							String a = instructionArray[3];
							String b = instructionArray[4];
							int IndexA = this.Memory.get(programId).indexOf(a);
							int IndexB = this.Memory.get(programId).indexOf(b);

							int A = Integer.parseInt(this.Memory.get(programId)
									.get(IndexA + 1));
							int B = Integer.parseInt(this.Memory.get(programId)
									.get(IndexB + 1));

							int result = A - B;

							if (this.Memory.get(programId).contains(
									instructionArray[1])) { // updates the
								// existing variable
								int inputindex = this.Memory.get(programId)
										.indexOf(instructionArray[1]);
								this.Memory.get(programId).remove(
										inputindex + 1);
								this.Memory.get(programId).add(inputindex + 1,
										String.valueOf(result));
							} else // adds the new variable and assign it's
							// input
							{
								this.Memory.get(programId).add(
										instructionArray[1]);
								this.Memory.get(programId).add(
										String.valueOf(result));
							}

						}

					}

					break;

				case "print":
					// System.out.println("ping");
					// System.out.println(Memory);
					int index = this.Memory.get(programId).indexOf(
							instructionArray[1]);
					System.out.println(this.Memory.get(programId)
							.get(index + 1));

					break;
				case "writeFile":
					String a = instructionArray[1];
					String b = instructionArray[2]; // / filePath
					int IndexA = this.Memory.get(programId).indexOf(a);
					int IndexB = this.Memory.get(programId).indexOf(b);

					String A = this.Memory.get(programId).get(IndexA + 1);
					String B = this.Memory.get(programId).get(IndexB + 1); // /filePath

					try (FileWriter writer = new FileWriter(B)) {
						// Write the string to the file
						writer.write(A);

						// System.out
						// .println("String has been written to the file successfully.");
					} catch (IOException e) {
						// Handle IO exception, e.g., permission issues
						e.printStackTrace();
					}

				}

				// update the program counter
				this.PCB.get(programId).remove(1);
				this.PCB.get(programId).add(1, counter + 1);

			}
		}
		// System.out.println(done);

	}

	public static void main(String[] args) {

		new os("src/programs/Program_1.txt", "src/programs/Program_2.txt",
				"src/programs/Program_3.txt");

	}

}
