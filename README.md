# MIDI Reader Project

This is a MIDI reader developed in Kotlin that utilizes the `javax.sound.midi` package to read and process MIDI files.

## Prerequisites

Before running this project, make sure you have the following software installed on your system:

- Kotlin Compiler (`kotlinc`)
- Java Runtime Environment (JRE)

## Installation

1. Clone this repository to your local machine:

```bash
git clone https://github.com/leoarmelin/midi-reader-kotlin
```

2. Navigate to the project's root directory:
```bash
cd midi-reader-kotlin
```

## Usage
To run the MIDI reader, follow these steps:
1. Add the midi file to the root directory (the same as Main.kt)
2. Compile the Kotlin source code and create an executable JAR file:
```bash
kotlinc Main.kt -include-runtime -d Main.jar
```
3. Run the compiled JAR file, providing the path to the MIDI file you want to read as an argument:
```bash
java -jar Main.jar <MIDI_FILE>.mid
```
Replace <MIDI_FILE> with the name of the MIDI file you want to process. The MIDI file should be located in the root directory of the project.

**Note:** you can use just one line by adding `&&`, like this:
```bash
kotlinc Main.kt -include-runtime -d Main.jar && java -jar Main.jar <MIDI_FILE>.mid
```

The output should be the following pattern:

<img width="377" alt="image" src="https://github.com/leoarmelin/midi-reader-kotlin/assets/1687782/c65b0241-d5c7-4fc8-952e-d0af08e0ffb7">

