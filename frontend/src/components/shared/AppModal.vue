<template>
  <Teleport to="body">
    <Transition name="modal">
      <div v-if="modelValue" class="app-modal" @click.self="onOverlayClick">
        <div :class="['app-modal__dialog', `app-modal__dialog--${size}`]">
          <div class="app-modal__header">
            <h3 class="app-modal__title">{{ title }}</h3>
            <button class="app-modal__close" @click="$emit('update:modelValue', false)" aria-label="Close">
              ✕
            </button>
          </div>
          <div class="app-modal__body">
            <slot />
          </div>
          <div v-if="$slots.footer" class="app-modal__footer">
            <slot name="footer" />
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup lang="ts">
const props = defineProps({
  modelValue: { type: Boolean, default: false },
  title: { type: String, default: '' },
  size: { type: String, default: 'md' },
  closeOnOverlay: { type: Boolean, default: true },
});

const emit = defineEmits(['update:modelValue']);

const onOverlayClick = () => {
  if (props.closeOnOverlay) emit('update:modelValue', false);
};
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;

.app-modal {
  position: fixed;
  inset: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(4px);
  padding: $spacing-lg;

  &__dialog {
    background: $color-surface;
    border: 1px solid $color-border;
    border-radius: $radius-xl;
    width: 100%;
    max-height: 90vh;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    box-shadow: $shadow-lg;

    &--sm { max-width: 400px; }
    &--md { max-width: 600px; }
    &--lg { max-width: 800px; }
    &--xl { max-width: 1000px; }
  }

  &__header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: $spacing-lg $spacing-xl;
    border-bottom: 1px solid $color-border-light;
    flex-shrink: 0;
  }

  &__title {
    font-size: $font-size-xl;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }

  &__close {
    color: $color-text-muted;
    font-size: $font-size-lg;
    padding: $spacing-xs;
    border-radius: $radius-sm;
    transition: color $transition-fast, background $transition-fast;
    cursor: pointer;
    background: none;
    border: none;

    &:hover {
      color: $color-text-primary;
      background: $color-surface-elevated;
    }
  }

  &__body {
    padding: $spacing-xl;
    overflow-y: auto;
    flex: 1;
  }

  &__footer {
    padding: $spacing-lg $spacing-xl;
    border-top: 1px solid $color-border-light;
    display: flex;
    gap: $spacing-md;
    justify-content: flex-end;
    flex-shrink: 0;
  }
}

.modal-enter-active, .modal-leave-active {
  transition: opacity 0.25s ease;

  .app-modal__dialog {
    transition: transform 0.25s ease, opacity 0.25s ease;
  }
}

.modal-enter-from, .modal-leave-to {
  opacity: 0;

  .app-modal__dialog {
    transform: scale(0.95) translateY(-10px);
    opacity: 0;
  }
}
</style>
