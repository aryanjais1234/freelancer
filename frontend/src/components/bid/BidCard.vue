<template>
  <div class="bid-card">
    <div class="bid-card__header">
      <div class="bid-card__meta">
        <span class="bid-card__amount">💰 ${{ bid.bidAmount.toLocaleString() }}</span>
        <AppBadge :variant="statusVariant" dot>{{ bid.status }}</AppBadge>
      </div>
      <span class="bid-card__date">{{ dateDisplay }}</span>
    </div>

    <p class="bid-card__proposal">{{ bid.proposalText }}</p>

    <div v-if="showActions && bid.status === 'PENDING'" class="bid-card__actions">
      <AppButton variant="success" size="sm" :loading="accepting" @click="$emit('accept', bid.bidId)">
        ✅ Accept
      </AppButton>
      <AppButton variant="danger" size="sm" :loading="rejecting" @click="$emit('reject', bid.bidId)">
        ❌ Reject
      </AppButton>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import type { BidUI } from '../../types/bid.types';
import AppBadge from '../shared/AppBadge.vue';
import AppButton from '../shared/AppButton.vue';

const props = defineProps<{
  bid: BidUI;
  showActions?: boolean;
  accepting?: boolean;
  rejecting?: boolean;
}>();

defineEmits<{
  (e: 'accept', bidId: number): void;
  (e: 'reject', bidId: number): void;
}>();

const statusVariant = computed(() => ({
  PENDING: 'warning',
  ACCEPTED: 'success',
  REJECTED: 'danger',
}[props.bid.status] || 'default'));

const dateDisplay = computed(() =>
  props.bid.createdAt.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })
);
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.bid-card {
  background: $color-surface-elevated;
  border: 1px solid $color-border-light;
  border-radius: $radius-md;
  padding: $spacing-lg;
  @include flex-col;
  gap: $spacing-md;
  transition: border-color $transition-fast;

  &:hover {
    border-color: $color-border;
  }

  &__header {
    @include flex-between;
    flex-wrap: wrap;
    gap: $spacing-sm;
  }

  &__meta {
    display: flex;
    align-items: center;
    gap: $spacing-md;
  }

  &__amount {
    font-size: $font-size-lg;
    font-weight: $font-weight-bold;
    color: $color-secondary-light;
  }

  &__date {
    font-size: $font-size-xs;
    color: $color-text-muted;
  }

  &__proposal {
    font-size: $font-size-sm;
    color: $color-text-secondary;
    line-height: $line-height-relaxed;
  }

  &__actions {
    display: flex;
    gap: $spacing-sm;
  }
}
</style>
