<template>
  <div class="page-content">
    <div class="container">
      <AppLoader v-if="isLoading && !profile" size="lg" text="Loading profile..." />
      <AppAlert v-else-if="error" variant="danger" :message="error" />

      <div v-else-if="profile" class="freelancer-profile">
        <button class="freelancer-profile__back" @click="$router.back()">← Back</button>

        <div class="freelancer-profile__hero">
          <div class="freelancer-profile__avatar">
            <img v-if="profile.profilePictureUrl" :src="profile.profilePictureUrl" :alt="profile.name" />
            <span v-else>{{ initials }}</span>
          </div>

          <div class="freelancer-profile__hero-info">
            <div class="freelancer-profile__hero-top">
              <h1 class="freelancer-profile__name">{{ profile.name }}</h1>
              <AppBadge :variant="profile.availabilityStatus === 'AVAILABLE' ? 'success' : 'warning'" dot size="lg">
                {{ profile.availabilityStatus }}
              </AppBadge>
            </div>
            <p v-if="profile.location" class="freelancer-profile__location">📍 {{ profile.location }}</p>
            <div class="freelancer-profile__stats">
              <div class="freelancer-profile__stat">⭐ {{ profile.rating.toFixed(1) }} Rating</div>
              <div class="freelancer-profile__stat-divider"></div>
              <div class="freelancer-profile__stat">✅ {{ profile.completedProjects }} Completed</div>
              <div v-if="profile.hourlyRate" class="freelancer-profile__stat-divider"></div>
              <div v-if="profile.hourlyRate" class="freelancer-profile__stat freelancer-profile__stat--rate">
                💰 ${{ profile.hourlyRate }}/hr
              </div>
            </div>
            <div class="freelancer-profile__skills" v-if="profile.skills.length">
              <AppBadge v-for="skill in profile.skills" :key="skill" variant="primary">{{ skill }}</AppBadge>
            </div>
          </div>
        </div>

        <div class="freelancer-profile__grid">
          <div class="freelancer-profile__main">
            <AppCard v-if="profile.profileSummary">
              <template #header><h3 class="freelancer-profile__section-title">👤 About</h3></template>
              <p class="freelancer-profile__text">{{ profile.profileSummary }}</p>
            </AppCard>

            <AppCard v-if="profile.workExperience">
              <template #header><h3 class="freelancer-profile__section-title">💼 Work Experience</h3></template>
              <p class="freelancer-profile__text">{{ profile.workExperience }}</p>
            </AppCard>

            <AppCard v-if="profile.education">
              <template #header><h3 class="freelancer-profile__section-title">🎓 Education</h3></template>
              <p class="freelancer-profile__text">{{ profile.education }}</p>
            </AppCard>

            <AppCard v-if="profile.certifications">
              <template #header><h3 class="freelancer-profile__section-title">🏅 Certifications</h3></template>
              <p class="freelancer-profile__text">{{ profile.certifications }}</p>
            </AppCard>
          </div>

          <div class="freelancer-profile__sidebar">
            <AppCard elevated>
              <template #header><h3 class="freelancer-profile__section-title">📬 Contact Info</h3></template>
              <div class="freelancer-profile__contact-list">
                <div v-if="profile.email" class="freelancer-profile__contact-item">
                  <span>📧</span> {{ profile.email }}
                </div>
                <div v-if="profile.phoneNumber" class="freelancer-profile__contact-item">
                  <span>📞</span> {{ profile.phoneNumber }}
                </div>
                <a v-if="profile.portfolioLink" :href="profile.portfolioLink" target="_blank" class="freelancer-profile__contact-item freelancer-profile__contact-item--link">
                  <span>🌐</span> Portfolio
                </a>
                <div v-if="profile.languages" class="freelancer-profile__contact-item">
                  <span>🗣️</span> {{ profile.languages }}
                </div>
              </div>
            </AppCard>

            <AppCard v-if="profile.additionalInfo" elevated>
              <template #header><h3 class="freelancer-profile__section-title">ℹ️ Additional Info</h3></template>
              <p class="freelancer-profile__text freelancer-profile__text--sm">{{ profile.additionalInfo }}</p>
            </AppCard>
          </div>
        </div>
      </div>

      <div v-else class="freelancer-profile__not-found">
        <div>😔</div>
        <h2>Freelancer not found</h2>
        <button @click="$router.back()">← Go Back</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import AppCard from '../../components/shared/AppCard.vue';
