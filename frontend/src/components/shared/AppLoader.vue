<template>
  <div :class="['app-loader', `app-loader--${size}`, { 'app-loader--overlay': overlay }]">
    <div class="app-loader__spinner">
      <div class="app-loader__ring"></div>
      <div class="app-loader__ring app-loader__ring--inner"></div>
    </div>
    <p v-if="text" class="app-loader__text">{{ text }}</p>
  </div>
</template>

<script setup lang="ts">
defineProps({
  size: { type: String, default: 'md' },
  overlay: { type: Boolean, default: false },
  text: { type: String, default: '' },
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;

.app-loader {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: $spacing-md;

  &--overlay {
    position: fixed;
    inset: 0;
    background: rgba($color-background, 0.85);
    z-index: 999;
    backdrop-filter: blur(4px);
  }

  &--sm &__spinner { width: 28px; height: 28px; }
  &--md &__spinner { width: 44px; height: 44px; }
  &--lg &__spinner { width: 64px; height: 64px; }

  &__spinner {
    position: relative;
  }

  &__ring {
    position: absolute;
    inset: 0;
    border-radius: 50%;
    border: 3px solid transparent;
    border-top-color: $color-primary;
    animation: spin 1s linear infinite;

    &--inner {
      inset: 6px;
      border-top-color: $color-secondary;
      animation-duration: 0.7s;
      animation-direction: reverse;
    }
  }

  &__text {
    color: $color-text-secondary;
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
  }
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>
