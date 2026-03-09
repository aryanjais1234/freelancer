<template>
  <div class="page-content">
    <div class="container">
      <AppLoader v-if="isLoading && !project" size="lg" text="Loading project..." class="project-detail__loading" />
      <AppAlert v-else-if="error" variant="danger" :message="error" />

      <div v-else-if="project" class="project-detail">
        <div class="project-detail__back">
          <button class="project-detail__back-btn" @click="$router.back()">← Back</button>
        </div>

        <div class="project-detail__grid">
          <div class="project-detail__main">
            <AppCard>
              <template #header>
                <div class="project-detail__card-header">
                  <div class="project-detail__badges">
                    <AppBadge :variant="statusVariant" dot size="lg">{{ project.status.replace('_', ' ') }}</AppBadge>
                    <AppBadge variant="default">{{ project.category }}</AppBadge>
                  </div>
                  <h1 class="project-detail__title">{{ project.title }}</h1>
                </div>
              </template>

              <div class="project-detail__description">
                <h3>📝 Description</h3>
                <p>{{ project.description }}</p>
              </div>
            </AppCard>

            <AppCard v-if="isFreelancer && project.status === 'OPEN'">
              <template #header>
                <h3 class="project-detail__section-title">💰 Submit a Bid</h3>
              </template>

              <AppAlert v-if="bidError" variant="danger" :message="bidError" />
              <AppAlert v-if="bidSuccess" variant="success" message="Your bid was submitted successfully! 🎉" />

              <form @submit.prevent="submitBid" class="bid-form">
                <AppInput
                  v-model.number="bidForm.bidAmount"
                  label="Bid Amount ($)"
                  type="number"
                  placeholder="Enter your bid amount"
                  required
                  :error="bidErrors.bidAmount"
                />
                <AppInput
                  v-model="bidForm.proposalText"
                  label="Proposal"
                  type="textarea"
                  placeholder="Describe why you're the best fit for this project..."
                  :rows="5"
                  required
                  :error="bidErrors.proposalText"
                />
                <AppButton type="submit" variant="primary" :loading="bidLoading">
                  📤 Submit Bid
                </AppButton>
              </form>
            </AppCard>

            <AppCard v-if="isClient && projectBids.length > 0">
              <template #header>
                <h3 class="project-detail__section-title">📥 Bids ({{ projectBids.length }})</h3>
              </template>

              <div class="bids-list">
                <BidCard
                  v-for="bid in projectBids"
                  :key="bid.bidId"
                  :bid="bid"
                  show-actions
                  :accepting="acceptingBid === bid.bidId"
                  :rejecting="rejectingBid === bid.bidId"
                  @accept="handleAccept"
                  @reject="handleReject"
                />
              </div>
            </AppCard>
          </div>

          <div class="project-detail__sidebar">
            <AppCard elevated>
              <template #header>
                <h3 class="project-detail__section-title">📊 Project Details</h3>
              </template>

              <div class="project-detail__meta-list">
                <div class="project-detail__meta-item">
                  <span class="project-detail__meta-icon">💰</span>
                  <div>
                    <div class="project-detail__meta-label">Budget</div>
                    <div class="project-detail__meta-value">${{ budgetDisplay }}</div>
                  </div>
                </div>
                <div class="project-detail__meta-item">
                  <span class="project-detail__meta-icon">⏱️</span>
                  <div>
                    <div class="project-detail__meta-label">Duration</div>
                    <div class="project-detail__meta-value">{{ project.duration || 'Flexible' }}</div>
                  </div>
                </div>
                <div v-if="project.deadline" class="project-detail__meta-item">
                  <span class="project-detail__meta-icon">📅</span>
                  <div>
                    <div class="project-detail__meta-label">Deadline</div>
                    <div class="project-detail__meta-value">{{ deadlineDisplay }}</div>
                  </div>
                </div>
                <div class="project-detail__meta-item">
                  <span class="project-detail__meta-icon">🗓️</span>
                  <div>
                    <div class="project-detail__meta-label">Posted</div>
                    <div class="project-detail__meta-value">{{ postedDisplay }}</div>
                  </div>
                </div>
              </div>
            </AppCard>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import AppCard from '../../components/shared/AppCard.vue';
import AppBadge from '../../components/shared/AppBadge.vue';
import AppButton from '../../components/shared/AppButton.vue';
import AppInput from '../../components/shared/AppInput.vue';
import AppLoader from '../../components/shared/AppLoader.vue';
import AppAlert from '../../components/shared/AppAlert.vue';
import BidCard from '../../components/bid/BidCard.vue';

const store = useStore();
const route = useRoute();

const projectId = computed(() => Number(route.params.id));
const project = computed(() => store.getters['project/currentProject']);
const isLoading = computed(() => store.getters['project/isLoading']);
const error = computed(() => store.getters['project/error']);
const isFreelancer = computed(() => store.getters['auth/isFreelancer']);
const isClient = computed(() => store.getters['auth/isClient']);
const freelancerProfile = computed(() => store.getters['freelancer/profile']);
const projectBids = computed(() => store.getters['bid/projectBids']);
const bidLoading = computed(() => store.getters['bid/isLoading']);

