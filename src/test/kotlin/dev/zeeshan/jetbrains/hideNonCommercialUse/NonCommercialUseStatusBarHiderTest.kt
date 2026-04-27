package dev.zeeshan.jetbrains.hideNonCommercialUse

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.awt.BorderLayout
import javax.swing.JLabel
import javax.swing.JPanel

class NonCommercialUseStatusBarHiderTest {
    @Test
    fun `hides matching label nested inside status bar component`() {
        val root = JPanel(BorderLayout())
        val left = JLabel("playground")
        val badge = JLabel("Non-commercial use")
        root.add(left, BorderLayout.WEST)
        root.add(badge, BorderLayout.EAST)

        val hiddenCount = NonCommercialUseStatusBarHider.hideIn(root)

        assertEquals(1, hiddenCount)
        assertTrue(left.isVisible)
        assertFalse(badge.isVisible)
    }

    @Test
    fun `ignores unrelated labels`() {
        val root = JPanel()
        val label = JLabel("Commercial license")
        root.add(label)

        val hiddenCount = NonCommercialUseStatusBarHider.hideIn(root)

        assertEquals(0, hiddenCount)
        assertTrue(label.isVisible)
    }

    @Test
    fun `normalizes whitespace when matching status text`() {
        val root = JPanel()
        val badge = JLabel("  Non-commercial   use  ")
        root.add(badge)

        val hiddenCount = NonCommercialUseStatusBarHider.hideIn(root)

        assertEquals(1, hiddenCount)
        assertFalse(badge.isVisible)
    }

    @Test
    fun `hides compact wrapper around matching label`() {
        val root = JPanel()
        val wrapper = JPanel()
        val badge = JLabel("Non-commercial use")
        wrapper.add(badge)
        root.add(wrapper)

        val hiddenCount = NonCommercialUseStatusBarHider.hideIn(root)

        assertEquals(1, hiddenCount)
        assertFalse(wrapper.isVisible)
        assertTrue(root.isVisible)
    }

    @Test
    fun `hides component matched by accessible name`() {
        val root = JPanel()
        val badge = JPanel()
        badge.accessibleContext.accessibleName = "Non-commercial use"
        root.add(badge)

        val hiddenCount = NonCommercialUseStatusBarHider.hideIn(root)

        assertEquals(1, hiddenCount)
        assertFalse(badge.isVisible)
    }
}
