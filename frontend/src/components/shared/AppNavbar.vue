<template>
  <nav class="app-navbar">
    <div class="container app-navbar__inner">
      <RouterLink to="/" class="app-navbar__brand">
        <span class="app-navbar__logo">🔗</span>
        <span class="app-navbar__name">FreelancerConnect</span>
      </RouterLink>

      <div v-if="isAuthenticated" class="app-navbar__nav">
        <RouterLink v-for="link in navLinks" :key="link.to" :to="link.to" class="app-navbar__link">
          <span>{{ link.icon }}</span>
          {{ link.label }}
        </RouterLink>
      </div>

      <div class="app-navbar__actions">
        <template v-if="isAuthenticated">
          <div class="app-navbar__user">
            <div class="app-navbar__avatar">{{ userInitials }}</div>
            <div class="app-navbar__user-info">
              <span class="app-navbar__user-name">{{ currentUser?.fullName }}</span>
              <AppBadge :variant="currentUser?.role === 'CLIENT' ? 'info' : 'primary'" size="sm">
                {{ currentUser?.role }}
              </AppBadge>
            </div>
          </div>
          <AppButton variant="ghost" size="sm" @click="handleLogout">
            🚪 Logout
          </AppButton>
        </template>
        <template v-else>
          <RouterLink to="/login">
            <AppButton variant="ghost" size="sm">Login</AppButton>
          </RouterLink>
          <RouterLink to="/register">
            <AppButton variant="primary" size="sm">Get Started 🚀</AppButton>
          </RouterLink>
        </template>
      </div>

      <button class="app-navbar__burger" @click="menuOpen = !menuOpen" :aria-expanded="menuOpen">
        <span></span><span></span><span></span>
      </button>
    </div>

    <Transition name="slide-up">
      <div v-if="menuOpen && isAuthenticated" class="app-navbar__mobile-menu">
        <RouterLink
          v-for="link in navLinks"
          :key="link.to"
          :to="link.to"
          class="app-navbar__mobile-link"
          @click="menuOpen = false"
        >
          {{ link.icon }} {{ link.label }}
        </RouterLink>
        <button class="app-navbar__mobile-link app-navbar__mobile-logout" @click="handleLogout">
          🚪 Logout
        </button>
      </div>
    </Transition>
  </nav>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import AppButton from './AppButton.vue';
import AppBadge from './AppBadge.vue';

const store = useStore();
const router = useRouter();
const menuOpen = ref(false);

const isAuthenticated = computed(() => store.getters['auth/isAuthenticated']);
const currentUser = computed(() => store.getters['auth/currentUser']);
const userRole = computed(() => store.getters['auth/userRole']);

const userInitials = computed(() => {
  const name = currentUser.value?.fullName || '';
  return name.split(' ').map((n: string) => n[0]).join('').toUpperCase().slice(0, 2) || '?';
});

const navLinks = computed(() => {
  if (userRole.value === 'CLIENT') {
    return [
      { to: '/dashboard/client', label: 'Dashboard', icon: '📊' },
      { to: '/projects', label: 'Projects', icon: '📋' },
      { to: '/freelancers', label: 'Find Talent', icon: '🔍' },
      { to: '/profile/client', label: 'Profile', icon: '👤' },
    ];
  }
  return [
    { to: '/dashboard/freelancer', label: 'Dashboard', icon: '📊' },
    { to: '/projects', label: 'Browse Jobs', icon: '💼' },
    { to: '/freelancers', label: 'Community', icon: '👥' },
    { to: '/profile/freelancer', label: 'Profile', icon: '👤' },
  ];
});

const handleLogout = async () => {
  menuOpen.value = false;
  await store.dispatch('auth/logout');
  router.push('/login');
};
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.app-navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  background: rgba($color-background, 0.85);
  backdrop-filter: blur(12px);
  border-bottom: 1px solid $color-border-light;
  height: $navbar-height;

  &__inner {
    @include flex-between;
    height: 100%;
  }

  &__brand {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    text-decoration: none;
    flex-shrink: 0;
  }

  &__logo {
    font-size: $font-size-2xl;
  }

  &__name {
    font-size: $font-size-lg;
    font-weight: $font-weight-bold;
    background: $gradient-primary;
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }

  &__nav {
    display: flex;
    align-items: center;
    gap: $spacing-xs;

    @include respond-to(lg) { display: none; }
  }

  &__link {
    display: flex;
    align-items: center;
    gap: 6px;
    padding: $spacing-xs $spacing-md;
    border-radius: $radius-md;
    color: $color-text-secondary;
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    transition: all $transition-fast;
    text-decoration: none;

    &:hover, &.router-link-active {
      color: $color-text-primary;
      background: rgba(99, 102, 241, 0.15);
    }
  }

  &__actions {
    display: flex;
    align-items: center;
    gap: $spacing-md;

    @include respond-to(lg) {
      .app-button { display: none; }
    }
  }

  &__user {
    display: flex;
    align-items: center;
    gap: $spacing-sm;

    @include respond-to(md) { display: none; }
  }

  &__avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background: $gradient-primary;
    @include flex-center;
    font-size: $font-size-sm;
    font-weight: $font-weight-bold;
    color: white;
    flex-shrink: 0;
  }

  &__user-info {
    @include flex-col;
    gap: 2px;
  }

  &__user-name {
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    color: $color-text-primary;
    max-width: 120px;
    @include truncate;
  }

  &__burger {
    display: none;
    flex-direction: column;
    gap: 5px;
    padding: $spacing-xs;
    cursor: pointer;
    background: none;
    border: none;

    @include respond-to(lg) { display: flex; }

    span {
      display: block;
      width: 22px;
      height: 2px;
      background: $color-text-secondary;
      border-radius: 2px;
      transition: background $transition-fast;
    }

    &:hover span { background: $color-text-primary; }
  }

  &__mobile-menu {
    position: fixed;
    top: $navbar-height;
    left: 0;
    right: 0;
    background: $color-surface;
    border-bottom: 1px solid $color-border;
    padding: $spacing-md;
    display: flex;
    flex-direction: column;
    gap: $spacing-xs;
    z-index: 99;

    @media (min-width: #{$breakpoint-lg + 1px}) { display: none; }
  }

  &__mobile-link {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    padding: $spacing-md;
    border-radius: $radius-md;
    color: $color-text-secondary;
    font-size: $font-size-base;
    font-weight: $font-weight-medium;
    text-decoration: none;
    transition: all $transition-fast;
    cursor: pointer;
    background: none;
    border: none;
    width: 100%;
    text-align: left;

    &:hover { color: $color-text-primary; background: $color-surface-elevated; }
  }

  &__mobile-logout {
    color: $color-danger-light;
  }
}
</style>
