package dev.zeeshan.jetbrains.hideNonCommercialUse

import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.impl.status.widget.StatusBarWidgetSettings

object LicenseWidgetSuppressor {
    val widgetIds = setOf(
        "NonCommercial",
        "TrialStatusBarWidget",
    )

    fun disableRegisteredWidgets() {
        val settings = StatusBarWidgetSettings.getInstance()

        StatusBarWidgetFactory.EP_NAME.extensionList
            .filter { it.id in widgetIds }
            .forEach { factory ->
                settings.setEnabled(factory, false)
            }
    }
}