const bidForm = reactive({ bidAmount: 0, proposalText: '' });
const bidErrors = reactive({ bidAmount: '', proposalText: '' });
const bidError = ref('');
const bidSuccess = ref(false);
const acceptingBid = ref<number | null>(null);
const rejectingBid = ref<number | null>(null);

const statusVariant = computed((): string => {
  const map: Record<string, string> = { OPEN: 'success', IN_PROGRESS: 'warning', COMPLETED: 'default' };
  return map[project.value?.status as string] || 'default';
});

const budgetDisplay = computed(() => {
  if (!project.value) return '';
  const { budget, budgetMin, budgetMax } = project.value;
  if (budgetMin && budgetMax) return `${budgetMin.toLocaleString()} – ${budgetMax.toLocaleString()}`;
  if (budget) return budget.toLocaleString();
  return 'Negotiable';
});

const deadlineDisplay = computed(() =>
  project.value?.deadline?.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' }) || ''
);
const postedDisplay = computed(() =>
  project.value?.createdAt?.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' }) || ''
);

const submitBid = async () => {
  bidErrors.bidAmount = bidForm.bidAmount > 0 ? '' : 'Enter a valid amount';
  bidErrors.proposalText = bidForm.proposalText ? '' : 'Proposal is required';
  if (bidErrors.bidAmount || bidErrors.proposalText) return;

  bidError.value = '';
  try {
    await store.dispatch('bid/submitBid', {
      projectId: projectId.value,
      freelancerId: freelancerProfile.value?.freelancerId,
      bidAmount: bidForm.bidAmount,
      proposalText: bidForm.proposalText,
    });
    bidSuccess.value = true;
    bidForm.bidAmount = 0;
    bidForm.proposalText = '';
  } catch (err: any) {
    bidError.value = err.response?.data?.message || 'Failed to submit bid';
  }
};

const handleAccept = async (bidId: number) => {
  acceptingBid.value = bidId;
  try {
    await store.dispatch('bid/acceptBid', bidId);
    await store.dispatch('bid/fetchByProject', projectId.value);
  } finally {
    acceptingBid.value = null;
  }
};

const handleReject = async (bidId: number) => {
  rejectingBid.value = bidId;
  try {
    await store.dispatch('bid/rejectBid', bidId);
    await store.dispatch('bid/fetchByProject', projectId.value);
  } finally {
    rejectingBid.value = null;
  }
};

onMounted(async () => {
  await store.dispatch('project/fetchById', projectId.value);
  if (isClient.value) {
    await store.dispatch('bid/fetchByProject', projectId.value);
  }
  if (isFreelancer.value && store.getters['auth/currentUser']?.id) {
    try {
      await store.dispatch('freelancer/fetchProfile', store.getters['auth/currentUser'].id);
    } catch {
      // ok
    }
  }
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.project-detail {
  @include flex-col;
  gap: $spacing-xl;

  &__loading {
    padding: $spacing-3xl;
    @include flex-center;
  }

  &__back {
    margin-bottom: -$spacing-sm;
  }

  &__back-btn {
    color: $color-text-secondary;
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    cursor: pointer;
    background: none;
    border: none;
    transition: color $transition-fast;

    &:hover { color: $color-text-primary; }
  }

  &__grid {
    display: grid;
    grid-template-columns: 1fr 320px;
    gap: $spacing-xl;
    align-items: start;

    @include respond-to(lg) {
      grid-template-columns: 1fr;
    }
  }

  &__main {
    @include flex-col;
    gap: $spacing-lg;
  }

  &__sidebar {
    position: sticky;
    top: calc($navbar-height + $spacing-md);
  }

  &__card-header {
    @include flex-col;
    gap: $spacing-md;
  }

  &__badges {
    display: flex;
    gap: $spacing-sm;
    flex-wrap: wrap;
  }

  &__title {
    font-size: $font-size-2xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
  }

  &__description {
    @include flex-col;
    gap: $spacing-md;

    h3 {
      font-size: $font-size-lg;
      font-weight: $font-weight-semibold;
      color: $color-text-primary;
    }

    p {
      color: $color-text-secondary;
      line-height: $line-height-relaxed;
      white-space: pre-wrap;
    }
  }

  &__section-title {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }

  &__meta-list {
    @include flex-col;
    gap: $spacing-lg;
  }

  &__meta-item {
    display: flex;
    align-items: flex-start;
    gap: $spacing-md;
  }

  &__meta-icon {
    font-size: $font-size-xl;
    line-height: 1;
    flex-shrink: 0;
    margin-top: 2px;
  }

  &__meta-label {
    font-size: $font-size-xs;
    color: $color-text-muted;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    margin-bottom: 2px;
  }

  &__meta-value {
    font-size: $font-size-base;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }
}

.bid-form {
  @include flex-col;
  gap: $spacing-lg;
}

.bids-list {
  @include flex-col;
  gap: $spacing-md;
}
</style>
