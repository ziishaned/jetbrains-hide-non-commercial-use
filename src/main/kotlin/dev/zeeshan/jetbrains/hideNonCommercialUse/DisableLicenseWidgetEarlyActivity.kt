package dev.zeeshan.jetbrains.hideNonCommercialUse

import com.intellij.ide.ApplicationInitializedListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.InitProjectActivity

class DisableLicenseWidgetEarlyActivity : ApplicationInitializedListener, InitProjectActivity {
    override suspend fun execute() {
        LicenseWidgetSuppressor.disableRegisteredWidgets()
    }

    override suspend fun run(project: Project) {
        LicenseWidgetSuppressor.disableRegisteredWidgets()
    }
}
