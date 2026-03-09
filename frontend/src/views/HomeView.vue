<template>
  <div class="home-view">
    <section class="hero">
      <div class="container hero__content">
        <div class="hero__badge">
          <AppBadge variant="primary">🚀 The Future of Work</AppBadge>
        </div>
        <h1 class="hero__title">
          Connect with <span class="hero__title--gradient">Top Freelancers</span><br/>
          & Amazing Clients
        </h1>
        <p class="hero__subtitle">
          FreelancerConnect brings together talented professionals and innovative businesses.
          Find your next project or hire the perfect freelancer today.
        </p>
        <div class="hero__actions">
          <template v-if="!isAuthenticated">
            <RouterLink to="/register">
              <AppButton variant="primary" size="lg">Get Started Free 🎯</AppButton>
            </RouterLink>
            <RouterLink to="/login">
              <AppButton variant="ghost" size="lg">Sign In →</AppButton>
            </RouterLink>
          </template>
          <template v-else>
            <RouterLink :to="dashboardLink">
              <AppButton variant="primary" size="lg">Go to Dashboard 📊</AppButton>
            </RouterLink>
            <RouterLink to="/projects">
              <AppButton variant="ghost" size="lg">Browse Projects 💼</AppButton>
            </RouterLink>
          </template>
        </div>

        <div class="hero__stats">
          <div class="hero__stat">
            <span class="hero__stat-value">10K+</span>
            <span class="hero__stat-label">Freelancers</span>
          </div>
          <div class="hero__stat-divider"></div>
          <div class="hero__stat">
            <span class="hero__stat-value">5K+</span>
            <span class="hero__stat-label">Projects</span>
          </div>
          <div class="hero__stat-divider"></div>
          <div class="hero__stat">
            <span class="hero__stat-value">98%</span>
            <span class="hero__stat-label">Satisfaction</span>
          </div>
        </div>
      </div>

      <div class="hero__bg-orbs">
        <div class="hero__orb hero__orb--1"></div>
        <div class="hero__orb hero__orb--2"></div>
        <div class="hero__orb hero__orb--3"></div>
      </div>
    </section>

    <section class="features container">
      <h2 class="features__title">Why Choose FreelancerConnect?</h2>
      <div class="features__grid">
        <div v-for="feature in features" :key="feature.title" class="feature-card">
          <div class="feature-card__icon">{{ feature.icon }}</div>
          <h3 class="feature-card__title">{{ feature.title }}</h3>
          <p class="feature-card__desc">{{ feature.description }}</p>
        </div>
      </div>
    </section>

    <section class="cta container">
      <div class="cta__card">
        <h2 class="cta__title">Ready to get started?</h2>
        <p class="cta__subtitle">Join thousands of professionals already using FreelancerConnect</p>
        <div class="cta__actions" v-if="!isAuthenticated">
          <RouterLink to="/register?role=FREELANCER">
            <AppButton variant="primary" size="lg">I'm a Freelancer 💻</AppButton>
          </RouterLink>
          <RouterLink to="/register?role=CLIENT">
            <AppButton variant="secondary" size="lg">I'm Hiring 🏢</AppButton>
          </RouterLink>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useStore } from 'vuex';
import AppButton from '../components/shared/AppButton.vue';
import AppBadge from '../components/shared/AppBadge.vue';

const store = useStore();
const isAuthenticated = computed(() => store.getters['auth/isAuthenticated']);
const userRole = computed(() => store.getters['auth/userRole']);
const dashboardLink = computed(() =>
  userRole.value === 'CLIENT' ? '/dashboard/client' : '/dashboard/freelancer'
);

const features = [
  { icon: '🔍', title: 'Find the Right Match', description: 'Advanced search and filtering to connect you with the perfect professional for your project.' },
  { icon: '🔒', title: 'Secure & Trusted', description: 'JWT-based authentication and role-based access ensures your data stays safe.' },
  { icon: '⚡', title: 'Fast Bidding System', description: 'Submit and manage proposals in real-time with our streamlined bidding platform.' },
  { icon: '📊', title: 'Project Management', description: 'Track project status from open to completion with full visibility for both parties.' },
  { icon: '🌍', title: 'Global Talent Pool', description: 'Access professionals from around the world with diverse skills and expertise.' },
  { icon: '💬', title: 'Clear Communication', description: 'Transparent proposal system so clients understand exactly what they are getting.' },
];
</script>

