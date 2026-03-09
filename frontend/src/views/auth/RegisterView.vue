<template>
  <div class="auth-view">
    <div class="auth-view__card">
      <div class="auth-view__header">
        <RouterLink to="/" class="auth-view__logo">🔗 FreelancerConnect</RouterLink>
        <h1 class="auth-view__title">Create Account 🎉</h1>
        <p class="auth-view__subtitle">Join thousands of professionals today</p>
      </div>

      <AppAlert v-if="error" variant="danger" :message="error" class="auth-view__alert" />
      <AppAlert v-if="success" variant="success" message="Account created! Redirecting to login..." class="auth-view__alert" />

      <form @submit.prevent="handleRegister" class="auth-view__form">
        <AppInput
          v-model="form.name"
          label="Full Name"
          placeholder="John Doe"
          required
          :error="errors.name"
        />
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
          placeholder="Min. 8 characters"
          required
          :error="errors.password"
        />

        <div class="auth-view__role-group">
          <label class="auth-view__role-label">I am a...</label>
          <div class="auth-view__roles">
            <button
              type="button"
              :class="['auth-view__role-btn', { 'auth-view__role-btn--active': form.role === 'FREELANCER' }]"
              @click="form.role = 'FREELANCER'"
            >
              <span class="auth-view__role-icon">💻</span>
              <span>Freelancer</span>
              <small>I offer services</small>
            </button>
            <button
              type="button"
              :class="['auth-view__role-btn', { 'auth-view__role-btn--active': form.role === 'CLIENT' }]"
              @click="form.role = 'CLIENT'"
            >
              <span class="auth-view__role-icon">🏢</span>
              <span>Client</span>
              <small>I hire talent</small>
            </button>
          </div>
          <p v-if="errors.role" class="auth-view__field-error">{{ errors.role }}</p>
        </div>

        <AppButton type="submit" variant="primary" size="lg" :loading="isLoading" fullWidth>
          Create Account →
        </AppButton>
      </form>

      <p class="auth-view__switch">
        Already have an account?
        <RouterLink to="/login" class="auth-view__link">Sign in →</RouterLink>
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, computed, ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter, useRoute } from 'vue-router';
import AppInput from '../../components/shared/AppInput.vue';
import AppButton from '../../components/shared/AppButton.vue';
import AppAlert from '../../components/shared/AppAlert.vue';

const store = useStore();
const router = useRouter();
const route = useRoute();

const success = ref(false);
const form = reactive({
  name: '',
  userName: '',
  password: '',
  role: 'FREELANCER' as 'FREELANCER' | 'CLIENT',
});
const errors = reactive({ name: '', userName: '', password: '', role: '' });

const isLoading = computed(() => store.getters['auth/isLoading']);
const error = computed(() => store.getters['auth/error']);

onMounted(() => {
  const roleParam = route.query.role as string;
  if (roleParam === 'CLIENT' || roleParam === 'FREELANCER') {
    form.role = roleParam;
  }
});

const validate = () => {
  errors.name = form.name ? '' : 'Name is required';
  errors.userName = form.userName ? '' : 'Email is required';
  errors.password = form.password.length >= 6 ? '' : 'Password must be at least 6 characters';
  errors.role = form.role ? '' : 'Please select a role';
  return !errors.name && !errors.userName && !errors.password && !errors.role;
};

const handleRegister = async () => {
  if (!validate()) return;
  try {
    await store.dispatch('auth/register', form);
    success.value = true;
    setTimeout(() => router.push('/login'), 1500);
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
    bottom: -200px;
    left: -200px;
  }

  &__card {
    width: 100%;
    max-width: 480px;
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

  &__role-group {
    @include flex-col;
    gap: $spacing-sm;
  }

  &__role-label {
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    color: $color-text-secondary;
  }

  &__roles {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: $spacing-md;
  }

  &__role-btn {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 4px;
    padding: $spacing-md;
    background: $color-surface-elevated;
    border: 2px solid $color-border;
    border-radius: $radius-md;
    color: $color-text-secondary;
    cursor: pointer;
    transition: all $transition-fast;

    span:not(.auth-view__role-icon) {
      font-size: $font-size-sm;
      font-weight: $font-weight-semibold;
      color: $color-text-primary;
    }

    small {
      font-size: $font-size-xs;
      color: $color-text-muted;
    }

    &:hover {
      border-color: $color-primary;
      background: rgba(99, 102, 241, 0.1);
    }

    &--active {
      border-color: $color-primary;
      background: rgba(99, 102, 241, 0.15);
      box-shadow: 0 0 0 2px rgba(99, 102, 241, 0.2);
    }
  }

  &__role-icon {
    font-size: $font-size-2xl;
  }

  &__field-error {
    font-size: $font-size-xs;
    color: $color-danger;
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
