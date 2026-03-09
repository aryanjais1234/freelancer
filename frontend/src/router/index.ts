import { createRouter, createWebHistory } from 'vue-router';
import store from '../store';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/auth/LoginView.vue'),
      meta: { guestOnly: true },
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/auth/RegisterView.vue'),
      meta: { guestOnly: true },
    },
    {
      path: '/dashboard/client',
      name: 'client-dashboard',
      component: () => import('../views/dashboard/ClientDashboardView.vue'),
      meta: { requiresAuth: true, role: 'CLIENT' },
    },
    {
      path: '/dashboard/freelancer',
      name: 'freelancer-dashboard',
      component: () => import('../views/dashboard/FreelancerDashboardView.vue'),
      meta: { requiresAuth: true, role: 'FREELANCER' },
    },
    {
      path: '/projects',
      name: 'projects',
      component: () => import('../views/projects/ProjectsView.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/projects/create',
      name: 'create-project',
      component: () => import('../views/projects/CreateProjectView.vue'),
      meta: { requiresAuth: true, role: 'CLIENT' },
    },
    {
      path: '/projects/:id',
      name: 'project-detail',
      component: () => import('../views/projects/ProjectDetailView.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/freelancers',
      name: 'freelancers',
      component: () => import('../views/freelancers/FreelancersView.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/freelancers/:id',
      name: 'freelancer-profile',
      component: () => import('../views/freelancers/FreelancerProfileView.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/profile/client',
      name: 'client-profile',
      component: () => import('../views/profile/ClientProfileView.vue'),
      meta: { requiresAuth: true, role: 'CLIENT' },
    },
    {
      path: '/profile/freelancer',
      name: 'freelancer-profile-edit',
      component: () => import('../views/profile/FreelancerProfileEditView.vue'),
      meta: { requiresAuth: true, role: 'FREELANCER' },
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/',
    },
  ],
});

router.beforeEach((to, _from, next) => {
  const isAuthenticated = store.getters['auth/isAuthenticated'];
  const userRole = store.getters['auth/userRole'];

  if (to.meta.requiresAuth && !isAuthenticated) {
    return next({ name: 'login' });
  }

  if (to.meta.guestOnly && isAuthenticated) {
    if (userRole === 'CLIENT') return next({ name: 'client-dashboard' });
    if (userRole === 'FREELANCER') return next({ name: 'freelancer-dashboard' });
    return next({ name: 'home' });
  }

  if (to.meta.role && isAuthenticated && userRole !== to.meta.role) {
    if (userRole === 'CLIENT') return next({ name: 'client-dashboard' });
    if (userRole === 'FREELANCER') return next({ name: 'freelancer-dashboard' });
    return next({ name: 'home' });
  }

  next();
});

export default router;
