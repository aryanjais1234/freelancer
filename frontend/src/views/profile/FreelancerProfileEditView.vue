<template>
  <div class="page-content">
    <div class="container">
      <div class="profile-view">
        <h1 class="profile-view__title">✏️ Edit Profile</h1>

        <AppAlert v-if="saveError" variant="danger" :message="saveError" />
        <AppAlert v-if="saveSuccess" variant="success" message="Profile saved successfully! ✅" />

        <AppLoader v-if="loadingProfile" text="Loading profile..." />

        <form v-else @submit.prevent="handleSave" class="profile-view__form">
          <AppCard>
            <template #header><h3 class="profile-view__card-title">👤 Basic Info</h3></template>
            <div class="form-grid">
              <AppInput v-model="form.name" label="Full Name" required :error="errors.name" />
              <AppInput v-model="form.email" label="Email" type="email" required :error="errors.email" />
              <AppInput v-model="form.hourlyRate" label="Hourly Rate ($)" placeholder="e.g., 75" />
              <AppInput v-model="form.location" label="Location" placeholder="e.g., New York, USA" />
              <AppInput v-model="form.phoneNumber" label="Phone Number" placeholder="+1 234 567 8900" />
              <div class="form-grid__field">
                <label class="form-grid__label">Availability Status</label>
                <select v-model="form.availabilityStatus" class="form-grid__select">
                  <option value="AVAILABLE">🟢 Available</option>
                  <option value="BUSY">🔴 Busy</option>
                </select>
              </div>
              <AppInput v-model="form.profileSummary" label="Profile Summary" type="textarea" placeholder="Describe your expertise..." :rows="4" class="form-grid__full" />
            </div>
          </AppCard>

          <AppCard>
            <template #header><h3 class="profile-view__card-title">💡 Skills & Experience</h3></template>
            <div class="form-grid">
              <AppInput v-model="form.skills" label="Skills (comma separated)" placeholder="JavaScript, Vue.js, React, Node.js" class="form-grid__full" hint="Separate skills with commas" />
              <AppInput v-model="form.workExperience" label="Work Experience" type="textarea" placeholder="Describe your past work experience..." :rows="4" class="form-grid__full" />
              <AppInput v-model="form.education" label="Education" type="textarea" placeholder="Your educational background..." :rows="3" class="form-grid__full" />
              <AppInput v-model="form.certifications" label="Certifications" placeholder="AWS, Google Cloud, etc." class="form-grid__full" />
            </div>
          </AppCard>

          <AppCard>
            <template #header><h3 class="profile-view__card-title">🔗 Links & Additional</h3></template>
            <div class="form-grid">
              <AppInput v-model="form.portfolioLink" label="Portfolio URL" type="url" placeholder="https://your-portfolio.com" />
              <AppInput v-model="form.socialLinks" label="Social Links" placeholder="LinkedIn, GitHub, etc." />
              <AppInput v-model="form.languages" label="Languages" placeholder="English, Spanish, etc." />
              <AppInput v-model="form.profilePictureUrl" label="Profile Picture URL" placeholder="https://..." />
              <AppInput v-model="form.additionalInfo" label="Additional Info" type="textarea" placeholder="Anything else you'd like to share..." :rows="3" class="form-grid__full" />
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
const profile = computed(() => store.getters['freelancer/profile']);
const loadingProfile = computed(() => store.getters['freelancer/isLoading']);
const saveError = ref('');
const saveSuccess = ref(false);
const isSaving = ref(false);

const form = reactive({
  name: '', email: '', hourlyRate: '', location: '', phoneNumber: '',
  availabilityStatus: 'AVAILABLE' as 'AVAILABLE' | 'BUSY',
  profileSummary: '', skills: '', workExperience: '', education: '',
  certifications: '', portfolioLink: '', socialLinks: '', languages: '',
  profilePictureUrl: '', additionalInfo: '',
});
const errors = reactive({ name: '', email: '' });

onMounted(async () => {
  if (currentUser.value?.id) {
    try {
      const p = await store.dispatch('freelancer/fetchProfile', currentUser.value.id);
      if (p) {
        Object.assign(form, {
          name: p.name || '',
          email: p.email || '',
          hourlyRate: p.hourlyRate || '',
          location: p.location || '',
          phoneNumber: p.phoneNumber || '',
          availabilityStatus: p.availabilityStatus || 'AVAILABLE',
          profileSummary: p.profileSummary || '',
          skills: p.skills || '',
          workExperience: p.workExperience || '',
          education: p.education || '',
          certifications: p.certifications || '',
          portfolioLink: p.portfolioLink || '',
          socialLinks: p.socialLinks || '',
          languages: p.languages || '',
          profilePictureUrl: p.profilePictureUrl || '',
          additionalInfo: p.additionalInfo || '',
        });
      }
    } catch {
      form.name = currentUser.value?.fullName || '';
      form.email = currentUser.value?.email || '';
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

  const payload = { ...form, userId: currentUser.value?.id };

  try {
    if (profile.value) {
      await store.dispatch('freelancer/updateProfile', {
        userId: currentUser.value.id,
        data: payload,
      });
    } else {
      await store.dispatch('freelancer/createProfile', payload);
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
  max-width: 900px;
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

  &__field {
    @include flex-col;
    gap: $spacing-xs;
  }

  &__label {
    font-size: $font-size-sm;
    font-weight: $font-weight-medium;
    color: $color-text-secondary;
  }

  &__select {
    background: $color-surface-elevated;
    border: 1px solid $color-border;
    border-radius: $radius-md;
    color: $color-text-primary;
    padding: $spacing-sm $spacing-md;
    font-size: $font-size-base;
    cursor: pointer;
    outline: none;
    transition: border-color $transition-fast;

    &:focus { border-color: $color-primary; }
    option { background: $color-surface; }
  }
}
</style>
