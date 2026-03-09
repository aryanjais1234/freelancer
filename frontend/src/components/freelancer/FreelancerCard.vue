<template>
  <RouterLink :to="`/freelancers/${freelancer.freelancerId}`" class="freelancer-card">
    <div class="freelancer-card__top">
      <div class="freelancer-card__avatar">
        <img
          v-if="freelancer.profilePictureUrl"
          :src="freelancer.profilePictureUrl"
          :alt="freelancer.name"
          class="freelancer-card__avatar-img"
        />
        <span v-else class="freelancer-card__avatar-initials">{{ initials }}</span>
      </div>
      <div class="freelancer-card__info">
        <h3 class="freelancer-card__name">{{ freelancer.name }}</h3>
        <p class="freelancer-card__location" v-if="freelancer.location">📍 {{ freelancer.location }}</p>
        <AppBadge :variant="freelancer.availabilityStatus === 'AVAILABLE' ? 'success' : 'warning'" dot size="sm">
          {{ freelancer.availabilityStatus }}
        </AppBadge>
      </div>
      <div class="freelancer-card__rate" v-if="freelancer.hourlyRate">
        <span class="freelancer-card__rate-value">${{ freelancer.hourlyRate }}</span>
        <span class="freelancer-card__rate-unit">/hr</span>
      </div>
    </div>

    <p class="freelancer-card__bio">{{ freelancer.profileSummary || 'No bio provided.' }}</p>

    <div class="freelancer-card__skills" v-if="freelancer.skills.length">
      <AppBadge
        v-for="skill in freelancer.skills.slice(0, 5)"
        :key="skill"
        variant="primary"
        size="sm"
      >
        {{ skill }}
      </AppBadge>
      <AppBadge v-if="freelancer.skills.length > 5" variant="default" size="sm">
        +{{ freelancer.skills.length - 5 }}
      </AppBadge>
    </div>

    <div class="freelancer-card__stats">
      <div class="freelancer-card__stat">
        <span class="freelancer-card__stat-value">⭐ {{ freelancer.rating.toFixed(1) }}</span>
        <span class="freelancer-card__stat-label">Rating</span>
      </div>
      <div class="freelancer-card__stat">
        <span class="freelancer-card__stat-value">✅ {{ freelancer.completedProjects }}</span>
        <span class="freelancer-card__stat-label">Completed</span>
      </div>
    </div>
  </RouterLink>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import type { FreelancerUI } from '../../types/freelancer.types';
import AppBadge from '../shared/AppBadge.vue';

const props = defineProps<{ freelancer: FreelancerUI }>();

const initials = computed(() => {
  return props.freelancer.name
    .split(' ')
    .map((n) => n[0])
    .join('')
    .toUpperCase()
    .slice(0, 2);
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.freelancer-card {
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

  &__top {
    display: flex;
    align-items: flex-start;
    gap: $spacing-md;
  }

  &__avatar {
    width: 56px;
    height: 56px;
    border-radius: 50%;
    overflow: hidden;
    flex-shrink: 0;
    background: $gradient-primary;
    @include flex-center;
    border: 2px solid $color-border;
  }

  &__avatar-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  &__avatar-initials {
    font-size: $font-size-lg;
    font-weight: $font-weight-bold;
    color: white;
  }

  &__info {
    flex: 1;
    @include flex-col;
    gap: 4px;
  }

  &__name {
    font-size: $font-size-base;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }

  &__location {
    font-size: $font-size-xs;
    color: $color-text-muted;
  }

  &__rate {
    text-align: right;
    flex-shrink: 0;
  }

  &__rate-value {
    display: block;
    font-size: $font-size-xl;
    font-weight: $font-weight-bold;
    color: $color-secondary-light;
  }

  &__rate-unit {
    font-size: $font-size-xs;
    color: $color-text-muted;
  }

  &__bio {
    font-size: $font-size-sm;
    color: $color-text-secondary;
    line-height: $line-height-relaxed;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }

  &__skills {
    display: flex;
    flex-wrap: wrap;
    gap: $spacing-xs;
  }

  &__stats {
    display: flex;
    gap: $spacing-xl;
    padding-top: $spacing-sm;
    border-top: 1px solid $color-border-light;
  }

  &__stat {
    @include flex-col;
    gap: 2px;
  }

  &__stat-value {
    font-size: $font-size-sm;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }

  &__stat-label {
    font-size: $font-size-xs;
    color: $color-text-muted;
  }
}
</style>
