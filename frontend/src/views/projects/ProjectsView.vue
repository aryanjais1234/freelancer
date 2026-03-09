<template>
  <div class="page-content">
    <div class="container">
      <div class="projects-view">
        <div class="projects-view__header">
          <div>
            <h1 class="projects-view__title">💼 Projects</h1>
            <p class="projects-view__subtitle">{{ isClient ? 'Your posted projects' : 'Find your next opportunity' }}</p>
          </div>
          <RouterLink v-if="isClient" to="/projects/create">
            <AppButton variant="primary">➕ Post Project</AppButton>
          </RouterLink>
        </div>

        <div class="projects-view__filters">
          <AppInput
            v-model="searchQuery"
            placeholder="🔍 Search projects..."
            class="projects-view__search"
          />
          <div class="projects-view__filter-group">
            <select v-model="statusFilter" class="projects-view__select">
              <option value="">All Status</option>
              <option value="OPEN">Open</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="COMPLETED">Completed</option>
            </select>
            <select v-model="categoryFilter" class="projects-view__select">
              <option value="">All Categories</option>
              <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
            </select>
          </div>
        </div>

        <AppAlert v-if="error" variant="danger" :message="error" />

        <div class="projects-view__count" v-if="!isLoading">
          {{ filteredProjects.length }} project{{ filteredProjects.length !== 1 ? 's' : '' }} found
        </div>

        <ProjectList :projects="filteredProjects" :loading="isLoading" empty-message="No projects match your filters." />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import AppButton from '../../components/shared/AppButton.vue';
import AppInput from '../../components/shared/AppInput.vue';
import AppAlert from '../../components/shared/AppAlert.vue';
import ProjectList from '../../components/project/ProjectList.vue';
import type { ProjectUI } from '../../types/project.types';

const store = useStore();
const searchQuery = ref('');
const statusFilter = ref('');
const categoryFilter = ref('');

const isClient = computed(() => store.getters['auth/isClient']);
const allProjects = computed(() => store.getters['project/allProjects']);
const isLoading = computed(() => store.getters['project/isLoading']);
const error = computed(() => store.getters['project/error']);

const categories = computed((): string[] => {
  const cats = [...new Set(allProjects.value.map((p: ProjectUI) => p.category).filter(Boolean))] as string[];
  return cats.sort();
});

const filteredProjects = computed(() => {
  return allProjects.value.filter((p: ProjectUI) => {
    const matchesSearch = !searchQuery.value ||
      p.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      p.description.toLowerCase().includes(searchQuery.value.toLowerCase());
    const matchesStatus = !statusFilter.value || p.status === statusFilter.value;
    const matchesCategory = !categoryFilter.value || p.category === categoryFilter.value;
    return matchesSearch && matchesStatus && matchesCategory;
  });
});

onMounted(() => store.dispatch('project/fetchAll'));
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.projects-view {
  @include flex-col;
  gap: $spacing-xl;

  &__header {
    @include flex-between;
    flex-wrap: wrap;
    gap: $spacing-md;
  }

  &__title {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
  }

  &__subtitle {
    color: $color-text-secondary;
    margin-top: $spacing-xs;
  }

  &__filters {
    display: flex;
    gap: $spacing-md;
    flex-wrap: wrap;
    align-items: center;
  }

  &__search {
    flex: 1;
    min-width: 250px;
  }

  &__filter-group {
    display: flex;
    gap: $spacing-md;
  }

  &__select {
    background: $color-surface-elevated;
    border: 1px solid $color-border;
    border-radius: $radius-md;
    color: $color-text-primary;
    padding: $spacing-sm $spacing-md;
    font-size: $font-size-sm;
    cursor: pointer;
    outline: none;
    transition: border-color $transition-fast;

    &:focus { border-color: $color-primary; }

    option { background: $color-surface; }
  }

  &__count {
    font-size: $font-size-sm;
    color: $color-text-muted;
  }
}
</style>
