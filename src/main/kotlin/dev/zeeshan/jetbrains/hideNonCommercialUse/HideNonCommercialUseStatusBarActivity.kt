package dev.zeeshan.jetbrains.hideNonCommercialUse

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.wm.WindowManager
import java.awt.Container
import java.awt.Window
import javax.swing.Timer

class HideNonCommercialUseStatusBarActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        ApplicationManager.getApplication().invokeLater {
            startStatusBarHider(project)
        }
    }

    private fun startStatusBarHider(project: Project) {
        var attempts = 0
        val timer = Timer(RETRY_DELAY_MS, null)

        timer.addActionListener {
            attempts += 1

            if (project.isDisposed) {
                timer.stop()
                return@addActionListener
            }

            hideStatusBarBadge(project)
            if (attempts >= MAX_ATTEMPTS) {
                timer.stop()
            }
        }

        timer.initialDelay = 0
        timer.start()
    }

    private fun hideStatusBarBadge(project: Project): Int {
        return candidateRoots(project).sumOf { root ->
            NonCommercialUseStatusBarHider.hideIn(root)
        }
    }

    private fun candidateRoots(project: Project): List<Container> {
        val statusBarComponent = WindowManager
            .getInstance()
            .getStatusBar(project)
            ?.component

        val windows = Window
            .getWindows()
            .filter { it.isShowing }

        return (listOfNotNull(statusBarComponent) + windows).distinct()
    }

    private companion object {
        private const val RETRY_DELAY_MS = 500
        private const val MAX_ATTEMPTS = 720
    }
}
