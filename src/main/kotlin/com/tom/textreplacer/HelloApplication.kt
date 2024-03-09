package com.tom.textreplacer

import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.input.TransferMode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import java.io.File
import java.util.UUID




class HelloApplication : Application() {

    private val originalReplacers = listOf(
        Pair("%%&", "ul"),
        Pair("*@*", "er")
    )

    private val replacers = mutableListOf<Replacer>()

    private var currentFile: File? = null

    override fun start(primaryStage: Stage) {
        primaryStage.icons.add(
            Image("/icon.png")
        )

        val root = BorderPane()

        val generateButton = Button("Fix").apply {
            setOnAction {
                if(currentFile != null) {
                    val outputFileName = "${currentFile!!.nameWithoutExtension}-fixed.${currentFile!!.extension}"
                    val outputFile = File(currentFile!!.parentFile, outputFileName)

                    // Read the content of the input file
                    var content = currentFile!!.readText()

                    replacers.forEach {
                        content =  content.replace(it.getOriginal(), it.getNew())
                    }

                    // Replace "WRONGSTRING" with "CORRECTED"
//                    val correctedContent = content.replace("%%&", "CORRECTED")

                    // Write the corrected content to the output file
                    outputFile.writeText(content)
                }
            }
        }

        root.top = dragAndDrop()
        root.center = grid()
        root.bottom = generateButton

        val scene = Scene(root, 900.0, 750.0)
        primaryStage.scene = scene
        primaryStage.title = "Excel bestand fixer"
        primaryStage.show()
    }

    private fun dragAndDrop(): GridPane {

        // Drag and drop section
        val dragAndDropLabel = Label("Drag a file here")
        GridPane.setValignment(dragAndDropLabel, javafx.geometry.VPos.CENTER)
        GridPane.setHalignment(dragAndDropLabel, javafx.geometry.HPos.CENTER)

        val dragAndDropPane = GridPane().apply {
            add(dragAndDropLabel, 0, 0)
            minHeight = 150.0
        }
        dragAndDropPane.setOnDragOver { event ->
            if (event.dragboard.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY)
            }
            event.consume()
        }

        dragAndDropPane.setOnDragDropped { event ->
            val db = event.dragboard
            if (db.hasFiles()) {
                currentFile = db.files[0]
                val filePath = currentFile?.absolutePath
                dragAndDropLabel.text = "File path: $filePath"
            }
            event.isDropCompleted = true
            event.consume()
        }

        return dragAndDropPane
    }

    private fun grid(): ScrollPane {

        val gridPane = GridPane().apply {
            hgap = 10.0
            vgap = 10.0
        }
        val root = ScrollPane().apply {
            isFitToHeight = true
            content = gridPane
        }

        // Add initial rows
        originalReplacers.forEach {
            addRow(gridPane, Replacer(it.first, it.second))
        }

        val addButton = Button("Add Row").apply {
            setOnAction {
                addRow(gridPane, Replacer())
            }
        }

        gridPane.addRow(0, addButton)
        return root
    }

    private fun addRow(gridPane: GridPane, entry: Replacer) {
        val deleteButton = Button("Delete").apply {
            setOnAction {
                replacers.removeIf { it.id == entry.id }
                gridPane.children.removeAll(entry.originalTextField, entry.replaceWithTextField, this)
            }
        }

        replacers.add(entry)
        gridPane.addRow(replacers.size + 1, entry.originalTextField, entry.replaceWithTextField, deleteButton)
    }
}

class Replacer(private var original: String = "", private var new: String = "", val id: UUID = UUID.randomUUID()) {
    val originalTextField = TextField(original)
    val replaceWithTextField = TextField(new)

    fun getOriginal() = original
    fun getNew() = new

    init {
        originalTextField.textProperty().addListener(object : ChangeListener<String> {
            override fun changed(
                observable: ObservableValue<out String>,
                oldValue: String?,
                newValue: String?
            ) {
                if(newValue != null) original = newValue
            }
        })

        replaceWithTextField.textProperty().addListener(object : ChangeListener<String> {
            override fun changed(
                observable: ObservableValue<out String>,
                oldValue: String?,
                newValue: String?
            ) {
                if(newValue != null) new = newValue
            }
        })
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}