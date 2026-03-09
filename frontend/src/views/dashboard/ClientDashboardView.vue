<template>
  <div class="page-content">
    <div class="container">
      <div class="dashboard">
        <div class="dashboard__header">
          <div>
            <h1 class="dashboard__title">Welcome back, {{ userName }} 👋</h1>
            <p class="dashboard__subtitle">Manage your projects and find top talent</p>
          </div>
          <RouterLink to="/projects/create">
            <AppButton variant="primary">➕ Post a Project</AppButton>
          </RouterLink>
        </div>

        <div class="dashboard__stats">
          <div class="stat-card">
            <div class="stat-card__icon">📋</div>
            <div class="stat-card__info">
              <span class="stat-card__value">{{ totalProjects }}</span>
              <span class="stat-card__label">Total Projects</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-card__icon">🟢</div>
            <div class="stat-card__info">
              <span class="stat-card__value">{{ openProjects }}</span>
              <span class="stat-card__label">Open Projects</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-card__icon">⚡</div>
            <div class="stat-card__info">
              <span class="stat-card__value">{{ inProgressProjects }}</span>
              <span class="stat-card__label">In Progress</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-card__icon">✅</div>
            <div class="stat-card__info">
              <span class="stat-card__value">{{ completedProjects }}</span>
              <span class="stat-card__label">Completed</span>
            </div>
          </div>
        </div>

        <div class="dashboard__section">
          <div class="dashboard__section-header">
            <h2>📋 My Projects</h2>
            <RouterLink to="/projects/create">
              <AppButton variant="ghost" size="sm">+ New Project</AppButton>
            </RouterLink>
          </div>

          <AppLoader v-if="isLoading" text="Loading projects..." />
          <AppAlert v-else-if="projectError" variant="danger" :message="projectError" />
          <ProjectList v-else :projects="myProjects" empty-message="You haven't posted any projects yet." />
        </div>

        <div class="dashboard__section">
          <h2 class="dashboard__section-title">🔍 Find Freelancers</h2>
          <RouterLink to="/freelancers">
            <AppButton variant="secondary">Browse All Talent →</AppButton>
          </RouterLink>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import AppButton from '../../components/shared/AppButton.vue';
import AppLoader from '../../components/shared/AppLoader.vue';
import AppAlert from '../../components/shared/AppAlert.vue';
import ProjectList from '../../components/project/ProjectList.vue';

const store = useStore();

const currentUser = computed(() => store.getters['auth/currentUser']);
const userName = computed(() => currentUser.value?.fullName?.split(' ')[0] || 'there');
const myProjects = computed(() => store.getters['client/projects']);
const isLoading = computed(() => store.getters['client/isLoading']);
const projectError = computed(() => store.getters['client/error']);

const totalProjects = computed(() => myProjects.value.length);
const openProjects = computed(() => myProjects.value.filter((p: any) => p.status === 'OPEN').length);
const inProgressProjects = computed(() => myProjects.value.filter((p: any) => p.status === 'IN_PROGRESS').length);
const completedProjects = computed(() => myProjects.value.filter((p: any) => p.status === 'COMPLETED').length);

onMounted(async () => {
  if (currentUser.value?.id) {
    try {
      await store.dispatch('client/fetchProjects', currentUser.value.id);
    } catch {
      // handled by store
    }
  }
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.dashboard {
  @include flex-col;
  gap: $spacing-2xl;

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

  &__stats {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: $spacing-lg;
  }

  &__section {
    @include flex-col;
    gap: $spacing-lg;
  }

  &__section-header {
    @include flex-between;

    h2 {
      font-size: $font-size-xl;
      font-weight: $font-weight-semibold;
      color: $color-text-primary;
    }
  }

  &__section-title {
    font-size: $font-size-xl;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }
}

.stat-card {
  background: $color-surface;
  border: 1px solid $color-border-light;
  border-radius: $radius-lg;
  padding: $spacing-lg;
  display: flex;
  align-items: center;
  gap: $spacing-lg;
  transition: transform $transition-base, box-shadow $transition-base;

  &:hover {
    transform: translateY(-2px);
    box-shadow: $shadow-md;
    border-color: $color-primary;
  }

  &__icon {
    font-size: 2.5rem;
    line-height: 1;
  }

  &__info {
    @include flex-col;
    gap: 4px;
  }

  &__value {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
    line-height: 1;
  }

  &__label {
    font-size: $font-size-sm;
    color: $color-text-muted;
  }
}
</style>
