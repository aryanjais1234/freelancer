<template>
  <div class="page-content">
    <div class="container">
      <div class="dashboard">
        <div class="dashboard__header">
          <div>
            <h1 class="dashboard__title">Hey, {{ userName }} 👋</h1>
            <p class="dashboard__subtitle">Discover opportunities and grow your career</p>
          </div>
          <AppBadge :variant="availabilityStatus === 'AVAILABLE' ? 'success' : 'warning'" size="lg" dot>
            {{ availabilityStatus || 'Status Unknown' }}
          </AppBadge>
        </div>

        <div class="dashboard__stats">
          <div class="stat-card">
            <div class="stat-card__icon">📤</div>
            <div class="stat-card__info">
              <span class="stat-card__value">{{ totalBids }}</span>
              <span class="stat-card__label">Total Bids</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-card__icon">⏳</div>
            <div class="stat-card__info">
              <span class="stat-card__value">{{ pendingBids }}</span>
              <span class="stat-card__label">Pending</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-card__icon">✅</div>
            <div class="stat-card__info">
              <span class="stat-card__value">{{ acceptedBids }}</span>
              <span class="stat-card__label">Accepted</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-card__icon">🏆</div>
            <div class="stat-card__info">
              <span class="stat-card__value">{{ completedCount }}</span>
              <span class="stat-card__label">Completed</span>
            </div>
          </div>
        </div>

        <div class="dashboard__section">
          <div class="dashboard__section-header">
            <h2>💼 Open Projects</h2>
            <RouterLink to="/projects">
              <AppButton variant="ghost" size="sm">View All →</AppButton>
            </RouterLink>
          </div>
          <AppLoader v-if="projectsLoading" text="Loading projects..." />
          <ProjectList v-else :projects="openProjects.slice(0, 6)" empty-message="No open projects available right now." />
        </div>

        <div class="dashboard__section">
          <h2 class="dashboard__section-title">📤 My Bids</h2>
          <AppLoader v-if="bidsLoading" text="Loading bids..." />
          <div v-else-if="myBids.length === 0" class="dashboard__empty">
            <p>📭 You haven't submitted any bids yet.</p>
            <RouterLink to="/projects">
              <AppButton variant="primary" size="sm">Browse Projects</AppButton>
            </RouterLink>
          </div>
          <div v-else class="bids-list">
            <BidCard v-for="bid in myBids" :key="bid.bidId" :bid="bid" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import AppButton from '../../components/shared/AppButton.vue';
import AppBadge from '../../components/shared/AppBadge.vue';
import AppLoader from '../../components/shared/AppLoader.vue';
import ProjectList from '../../components/project/ProjectList.vue';
import BidCard from '../../components/bid/BidCard.vue';

const store = useStore();
const currentUser = computed(() => store.getters['auth/currentUser']);
const userName = computed(() => currentUser.value?.fullName?.split(' ')[0] || 'there');
const freelancerProfile = computed(() => store.getters['freelancer/profile']);
const availabilityStatus = computed(() => freelancerProfile.value?.availabilityStatus || 'AVAILABLE');
const openProjects = computed(() => store.getters['project/openProjects']);
const myBids = computed(() => store.getters['bid/freelancerBids']);
const projectsLoading = computed(() => store.getters['project/isLoading']);
const bidsLoading = computed(() => store.getters['bid/isLoading']);
const completedCount = computed(() => freelancerProfile.value?.completedProjects || 0);

const totalBids = computed(() => myBids.value.length);
const pendingBids = computed(() => myBids.value.filter((b: any) => b.status === 'PENDING').length);
const acceptedBids = computed(() => myBids.value.filter((b: any) => b.status === 'ACCEPTED').length);

onMounted(async () => {
  await store.dispatch('project/fetchOpen');
  if (currentUser.value?.id) {
    try {
      const profile = await store.dispatch('freelancer/fetchProfile', currentUser.value.id);
      if (profile?.freelancerId) {
        await store.dispatch('bid/fetchByFreelancer', profile.freelancerId);
      }
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

  &__empty {
    @include flex-col;
    align-items: center;
    gap: $spacing-md;
    padding: $spacing-2xl;
    color: $color-text-secondary;
    text-align: center;
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

  &__icon { font-size: 2.5rem; line-height: 1; }
  &__info { @include flex-col; gap: 4px; }
  &__value {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
    line-height: 1;
  }
  &__label { font-size: $font-size-sm; color: $color-text-muted; }
}

.bids-list {
  @include flex-col;
  gap: $spacing-md;
}
</style>
