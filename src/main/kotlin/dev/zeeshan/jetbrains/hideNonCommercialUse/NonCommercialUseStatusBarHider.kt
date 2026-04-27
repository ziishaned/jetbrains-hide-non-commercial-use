package dev.zeeshan.jetbrains.hideNonCommercialUse

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import javax.swing.AbstractButton
import javax.swing.JComponent
import javax.swing.JLabel

object NonCommercialUseStatusBarHider {
    private const val TARGET_TEXT = "Non-commercial use"

    fun hideIn(root: Container): Int {
        val matches = root
            .allDescendants()
            .filter { it.isVisible && it.visibleText().matchesTargetText() }
            .map { it.hideTargetWithin(root) }
            .distinct()
            .toList()

        matches.forEach { component ->
            component.isVisible = false
            component.minimumSize = Dimension(0, 0)
            component.preferredSize = Dimension(0, 0)
            component.maximumSize = Dimension(0, 0)
            component.parent?.revalidate()
            component.parent?.repaint()
        }

        if (matches.isNotEmpty()) {
            root.revalidate()
            root.repaint()
        }

        return matches.size
    }

    private fun Container.allDescendants(): Sequence<Component> = sequence {
        for (component in components) {
            yield(component)
            if (component is Container) {
                yieldAll(component.allDescendants())
            }
        }
    }

    private fun Component.visibleText(): String? = when (this) {
        is JLabel -> text
        is AbstractButton -> text
        else -> reflectedText() ?: accessibleContext?.accessibleName ?: accessibleContext?.accessibleDescription ?: toolTipText()
    }

    private fun Component.reflectedText(): String? {
        if (this !is JComponent) {
            return null
        }

        return runCatching {
            val method = javaClass.getMethod("getText")
            method.invoke(this) as? String
        }.getOrNull()
    }

    private fun Component.toolTipText(): String? =
        (this as? JComponent)?.toolTipText

    private fun Component.hideTargetWithin(root: Container): Component {
        var target = this
        var parent = target.parent

        while (parent != null && parent != root && parent.components.count { it.isVisible } == 1) {
            target = parent
            parent = target.parent
        }

        return target
    }

    private fun String?.matchesTargetText(): Boolean =
        this
            ?.trim()
            ?.replace(Regex("\\s+"), " ")
            ?.equals(TARGET_TEXT, ignoreCase = true)
            ?: false
}
