package dev.zeeshan.jetbrains.hideNonCommercialUse

import com.intellij.ide.ApplicationInitializedListener

class DisableLicenseWidgetEarlyActivity : ApplicationInitializedListener {
    override suspend fun execute() {
        LicenseWidgetSuppressor.disableRegisteredWidgets()
    }
}