import AppBadge from '../../components/shared/AppBadge.vue';
import AppLoader from '../../components/shared/AppLoader.vue';
import AppAlert from '../../components/shared/AppAlert.vue';

const store = useStore();
const route = useRoute();

const profile = computed(() => store.getters['freelancer/profile']);
const isLoading = computed(() => store.getters['freelancer/isLoading']);
const error = computed(() => store.getters['freelancer/error']);

const initials = computed(() =>
  profile.value?.name.split(' ').map((n: string) => n[0]).join('').toUpperCase().slice(0, 2) || '?'
);

onMounted(async () => {
  const userId = Number(route.params.id);
  await store.dispatch('freelancer/fetchProfile', userId);
});
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.freelancer-profile {
  @include flex-col;
  gap: $spacing-xl;

  &__back {
    background: none;
    border: none;
    color: $color-text-secondary;
    font-size: $font-size-sm;
    cursor: pointer;
    padding: 0;
    width: fit-content;
    transition: color $transition-fast;
    &:hover { color: $color-text-primary; }
  }

  &__hero {
    background: $gradient-card;
    border: 1px solid $color-border-light;
    border-radius: $radius-xl;
    padding: $spacing-2xl;
    display: flex;
    gap: $spacing-2xl;
    align-items: flex-start;

    @include respond-to(md) {
      flex-direction: column;
      align-items: center;
      text-align: center;
    }
  }

  &__avatar {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    background: $gradient-primary;
    @include flex-center;
    flex-shrink: 0;
    border: 3px solid $color-border;
    overflow: hidden;
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: white;

    img { width: 100%; height: 100%; object-fit: cover; }
  }

  &__hero-info {
    flex: 1;
    @include flex-col;
    gap: $spacing-md;
  }

  &__hero-top {
    display: flex;
    align-items: center;
    gap: $spacing-md;
    flex-wrap: wrap;
  }

  &__name {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
  }

  &__location {
    font-size: $font-size-sm;
    color: $color-text-muted;
  }

  &__stats {
    display: flex;
    align-items: center;
    gap: $spacing-lg;
    flex-wrap: wrap;
  }

  &__stat {
    font-size: $font-size-sm;
    font-weight: $font-weight-semibold;
    color: $color-text-secondary;

    &--rate { color: $color-secondary-light; font-size: $font-size-base; }
  }

  &__stat-divider {
    width: 1px;
    height: 16px;
    background: $color-border;
  }

  &__skills {
    display: flex;
    flex-wrap: wrap;
    gap: $spacing-xs;
  }

  &__grid {
    display: grid;
    grid-template-columns: 1fr 300px;
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
    @include flex-col;
    gap: $spacing-lg;
    position: sticky;
    top: calc($navbar-height + $spacing-md);
  }

  &__section-title {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }

  &__text {
    color: $color-text-secondary;
    line-height: $line-height-relaxed;
    white-space: pre-wrap;

    &--sm { font-size: $font-size-sm; }
  }

  &__contact-list {
    @include flex-col;
    gap: $spacing-md;
  }

  &__contact-item {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    font-size: $font-size-sm;
    color: $color-text-secondary;

    &--link {
      color: $color-primary-light;
      text-decoration: none;
      &:hover { text-decoration: underline; }
    }
  }

  &__not-found {
    @include flex-col;
    align-items: center;
    text-align: center;
    padding: $spacing-3xl;
    gap: $spacing-lg;
    color: $color-text-secondary;

    div { font-size: 5rem; }
    h2 { font-size: $font-size-2xl; color: $color-text-primary; }

    button {
      background: none;
      border: none;
      color: $color-primary-light;
      cursor: pointer;
      font-size: $font-size-base;
      &:hover { text-decoration: underline; }
    }
  }
}
</style>
