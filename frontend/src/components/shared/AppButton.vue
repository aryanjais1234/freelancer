<template>
  <button
    :class="['app-button', `app-button--${variant}`, `app-button--${size}`, { 'app-button--loading': loading, 'app-button--full': fullWidth }]"
    :disabled="disabled || loading"
    v-bind="$attrs"
  >
    <span v-if="loading" class="app-button__spinner" aria-hidden="true"></span>
    <span v-if="$slots.icon && !loading" class="app-button__icon">
      <slot name="icon" />
    </span>
    <span class="app-button__text"><slot /></span>
  </button>
</template>

<script setup lang="ts">
defineProps({
  variant: { type: String, default: 'primary' },
  size: { type: String, default: 'md' },
  loading: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
  fullWidth: { type: Boolean, default: false },
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.app-button {
  @include button-base;

  &--primary {
    background: $gradient-primary;
    color: $color-text-primary;
    box-shadow: 0 4px 15px rgba(99, 102, 241, 0.3);

    &:hover:not(:disabled) {
      transform: translateY(-1px);
      box-shadow: 0 6px 20px rgba(99, 102, 241, 0.4);
    }

    &:active:not(:disabled) {
      transform: translateY(0);
    }
  }

  &--secondary {
    background: $color-surface-elevated;
    color: $color-text-primary;
    border: 1px solid $color-border;

    &:hover:not(:disabled) {
      background: $color-surface-hover;
      border-color: $color-primary;
    }
  }

  &--danger {
    background: $gradient-danger;
    color: $color-text-primary;

    &:hover:not(:disabled) {
      transform: translateY(-1px);
      box-shadow: 0 4px 15px rgba(239, 68, 68, 0.3);
    }
  }

  &--success {
    background: $gradient-success;
    color: $color-text-primary;

    &:hover:not(:disabled) {
      transform: translateY(-1px);
      box-shadow: $shadow-glow-success;
    }
  }

  &--ghost {
    background: transparent;
    color: $color-text-secondary;
    border: 1px solid $color-border;

    &:hover:not(:disabled) {
      color: $color-text-primary;
      border-color: $color-primary;
      background: rgba(99, 102, 241, 0.1);
    }
  }

  &--outline {
    background: transparent;
    color: $color-primary;
    border: 1px solid $color-primary;

    &:hover:not(:disabled) {
      background: rgba(99, 102, 241, 0.15);
    }
  }

  &--sm {
    padding: $spacing-xs $spacing-md;
    font-size: $font-size-xs;
    border-radius: $radius-sm;
  }

  &--md {
    padding: 10px $spacing-lg;
    font-size: $font-size-sm;
  }

  &--lg {
    padding: $spacing-md $spacing-xl;
    font-size: $font-size-base;
    border-radius: $radius-lg;
  }

  &--full {
    width: 100%;
    justify-content: center;
  }

  &--loading {
    cursor: wait;
  }

  &__spinner {
    width: 16px;
    height: 16px;
    border: 2px solid rgba(255, 255, 255, 0.3);
    border-top-color: white;
    border-radius: 50%;
    animation: spin 0.7s linear infinite;
    flex-shrink: 0;
  }

  &__icon {
    display: flex;
    align-items: center;
  }
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>
