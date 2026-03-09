import { createStore } from 'vuex';
import { state as authState } from './modules/auth/state';
import { getters as authGetters } from './modules/auth/getters';
import { mutations as authMutations } from './modules/auth/mutations';
import { actions as authActions } from './modules/auth/actions';
import { state as clientState } from './modules/client/state';
import { getters as clientGetters } from './modules/client/getters';
import { mutations as clientMutations } from './modules/client/mutations';
import { actions as clientActions } from './modules/client/actions';
import { state as freelancerState } from './modules/freelancer/state';
import { getters as freelancerGetters } from './modules/freelancer/getters';
import { mutations as freelancerMutations } from './modules/freelancer/mutations';
import { actions as freelancerActions } from './modules/freelancer/actions';
import { state as projectState } from './modules/project/state';
import { getters as projectGetters } from './modules/project/getters';
import { mutations as projectMutations } from './modules/project/mutations';
import { actions as projectActions } from './modules/project/actions';
import { state as bidState } from './modules/bid/state';
import { getters as bidGetters } from './modules/bid/getters';
import { mutations as bidMutations } from './modules/bid/mutations';
import { actions as bidActions } from './modules/bid/actions';

export default createStore({
  modules: {
    auth: {
      namespaced: true,
      state: authState,
      getters: authGetters,
      mutations: authMutations,
      actions: authActions,
    },
    client: {
      namespaced: true,
      state: clientState,
      getters: clientGetters,
      mutations: clientMutations,
      actions: clientActions,
    },
    freelancer: {
      namespaced: true,
      state: freelancerState,
      getters: freelancerGetters,
      mutations: freelancerMutations,
      actions: freelancerActions,
    },
    project: {
      namespaced: true,
      state: projectState,
      getters: projectGetters,
      mutations: projectMutations,
      actions: projectActions,
    },
    bid: {
      namespaced: true,
      state: bidState,
      getters: bidGetters,
      mutations: bidMutations,
      actions: bidActions,
    },
  },
});
