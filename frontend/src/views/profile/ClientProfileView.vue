<template>
  <div class="page-content">
    <div class="container">
      <div class="profile-view">
        <h1 class="profile-view__title">👤 Client Profile</h1>

        <AppAlert v-if="saveError" variant="danger" :message="saveError" />
        <AppAlert v-if="saveSuccess" variant="success" message="Profile saved successfully! ✅" />

        <AppLoader v-if="loadingProfile" text="Loading profile..." />

        <form v-else @submit.prevent="handleSave" class="profile-view__form">
          <AppCard>
            <template #header><h3 class="profile-view__card-title">🏢 Company Information</h3></template>
            <div class="form-grid">
              <AppInput v-model="form.name" label="Full Name" placeholder="Your name" required :error="errors.name" />
              <AppInput v-model="form.email" label="Email" type="email" placeholder="your@email.com" required :error="errors.email" />
              <AppInput v-model="form.companyName" label="Company Name" placeholder="Your company" />
              <AppInput v-model="form.website" label="Website" type="url" placeholder="https://yoursite.com" />
              <AppInput v-model="form.bio" label="Bio" type="textarea" placeholder="Tell freelancers about your company..." :rows="4" class="form-grid__full" />
            </div>
          </AppCard>

          <div class="profile-view__actions">
            <AppButton type="submit" variant="primary" :loading="isSaving">
              💾 Save Profile
            </AppButton>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue';
import { useStore } from 'vuex';
import AppCard from '../../components/shared/AppCard.vue';
import AppInput from '../../components/shared/AppInput.vue';
import AppButton from '../../components/shared/AppButton.vue';
import AppLoader from '../../components/shared/AppLoader.vue';
import AppAlert from '../../components/shared/AppAlert.vue';

const store = useStore();
const currentUser = computed(() => store.getters['auth/currentUser']);
const profile = computed(() => store.getters['client/profile']);
const loadingProfile = computed(() => store.getters['client/isLoading']);
const saveError = ref('');
const saveSuccess = ref(false);
const isSaving = ref(false);

const form = reactive({ name: '', email: '', companyName: '', website: '', bio: '' });
const errors = reactive({ name: '', email: '' });

onMounted(async () => {
  if (currentUser.value?.id) {
    try {
      const p = await store.dispatch('client/fetchProfile', currentUser.value.id);
      if (p) {
        form.name = p.name || '';
        form.email = p.email || '';
        form.companyName = p.companyName || '';
        form.website = p.website || '';
        form.bio = p.bio || '';
      }
    } catch {
      if (currentUser.value) {
        form.name = currentUser.value.fullName || '';
        form.email = currentUser.value.email || '';
      }
    }
  }
});

const handleSave = async () => {
  errors.name = form.name ? '' : 'Name is required';
  errors.email = form.email ? '' : 'Email is required';
  if (errors.name || errors.email) return;

  saveError.value = '';
  saveSuccess.value = false;
  isSaving.value = true;

  try {
    if (profile.value) {
      await store.dispatch('client/updateProfile', {
        userId: currentUser.value.id,
        data: { ...form, userId: currentUser.value.id },
      });
    } else {
      await store.dispatch('client/createProfile', { ...form, userId: currentUser.value.id });
    }
    saveSuccess.value = true;
  } catch (err: any) {
    saveError.value = err.response?.data?.message || 'Failed to save profile';
  } finally {
    isSaving.value = false;
  }
};
</script>

<style lang="scss" scoped>
@use '../../styles/colors' as *;
@use '../../styles/variables' as *;
@use '../../styles/mixins' as *;

.profile-view {
  max-width: 800px;
  margin: 0 auto;
  @include flex-col;
  gap: $spacing-xl;

  &__title {
    font-size: $font-size-3xl;
    font-weight: $font-weight-bold;
    color: $color-text-primary;
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
  }
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: $spacing-lg;

  @include respond-to(md) { grid-template-columns: 1fr; }

  &__full { grid-column: 1 / -1; }
}
</style>
