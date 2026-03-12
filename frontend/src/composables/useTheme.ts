import { ref } from 'vue';

type Theme = 'dark' | 'light';

const THEME_KEY = 'app-theme';

function getStoredTheme(): Theme {
  const stored = localStorage.getItem(THEME_KEY);
  return stored === 'light' ? 'light' : 'dark';
}

const theme = ref<Theme>(getStoredTheme());

function applyTheme(t: Theme) {
  document.documentElement.setAttribute('data-theme', t);
  localStorage.setItem(THEME_KEY, t);
}

export function useTheme() {
  const toggleTheme = () => {
    theme.value = theme.value === 'dark' ? 'light' : 'dark';
    applyTheme(theme.value);
  };

  const initTheme = () => {
    applyTheme(theme.value);
  };

  return { theme, toggleTheme, initTheme };
}
