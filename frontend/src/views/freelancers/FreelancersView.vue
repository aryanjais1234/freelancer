<template>
  <div class="page-content">
    <div class="container">
      <div class="freelancers-view">
        <div class="freelancers-view__header">
          <h1 class="freelancers-view__title">👥 Freelancers</h1>
          <p class="freelancers-view__subtitle">Discover talented professionals for your projects</p>
        </div>

        <div class="freelancers-view__search-bar">
          <AppInput
            v-model="searchQuery"
            placeholder="🔍 Search by name or skill..."
            class="freelancers-view__search"
            @keyup.enter="handleSkillSearch"
          />
          <AppButton variant="primary" @click="handleSkillSearch" :loading="isLoading">
            Search
          </AppButton>
          <AppButton v-if="searchQuery" variant="ghost" @click="clearSearch">
            Clear
          </AppButton>
        </div>

        <div class="freelancers-view__filters">
          <button
            :class="['freelancers-view__filter-btn', { 'freelancers-view__filter-btn--active': availabilityFilter === '' }]"
            @click="availabilityFilter = ''"
          >
            All
          </button>
          <button
            :class="['freelancers-view__filter-btn', { 'freelancers-view__filter-btn--active': availabilityFilter === 'AVAILABLE' }]"
            @click="availabilityFilter = 'AVAILABLE'"
          >
            🟢 Available
          </button>
          <button
            :class="['freelancers-view__filter-btn', { 'freelancers-view__filter-btn--active': availabilityFilter === 'BUSY' }]"
            @click="availabilityFilter = 'BUSY'"
          >
            🔴 Busy
          </button>
        </div>

        <AppAlert v-if="error" variant="danger" :message="error" />

        <div class="freelancers-view__count" v-if="!isLoading">
          {{ filteredFreelancers.length }} freelancer{{ filteredFreelancers.length !== 1 ? 's' : '' }} found
        </div>

        <AppLoader v-if="isLoading" size="md" text="Loading freelancers..." />
        <div v-else-if="filteredFreelancers.length === 0" class="freelancers-view__empty">
          <div>🔍</div>
          <h3>No freelancers found</h3>
          <p>Try adjusting your search terms</p>
        </div>
        <div v-else class="freelancers-view__grid">
          <FreelancerCard v-for="f in filteredFreelancers" :key="f.freelancerId" :freelancer="f" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import AppInput from '../../components/shared/AppInput.vue';
import AppButton from '../../components/shared/AppButton.vue';
import AppLoader from '../../components/shared/AppLoader.vue';
import AppAlert from '../../components/shared/AppAlert.vue';
import FreelancerCard from '../../components/freelancer/FreelancerCard.vue';
import type { FreelancerUI } from '../../types/freelancer.types';

const store = useStore();
const searchQuery = ref('');
const availabilityFilter = ref('');
const usingSearch = ref(false);

const allFreelancers = computed(() => store.getters['freelancer/allFreelancers']);
const searchResults = computed(() => store.getters['freelancer/searchResults']);
const isLoading = computed(() => store.getters['freelancer/isLoading']);
const error = computed(() => store.getters['freelancer/error']);

const displayList = computed(() => usingSearch.value ? searchResults.value : allFreelancers.value);

const filteredFreelancers = computed(() =>
  displayList.value.filter((f: FreelancerUI) =>
    !availabilityFilter.value || f.availabilityStatus === availabilityFilter.value
  )
);

const handleSkillSearch = async () => {
  if (!searchQuery.value.trim()) {
    clearSearch();
    return;
  }
  usingSearch.value = true;
  await store.dispatch('freelancer/searchBySkill', searchQuery.value.trim());
};

const clearSearch = () => {
  searchQuery.value = '';
  usingSearch.value = false;
};

onMounted(() => store.dispatch('freelancer/fetchAll'));
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.freelancers-view {
  @include flex-col;
  gap: $spacing-xl;

  &__header {
    @include flex-col;
    gap: $spacing-xs;
  }

  &__title {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
  }

  &__subtitle { color: $color-text-secondary; }

  &__search-bar {
    display: flex;
    gap: $spacing-md;
    align-items: flex-end;
  }

  &__search { flex: 1; }

  &__filters {
    display: flex;
    gap: $spacing-sm;
    flex-wrap: wrap;
  }

  &__filter-btn {
    padding: $spacing-xs $spacing-md;
    border-radius: $radius-full;
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    cursor: pointer;
    border: 1px solid $color-border;
    background: $color-surface;
    color: $color-text-secondary;
    transition: all $transition-fast;

    &:hover {
      border-color: $color-primary;
      color: $color-text-primary;
    }

    &--active {
      background: rgba(99, 102, 241, 0.15);
      border-color: $color-primary;
      color: $color-primary-light;
    }
  }

  &__count {
    font-size: $font-size-sm;
    color: $color-text-muted;
  }

  &__empty {
    @include flex-col;
    align-items: center;
    text-align: center;
    padding: $spacing-3xl;
    color: $color-text-secondary;
    gap: $spacing-md;

    div { font-size: 4rem; opacity: 0.5; }
    h3 { font-size: $font-size-xl; font-weight: $font-weight-semibold; color: $color-text-primary; }
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
