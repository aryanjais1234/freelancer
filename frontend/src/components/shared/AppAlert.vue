<template>
  <Transition name="slide-up">
    <div v-if="visible" :class="['app-alert', `app-alert--${variant}`]" role="alert">
      <span class="app-alert__icon">{{ icons[variant] }}</span>
      <div class="app-alert__content">
        <strong v-if="title" class="app-alert__title">{{ title }}</strong>
        <p class="app-alert__message">{{ message }}</p>
      </div>
      <button v-if="dismissible" class="app-alert__dismiss" @click="dismiss" aria-label="Dismiss">✕</button>
    </div>
  </Transition>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps({
  variant: { type: String, default: 'info' },
  title: { type: String, default: '' },
  message: { type: String, default: '' },
  dismissible: { type: Boolean, default: true },
});

const emit = defineEmits(['dismiss']);

const visible = ref(true);

const icons: Record<string, string> = {
  info: 'ℹ️',
  success: '✅',
  warning: '⚠️',
  danger: '❌',
};

const dismiss = () => {
  visible.value = false;
  emit('dismiss');
};

defineExpose({ dismiss });
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;

.app-alert {
  display: flex;
  align-items: flex-start;
  gap: $spacing-md;
  padding: $spacing-md $spacing-lg;
  border-radius: $radius-md;
  border: 1px solid;

  &--info {
    background: rgba(59, 130, 246, 0.1);
    border-color: rgba(59, 130, 246, 0.3);
    color: #93c5fd;
  }

  &--success {
    background: rgba(16, 185, 129, 0.1);
    border-color: rgba(16, 185, 129, 0.3);
    color: $color-success-light;
  }

  &--warning {
    background: rgba(245, 158, 11, 0.1);
    border-color: rgba(245, 158, 11, 0.3);
    color: $color-secondary-light;
  }

  &--danger {
    background: rgba(239, 68, 68, 0.1);
    border-color: rgba(239, 68, 68, 0.3);
    color: $color-danger-light;
  }

  &__icon {
    font-size: $font-size-lg;
    flex-shrink: 0;
    margin-top: 1px;
  }

  &__content {
    flex: 1;
  }

  &__title {
    display: block;
    font-weight: $font-weight-semibold;
    margin-bottom: 2px;
  }

  &__message {
    font-size: $font-size-sm;
    opacity: 0.9;
  }

  &__dismiss {
    color: currentColor;
    opacity: 0.6;
    font-size: $font-size-sm;
    padding: 2px $spacing-xs;
    border-radius: $radius-sm;
    flex-shrink: 0;
    transition: opacity $transition-fast;
    background: none;
    border: none;
    cursor: pointer;

    &:hover { opacity: 1; }
  }
}
</style>
