package dev.zeeshan.jetbrains.hideNonCommercialUse

import com.intellij.openapi.wm.CustomStatusBarWidget
import com.intellij.openapi.wm.StatusBar
import java.awt.Dimension
import javax.swing.JComponent

object LicenseStatusBarWidgetHider {
    val widgetIds = setOf(
        "NonCommercial",
        "TrialStatusBarWidget",
    )

    fun hideWidgetsIn(statusBar: StatusBar): Int =
        widgetIds.count { widgetId ->
            val widget = statusBar.getWidget(widgetId) as? CustomStatusBarWidget ?: return@count false
            hide(widget.component)
        }

    private fun hide(component: JComponent): Boolean {
        val wasVisible = component.isVisible
        component.isVisible = false
        component.minimumSize = Dimension(0, 0)
        component.preferredSize = Dimension(0, 0)
        component.maximumSize = Dimension(0, 0)
        component.parent?.revalidate()
        component.parent?.repaint()
        return wasVisible
    }
}
