package dev.zeeshan.jetbrains.hideNonCommercialUse

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LicenseWidgetSuppressorTest {
    @Test
    fun `targets non-commercial license widget IDs`() {
        assertTrue("NonCommercial" in LicenseWidgetSuppressor.widgetIds)
        assertTrue("TrialStatusBarWidget" in LicenseWidgetSuppressor.widgetIds)
    }
}
