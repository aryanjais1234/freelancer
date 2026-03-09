<template>
  <div class="page-content">
    <div class="container">
      <div class="create-project">
        <div class="create-project__header">
          <button class="create-project__back" @click="$router.back()">← Back</button>
          <h1 class="create-project__title">🚀 Post a New Project</h1>
          <p class="create-project__subtitle">Describe your project to attract the best freelancers</p>
        </div>

        <AppAlert v-if="error" variant="danger" :message="error" />
        <AppAlert v-if="success" variant="success" message="Project posted successfully! Redirecting..." />

        <form @submit.prevent="handleSubmit" class="create-project__form">
          <AppCard>
            <template #header><h3 class="create-project__card-title">📋 Basic Information</h3></template>

            <div class="form-grid">
              <AppInput
                v-model="form.title"
                label="Project Title"
                placeholder="e.g., Build a responsive e-commerce website"
                required
                :error="errors.title"
                class="form-grid__full"
              />
              <AppInput
                v-model="form.description"
                label="Project Description"
                type="textarea"
                placeholder="Describe your project requirements, goals, and expectations..."
                :rows="6"
                required
                :error="errors.description"
                class="form-grid__full"
              />
              <AppInput
                v-model="form.category"
                label="Category"
                placeholder="e.g., Web Development, Design, Marketing"
                required
                :error="errors.category"
              />
              <AppInput
                v-model="form.duration"
                label="Project Duration"
                placeholder="e.g., 2 weeks, 1 month"
              />
            </div>
          </AppCard>

          <AppCard>
            <template #header><h3 class="create-project__card-title">💰 Budget & Timeline</h3></template>

            <div class="form-grid">
              <AppInput
                v-model.number="form.budget"
                label="Fixed Budget ($)"
                type="number"
                placeholder="e.g., 5000"
              />
              <AppInput
                v-model="form.deadline"
                label="Deadline"
                type="date"
              />
              <AppInput
                v-model.number="form.budgetMin"
                label="Budget Min ($)"
                type="number"
                placeholder="e.g., 3000"
              />
              <AppInput
                v-model.number="form.budgetMax"
                label="Budget Max ($)"
                type="number"
                placeholder="e.g., 7000"
              />
            </div>
          </AppCard>

          <div class="create-project__actions">
            <AppButton type="button" variant="ghost" @click="$router.back()">Cancel</AppButton>
            <AppButton type="submit" variant="primary" size="lg" :loading="isLoading">
              🚀 Post Project
            </AppButton>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, reactive, ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import AppCard from '../../components/shared/AppCard.vue';
import AppInput from '../../components/shared/AppInput.vue';
import AppButton from '../../components/shared/AppButton.vue';
import AppAlert from '../../components/shared/AppAlert.vue';

const store = useStore();
const router = useRouter();

const currentUser = computed(() => store.getters['auth/currentUser']);
const clientProfile = computed(() => store.getters['client/profile']);
const isLoading = computed(() => store.getters['client/isLoading']);
const error = computed(() => store.getters['client/error']);
const success = ref(false);

const form = reactive({
  title: '',
  description: '',
  category: '',
  duration: '',
  budget: 0,
  budgetMin: 0,
  budgetMax: 0,
  deadline: '',
});

const errors = reactive({ title: '', description: '', category: '' });

const validate = () => {
  errors.title = form.title ? '' : 'Project title is required';
  errors.description = form.description ? '' : 'Description is required';
  errors.category = form.category ? '' : 'Category is required';
  return !errors.title && !errors.description && !errors.category;
};

const handleSubmit = async () => {
  if (!validate()) return;

  const clientId = clientProfile.value?.id || 0;
  const payload = {
    clientId,
    title: form.title,
    description: form.description,
    category: form.category,
    duration: form.duration,
    budget: form.budget,
    budgetMin: form.budgetMin,
    budgetMax: form.budgetMax,
    deadline: form.deadline,
    status: 'OPEN' as const,
  };

  try {
    await store.dispatch('client/createProject', {
      data: payload,
      username: currentUser.value?.email || '',
      userId: currentUser.value?.id || 0,
    });
    success.value = true;
    setTimeout(() => router.push('/dashboard/client'), 1500);
  } catch {
    // handled by store
  }
};
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.create-project {
  max-width: 860px;
  margin: 0 auto;
  @include flex-col;
  gap: $spacing-xl;

  &__header {
    @include flex-col;
    gap: $spacing-xs;
  }

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

  &__title {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
  }

  &__subtitle {
    color: $color-text-secondary;
  }

  &__form {
    @include flex-col;
    gap: $spacing-xl;
  }

  &__card-title {
    font-size: $font-size-lg;
    font-weight: $font-weight-semibold;
    color: $color-text-primary;
  }

  &__actions {
    display: flex;
    justify-content: flex-end;
    gap: $spacing-md;
  }
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: $spacing-lg;

  @include respond-to(md) {
    grid-template-columns: 1fr;
  }

  &__full {
    grid-column: 1 / -1;
  }
}
</style>
