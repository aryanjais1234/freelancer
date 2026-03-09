<template>
  <span :class="['app-badge', `app-badge--${variant}`, `app-badge--${size}`]">
    <span v-if="dot" class="app-badge__dot"></span>
    <slot />
  </span>
</template>

<script setup lang="ts">
defineProps({
  variant: { type: String, default: 'default' },
  size: { type: String, default: 'md' },
  dot: { type: Boolean, default: false },
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;

.app-badge {
  display: inline-flex;
  align-items: center;
  gap: $spacing-xs;
  font-weight: $font-weight-semibold;
  border-radius: $radius-full;
  border: 1px solid transparent;

  &--sm {
    padding: 2px $spacing-sm;
    font-size: $font-size-xs;
  }

  &--md {
    padding: 4px 10px;
    font-size: $font-size-xs;
  }

  &--lg {
    padding: $spacing-xs $spacing-md;
    font-size: $font-size-sm;
  }

  &--default {
    background: $color-surface-elevated;
    color: $color-text-secondary;
    border-color: $color-border;
  }

  &--primary {
    background: rgba(99, 102, 241, 0.15);
    color: $color-primary-light;
    border-color: rgba(99, 102, 241, 0.3);
  }

  &--success {
    background: rgba(16, 185, 129, 0.15);
    color: $color-success-light;
    border-color: rgba(16, 185, 129, 0.3);
  }

  &--danger {
    background: rgba(239, 68, 68, 0.15);
    color: $color-danger-light;
    border-color: rgba(239, 68, 68, 0.3);
  }

  &--warning {
    background: rgba(245, 158, 11, 0.15);
    color: $color-secondary-light;
    border-color: rgba(245, 158, 11, 0.3);
  }

  &--info {
    background: rgba(59, 130, 246, 0.15);
    color: #93c5fd;
    border-color: rgba(59, 130, 246, 0.3);
  }

  &__dot {
    width: 6px;
    height: 6px;
    border-radius: 50%;
    background: currentColor;
    flex-shrink: 0;
  }
}
</style>
