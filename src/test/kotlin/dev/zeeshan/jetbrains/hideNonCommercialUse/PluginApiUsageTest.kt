package dev.zeeshan.jetbrains.hideNonCommercialUse

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.readText

class PluginApiUsageTest {
    @Test
    fun `does not reference rejected IntelliJ Platform APIs`() {
        val forbiddenReferences = listOf(
            "ApplicationInitializedListener",
            "applicationInitializedListener",
            "StatusBarWidgetSettings",
            "removeWidget(",
        )

        val projectRoot = Path.of("").toAbsolutePath()
        val files = listOf(projectRoot.resolve("src/main/kotlin"), projectRoot.resolve("src/main/resources"))
            .flatMap { root ->
                Files.walk(root).use { paths ->
                    paths
                        .filter { Files.isRegularFile(it) }
                        .filter { it.extension in setOf("kt", "xml") }
                        .toList()
                }
            }

        forbiddenReferences.forEach { reference ->
            val matchingFiles = files.filter { reference in it.readText() }

            assertFalse(
                matchingFiles.isNotEmpty(),
                "$reference is used in ${matchingFiles.joinToString { projectRoot.relativize(it).toString() }}",
            )
        }
    }
}
