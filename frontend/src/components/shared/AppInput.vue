<template>
  <div class="app-input">
    <label v-if="label" :for="inputId" class="app-input__label">
      {{ label }}
      <span v-if="required" class="app-input__required">*</span>
    </label>
    <div class="app-input__wrapper">
      <span v-if="$slots.prefix" class="app-input__prefix">
        <slot name="prefix" />
      </span>
      <component
        :is="type === 'textarea' ? 'textarea' : 'input'"
        :id="inputId"
        :type="type !== 'textarea' ? type : undefined"
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :required="required"
        :rows="rows"
        :class="['app-input__field', { 'app-input__field--has-prefix': $slots.prefix, 'app-input__field--has-suffix': $slots.suffix, 'app-input__field--error': error, 'app-input__field--textarea': type === 'textarea' }]"
        @input="$emit('update:modelValue', ($event.target as HTMLInputElement).value)"
        @blur="$emit('blur', $event)"
        @focus="$emit('focus', $event)"
      />
      <span v-if="$slots.suffix" class="app-input__suffix">
        <slot name="suffix" />
      </span>
    </div>
    <span v-if="error" class="app-input__error">{{ error }}</span>
    <span v-else-if="hint" class="app-input__hint">{{ hint }}</span>
  </div>
</template>

<script setup lang="ts">
import { computed, getCurrentInstance } from 'vue';

const props = defineProps({
  modelValue: { type: [String, Number], default: '' },
  label: { type: String, default: '' },
  type: { type: String, default: 'text' },
  placeholder: { type: String, default: '' },
  disabled: { type: Boolean, default: false },
  required: { type: Boolean, default: false },
  error: { type: String, default: '' },
  hint: { type: String, default: '' },
  rows: { type: Number, default: 4 },
});

defineEmits(['update:modelValue', 'blur', 'focus']);

const instance = getCurrentInstance();
const inputId = computed(() => `input-${instance?.uid ?? 0}`);
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.app-input {
  @include flex-col;
  gap: $spacing-xs;

  &__label {
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    color: $color-text-secondary;
  }

  &__required {
    color: $color-danger;
    margin-left: 2px;
  }

  &__wrapper {
    position: relative;
    display: flex;
    align-items: center;
  }

  &__field {
    @include input-base;

    &--has-prefix { padding-left: 40px; }
    &--has-suffix { padding-right: 40px; }
    &--error {
      border-color: $color-danger;
      &:focus { box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.2); }
    }
    &--textarea {
      resize: vertical;
      min-height: 100px;
    }
  }

  &__prefix, &__suffix {
    position: absolute;
    display: flex;
    align-items: center;
    color: $color-text-muted;
    pointer-events: none;
  }

  &__prefix { left: $spacing-md; }
  &__suffix { right: $spacing-md; }

  &__error {
    font-size: $font-size-xs;
    color: $color-danger;
  }

  &__hint {
    font-size: $font-size-xs;
    color: $color-text-muted;
  }
}
</style>
