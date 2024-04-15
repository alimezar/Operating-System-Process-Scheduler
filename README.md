# Operating System Process Scheduler

This is a simple operating system simulator implemented in Java. It includes a process scheduler and memory manager capable of handling multiple programs with various operations like variable assignments, arithmetic calculations, file I/O, and print commands. The system supports two scheduling algorithms: Round Robin and Shortest Job First.

## Features

- **Process Scheduler:** Implements Round Robin and Shortest Job First scheduling algorithms.
- **Memory Management:** Allocates memory for each program and manages variable storage.
- **File I/O:** Supports reading from and writing to files.
- **User Input:** Allows user input for interactive program execution.
- **Program Execution:** Executes programs with variable assignments, arithmetic operations, file I/O, and print commands.

## Program Files
The program files should follow a specific format to be compatible with the simulator. Each program should contain a series of instructions written in plain text format. The instructions may include variable assignments, arithmetic operations, file I/O commands, and print statements.

## Usage

1. Clone the repository to your local machine.
2. Compile the Java files using `javac` command.
3. Run the `os` class with the paths to the programs as arguments.

Example:
```bash
javac OperatingSystem/os.java
java OperatingSystem.os "path_to_Program_1" "path_to_Program_2" "path_to_Program_3"
