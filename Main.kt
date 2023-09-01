import java.io.*
import java.util.NoSuchElementException
import javax.sound.midi.MidiSystem
import javax.sound.midi.Sequence
import javax.sound.midi.ShortMessage

fun main(args: Array<String>) {
    val midiFile = args.firstOrNull() ?: run {
        throw NoSuchElementException("Midi file not found")
    }

    val sequence = MidiSystem.getSequence(File(midiFile))

    val trackedNotes = getTrackedNotesFrom(sequence)

    // There are two ways to save MIDI in this project:
    saveAsKotlinClass(trackedNotes, midiFile)
    saveAsText(trackedNotes, midiFile)
}

// Save functions

private fun saveAsKotlinClass(trackedNotes: List<List<Note>>, midiPath: String) {
    // Example: MIDI_FILE.mid -> MIDI_FILE_data.kt
    val name = midiPath.split(".").first() + "_data.kt"

    val file = createFile(name)

    addImportsToFile(file)
    addNotesToFile(file, trackedNotes)
}

private fun saveAsText(trackedNotes: List<List<Note>>, midiPath: String) {
    // Example: MIDI_FILE.mid -> MIDI_FILE_data.txt
    val name = midiPath.split(".").first() + "_data.txt"

    val file = createFile(name)

    file.writeText(trackedNotes.toString())
}

// File functions

fun createFile(name: String): File {
    val file = File(name)
    file.delete()
    val success = file.createNewFile()

    if (success) {
        return file
    }

    file.delete()
    val newFile = File(name)
    newFile.createNewFile()
    return newFile
}

fun addImportsToFile(file: File) {
    file.appendText("import NoteName.*\n\n")
}

fun addNotesToFile(file: File, trackedNotes: List<List<Note>>) {
    var tabs = 0
    file.appendTextWithIndentation("object MidiData {\n", tabs++)
    trackedNotes.forEachIndexed { i, track ->
        if (track.isEmpty()) {
            file.appendTextWithIndentation("val track${i} = listOf<Note>()\n\n", tabs)
        } else {
            file.appendTextWithIndentation("val track${i} = listOf(\n", tabs++)

            track.forEach { note ->
                file.appendTextWithIndentation("${note},\n", tabs)
            }

            file.appendTextWithIndentation(")\n\n", --tabs)
        }
    }
    file.appendTextWithIndentation("}", --tabs)
}

fun File.appendTextWithIndentation(text: String, tabs: Int) {
    this.appendText(" ".repeat(tabs * 4) + text)
}

// Notes function

fun getTrackedNotesFrom(sequence: Sequence): List<List<Note>> = sequence.tracks.map { track ->
    (0 until track.size()).mapNotNull { i ->
        val event = track.get(i)
        val message = event.message

        if (message is ShortMessage) Note.create(message) else null
    }
}

// Models

data class Note(
    val key: Int,
    val octave: Int,
    val name: NoteName,
    val velocity: Int,
    val command: Int
) {
    companion object {
        fun create(message: ShortMessage): Note = Note(
            key = message.data1,
            octave = (message.data1 / 12)-1,
            name = NoteName.getNote(message.data1 % 12),
            velocity = message.data2,
            command = message.command
        )
    }
}

// Data objects are available after kotlin 1.9.0, if you are
// using a previous version, use only `object`.
sealed class NoteName(
    val stringValue: String
) {
    data object C : NoteName("C")
    data object Ch : NoteName("C#")
    data object D : NoteName("D")
    data object Dh : NoteName("D#")
    data object E : NoteName("E")
    data object F : NoteName("F")
    data object Fh : NoteName("F#")
    data object G : NoteName("G")
    data object Gh : NoteName("G#")
    data object A : NoteName("A")
    data object Ah : NoteName("A#")
    data object B : NoteName("B")

    companion object {
        fun getNote(value: Int) = when (value) {
            0 -> C
            1 -> Ch
            2 -> D
            3 -> Dh
            4 -> E
            5 -> F
            6 -> Fh
            7 -> G
            8 -> Gh
            9 -> A
            10 -> Ah
            11 -> B
            else -> throw NoSuchElementException()
        }
    }
}




