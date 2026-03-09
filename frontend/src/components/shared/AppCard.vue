<template>
  <div :class="['app-card', { 'app-card--hoverable': hoverable, 'app-card--elevated': elevated }]">
    <div v-if="$slots.header" class="app-card__header">
      <slot name="header" />
    </div>
    <div class="app-card__body">
      <slot />
    </div>
    <div v-if="$slots.footer" class="app-card__footer">
      <slot name="footer" />
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps({
  hoverable: { type: Boolean, default: false },
  elevated: { type: Boolean, default: false },
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.app-card {
  background: $color-surface;
  border: 1px solid $color-border-light;
  border-radius: $radius-lg;
  overflow: hidden;
  box-shadow: $shadow-md;

  &--hoverable {
    transition: transform $transition-base, box-shadow $transition-base, border-color $transition-base;
    cursor: pointer;

    &:hover {
      transform: translateY(-3px);
      box-shadow: $shadow-lg;
      border-color: $color-primary;
    }
  }

  &--elevated {
    background: $color-surface-elevated;
    border-color: $color-border;
  }

  &__header {
    padding: $spacing-lg;
    border-bottom: 1px solid $color-border-light;
    background: linear-gradient(180deg, $color-surface-elevated, $color-surface);
  }

  &__body {
    padding: $spacing-lg;
  }

  &__footer {
    padding: $spacing-md $spacing-lg;
    border-top: 1px solid $color-border-light;
    background: rgba(0, 0, 0, 0.15);
  }
}
</style>