<style lang="scss" scoped>
@use '../styles/colors' as *;
@use '../styles/variables' as *;
@use '../styles/mixins' as *;

.home-view {
  padding-top: $navbar-height;
}

.hero {
  position: relative;
  min-height: calc(100vh - $navbar-height);
  @include flex-center;
  overflow: hidden;
  background: $gradient-hero;

  &__content {
    position: relative;
    z-index: 1;
    text-align: center;
    padding: $spacing-3xl $spacing-lg;
    @include flex-col;
    align-items: center;
    gap: $spacing-xl;
  }

  &__badge {
    margin-bottom: -$spacing-sm;
  }

  &__title {
    font-size: clamp(2.5rem, 6vw, $font-size-5xl);
    font-weight: $font-weight-extrabold;
    line-height: $line-height-tight;
    color: $color-text-primary;
    max-width: 800px;

    &--gradient {
      background: $gradient-primary;
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }
  }

  &__subtitle {
    font-size: $font-size-xl;
    color: $color-text-secondary;
    max-width: 600px;
    line-height: $line-height-relaxed;
  }

  &__actions {
    display: flex;
    gap: $spacing-md;
    flex-wrap: wrap;
    justify-content: center;
  }

  &__stats {
    display: flex;
    align-items: center;
    gap: $spacing-xl;
    margin-top: $spacing-lg;
    flex-wrap: wrap;
    justify-content: center;
  }

  &__stat {
    @include flex-col;
    align-items: center;
    gap: 4px;

    &-value {
      font-size: $font-size-3xl;
      font-weight: $font-weight-extrabold;
      background: $gradient-primary;
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    &-label {
      font-size: $font-size-sm;
      color: $color-text-muted;
      text-transform: uppercase;
      letter-spacing: 0.1em;
    }
  }

  &__stat-divider {
    width: 1px;
    height: 40px;
    background: $color-border;
  }

  &__bg-orbs {
    position: absolute;
    inset: 0;
    pointer-events: none;
    overflow: hidden;
  }

  &__orb {
    position: absolute;
    border-radius: 50%;
    filter: blur(80px);
    opacity: 0.12;

    &--1 {
      width: 500px;
      height: 500px;
      background: $color-primary;
      top: -100px;
      right: -100px;
      animation: float 8s ease-in-out infinite;
    }

    &--2 {
      width: 400px;
      height: 400px;
      background: #8b5cf6;
      bottom: -100px;
      left: -100px;
      animation: float 10s ease-in-out infinite reverse;
    }

    &--3 {
      width: 300px;
      height: 300px;
      background: $color-secondary;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      animation: float 12s ease-in-out infinite;
    }
  }
}

.features {
  padding: $spacing-3xl $spacing-lg;

  &__title {
    text-align: center;
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
    margin-bottom: $spacing-2xl;
  }

  &__grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    gap: $spacing-lg;
  }
}

.feature-card {
  @include card;
  text-align: center;
  gap: $spacing-md;

  &__icon {
    font-size: 3rem;
    line-height: 1;
  }

  &__title {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }

  &__desc {
    font-size: $font-size-sm;
    color: $color-text-secondary;
    line-height: $line-height-relaxed;
  }
}

.cta {
  padding: $spacing-3xl $spacing-lg;

  &__card {
    background: linear-gradient(135deg, rgba(99, 102, 241, 0.15), rgba(139, 92, 246, 0.15));
    border: 1px solid rgba(99, 102, 241, 0.3);
    border-radius: $radius-xl;
    padding: $spacing-3xl;
    text-align: center;
    @include flex-col;
    align-items: center;
    gap: $spacing-lg;
  }

  &__title {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
  }

  &__subtitle {
    color: $color-text-secondary;
    font-size: $font-size-lg;
  }

  &__actions {
    display: flex;
    gap: $spacing-md;
    flex-wrap: wrap;
    justify-content: center;
  }
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-30px); }
}
</style>
