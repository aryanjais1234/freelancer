<template>
  <div class="auth-view">
    <div class="auth-view__card">
      <div class="auth-view__header">
        <RouterLink to="/" class="auth-view__logo">🔗 FreelancerConnect</RouterLink>
        <h1 class="auth-view__title">Welcome back 👋</h1>
        <p class="auth-view__subtitle">Sign in to your account to continue</p>
      </div>

      <AppAlert v-if="error" variant="danger" :message="error" class="auth-view__alert" />

      <form @submit.prevent="handleLogin" class="auth-view__form">
        <AppInput
          v-model="form.userName"
          label="Email Address"
          type="email"
          placeholder="your@email.com"
          required
          :error="errors.userName"
        />
        <AppInput
          v-model="form.password"
          label="Password"
          type="password"
          placeholder="••••••••"
          required
          :error="errors.password"
        />

        <AppButton type="submit" variant="primary" size="lg" :loading="isLoading" fullWidth>
          Sign In 🚀
        </AppButton>
      </form>

      <p class="auth-view__switch">
        Don't have an account?
        <RouterLink to="/register" class="auth-view__link">Create one →</RouterLink>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import AppInput from '../../components/shared/AppInput.vue';
import AppButton from '../../components/shared/AppButton.vue';
import AppAlert from '../../components/shared/AppAlert.vue';

const store = useStore();
const router = useRouter();

const form = reactive({ userName: '', password: '' });
const errors = reactive({ userName: '', password: '' });

const isLoading = computed(() => store.getters['auth/isLoading']);
const error = computed(() => store.getters['auth/error']);

const validate = () => {
  errors.userName = form.userName ? '' : 'Email is required';
  errors.password = form.password ? '' : 'Password is required';
  return !errors.userName && !errors.password;
};

const handleLogin = async () => {
  if (!validate()) return;
  try {
    await store.dispatch('auth/login', { userName: form.userName, password: form.password });
    const role = store.getters['auth/userRole'];
    if (role === 'CLIENT') router.push('/dashboard/client');
    else router.push('/dashboard/freelancer');
  } catch {
    // error handled by store
  }
};
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.auth-view {
  min-height: 100vh;
  @include flex-center;
  padding: $spacing-lg;
  background: $gradient-hero;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    width: 600px;
    height: 600px;
    border-radius: 50%;
    background: $color-primary;
    filter: blur(120px);
    opacity: 0.08;
    top: -200px;
    right: -200px;
  }

  &__card {
    width: 100%;
    max-width: 440px;
    background: $color-surface;
    border: 1px solid $color-border-light;
    border-radius: $radius-xl;
    padding: $spacing-2xl;
    box-shadow: $shadow-lg;
    position: relative;
    z-index: 1;
  }

  &__header {
    text-align: center;
    margin-bottom: $spacing-2xl;
    @include flex-col;
    gap: $spacing-sm;
  }

  &__logo {
    font-size: $font-size-xl;
    font-weight: $font-weight-bold;
    background: $gradient-primary;
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    text-decoration: none;
  }

  &__title {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
  }

  &__subtitle {
    color: $color-text-secondary;
    font-size: $font-size-sm;
  }

  &__alert {
    margin-bottom: $spacing-lg;
  }

  &__form {
    @include flex-col;
    gap: $spacing-lg;
  }

  &__switch {
    text-align: center;
    margin-top: $spacing-lg;
    font-size: $font-size-sm;
    color: $color-text-secondary;
  }

  &__link {
    color: $color-primary-light;
    font-weight: $font-weight-medium;
    text-decoration: none;

    &:hover { text-decoration: underline; }
  }
}
</style>
