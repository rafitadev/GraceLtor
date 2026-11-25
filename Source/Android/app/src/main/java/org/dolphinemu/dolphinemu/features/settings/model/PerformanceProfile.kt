// SPDX-License-Identifier: GPL-2.0-or-later

package org.dolphinemu.dolphinemu.features.settings.model

/**
 * One-tap presets for weak phones. Each preset rewrites every performance-related setting it
 * knows about, so switching between presets always gives the same result.
 *
 * VERY_LOW and LOW write explicit values. DEFAULT removes those values again, so Dolphin's own
 * defaults (and per-game settings) apply.
 */
enum class PerformanceProfile(
    /** Matches ShaderCompilationMode in VideoConfig.h: 0 = Synchronous, 3 = Skip Rendering. */
    private val shaderCompilationMode: Int?
) {
    VERY_LOW(3),
    LOW(0),
    DEFAULT(null);

    fun apply(settings: Settings) {
        if (this == DEFAULT) {
            for (setting in managedSettings) {
                setting.delete(settings)
            }
            return
        }

        IntSetting.GFX_SHADER_COMPILATION_MODE.setInt(settings, shaderCompilationMode!!)

        IntSetting.GFX_EFB_SCALE.setInt(settings, 1) // Native resolution
        IntSetting.GFX_MSAA.setInt(settings, 1)
        IntSetting.GFX_ENHANCE_MAX_ANISOTROPY.setInt(settings, 0)
        IntSetting.GFX_ENHANCE_FORCE_TEXTURE_FILTERING.setInt(settings, 0)

        BooleanSetting.MAIN_CPU_THREAD.setBoolean(settings, true)
        BooleanSetting.MAIN_DSP_HLE.setBoolean(settings, true)
        BooleanSetting.GFX_BACKEND_MULTITHREADING.setBoolean(settings, true)
        BooleanSetting.GFX_WAIT_FOR_SHADERS_BEFORE_STARTING.setBoolean(settings, false)
        BooleanSetting.GFX_ENABLE_PIXEL_LIGHTING.setBoolean(settings, false)
        BooleanSetting.GFX_ENABLE_GPU_TEXTURE_DECODING.setBoolean(settings, false)
        BooleanSetting.GFX_HACK_EFB_ACCESS_ENABLE.setBoolean(settings, false)
        BooleanSetting.GFX_HACK_BBOX_ENABLE.setBoolean(settings, false)
    }

    private companion object {
        val managedSettings: List<AbstractSetting> = listOf(
            IntSetting.GFX_SHADER_COMPILATION_MODE,
            IntSetting.GFX_EFB_SCALE,
            IntSetting.GFX_MSAA,
            IntSetting.GFX_ENHANCE_MAX_ANISOTROPY,
            IntSetting.GFX_ENHANCE_FORCE_TEXTURE_FILTERING,
            BooleanSetting.MAIN_CPU_THREAD,
            BooleanSetting.MAIN_DSP_HLE,
            BooleanSetting.GFX_BACKEND_MULTITHREADING,
            BooleanSetting.GFX_WAIT_FOR_SHADERS_BEFORE_STARTING,
            BooleanSetting.GFX_ENABLE_PIXEL_LIGHTING,
            BooleanSetting.GFX_ENABLE_GPU_TEXTURE_DECODING,
            BooleanSetting.GFX_HACK_EFB_ACCESS_ENABLE,
            BooleanSetting.GFX_HACK_BBOX_ENABLE
        )
    }
}
