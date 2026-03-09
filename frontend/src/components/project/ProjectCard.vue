<template>
  <RouterLink :to="`/projects/${project.id}`" class="project-card">
    <div class="project-card__header">
      <AppBadge :variant="statusVariant" dot>{{ project.status.replace('_', ' ') }}</AppBadge>
      <AppBadge variant="default">{{ project.category }}</AppBadge>
    </div>

    <h3 class="project-card__title">{{ project.title }}</h3>
    <p class="project-card__description">{{ project.description }}</p>

    <div class="project-card__meta">
      <div class="project-card__meta-item">
        <span class="project-card__meta-icon">💰</span>
        <span>${{ budgetDisplay }}</span>
      </div>
      <div class="project-card__meta-item">
        <span class="project-card__meta-icon">⏱️</span>
        <span>{{ project.duration || 'Flexible' }}</span>
      </div>
      <div v-if="project.deadline" class="project-card__meta-item">
        <span class="project-card__meta-icon">📅</span>
        <span>{{ deadlineDisplay }}</span>
      </div>
    </div>

    <div class="project-card__footer">
      <span class="project-card__date">Posted {{ postedAt }}</span>
      <span class="project-card__arrow">→</span>
    </div>
  </RouterLink>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import type { ProjectUI } from '../../types/project.types';
import AppBadge from '../shared/AppBadge.vue';

const props = defineProps<{ project: ProjectUI }>();

const statusVariant = computed(() => ({
  OPEN: 'success',
  IN_PROGRESS: 'warning',
  COMPLETED: 'default',
}[props.project.status] || 'default'));

const budgetDisplay = computed(() => {
  const { budget, budgetMin, budgetMax } = props.project;
  if (budgetMin && budgetMax) return `${budgetMin.toLocaleString()} – ${budgetMax.toLocaleString()}`;
  if (budget) return budget.toLocaleString();
  return 'Negotiable';
});

const deadlineDisplay = computed(() => {
  if (!props.project.deadline) return '';
  return props.project.deadline.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
});

const postedAt = computed(() => {
  const diff = Date.now() - props.project.createdAt.getTime();
  const days = Math.floor(diff / 86400000);
  if (days === 0) return 'today';
  if (days === 1) return 'yesterday';
  return `${days} days ago`;
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.project-card {
  @include card;
  display: flex;
  flex-direction: column;
  gap: $spacing-md;
  text-decoration: none;
  color: inherit;

  &:hover {
    border-color: $color-primary;
    box-shadow: $shadow-glow;
  }

  &__header {
    display: flex;
    gap: $spacing-sm;
    flex-wrap: wrap;
  }

  &__title {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
    line-height: $line-height-tight;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }

  &__description {
    font-size: $font-size-sm;
    color: $color-text-secondary;
    line-height: $line-height-relaxed;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    flex: 1;
  }

  &__meta {
    display: flex;
    flex-wrap: wrap;
    gap: $spacing-md;
  }

  &__meta-item {
    display: flex;
    align-items: center;
    gap: $spacing-xs;
    font-size: $font-size-sm;
    color: $color-text-secondary;
    font-weight: $font-weight-medium;
  }

  &__meta-icon {
    font-size: $font-size-base;
  }

  &__footer {
    @include flex-between;
    margin-top: auto;
    padding-top: $spacing-sm;
    border-top: 1px solid $color-border-light;
  }

  &__date {
    font-size: $font-size-xs;
    color: $color-text-muted;
  }

  &__arrow {
    color: $color-primary;
    font-size: $font-size-lg;
    transition: transform $transition-fast;
  }

  &:hover &__arrow {
    transform: translateX(4px);
  }
}
</style>
