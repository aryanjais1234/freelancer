<template>
  <div class="project-list">
    <div v-if="loading" class="project-list__loading">
      <AppLoader size="md" text="Loading projects..." />
    </div>

    <div v-else-if="projects.length === 0" class="project-list__empty">
      <div class="project-list__empty-icon">📭</div>
      <h3>No projects found</h3>
      <p>{{ emptyMessage }}</p>
    </div>

    <div v-else class="project-list__grid">
      <ProjectCard v-for="project in projects" :key="project.id" :project="project" />
    </div>
  </div>
</template>

<script setup lang="ts">
import type { ProjectUI } from '../../types/project.types';
import ProjectCard from './ProjectCard.vue';
import AppLoader from '../shared/AppLoader.vue';

withDefaults(defineProps<{
  projects: ProjectUI[];
  loading?: boolean;
  emptyMessage?: string;
}>(), {
  loading: false,
  emptyMessage: 'No projects available at the moment.',
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.project-list {
  &__loading {
    @include flex-center;
    padding: $spacing-3xl;
  }

  &__empty {
    @include flex-col;
    align-items: center;
    text-align: center;
    padding: $spacing-3xl $spacing-lg;
    color: $color-text-secondary;
    gap: $spacing-md;

    &-icon {
      font-size: 4rem;
      opacity: 0.5;
    }

    h3 {
      font-size: $font-size-xl;
      font-weight: $font-weight-semibold;
      color: $color-text-primary;
    }

    p { font-size: $font-size-sm; }
  }

  &__grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
    gap: $spacing-lg;

    @include respond-to(sm) {
      grid-template-columns: 1fr;
    }
  }
}
</style>
