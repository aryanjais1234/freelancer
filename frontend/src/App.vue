<script setup lang="ts">
import { onMounted } from 'vue';
import { useStore } from 'vuex';
import AppNavbar from './components/shared/AppNavbar.vue';
import { useTheme } from './composables/useTheme';

const store = useStore();
const { initTheme } = useTheme();

onMounted(() => {
  initTheme();
  store.dispatch('auth/restoreSession');
});
</script>

<template>
  <div id="app-root">
    <AppNavbar />
    <main>
      <RouterView v-slot="{ Component }">
        <Transition name="fade" mode="out-in">
          <component :is="Component" />
        </Transition>
      </RouterView>
    </main>
  </div>
</template>

<style lang="scss">
@use './styles/main.scss';

#app-root {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

main {
  flex: 1;
}
</style>
